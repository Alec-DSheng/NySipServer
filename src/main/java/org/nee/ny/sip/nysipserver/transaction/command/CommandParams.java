package org.nee.ny.sip.nysipserver.transaction.command;

import lombok.*;
import org.nee.ny.sip.nysipserver.domain.Device;

/**
 * @Author: alec
 * Description: 执行命令请求参数
 * @date: 10:09 2020-12-01
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public  class CommandParams {

    @Getter
    @Setter
    private Device device;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private String from;

    @Getter
    @Setter
    private String to;

    @Getter
    @Setter
    private String via;

}
