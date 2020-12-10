package org.nee.ny.sip.nysipserver.interfaces.webhook;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.interfaces.webhook.kit.ZlMediaKitRequest;
import org.nee.ny.sip.nysipserver.interfaces.webhook.kit.ZlMediaKitResponse;
import org.nee.ny.sip.nysipserver.utils.IntentAddressUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

/**
 * @Author: alec
 * Description: 监听zlMedia回调
 * @date: 22:29 2020-12-08
 */
@RestController
@RequestMapping(value = "index/hook")
@Slf4j
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
    public void onStreamChanged(@RequestBody JSONObject jsonObject) {
        log.info("监听到流注册或注销时触发 {}", jsonObject);
    }


    /**
     * 流无人观看触发
     * */
    @PostMapping(value = "on_stream_none_reader")
    public ZlMediaKitResponse onStreamNoneReader(@RequestBody JSONObject jsonObject) {
        log.info("监听到无人观看事件 {}", jsonObject);

        return ZlMediaKitResponse.reposeNoReader(true);
    }

    /**
     * 流未找到事件，实现按需拉流
     * */
    @PostMapping(value = "on_stream_not_found")
    public ZlMediaKitResponse onStreamNotFound() {
        log.info("流未发现事件,做拉流处理");
        return ZlMediaKitResponse.responsePublishSuccess();
    }


    /**
     * 推流鉴权
     * */
    @PostMapping(value = "on_publish")
    public ZlMediaKitResponse onPublish(@RequestBody ZlMediaKitRequest zlMediaKitRequest) {
        log.info("on_publish {}", zlMediaKitRequest);
        return ZlMediaKitResponse.responsePublishSuccess();
    }

    /**
     * 服务器注册
     * 记录服务器上线日志
     * 存储流媒体服务，做内部负载
     * */
    @PostMapping(value = "on_server_started", produces = "application/json;charset=UTF-8")
    public ZlMediaKitResponse onServerStarted(@RequestBody Map<String, Object> zlMediaKitRequest) {
        log.info("on_publish {}", zlMediaKitRequest);
        return ZlMediaKitResponse.responseSuccess();
    }
}
