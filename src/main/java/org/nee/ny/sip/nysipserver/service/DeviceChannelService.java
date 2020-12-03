package org.nee.ny.sip.nysipserver.service;

import org.nee.ny.sip.nysipserver.domain.DeviceChannel;

import java.util.List;

/**
 * @Author: alec
 * Description:
 * @date: 16:14 2020-12-03
 */
public interface DeviceChannelService {

    void channelReport(List<DeviceChannel> deviceChannelList);
}
