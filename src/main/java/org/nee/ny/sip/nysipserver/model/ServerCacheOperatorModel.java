package org.nee.ny.sip.nysipserver.model;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.DeviceCommonKey;
import org.nee.ny.sip.nysipserver.domain.ZlMediaKitServer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * Description:
 * @date: 17:07 2020-12-28
 */
@Component
@Slf4j
public class ServerCacheOperatorModel {

    private final RedisTemplate<String, String> redisTemplate;

    public ServerCacheOperatorModel (RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void cacheEnableServer(ZlMediaKitServer zlMediaKitServer) {
        String server = String.format("%s:%s", zlMediaKitServer.getIp(), zlMediaKitServer.getHttpPort());
        redisTemplate.opsForHash().put(DeviceCommonKey.SERVER, zlMediaKitServer.getMediaServerId(), server);
    }

    public List<String> getAllEnableServer() {
       List<Object> objectList = redisTemplate.opsForHash().values(DeviceCommonKey.SERVER);
       if (CollectionUtils.isEmpty(objectList)) {
           return new ArrayList<>();
       }
       return objectList.stream().map(String::valueOf).collect(Collectors.toList());
    }

    public void remoteCacheEnableServer(String mediaId) {
        redisTemplate.opsForHash().delete(DeviceCommonKey.SERVER, mediaId);
    }

    public String getEnableMediaServer() throws NotFoundException {
        List<String> zlMediaKitServers = getAllEnableServer();
        if (CollectionUtils.isEmpty(zlMediaKitServers)) {
            throw new NotFoundException("无可用服务");
        }
        /**
         * 临时采用随机算法返回可用IP。 可用优化为 一致性hash 算法
         * */
        int index = new Random().nextInt(zlMediaKitServers.size() -1 );
        String address = zlMediaKitServers.get(index);
        return address.substring(0, address.lastIndexOf(":"));
    }
}
