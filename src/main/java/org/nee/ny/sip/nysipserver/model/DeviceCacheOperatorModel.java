package org.nee.ny.sip.nysipserver.model;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.DeviceCommonKey;
import org.nee.ny.sip.nysipserver.event.RegisterEvent;
import org.nee.ny.sip.nysipserver.utils.DateUtil;
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
@Slf4j
public class DeviceCacheOperatorModel {

    private final RedisTemplate<String, String> redisTemplate;

    private final static int EXPIRE_TIME = 3;

    public DeviceCacheOperatorModel(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    public boolean isFirstRegister(RegisterEvent registerEvent) {
        String key = DeviceCommonKey.deviceNo + registerEvent.getDeviceId();
        String value = redisTemplate.opsForValue().get(key);
        boolean hasValue = StringUtils.hasLength(value);
        if (!hasValue) {
            redisTemplate.opsForValue().set(key, String.format("%s:%s", registerEvent.getHost(),
                    registerEvent.getPort()));
        }
        return !hasValue;
    }

    public void recordHeart(String deviceNo) {
        String key = DeviceCommonKey.heart + deviceNo;
        redisTemplate.opsForValue().set(key, DateUtil.getDateFormat(), EXPIRE_TIME, TimeUnit.MINUTES);
    }

    public boolean hasHeart(String deviceNo) {
        String key = DeviceCommonKey.heart + deviceNo;
        String heartValue = redisTemplate.opsForValue().get(key);
        return StringUtils.hasLength(heartValue);
    }
}
