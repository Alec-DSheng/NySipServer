package org.nee.ny.sip.nysipserver.domain;

import lombok.*;

/**
 * @Author: alec
 * Description:
 * @date: 16:41 2020-12-05
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VideoPlayer {

    private String channelId;

    private String deviceId;

    private String streamCode;
}
