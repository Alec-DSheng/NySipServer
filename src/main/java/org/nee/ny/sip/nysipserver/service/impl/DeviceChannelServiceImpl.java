package org.nee.ny.sip.nysipserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.Constants;
import org.nee.ny.sip.nysipserver.domain.DeviceChannel;
import org.nee.ny.sip.nysipserver.domain.EventEnvelope;
import org.nee.ny.sip.nysipserver.service.DeviceChannelService;
import org.nee.ny.sip.nysipserver.service.kafka.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private KafkaSender kafkaSender;

    @Override
    public void channelReport(List<DeviceChannel> deviceChannelList) {
        log.info("send msg {}", deviceChannelList);
         deviceChannelList.forEach(deviceChannel ->
             kafkaSender.sendMessage(Constants.TOPIC_DEVICE_CHANNEL_REGISTER,
             new EventEnvelope<>(Constants.TYPE_CHANNEL, deviceChannel)));
    }
}
