package org.nee.ny.sip.nysipserver.listeners;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.DeviceInfo;
import org.nee.ny.sip.nysipserver.event.message.CatalogMessageRequest;
import org.nee.ny.sip.nysipserver.event.message.DeviceInfoMessageRequest;
import org.nee.ny.sip.nysipserver.event.message.KeepLiveMessageRequest;
import org.nee.ny.sip.nysipserver.service.DeviceService;
import org.nee.ny.sip.nysipserver.transaction.command.message.CatalogQueryCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

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

    @EventListener
    public void deviceInfoReceive(DeviceInfoMessageRequest deviceInfoMessageRequest) {
        DeviceInfo deviceInfo = deviceInfoMessageRequest.getDeviceInfo();
        if (Objects.nonNull(deviceInfo)) {
            deviceService.dealReportDeviceProperty(deviceInfo);
        }
    }

    @EventListener
    public void catalogReceive(CatalogMessageRequest catalogMessageRequest) {

        log.info("上报渠道信息 {}", catalogMessageRequest.getContent());
    }
}
