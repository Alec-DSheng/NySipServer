package org.nee.ny.sip.nysipserver.event;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageHandler;
import org.nee.ny.sip.nysipserver.event.message.MessageRequestBean;
import org.nee.ny.sip.nysipserver.utils.XmlObjectConvertUtil;

import javax.sip.message.Request;

/**
 * @Author: alec
 * Description:
 * @date: 11:53 2020-11-28
 */
@Slf4j
@MessageHandler(name = "MESSAGE")
public class MessageEvent extends MessageEventAbstract {

    @Getter
    private MessageRequestBean messageRequestBean;

    @Override
    public void load() {
        Request request = requestEvent.getRequest();
        String content = new String(request.getRawContent());
        MessageRequestBean messageRequestBean = new MessageRequestBean();
        this.messageRequestBean = (MessageRequestBean) XmlObjectConvertUtil.xmlConvertObject(content, messageRequestBean);
        log.info("接收到设备上报 message 消息, 需要进行分类处理 {}" ,this.messageRequestBean);
    }

    public void dealMessageEvent() {

    }
}
