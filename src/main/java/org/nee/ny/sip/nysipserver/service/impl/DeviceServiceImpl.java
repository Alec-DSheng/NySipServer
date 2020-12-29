package org.nee.ny.sip.nysipserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.domain.*;
import org.nee.ny.sip.nysipserver.domain.enums.ChannelStatus;
import org.nee.ny.sip.nysipserver.event.RegisterEvent;
import org.nee.ny.sip.nysipserver.model.DeviceCacheOperatorModel;
import org.nee.ny.sip.nysipserver.service.DeviceService;
import org.nee.ny.sip.nysipserver.service.kafka.KafkaSender;
import org.nee.ny.sip.nysipserver.transaction.command.message.CatalogQueryCommand;
import org.nee.ny.sip.nysipserver.transaction.command.message.DeviceInfoQueryCommand;
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

    private final SipServerProperties sipServerProperties;

    private final KafkaSender kafkaSender;

    public DeviceServiceImpl(DeviceCacheOperatorModel deviceCacheOperatorModel, DeviceInfoQueryCommand deviceInfoQueryCommand,
                             CatalogQueryCommand catalogQueryCommand, SipServerProperties sipServerProperties, KafkaSender kafkaSender) {
        this.deviceCacheOperatorModel = deviceCacheOperatorModel;
        this.deviceInfoQueryCommand = deviceInfoQueryCommand;
        this.catalogQueryCommand = catalogQueryCommand;
        this.sipServerProperties = sipServerProperties;
        this.kafkaSender = kafkaSender;
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
        DeviceLineInfo deviceLineInfo = DeviceLineInfo.builder().deviceId(deviceNo).lineStatus(ChannelStatus.OFF.getCode()).build();
        kafkaSender.sendMessage(Constants.TOPIC_DEVICE_OFFLINE,
                new EventEnvelope<>(Constants.TYPE_CHANNEL, deviceLineInfo), () -> deviceCacheOperatorModel.clearHeart(deviceNo));
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
            log.info("向上发送上线事件");
            Optional.ofNullable(deviceCacheOperatorModel.getDevice(deviceNo)).ifPresent(device -> {
                DeviceLineInfo deviceLineInfo = DeviceLineInfo.builder().deviceId(deviceNo).lineStatus(ChannelStatus.ON.getCode()).build();
                kafkaSender.sendMessage(Constants.TOPIC_DEVICE_ONLINE,
                        new EventEnvelope<>(Constants.TYPE_CHANNEL, deviceLineInfo));
                catalogQueryCommand.sendCommand(device);
            });
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

    @Override
    public void loadChannel(String deviceId) {
        Optional.ofNullable(deviceCacheOperatorModel.getDevice(deviceId))
                .ifPresent(catalogQueryCommand::sendCommand);
    }
}
