package org.nee.ny.sip.nysipserver.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.rmi.UnknownHostException;
import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 20:39 2020-11-27
 */
@Slf4j
public class IntentAddressUtilTest {

    @Test
    public void shouldReturnIpAddress () throws UnknownHostException {

       IntentAddressUtil.getLocalIp4Address().ifPresent(inet4Address -> {
           log.info("iplist {}", inet4Address.getHostAddress());
       });

    }
}
