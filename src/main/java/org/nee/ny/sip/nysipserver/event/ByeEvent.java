package org.nee.ny.sip.nysipserver.event;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageHandler;
import org.nee.ny.sip.nysipserver.event.MessageEventAbstract;

/**
 * @Author: alec
 * Description:
 * @date: 08:45 2020-11-30
 */
@Slf4j
@MessageHandler(name = "BYE")
public class ByeEvent extends MessageEventAbstract {

    @Override
    public void load() {
        log.info("接收到设备上报 ByeEvent 消息, 需要进行分类处理");
    }
}

