package org.nee.ny.sip.nysipserver.listeners.factory;


import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.enums.EventType;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageHandler;
import org.nee.ny.sip.nysipserver.event.*;
import org.reflections.Reflections;

import javax.sip.RequestEvent;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: alec
 * Description: 终端上报事件Bean Factory 负责对 不同的RequestEvent 转换成对应的逻辑Bean
 * @date: 12:28 2020-11-27
 */
@Slf4j
public class MessageEventFactory {

    private final static String  basePackage = "org.nee.ny.sip.nysipserver.event";

    private static MessageEventFactory messageEventFactory = new MessageEventFactory();

    private static Map<String, MessageEventAbstract> messageEventMap = new ConcurrentHashMap<>();

    private MessageEventFactory(){
        Reflections reflections = new Reflections(basePackage);
        try {
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MessageHandler.class);
            for (Class<?> c : classes) {
                Object bean = c.newInstance();
                if (bean instanceof MessageEventAbstract) {
                    MessageHandler annotation = c.getAnnotation(MessageHandler.class);
                    messageEventMap.put(annotation.name(), (MessageEventAbstract)bean);
                }
            }
        } catch (Exception e) {
            log.error("扫描注解错误", e);
        }
        log.info("初始化消息处理器成功{}", messageEventMap.size());
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
