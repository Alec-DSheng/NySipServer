package org.nee.ny.sip.nysipserver.event;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageHandler;

/**
 * @Author: alec
 * Description:
 * @date: 08:47 2020-11-30
 */
@Slf4j
@MessageHandler(name = "INVITE")
public class InviteEvent extends MessageEventAbstract {

    @Override
    public void load() {
       byte[] bs =  requestEvent.getRequest().getRawContent();
       String content = new String(bs);
       log.info("invite {}", content);
    }
}