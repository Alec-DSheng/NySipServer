package org.nee.ny.sip.nysipserver.listeners.factory;


import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageHandler;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageRequest;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageResponse;
import org.nee.ny.sip.nysipserver.event.*;
import org.nee.ny.sip.nysipserver.event.message.*;
import org.reflections.Reflections;

import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
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

    private final static String  basePackage = "org.nee.ny.sip.nysipserver.event.*";

    private static MessageEventFactory messageEventFactory = new MessageEventFactory();

    private static final Map<String, MessageEventAbstract> messageEventMap = new ConcurrentHashMap<>();

    private static final Map<String, MessageRequestAbstract> messageRequestMap = new ConcurrentHashMap<>();

    private static final Map<String, MessageResponseAbstract> messageResponseMap = new ConcurrentHashMap<>();


    private MessageEventFactory(){

    }
    static {
        Reflections reflections = new Reflections(basePackage);
        try {
            initMessageHandler(reflections);
            initMessageRequest(reflections);
            initMessageResponse(reflections);
        } catch (Exception e) {
            log.error("扫描注解错误", e);
        }
        log.info("初始化消息处理器成功{}", messageEventMap.size());
    }

    private static void initMessageHandler(Reflections reflections) throws IllegalAccessException, InstantiationException {
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MessageHandler.class);
        for (Class<?> c : classes) {
            Object bean = c.newInstance();
            if (bean instanceof MessageEventAbstract) {
                MessageHandler annotation = c.getAnnotation(MessageHandler.class);
                messageEventMap.put(annotation.name(), (MessageEventAbstract)bean);
            }
        }
    }

    private static void initMessageResponse(Reflections reflections) throws IllegalAccessException, InstantiationException {
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MessageResponse.class);
        for (Class<?> c : classes) {
            Object bean = c.newInstance();
            if (bean instanceof MessageResponseAbstract) {
                MessageResponse annotation = c.getAnnotation(MessageResponse.class);
                messageResponseMap.put(annotation.name(), (MessageResponseAbstract)bean);
            }
        }
    }

    private static void initMessageRequest(Reflections reflections) throws
            IllegalAccessException, InstantiationException {

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MessageRequest.class);
        for (Class<?> c : classes) {
            Object bean = c.newInstance();
            if (bean instanceof MessageRequestAbstract) {
                MessageRequest annotation = c.getAnnotation(MessageRequest.class);
                messageRequestMap.put(annotation.name(), (MessageRequestAbstract)bean);
            }
        }
        log.info("message {}", messageRequestMap);
    }


    public MessageEventAbstract getMessageEvent (RequestEvent requestEvent) {
        String method = requestEvent.getRequest().getMethod();
        MessageEventAbstract messageEventAbstract = Optional.ofNullable(messageEventMap.get(method))
                .orElseThrow(NullPointerException::new);

        try {
            messageEventAbstract.init(requestEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messageEventAbstract;
    }

    public MessageRequestAbstract getMessageRequest (String cmdType, RequestEvent requestEvent) {
        MessageRequestAbstract requestAbstract = Optional.ofNullable(messageRequestMap.get(cmdType))
                .orElseThrow(NullPointerException::new);
        requestAbstract.init(requestEvent);
        return requestAbstract;
    }


    public MessageResponseAbstract getMessageResponse (String method, ResponseEvent responseEvent) {
        MessageResponseAbstract responseAbstract = Optional.ofNullable(messageResponseMap.get(method))
                .orElseThrow(NullPointerException::new);
        responseAbstract.init(responseEvent);
        return responseAbstract;
    }

    public static MessageEventFactory getInstance() {
        return messageEventFactory;
    }

}
