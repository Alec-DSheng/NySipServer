package org.nee.ny.sip.nysipserver;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.listeners.SipServerListeners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class NySipServerApplicationTests {


    @Autowired
    private SipServerProperties sipServerProperties;


    @Test
    void contextLoads() {
        log.info("{}", sipServerProperties.getDomain());
    }

}
