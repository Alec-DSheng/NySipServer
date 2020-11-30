package org.nee.ny.sip.nysipserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.Device;
import org.nee.ny.sip.nysipserver.domain.DeviceCommonKey;
import org.nee.ny.sip.nysipserver.event.RegisterEvent;
import org.nee.ny.sip.nysipserver.model.DeviceCacheOperatorModel;
import org.nee.ny.sip.nysipserver.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @Author: alec
 * Description:
 * @date: 11:29 2020-11-27
 */
@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    private final DeviceCacheOperatorModel deviceCacheOperatorModel;

    public DeviceServiceImpl(DeviceCacheOperatorModel deviceCacheOperatorModel) {
        this.deviceCacheOperatorModel = deviceCacheOperatorModel;
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
       log.info("下发查询设备指令{}", device);
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

}
