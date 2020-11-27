package org.nee.ny.sip.nysipserver.listeners.factory;


import org.nee.ny.sip.nysipserver.event.MessageEvent;
import org.nee.ny.sip.nysipserver.event.RegisterMessageEvent;

import javax.sip.RequestEvent;
import javax.sip.message.Request;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: alec
 * Description: 终端上报事件Bean Factory 负责对 不同的RequestEvent 转换成对应的逻辑Bean
 * @date: 12:28 2020-11-27
 */
public class MessageEventFactory {

    private static MessageEventFactory messageEventFactory = new MessageEventFactory();

    private static Map<String, MessageEvent> messageEventMap;

    private MessageEventFactory(){
        messageEventMap = new ConcurrentHashMap<>();
        messageEventMap.put("REGISTER", new RegisterMessageEvent());
    }


    public MessageEvent getMessageEvent (RequestEvent requestEvent) {
        String method = requestEvent.getRequest().getMethod();
        MessageEvent messageEvent = Optional.ofNullable(messageEventMap.get(method))
                .orElseThrow(NullPointerException::new);
        messageEvent.init(requestEvent);
        return messageEvent;
    }

    public static MessageEventFactory getInstance() {
        return messageEventFactory;
    }

}
