package org.nee.ny.sip.nysipserver.interfaces.controller;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.api.VideoInfoResponse;
import org.nee.ny.sip.nysipserver.service.VideoPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @Autowired
    private VideoPlayerService videoPlayerService;

    @GetMapping
    public Mono<VideoInfoResponse> player(@RequestParam(value = "deviceId") String deviceId,
                                          @RequestParam(value = "channelId") String channelId) {

        return Mono.just(videoPlayerService.player(deviceId, channelId));
    }
}
