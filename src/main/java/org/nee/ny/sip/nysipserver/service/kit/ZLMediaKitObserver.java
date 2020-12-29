package org.nee.ny.sip.nysipserver.service.kit;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.ZlMediaKitServer;
import org.springframework.scheduling.annotation.Async;

/**
 * @Author: alec
 * Description:
 * @date: 16:32 2020-12-28
 */
@Slf4j
public class ZLMediaKitObserver implements KitObserver {

    private ZlMediaKitServer zlMediaKitServer;

    public ZLMediaKitObserver (ZlMediaKitServer zlMediaKitServer) {
        this.zlMediaKitServer = zlMediaKitServer;
    }

    @Override
    @Async
    public void notifyObserver() {
        log.info("检查存活状态");
    }

    @Override
    public String getId() {
        return zlMediaKitServer.getMediaServerId();
    }
}
