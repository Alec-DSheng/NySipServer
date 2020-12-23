package org.nee.ny.sip.nysipserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.Constants;
import org.nee.ny.sip.nysipserver.domain.DeviceChannel;
import org.nee.ny.sip.nysipserver.domain.EventEnvelope;
import org.nee.ny.sip.nysipserver.service.DeviceChannelService;
import org.nee.ny.sip.nysipserver.service.kafka.KafkaSender;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 16:16 2020-12-03
 */
@Service
@Slf4j
public class DeviceChannelServiceImpl implements DeviceChannelService {

    private final KafkaSender kafkaSender;

    public DeviceChannelServiceImpl(KafkaSender kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    @Override
    public void channelReport(List<DeviceChannel> deviceChannelList) {
        log.info("send msg {}", deviceChannelList);
        for (int i = 0; i < deviceChannelList.size(); i++) {
            DeviceChannel deviceChannel = deviceChannelList.get(i);
            if (i < 100) {
                deviceChannel.setNo(String.format("%02d", i));
            }
            //第一个channel 带总数
            if (i != 0) {
                deviceChannel.setChannelNum(null);
            }
            kafkaSender.sendMessage(Constants.TOPIC_DEVICE_CHANNEL_REGISTER,
                     new EventEnvelope<>(Constants.TYPE_CHANNEL, deviceChannel));
        }
    }
}
