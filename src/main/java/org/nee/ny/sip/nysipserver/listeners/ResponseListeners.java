package org.nee.ny.sip.nysipserver.listeners;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.VideoPlayer;
import org.nee.ny.sip.nysipserver.event.response.InviteResponseEvent;
import org.nee.ny.sip.nysipserver.service.VideoPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * @Author: alec
 * Description:
 * @date: 13:42 2020-12-05
 */
@Component
@Slf4j
public class ResponseListeners {

    @Autowired
    private VideoPlayerService videoPlayerService;

    @EventListener
    public void responseInvite(InviteResponseEvent inviteResponseEvent) {
        inviteResponseEvent.dealResponse();
        VideoPlayer videoPlayer = inviteResponseEvent.getVideoPlayer();
        if (Objects.nonNull(videoPlayer)) {
            videoPlayerService.playingVideo(videoPlayer);
        }
    }
}
