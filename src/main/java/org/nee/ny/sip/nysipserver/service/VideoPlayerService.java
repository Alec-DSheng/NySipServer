package org.nee.ny.sip.nysipserver.service;

import org.nee.ny.sip.nysipserver.domain.VideoPlayer;
import org.nee.ny.sip.nysipserver.domain.api.VideoInfoResponse;
import org.nee.ny.sip.nysipserver.interfaces.webhook.kit.ZlMediaKitRequest;

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
    VideoInfoResponse stop(String deviceId, String channelId);


    /**
     * 停用
     * */
    void stop(String streamCode);
    /**
     * 此时是SIP发送了播放指令至终端设备,不代表终端设备一定能将流推送到流媒体服务器
     * 将此时产生的StreamCode 与 设备序列号做关联后缓存
     * 等流媒体服务器回调流注册后表示该streamCode 有效。做播放连接缓存
     * 另外为了方便后续可分布式扩展，缓存streamCode 时 将 域写入
     * */
    void playingVideo(VideoPlayer videoPlayer);


    /**
     * 由流媒体服务器web hook 注册流产生
     * 代表此时流媒体服务器已经有可拉去的流，此时需要将流的信息与当前设备信息缓存
     * */
    void playingVideo(String streamCode);

    /**
     * 注销流
     * */
    void cancelPlaying(String streamCode);
}
