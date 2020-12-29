package org.nee.ny.sip.nysipserver.interfaces.controller;

import org.nee.ny.sip.nysipserver.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @Author: alec
 * Description: 设备信息
 * @date: 10:59 2020-12-29
 */
@RestController
@RequestMapping(value = "device")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping(value = "load")
    public Mono loadChannelByDevice(@RequestParam(value = "deviceId") String deviceId) {
        deviceService.loadChannel(deviceId);
        return Mono.empty();
    }
}
