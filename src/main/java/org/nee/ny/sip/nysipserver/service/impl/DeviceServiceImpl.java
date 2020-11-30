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


    @Override
    public void dealDeviceRegister(String deviceNo) {

    }

    @Override
    public void dealDeviceOffline(String deviceNo) {
        log.info("设备 {} 下线", deviceNo);
        //发送kafka 消息由上层应用更新其设备状态

    }

    @Override
    public void dealDeviceOnline(String deviceNo) {

    }

    @Override
    public void dealDeviceDestory(String deviceNo) {

    }

}
