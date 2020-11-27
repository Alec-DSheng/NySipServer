package org.nee.ny.sip.nysipserver.listeners;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.DeviceCommonKey;
import org.nee.ny.sip.nysipserver.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description:
 * @date: 22:55 2020-11-27
 */
@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    private final DeviceService deviceService;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer, DeviceService deviceService) {
        super(listenerContainer);
        this.deviceService = deviceService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
       String expKey = message.toString();
       log.info("失效key {}", expKey);
       if (expKey.startsWith(DeviceCommonKey.deviceNo)) {
           //处理下线
           String deviceNo = expKey.substring(DeviceCommonKey.deviceNo.length());
           deviceService.dealDeviceOffline(deviceNo);
       }
    }
}
