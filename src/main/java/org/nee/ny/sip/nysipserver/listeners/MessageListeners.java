package org.nee.ny.sip.nysipserver.listeners;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.event.message.KeepLiveMessageRequest;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description:
 * @date: 16:44 2020-11-30
 */
@Component
@Slf4j
public class MessageListeners {

    @EventListener
    public void deviceRegister(KeepLiveMessageRequest keepLiveMessageRequest) {
        keepLiveMessageRequest = (KeepLiveMessageRequest) keepLiveMessageRequest.getMessageRequestAbstract();
        log.info("k 2 {}", keepLiveMessageRequest);
    }
}
