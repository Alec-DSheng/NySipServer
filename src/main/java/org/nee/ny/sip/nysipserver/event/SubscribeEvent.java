package org.nee.ny.sip.nysipserver.event;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: alec
 * Description:
 * @date: 08:47 2020-11-30
 */
@Slf4j
public class SubscribeEvent extends MessageEventAbstract {

    @Override
    public void load() {
        log.info("接收到设备上报 SubscribeEvent 消息, 需要进行分类处理");
    }
}