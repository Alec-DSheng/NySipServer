package org.nee.ny.sip.nysipserver.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.EventEnvelope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description:
 * @date: 15:27 2020-12-02
 */
@Component
@Slf4j
public class KafkaSender {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaSender(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, EventEnvelope eventEnvelope, SendSuccessCallback successCallback) {
        log.info("send topic {}, message {}", topic, eventEnvelope);
        kafkaTemplate.send(topic, eventEnvelope).addCallback(success -> {
                    log.info("发送kafka消息成功 topic  {} , body {} success {}", topic, eventEnvelope, success);
                    successCallback.callback();
                },
        failure -> log.error("发送kafka消息失败 错误原因 {}", failure.getMessage()));
    }

    public void sendMessage(String topic, EventEnvelope eventEnvelope) {
        log.info("send topic {}, message {}", topic, eventEnvelope);
        kafkaTemplate.send(topic, eventEnvelope).addCallback(success ->
                        log.info("发送kafka消息成功 topic  {} , body {} success {}", topic, eventEnvelope, success),
                failure ->
                log.error("发送kafka消息失败 错误原因 {}", failure.getMessage()));
    }
}
