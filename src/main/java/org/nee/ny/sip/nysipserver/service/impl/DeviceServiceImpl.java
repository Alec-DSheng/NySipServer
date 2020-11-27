package org.nee.ny.sip.nysipserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.DeviceInfo;
import org.nee.ny.sip.nysipserver.service.DeviceService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @Author: alec
 * Description:
 * @date: 11:29 2020-11-27
 */
@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {


    @EventListener
    public void deviceRegister(DeviceInfo deviceInfo) {
        log.debug("监听到注册事件,处理设备注册逻辑 {}", deviceInfo);
    }
}
