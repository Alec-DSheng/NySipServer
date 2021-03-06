package org.nee.ny.sip.nysipserver.interfaces.webhook;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.configuration.RetrieveClientWebFilter;
import org.nee.ny.sip.nysipserver.domain.ZlMediaKitServer;
import org.nee.ny.sip.nysipserver.interfaces.webhook.kit.ZlMediaKitRequest;
import org.nee.ny.sip.nysipserver.interfaces.webhook.kit.ZlMediaKitResponse;
import org.nee.ny.sip.nysipserver.service.VideoPlayerService;
import org.nee.ny.sip.nysipserver.service.ZLMediaKitService;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.Optional;

/**
 * @Author: alec
 * Description: 监听zlMedia回调
 * @date: 22:29 2020-12-08
 */
@RestController
@RequestMapping(value = "index/hook")
@Slf4j
public class ZlMediaKitWebHook {

    private final VideoPlayerService videoPlayerService;

    private final ZLMediaKitService zlMediaKitService;

    public ZlMediaKitWebHook(VideoPlayerService videoPlayerService, ZLMediaKitService zlMediaKitService) {
        this.videoPlayerService = videoPlayerService;
        this.zlMediaKitService = zlMediaKitService;
    }


    /**
     * 录制mp4视频通知事件
     * */
    @PostMapping(value = "on_record_mp4")
    public void onRecordMp4 () {

    }

    /**
     * rtsp / rtmp 流注册或注销时触发
     * 流注册,注销用户缓存和清除在推流的sscode
     * 当发生流注册时此时缓存流的相关数据。
     * */
    @PostMapping(value = "on_stream_changed")
    public ZlMediaKitResponse onStreamChanged(@RequestBody ZlMediaKitRequest zlMediaKitRequest) {
        if (!zlMediaKitRequest.isRtp()) {
            return ZlMediaKitResponse.responseSuccess();
        }
        log.info("监听到流触发 {} - {}", zlMediaKitRequest.isRegist() ? "注册" : "注销", zlMediaKitRequest);
        String streamCode = new DecimalFormat("0000000000").format(Integer.parseInt(zlMediaKitRequest.getStream(),
                16));
        if (zlMediaKitRequest.isRegist()) {
            log.info("处理流注册回调逻辑");
            videoPlayerService.playingVideo(streamCode);
        }
        return ZlMediaKitResponse.responseSuccess();
    }


    /**
     * 流无人观看触发
     * */
    @PostMapping(value = "on_stream_none_reader")
    public ZlMediaKitResponse onStreamNoneReader(@RequestBody ZlMediaKitRequest zlMediaKitRequest) {
        log.info("监听到无人观看事件 {}", zlMediaKitRequest);
        String streamCode = new DecimalFormat("0000000000").format(Integer.parseInt(zlMediaKitRequest.getStream(),
                16));
        videoPlayerService.stop(streamCode);
        return ZlMediaKitResponse.reposeNoReader(true);
    }

    /**
     * 流未找到事件，实现按需拉流
     * */
    @PostMapping(value = "on_stream_not_found")
    public ZlMediaKitResponse onStreamNotFound(@RequestBody ZlMediaKitRequest zlMediaKitRequest) {
        log.info("流未发现事件,做拉流处理 {}", zlMediaKitRequest);

        return ZlMediaKitResponse.responsePublishSuccess();
    }

    /**
     * 播放器权鉴
     * */
    @PostMapping(value = "on_play")
    public ZlMediaKitResponse onPlay(@RequestBody ZlMediaKitRequest zlMediaKitRequest) {
        log.info("on_publish {}", zlMediaKitRequest);
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
    @PostMapping(value = "on_server_started")
    public ZlMediaKitResponse onServerStarted(@RequestBody String mediaConfig, ServerHttpRequest httpRequest) {
        Optional.ofNullable(JSONObject.parseObject(mediaConfig)).ifPresent(jsonObject -> {
            String ip = httpRequest.getHeaders().getFirst(RetrieveClientWebFilter.IP_HEADER);
            ZlMediaKitServer server = ZlMediaKitServer.builder().mediaServerId(jsonObject.getString("mediaServerId"))
                    .secret(jsonObject.getString("api.secret"))
                    .ffmpegCmd(jsonObject.getString("ffmpeg.cmd"))
                    .httpCharSet(jsonObject.getString("http.charSet"))
                    .httpPort(jsonObject.getString("http.port"))
                    .httpSslPort(jsonObject.getString("http.sslport"))
                    .rtmpPort(jsonObject.getString("rtmp.port"))
                    .rtpProxyPort(jsonObject.getString("rtp_proxy.port"))
                    .rtspPort(jsonObject.getString("rtsp.port"))
                    .rtmpSslPort(jsonObject.getString("rtmp.sslport"))
                    .ip(ip).registerTime(System.currentTimeMillis()).build();
            log.info("流媒体服务器实例 {}", server);
            zlMediaKitService.register(server);
        });
        return ZlMediaKitResponse.responseSuccess();
    }
}
