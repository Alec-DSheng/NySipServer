package org.nee.ny.sip.nysipserver.listeners.factory;


import org.nee.ny.sip.nysipserver.domain.enums.EventType;
import org.nee.ny.sip.nysipserver.event.*;

import javax.sip.RequestEvent;
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

    private static Map<String, MessageEventAbstract> messageEventMap;

    private MessageEventFactory(){
        messageEventMap = new ConcurrentHashMap<>();
        messageEventMap.put(EventType.REGISTER.name(), new RegisterEvent());
        messageEventMap.put(EventType.MEESSAGE.name(), new MessageEvent());
        messageEventMap.put(EventType.ACK.name(), new AckEvent());
        messageEventMap.put(EventType.BYE.name(), new ByeEvent());
        messageEventMap.put(EventType.INVITE.name(), new InviteEvent());
        messageEventMap.put(EventType.CANCEL.name(), new CancelEvent());
        messageEventMap.put(EventType.SUBSCRIBE.name(), new SubscribeEvent());
    }


    public MessageEventAbstract getMessageEvent (RequestEvent requestEvent) {
        String method = requestEvent.getRequest().getMethod();
        MessageEventAbstract messageEventAbstract = Optional.ofNullable(messageEventMap.get(method))
                .orElseThrow(NullPointerException::new);
        messageEventAbstract.init(requestEvent);
        return messageEventAbstract;
    }

    public static MessageEventFactory getInstance() {
        return messageEventFactory;
    }

}
