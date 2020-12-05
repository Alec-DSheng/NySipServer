package org.nee.ny.sip.nysipserver.interfaces.controller;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.api.VideoInfoResponse;
import org.nee.ny.sip.nysipserver.service.VideoPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @Author: alec
 * Description:
 * @date: 09:53 2020-12-05
 */
@RestController
@RequestMapping(value = "video")
@Slf4j
public class VideoController {

    private final VideoPlayerService videoPlayerService;

    public VideoController(VideoPlayerService videoPlayerService) {
        this.videoPlayerService = videoPlayerService;
    }

    @GetMapping("/{deviceId}/{channelId}")
    public Mono<VideoInfoResponse> player(@PathVariable(value = "deviceId") String deviceId,
                                          @PathVariable(value = "channelId") String channelId) {

        return Mono.just(videoPlayerService.player(deviceId, channelId));
    }
}
