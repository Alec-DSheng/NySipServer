package org.nee.ny.sip.nysipserver.model;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.domain.Device;
import org.nee.ny.sip.nysipserver.domain.DeviceCommonKey;
import org.nee.ny.sip.nysipserver.event.RegisterEvent;
import org.nee.ny.sip.nysipserver.utils.DateUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

    private final SipServerProperties sipServerProperties;

    public DeviceCacheOperatorModel(RedisTemplate<String, String> redisTemplate, SipServerProperties sipServerProperties) {
        this.redisTemplate = redisTemplate;
        this.sipServerProperties = sipServerProperties;
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    public Device getDevice(String deviceNo) {
       String key = DeviceCommonKey.deviceNo + deviceNo;
       String value = redisTemplate.opsForValue().get(key);
       if (StringUtils.hasLength(value)) {
           String[] hostPorts = value.split(":");
           if (hostPorts.length == 3) {
               return Device.builder().deviceId(deviceNo).host(hostPorts[0])
                       .port(Integer.parseInt(hostPorts[1]))
                       .transport(hostPorts[2]).build();
           }
           return null;
       }
       return null;
    }

    public boolean isFirstRegister(RegisterEvent registerEvent) {
        String key = DeviceCommonKey.deviceNo + registerEvent.getDeviceId();
        String value = redisTemplate.opsForValue().get(key);
        boolean hasValue = StringUtils.hasLength(value);
        if (!hasValue) {
            redisTemplate.opsForValue().set(key, String.format("%s:%s:%s", registerEvent.getHost(),
                    registerEvent.getPort(), registerEvent.getTransport()));
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
        log.info("heart {}", heartValue);
        return StringUtils.hasLength(heartValue);
    }

    public void clearHeart(String deviceNo) {
        String key = DeviceCommonKey.heart + deviceNo;
        redisTemplate.delete(key);
    }

    public void cacheStreamCode(String deviceKey, String streamCode) {
        String key = String.format("%s%s", DeviceCommonKey.stream, deviceKey);
        redisTemplate.opsForValue().set(key, streamCode);
    }

    public void removeStreamCode(String deviceKey) {
        String key = String.format("%s%s", DeviceCommonKey.stream, deviceKey);
        redisTemplate.delete(key);
    }


    public String getStreamCode(String deviceId, String channelId) {
        String key = String.format("%s%s:%s", DeviceCommonKey.stream, deviceId, channelId);
        return redisTemplate.opsForValue().get(key);
    }

    //以StreamCode 为 key 缓存 设备终端序列号
    public void cacheDeviceAndChannel(String deviceId, String channelId, String streamCode) {
        String key =  String.format("%s%s:%s", DeviceCommonKey.streamCode, sipServerProperties.getDomain(), streamCode);
        redisTemplate.opsForValue().set(key, String.format("%s:%s", deviceId, channelId));
    }


    public String getDeviceAndChannel(String streamCode) {
        String key = String.format("%s%s:%s", DeviceCommonKey.streamCode, sipServerProperties.getDomain(), streamCode);
        return redisTemplate.opsForValue().get(key);
    }

    public void removeDeviceAndChannel(String streamCode) {
        String key = String.format("%s%s:%s", DeviceCommonKey.streamCode, sipServerProperties.getDomain(), streamCode);
        redisTemplate.delete(key);
    }
}
