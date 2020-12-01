package org.nee.ny.sip.nysipserver.domain;

import lombok.*;

import java.io.Serializable;

/**
 * @Author: alec
 * Description:
 * @date: 11:31 2020-11-27
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeviceInfo implements Serializable {

    private String deviceId;

    private String deviceName;

    private String manufacturer;

    private String model;

    private String firmware;

    private Integer channel;

    private String host;

    private String transport;

    private Integer port;

}
