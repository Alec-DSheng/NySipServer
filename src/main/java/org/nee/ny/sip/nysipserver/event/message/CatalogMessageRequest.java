package org.nee.ny.sip.nysipserver.event.message;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageReqeust;

/**
 * @Author: alec
 * Description:
 * @date: 11:34 2020-11-30
 */
@MessageReqeust(name = "Catalog")
@Slf4j
public class CatalogMessageRequest  extends MessageRequestAbstract   {

    @Override
    public void load() {
        super.load();
        String content = this.content;
        log.info("接收到 catalog {}", content);
    }
}
