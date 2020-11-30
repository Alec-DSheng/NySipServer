package org.nee.ny.sip.nysipserver.listeners;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.event.*;
import org.nee.ny.sip.nysipserver.model.DeviceCacheOperatorModel;
import org.nee.ny.sip.nysipserver.transaction.response.SipRegisterResponse;
import org.nee.ny.sip.nysipserver.transaction.response.impl.SipMessageResponseHandler;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sip.RequestEvent;
import javax.sip.message.Response;

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

    public DeviceListeners(SipRegisterResponse sipRegisterResponse, SipServerProperties sipServerProperties,
                           SipMessageResponseHandler sipMessageResponseHandler, DeviceCacheOperatorModel deviceCacheOperatorModel) {
        this.sipRegisterResponse = sipRegisterResponse;
        this.sipServerProperties = sipServerProperties;
        this.sipMessageResponseHandler = sipMessageResponseHandler;
        this.deviceCacheOperatorModel = deviceCacheOperatorModel;
    }

    @EventListener
    public void deviceRegister(RegisterEvent registerEvent) {
        RequestEvent requestEvent =  registerEvent.getRequestEvent();
        Response response;
        if (registerEvent.getFirstAuthorization() ||
                !registerEvent.validateAuthorization(sipServerProperties.getPassword())) {
            response = sipRegisterResponse.responseAuthenticationFailure(requestEvent.getRequest());
            sendResponse(requestEvent, response);
            return;
        }
        response = sipRegisterResponse.responseAuthenticationSuccess(requestEvent.getRequest());
        //取得设备信息后注册
        log.info("设备ID {}, {}, {}", registerEvent.getDeviceId(), registerEvent.getHost(), registerEvent.getPort());
        //从Redis中获取已注册设备,如果不存在则表示第一次注册,并发起查询设备信息指令
        boolean isFirst = deviceCacheOperatorModel.isFirstRegister(registerEvent);
        if (isFirst) {
            log.info("第一次注册,需要下发查询设备信息指令");
        }
        sendResponse(requestEvent, response);
    }


    @EventListener
    public void deviceMessage(MessageEvent messageEvent) {
        log.info("监听到message");
    }

    @EventListener
    public void ackMessage(AckEvent ackEvent) {
        log.info("监听到ackEvent");
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
