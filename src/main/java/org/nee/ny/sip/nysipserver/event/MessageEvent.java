package org.nee.ny.sip.nysipserver.event;


import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageHandler;

/**
 * @Author: alec
 * Description:
 * @date: 11:53 2020-11-28
 */
@Slf4j
@MessageHandler(name = "MESSAGE")
public class MessageEvent extends MessageEventAbstract {

    @Override
    public void load() {
        log.info("接收到设备上报 message 消息, 需要进行分类处理");
    }
}
