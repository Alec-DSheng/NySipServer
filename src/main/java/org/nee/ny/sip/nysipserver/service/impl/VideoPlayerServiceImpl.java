package org.nee.ny.sip.nysipserver.service.impl;

import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.domain.Device;
import org.nee.ny.sip.nysipserver.domain.VideoPlayer;
import org.nee.ny.sip.nysipserver.domain.api.VideoInfoResponse;
import org.nee.ny.sip.nysipserver.model.DeviceCacheOperatorModel;
import org.nee.ny.sip.nysipserver.service.VideoPlayerService;
import org.nee.ny.sip.nysipserver.transaction.command.Invite.VideoPlayCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @Author: alec
 * Description:
 * @date: 09:36 2020-12-05
 */
@Service
public class VideoPlayerServiceImpl implements VideoPlayerService {

    @Autowired
    private VideoPlayCommand videoPlayCommand;

    @Autowired
    private DeviceCacheOperatorModel deviceCacheOperatorModel;

    @Autowired
    private SipServerProperties sipServerProperties;

    @Override
    public VideoInfoResponse player(String deviceId, String channelId) {
        /**
         * 播放逻辑
         * 首先从redis中获取到device 的相关配置
         * */
       Device device = Optional.ofNullable(deviceCacheOperatorModel.getDevice(deviceId)).orElseThrow(() ->
                new NullPointerException("未发现相关设备,设备号 " + deviceId));

       String streamCode = deviceCacheOperatorModel.getStreamCode(deviceId, channelId);
       if (!StringUtils.hasLength(streamCode)) {
           streamCode = videoPlayCommand.sendCommand(device, channelId);
       }
       return new VideoInfoResponse(sipServerProperties.getMediaIp(), streamCode);
    }

    @Override
    public void stop() {

    }

    @Override
    public void playingVideo(VideoPlayer videoPlayer) {
        deviceCacheOperatorModel.cacheStreamCode(videoPlayer.getDeviceId(),
                videoPlayer.getChannelId(), videoPlayer.getStreamCode());

        //发送kafka消息,更新播放码
    }
}
