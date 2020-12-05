package org.nee.ny.sip.nysipserver.service;

import org.nee.ny.sip.nysipserver.domain.api.VideoInfoResponse;

/**
 * @Author: alec
 * Description:
 * @date: 09:35 2020-12-05
 */
public interface VideoPlayerService {

    /**
     * 播放
     * */
    VideoInfoResponse player(String deviceId, String channelId);

    /**
     * 停用
     * */
    void stop();
}
