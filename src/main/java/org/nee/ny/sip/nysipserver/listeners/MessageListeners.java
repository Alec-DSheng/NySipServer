package org.nee.ny.sip.nysipserver.listeners;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.event.message.KeepLiveMessageRequest;
import org.nee.ny.sip.nysipserver.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description:
 * @date: 16:44 2020-11-30
 */
@Component
@Slf4j
public class MessageListeners {

    private final DeviceService deviceService;

    public MessageListeners(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @EventListener
    public void deviceRegister(KeepLiveMessageRequest keepLiveMessageRequest) {
        keepLiveMessageRequest = (KeepLiveMessageRequest) keepLiveMessageRequest.getMessageRequestAbstract();
        log.info("当前对应心跳数据{}", keepLiveMessageRequest);
        //设备上线处理逻辑
        deviceService.dealDeviceOnline(keepLiveMessageRequest.getDeviceId());
    }
}
