package org.nee.ny.sip.nysipserver.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description:
 * @date: 13:07 2020-11-27
 */
@ConfigurationProperties(prefix = "sip.config")
@Component
@Setter
@Getter
public class SipServerProperties {

    private String domain;

    private String host;

    private Integer port;

    private String id;

    private String mediaIp;

    private Integer mediaPort;
}