package org.nee.ny.sip.nysipserver.domain;

import lombok.*;

/**
 * @Author: alec
 * Description:
 * @date: 19:41 2020-11-30
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Device {

    private String deviceId;

    private String transport;

    private String host;

    private Integer port;
}
