package org.nee.ny.sip.nysipserver.service;


import org.nee.ny.sip.nysipserver.domain.DeviceInfo;
import org.nee.ny.sip.nysipserver.event.RegisterEvent;

/**
 * @Author: alec
 * Description:
 * @date: 11:28 2020-11-27
 */
public interface DeviceService {

    /**
     * 设备注册dealReportDeviceProperty
     * */
    void dealDeviceRegister(RegisterEvent registerEvent);

    /**
     * 设备下线
     * */
    void dealDeviceOffline(String deviceNo);

    /**
     * 设备上线
     * */
    void dealDeviceOnline(String deviceNo);

    /**
     * 设备注销
     * */
    void dealDeviceDestroy(String deviceNo);

    /**
     * 设备属性上报
     * */
    void dealReportDeviceProperty(DeviceInfo deviceInfo);

    void loadChannel(String deviceId);
}
