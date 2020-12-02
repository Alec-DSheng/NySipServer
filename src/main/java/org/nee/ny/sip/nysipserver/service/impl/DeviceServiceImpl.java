package org.nee.ny.sip.nysipserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.domain.*;
import org.nee.ny.sip.nysipserver.event.RegisterEvent;
import org.nee.ny.sip.nysipserver.model.DeviceCacheOperatorModel;
import org.nee.ny.sip.nysipserver.service.DeviceService;
import org.nee.ny.sip.nysipserver.service.kafka.KafkaSender;
import org.nee.ny.sip.nysipserver.service.kafka.SendSuccessCallback;
import org.nee.ny.sip.nysipserver.transaction.command.message.CatalogQueryCommand;
import org.nee.ny.sip.nysipserver.transaction.command.message.DeviceInfoQueryCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @Author: alec
 * Description:
 * @date: 11:29 2020-11-27
 */
@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    private final DeviceCacheOperatorModel deviceCacheOperatorModel;

    private final DeviceInfoQueryCommand deviceInfoQueryCommand;

    private final CatalogQueryCommand catalogQueryCommand;

    @Autowired
    private SipServerProperties sipServerProperties;

    @Autowired
    private KafkaSender kafkaSender;

    public DeviceServiceImpl(DeviceCacheOperatorModel deviceCacheOperatorModel, DeviceInfoQueryCommand deviceInfoQueryCommand,
                             CatalogQueryCommand catalogQueryCommand) {
        this.deviceCacheOperatorModel = deviceCacheOperatorModel;
        this.deviceInfoQueryCommand = deviceInfoQueryCommand;
        this.catalogQueryCommand = catalogQueryCommand;
    }

    /**
     * 下发查询设备指令
     * */
    @Override
    public void dealDeviceRegister(RegisterEvent registerEvent) {
       Device device = Device.builder()
        .deviceId(registerEvent.getDeviceId())
        .host(registerEvent.getHost())
        .port(registerEvent.getPort())
        .transport(registerEvent.getTransport()).build();
        deviceInfoQueryCommand.sendCommand(device);
    }

    /**
     * 发送kafka下线记录
     * */
    @Override
    public void dealDeviceOffline(String deviceNo) {
        log.info("向上发送下线事件");
    }

    /**
     * 检查Redis中是否有该设备的心跳记录
     * 如果有心跳记录,更新过期时间
     * 如果无心跳记录,新增心跳记录 且发送设备上线事件至kafka
     * */
    @Override
    public void dealDeviceOnline(String deviceNo) {
        boolean hasHeart = deviceCacheOperatorModel.hasHeart(deviceNo);
        if (!hasHeart) {
            //发送上线事件
            log.info("向上发送上线事件");
        }
        deviceCacheOperatorModel.recordHeart(deviceNo);
    }

    /**
     * 删除redis中心跳记录
     * 发送kafka 下线 记录
     * */
    @Override
    public void dealDeviceDestroy(String deviceNo) {
        String heartKey = DeviceCommonKey.heart + deviceNo;
        String deviceKey = DeviceCommonKey.deviceNo + deviceNo;
        deviceCacheOperatorModel.deleteValue(heartKey);
        deviceCacheOperatorModel.deleteValue(deviceKey);
        dealDeviceOffline(deviceNo);
    }

    @Override
    public void dealReportDeviceProperty(DeviceInfo deviceInfo) {
        Optional.ofNullable(deviceCacheOperatorModel.getDevice(deviceInfo.getCode())).ifPresent(device -> {
            deviceInfo.setHost(device.getHost());
            deviceInfo.setPort(device.getPort());
            deviceInfo.setTransport(device.getTransport());
            deviceInfo.setDomain(sipServerProperties.getDomain());
            kafkaSender.sendMessage(Constants.TOPIC_DEVICE_REGISTER,
                    new EventEnvelope<>(Constants.TYPE_REGISTER, deviceInfo), () -> {
                try {
                    Thread.sleep(1000);
                    catalogQueryCommand.sendCommand(device);
                } catch (InterruptedException e) {
                    log.error("interruptException", e);
                }
            });
        });

    }

}
