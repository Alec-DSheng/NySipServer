package org.nee.ny.sip.nysipserver.listeners;

import org.nee.ny.sip.nysipserver.domain.DeviceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description:
 * @date: 12:23 2020-11-27
 */
@Component
public class SipServerListeners {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void listeners() {

        DeviceInfo deviceInfo = new DeviceInfo("001");
        applicationEventPublisher.publishEvent(deviceInfo);
    }
}
