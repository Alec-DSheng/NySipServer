package org.nee.ny.sip.nysipserver;

import org.junit.jupiter.api.Test;
import org.nee.ny.sip.nysipserver.domain.DeviceInfo;
import org.nee.ny.sip.nysipserver.listeners.SipServerListeners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
class NySipServerApplicationTests {

    @Autowired
    private SipServerListeners sipServerListeners;


    @Test
    void contextLoads() {
        sipServerListeners.listeners();
    }

}
