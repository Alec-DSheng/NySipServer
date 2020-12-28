package org.nee.ny.sip.nysipserver.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.utils.IntentAddressUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.Inet4Address;
import java.rmi.UnknownHostException;

/**
 * @Author: alec
 * Description:
 * @date: 13:07 2020-11-27
 */
@ConfigurationProperties(prefix = "sip.config")
@Component
@Setter
@Getter
@Slf4j
public class SipServerProperties {

    private String domain;

    private String host;

    private Integer port;

    private String id;

    private String mediaIp;

    private Integer mediaPort;

    private String password;

    @PostConstruct
    public void init () throws UnknownHostException {
        Inet4Address ip = IntentAddressUtil.getLocalIp4Address().orElseThrow(() -> new UnknownHostException("IP地址获取失败"));;
        this.host = ip.getHostAddress();
    }
}