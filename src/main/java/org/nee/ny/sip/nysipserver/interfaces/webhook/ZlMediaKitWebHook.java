package org.nee.ny.sip.nysipserver.interfaces.webhook;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: alec
 * Description: 监听zlMedia回调
 * @date: 22:29 2020-12-08
 */
@RestController
@RequestMapping(value = "index/hook")
public class ZlMediaKitWebHook {




    /**
     * 录制mp4视频通知事件
     * */
    @PostMapping(value = "on_record_mp4")
    public void onRecordMp4 () {

    }

    /**
     * rtsp / rtmp 流注册或注销时触发
     * */
    @PostMapping(value = "on_stream_changed")
    public void onStreamChanged() {

    }

    /**
     * 流无人观看触发
     * */
    @PostMapping(value = "on_stream_none_reader")
    public void onStreamNoneReader() {

    }

    /**
     * 流未找到事件，实现按需拉流
     * */
    @PostMapping(value = "on_stream_not_found")
    public void onStreamNotFound() {

    }





}
