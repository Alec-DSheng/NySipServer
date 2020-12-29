package org.nee.ny.sip.nysipserver.domain;

import lombok.*;

/**
 * @Author: alec
 * Description: 设备上下线
 * @date: 14:54 2020-12-21
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class DeviceLineInfo {

    private String deviceId;

    private Integer lineStatus;
}
