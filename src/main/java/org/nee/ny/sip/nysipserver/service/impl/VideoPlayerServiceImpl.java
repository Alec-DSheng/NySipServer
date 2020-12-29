package org.nee.ny.sip.nysipserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.domain.Device;
import org.nee.ny.sip.nysipserver.domain.VideoPlayer;
import org.nee.ny.sip.nysipserver.domain.api.VideoInfoResponse;
import org.nee.ny.sip.nysipserver.model.DeviceCacheOperatorModel;
import org.nee.ny.sip.nysipserver.service.VideoPlayerService;
import org.nee.ny.sip.nysipserver.transaction.command.Invite.VideoPlayCommand;
import org.nee.ny.sip.nysipserver.transaction.session.TransactionSessionManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @Author: alec
 * Description:
 * @date: 09:36 2020-12-05
 */
@Service
@Slf4j
public class VideoPlayerServiceImpl implements VideoPlayerService {

    private final VideoPlayCommand videoPlayCommand;

    private final DeviceCacheOperatorModel deviceCacheOperatorModel;

    private final SipServerProperties sipServerProperties;

    private final TransactionSessionManager transactionSessionManager;

    public VideoPlayerServiceImpl(VideoPlayCommand videoPlayCommand, DeviceCacheOperatorModel deviceCacheOperatorModel,
                                  SipServerProperties sipServerProperties, TransactionSessionManager transactionSessionManager) {
        this.videoPlayCommand = videoPlayCommand;
        this.deviceCacheOperatorModel = deviceCacheOperatorModel;
        this.sipServerProperties = sipServerProperties;
        this.transactionSessionManager = transactionSessionManager;
    }

    @Override
    public VideoInfoResponse player(String deviceId, String channelId) {
       Device device = Optional.ofNullable(deviceCacheOperatorModel.getDevice(deviceId)).orElseThrow(() ->
                new NullPointerException("未发现相关设备,设备号 " + deviceId));
       String streamCode = deviceCacheOperatorModel.getStreamCode(deviceId, channelId);
       if (!StringUtils.hasLength(streamCode)) {
           streamCode = sendPlayer(device, channelId);
           return new VideoInfoResponse(sipServerProperties.getMediaIp(), streamCode);
       }
       //直接返回，由客户端重试拉流，触发流未找到是发起按需拉流操作
       return new VideoInfoResponse(sipServerProperties.getMediaIp(), streamCode);
    }

    private String sendPlayer(Device device, String channelId) {
       String streamCode;
       synchronized (this) {
           streamCode = videoPlayCommand.sendCommand(device, channelId);
       }
       return streamCode;
    }

    @Override
    public VideoInfoResponse stop(String deviceId, String channelId) {
        String streamCode = deviceCacheOperatorModel.getStreamCode(deviceId, channelId);
        if (StringUtils.hasLength(streamCode)) {
            videoPlayCommand.stopPlayer(streamCode);
        }
        return new VideoInfoResponse(sipServerProperties.getMediaIp(), streamCode);
    }

    @Override
    public void stop(String streamCode) {
        videoPlayCommand.stopPlayer(streamCode);
        cancelPlaying(streamCode);
    }

    /**
     * 此时是SIP发送了播放指令至终端设备,不代表终端设备一定能将流推送到流媒体服务器
     * 将此时产生的StreamCode 与 设备序列号做关联后缓存
     * 等流媒体服务器回调流注册后表示该streamCode 有效。做播放连接缓存
     * 另外为了方便后续可分布式扩展，缓存streamCode 时 将 域写入
     * */
    @Override
    public void playingVideo(VideoPlayer videoPlayer) {
        deviceCacheOperatorModel.cacheDeviceAndChannel(videoPlayer.getDeviceId(),
                videoPlayer.getChannelId(), videoPlayer.getStreamCode());
    }

    /**
     * 由流媒体服务器web hook 注册流产生
     * 代表此时流媒体服务器已经有可拉去的流，此时需要将流的信息与当前设备信息缓存
     * */
    @Override
    public void playingVideo(String streamCode) {
       //检查是否已缓存好数据
       String deviceKey = deviceCacheOperatorModel.getDeviceAndChannel(streamCode);
       if (!StringUtils.hasLength(deviceKey)) {
           //当前streamCode 未对应具体设备逻辑。发送停止推流
           log.info("当前stream {} 无用户浏览, 需要向终端发送停止推流",streamCode);
           this.stop(streamCode);
           return;
       }
       //此时真正缓存StreamCode
        deviceCacheOperatorModel.cacheStreamCode(deviceKey, streamCode);
    }

    @Override
    public void cancelPlaying(String streamCode) {
        transactionSessionManager.destroy(streamCode);
        String deviceKey = deviceCacheOperatorModel.getDeviceAndChannel(streamCode);
        if (StringUtils.hasLength(deviceKey)) {
            deviceCacheOperatorModel.removeStreamCode(deviceKey);
            deviceCacheOperatorModel.removeDeviceAndChannel(streamCode);
        }
    }
}
