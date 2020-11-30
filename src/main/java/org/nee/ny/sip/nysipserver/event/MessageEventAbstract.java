package org.nee.ny.sip.nysipserver.event;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.transaction.response.MessageResponseHandler;
import org.nee.ny.sip.nysipserver.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import javax.sip.RequestEvent;
import javax.sip.message.MessageFactory;
import javax.sip.message.Response;
import javax.swing.*;
import java.text.ParseException;

/**
 * @Author: alec
 * Description:
 * 定义消息事件
 * @date: 14:07 2020-11-27
 */
@Slf4j
public abstract class MessageEventAbstract {

    @Getter
    public RequestEvent requestEvent;


    private MessageResponseHandler messageResponseHandler;

    private MessageFactory messageFactory;

    public abstract void load();


    public void init (RequestEvent requestEvent) {
        messageFactory = SpringUtil.getBean(MessageFactory.class);
        messageResponseHandler = SpringUtil.getBean(MessageResponseHandler.class);
        this.requestEvent = requestEvent;
        this.load();
    }

    void sendAck(){
        try {
            Response response = messageFactory.createResponse(Response.OK, requestEvent.getRequest());
            messageResponseHandler.sendResponse(requestEvent, response);
        } catch (ParseException e) {
            log.error("ACK 恢复错误", e);
        }
    }
}
