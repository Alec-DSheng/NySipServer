package org.nee.ny.sip.nysipserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.event.RegisterEvent;
import org.nee.ny.sip.nysipserver.model.DeviceCacheOperatorModel;
import org.nee.ny.sip.nysipserver.service.DeviceService;
import org.nee.ny.sip.nysipserver.transaction.response.SipRegisterResponse;
import org.nee.ny.sip.nysipserver.transaction.response.impl.SipMessageResponseHandler;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.sip.RequestEvent;
import javax.sip.message.Response;


/**
 * @Author: alec
 * Description:
 * @date: 11:29 2020-11-27
 */
@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    private final SipRegisterResponse sipRegisterResponse;

    private final SipServerProperties sipServerProperties;

    private final SipMessageResponseHandler sipMessageResponseHandler;

    private final DeviceCacheOperatorModel deviceCacheOperatorModel;

    public DeviceServiceImpl(SipRegisterResponse sipRegisterResponse, SipServerProperties sipServerProperties,
                             SipMessageResponseHandler sipMessageResponseHandler, DeviceCacheOperatorModel deviceCacheOperatorModel) {
        this.sipRegisterResponse = sipRegisterResponse;
        this.sipServerProperties = sipServerProperties;
        this.sipMessageResponseHandler = sipMessageResponseHandler;
        this.deviceCacheOperatorModel = deviceCacheOperatorModel;
    }


    @Override
    public void dealDeviceOffline(String deviceNo) {
        log.info("设备 {} 下线", deviceNo);
        //发送kafka 消息由上层应用更新其设备状态

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

    private void sendResponse(RequestEvent requestEvent, Response response) {
        sipMessageResponseHandler.sendResponse(requestEvent, response);
    }

}
