package org.nee.ny.sip.nysipserver.model;

import org.nee.ny.sip.nysipserver.domain.DeviceCommonKey;
import org.nee.ny.sip.nysipserver.event.RegisterMessageEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @Author: alec
 * Description:
 * @date: 11:22 2020-11-28
 */
@Component
public class DeviceCacheOperatorModel {

    private final RedisTemplate<String, String> redisTemplate;

    public DeviceCacheOperatorModel(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void putValue(String key, String value, long expTime) {
        redisTemplate.opsForValue().set(key, value,  expTime, TimeUnit.SECONDS);
    }

    public boolean isFirstRegister(RegisterMessageEvent registerMessageEvent) {
        String key = DeviceCommonKey.deviceNo + registerMessageEvent.getDeviceId();
        String value = redisTemplate.opsForValue().get(key);
        boolean hasValue = StringUtils.hasLength(value);
        if (hasValue) {
            redisTemplate.opsForValue().set(key, String.format("%s:%s", registerMessageEvent.getHost(),
                    registerMessageEvent.getPort()));
        }
        return !hasValue;
    }
}
