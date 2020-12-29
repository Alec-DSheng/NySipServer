package org.nee.ny.sip.nysipserver.service.kit;


import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.model.ServerCacheOperatorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: alec
 * Description:
 * @date: 16:26 2020-12-28
 */
@Component
@Slf4j
public class ZLMediaKitSubject implements KitSubject<KitObserver> {

    private final Map<String, KitObserver> kitServers = new ConcurrentHashMap<>();

    private final ServerCacheOperatorModel serverCacheOperatorModel;

    public ZLMediaKitSubject(ServerCacheOperatorModel serverCacheOperatorModel) {
        this.serverCacheOperatorModel = serverCacheOperatorModel;
    }

    @PostConstruct
    public void init() {
        List<String> enableServerHost = serverCacheOperatorModel.getAllEnableServer();
        log.info("初始化观察者队列 {}", enableServerHost);

    }

    @Override
    public void addObserver(KitObserver kitObserver) {
        kitServers.put(kitObserver.getId(), kitObserver);
    }

    @Override
    public void removeObject(KitObserver kitObserver) {
        kitServers.remove(kitObserver.getId());
    }

    @Override
    public void notifyObserver() {
        //采用异步执行
        for (KitObserver server: kitServers.values()) {
            server.notifyObserver();
        }
    }
}
