package org.nee.ny.sip.nysipserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.domain.Device;
import org.nee.ny.sip.nysipserver.domain.VideoPlayer;
import org.nee.ny.sip.nysipserver.domain.api.VideoInfoResponse;
import org.nee.ny.sip.nysipserver.model.DeviceCacheOperatorModel;
import org.nee.ny.sip.nysipserver.service.VideoPlayerService;
import org.nee.ny.sip.nysipserver.transaction.command.Invite.VideoPlayCommand;
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

    public VideoPlayerServiceImpl(VideoPlayCommand videoPlayCommand, DeviceCacheOperatorModel deviceCacheOperatorModel, SipServerProperties sipServerProperties) {
        this.videoPlayCommand = videoPlayCommand;
        this.deviceCacheOperatorModel = deviceCacheOperatorModel;
        this.sipServerProperties = sipServerProperties;
    }

    @Override
    public VideoInfoResponse player(String deviceId, String channelId) {

       Device device = Optional.ofNullable(deviceCacheOperatorModel.getDevice(deviceId)).orElseThrow(() ->
                new NullPointerException("未发现相关设备,设备号 " + deviceId));

       String streamCode = deviceCacheOperatorModel.getStreamCode(deviceId, channelId);
       if (!StringUtils.hasLength(streamCode)) {
           streamCode =  videoPlayCommand.sendCommand(device, channelId);
       }
       return new VideoInfoResponse(sipServerProperties.getMediaIp(), streamCode);
    }

    @Override
    public String stop(String deviceId, String channelId) {
        String streamCode = deviceCacheOperatorModel.getStreamCode(deviceId, channelId);
        if (!StringUtils.hasLength(streamCode)) {
            videoPlayCommand.stopPlayer(streamCode);
        }
        return streamCode;
    }

    @Override
    public void playingVideo(VideoPlayer videoPlayer) {
        deviceCacheOperatorModel.cacheStreamCode(videoPlayer.getDeviceId(),
                videoPlayer.getChannelId(), videoPlayer.getStreamCode());
        log.info("播放参数 {}", videoPlayer);
    }
}
