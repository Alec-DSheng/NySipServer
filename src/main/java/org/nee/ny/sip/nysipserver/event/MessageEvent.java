package org.nee.ny.sip.nysipserver.event;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageHandler;
import org.nee.ny.sip.nysipserver.event.message.MessageRequestAbstract;
import org.nee.ny.sip.nysipserver.listeners.factory.MessageEventFactory;
import org.nee.ny.sip.nysipserver.utils.XmlObjectConvertUtil;

/**
 * @Author: alec
 * Description:
 * @date: 11:53 2020-11-28
 */
@Slf4j
@MessageHandler(name = "MESSAGE")
public class MessageEvent extends MessageEventAbstract {

    private final static String CMD_FIELD = "CmdType";

    @Override
    public void load() {
        try {
            Element element = XmlObjectConvertUtil.getRootElement(requestEvent.getRequest().getRawContent());
            String cmdType = XmlObjectConvertUtil.getText(element,CMD_FIELD);
            log.info("Message 监听到消息,消息类型 {}", cmdType);
            MessageRequestAbstract messageRequest = MessageEventFactory.getInstance().getMessageRequest(cmdType, requestEvent);
//            publisher.publishEvent(messageRequest);
//            sendAck();
        } catch (DocumentException e) {
            log.error("解析消息错误", e);
        }
    }

}
