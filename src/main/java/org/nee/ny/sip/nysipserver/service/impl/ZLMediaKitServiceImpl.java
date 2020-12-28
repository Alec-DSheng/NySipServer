package org.nee.ny.sip.nysipserver.service.impl;

import org.nee.ny.sip.nysipserver.domain.EventEnvelope;
import org.nee.ny.sip.nysipserver.domain.ZlMediaKitServer;
import org.nee.ny.sip.nysipserver.service.ZLMediaKitService;
import org.nee.ny.sip.nysipserver.service.kafka.KafkaSender;
import org.nee.ny.sip.nysipserver.service.kit.ZLMediaKitObserver;
import org.nee.ny.sip.nysipserver.service.kit.ZLMediaKitSubject;
import org.springframework.stereotype.Service;

import static org.nee.ny.sip.nysipserver.domain.Constants.TOPIC_ZL_MEDIA_SERVER;
import static org.nee.ny.sip.nysipserver.domain.Constants.TYPE_REGISTER;

/**
 * @Author: alec
 * Description:
 * @date: 16:19 2020-12-28
 */
@Service
public class ZLMediaKitServiceImpl implements ZLMediaKitService {

    private final ZLMediaKitSubject zLMediaKitSubject;

    private final KafkaSender kafkaSender;

    public ZLMediaKitServiceImpl(ZLMediaKitSubject zLMediaKitSubject, KafkaSender kafkaSender) {
        this.zLMediaKitSubject = zLMediaKitSubject;
        this.kafkaSender = kafkaSender;
    }

    /**
     * 流媒体服务器注册逻辑
     * 将流媒体信息注册信息发送Kafka
     * 加入到监听队列中
     * 定时任务每三分钟发起一次检查
     * 通知所有队列如果保活时间是三分钟后的需要发起查询接口自检存活状态
     * */
    @Override
    public void register(ZlMediaKitServer zlMediaKitServer) {
        kafkaSender.sendMessage(TOPIC_ZL_MEDIA_SERVER, new EventEnvelope<>(TYPE_REGISTER, zlMediaKitServer), () ->
                zLMediaKitSubject.addObserver(new ZLMediaKitObserver(zlMediaKitServer)));
    }
}
