package org.nee.ny.sip.nysipserver.service.kit;


import org.springframework.stereotype.Component;

import java.util.Vector;

/**
 * @Author: alec
 * Description:
 * @date: 16:26 2020-12-28
 */
@Component
public class ZLMediaKitSubject implements KitSubject<KitObserver> {

    private final Vector<KitObserver> kitServers = new Vector<>();

    @Override
    public void addObserver(KitObserver kitObserver) {
        kitServers.add(kitObserver);
    }

    @Override
    public void removeObject(KitObserver kitObserver) {
        kitServers.remove(kitObserver);
    }

    @Override
    public void notifyObserver() {
        //采用异步执行
        for (KitObserver server: kitServers) {
            server.notifyObserver();
        }
    }
}
