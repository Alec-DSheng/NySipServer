package org.nee.ny.sip.nysipserver.listeners;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.DeviceChannel;
import org.nee.ny.sip.nysipserver.domain.DeviceInfo;
import org.nee.ny.sip.nysipserver.event.message.*;
import org.nee.ny.sip.nysipserver.service.DeviceChannelService;
import org.nee.ny.sip.nysipserver.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
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

    @Autowired
    private DeviceChannelService deviceChannelService;


    public MessageListeners(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @EventListener
    public void deviceRegister(KeepLiveMessageRequest keepLiveMessageRequest) {
        keepLiveMessageRequest = (KeepLiveMessageRequest) keepLiveMessageRequest.getMessageRequestAbstract();
        log.info("当前对应心跳数据{}", keepLiveMessageRequest);
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
        log.info("监听catalog 事件");
        List<DeviceChannel> deviceChannelList = catalogMessageRequest.getDeviceChannel();
        if (!CollectionUtils.isEmpty(deviceChannelList)) {
            deviceChannelService.channelReport(deviceChannelList);
        }
    }
}
