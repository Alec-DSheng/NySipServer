package org.nee.ny.sip.nysipserver.service;

/**
 * @Author: alec
 * Description:
 * @date: 09:35 2020-12-05
 */
public interface VideoPlayerService {

    /**
     * 播放
     * */
    String player(String deviceId, String channelId);

    /**
     * 停用
     * */
    void stop();
}
