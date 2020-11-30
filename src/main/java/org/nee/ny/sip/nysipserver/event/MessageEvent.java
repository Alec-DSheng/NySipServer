package org.nee.ny.sip.nysipserver.event;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageHandler;
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

    public String cmdType;

    @Override
    public void load() {
        try {
            Element element = XmlObjectConvertUtil.getRootElement(requestEvent.getRequest().getRawContent());
            this.cmdType = XmlObjectConvertUtil.getText(element,CMD_FIELD);
            log.info("Message 监听到消息,消息类型 {}", cmdType);
        } catch (DocumentException e) {
            log.error("解析消息错误", e);
        }
    }

}
