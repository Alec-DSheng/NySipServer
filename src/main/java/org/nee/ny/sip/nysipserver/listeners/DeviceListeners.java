package org.nee.ny.sip.nysipserver.listeners;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.event.*;
import org.nee.ny.sip.nysipserver.event.message.MessageRequestAbstract;
import org.nee.ny.sip.nysipserver.listeners.factory.MessageEventFactory;
import org.nee.ny.sip.nysipserver.model.DeviceCacheOperatorModel;
import org.nee.ny.sip.nysipserver.service.DeviceService;
import org.nee.ny.sip.nysipserver.transaction.response.SipRegisterResponse;
import org.nee.ny.sip.nysipserver.transaction.response.impl.SipMessageResponseHandler;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sip.InvalidArgumentException;
import javax.sip.RequestEvent;
import javax.sip.SipException;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.text.ParseException;

/**
 * @Author: alec
 * Description: 定义设备监听事件
 * @date: 12:06 2020-11-28
 */
@Component
@Slf4j
public class DeviceListeners {

    private final SipRegisterResponse sipRegisterResponse;

    private final SipServerProperties sipServerProperties;

    private final SipMessageResponseHandler sipMessageResponseHandler;

    private final DeviceCacheOperatorModel deviceCacheOperatorModel;

    private final ApplicationEventPublisher publisher;

    private final MessageFactory messageFactory;

    private final DeviceService deviceService;

    public DeviceListeners(SipRegisterResponse sipRegisterResponse, SipServerProperties sipServerProperties,
                           SipMessageResponseHandler sipMessageResponseHandler, DeviceCacheOperatorModel deviceCacheOperatorModel,
                           ApplicationEventPublisher publisher, MessageFactory messageFactory, DeviceService deviceService) {
        this.sipRegisterResponse = sipRegisterResponse;
        this.sipServerProperties = sipServerProperties;
        this.sipMessageResponseHandler = sipMessageResponseHandler;
        this.deviceCacheOperatorModel = deviceCacheOperatorModel;
        this.publisher = publisher;
        this.messageFactory = messageFactory;
        this.deviceService = deviceService;
    }

    @EventListener
    public void deviceRegister(RegisterEvent registerEvent) {
        RequestEvent requestEvent =  registerEvent.getRequestEvent();
        Response response;
        if (!registerEvent.validateAuthorization(sipServerProperties.getPassword())) {
            response = sipRegisterResponse.responseAuthenticationFailure(requestEvent.getRequest());
            sendResponse(requestEvent, response);
            return;
        }
        response = sipRegisterResponse.responseAuthenticationSuccess(requestEvent.getRequest());
        log.info("设备ID {}, {}, {}", registerEvent.getDeviceId(), registerEvent.getHost(), registerEvent.getPort());
        sendResponse(requestEvent, response);
        boolean isFirst = deviceCacheOperatorModel.isFirstRegister(registerEvent);
        if (registerEvent.getDestroy()) {
            log.info("设备注销 {}", registerEvent.getDeviceId());
            deviceService.dealDeviceDestroy(registerEvent.getDeviceId());
            return;
        }
        if (isFirst) {
            log.info("设备初次注册 {}", registerEvent.getDeviceId());
            deviceService.dealDeviceRegister(registerEvent);
            return;
        }
        log.info("设备重新注册产生心跳 {}", registerEvent.getDeviceId());
        deviceService.dealDeviceOnline(registerEvent.getDeviceId());
    }


    @EventListener
    public void deviceMessage(MessageEvent messageEvent) {
        RequestEvent requestEvent = messageEvent.getRequestEvent();
        Response response;
        try {
            MessageRequestAbstract messageRequestAbstract = MessageEventFactory.getInstance()
                    .getMessageRequest(messageEvent.cmdType, requestEvent);
            publisher.publishEvent(messageRequestAbstract);
            response = messageFactory.createResponse(Response.OK, requestEvent.getRequest());
            sendResponse(requestEvent, response);
        } catch (ParseException e) {
            log.error("响应response 错误", e);
        }
    }

    @EventListener
    public void ackMessage(AckEvent ackEvent) {
        try {
            Request ackRequest = ackEvent.getDialog().createAck(ackEvent.getSeqNumber());
            ackEvent.getDialog().sendAck(ackRequest);
        } catch (InvalidArgumentException | SipException e) {
            log.error("ack 异常", e);
        }
    }

    @EventListener
    public void byeMessage(ByeEvent byeEvent) {
        log.info("监听到byeEvent");
    }

    @EventListener
    public void cancelEvent(CancelEvent cancelEvent) {
        log.info("监听到cancelEvent");
    }

    @EventListener
    public void inviteEvent(InviteEvent inviteEvent) {
        log.info("监听到inviteEvent");
    }

    @EventListener
    public void subscribeEvent(SubscribeEvent subscribeEvent) {
        log.info("subscribeEvent");
    }

    private void sendResponse(RequestEvent requestEvent, Response response) {
        sipMessageResponseHandler.sendResponse(requestEvent, response);
    }
}
