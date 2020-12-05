package org.nee.ny.sip.nysipserver.domain;

import lombok.*;

/**
 * @Author: alec
 * Description:
 * @date: 15:48 2020-12-03
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeviceChannel {

    private String code;

    private String deviceId;

    private String name;

    private String manufacturer;

    private String model;

    private String owner;

    private String civilCode;

    private String address;

    private Integer registerWay;

    private Integer secret;

    private Integer status;

    private Integer num;
}
