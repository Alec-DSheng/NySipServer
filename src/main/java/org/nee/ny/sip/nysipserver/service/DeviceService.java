package org.nee.ny.sip.nysipserver.service;


/**
 * @Author: alec
 * Description:
 * @date: 11:28 2020-11-27
 */
public interface DeviceService {

    /**
     * 设备注册
     * */
    void dealDeviceRegister(String deviceNo);

    /**
     * 设备上线
     * */
    void dealDeviceOffline(String deviceNo);

    /**
     * 设备下线
     * */
    void dealDeviceOnline(String deviceNo);

    /**
     * 设备注销
     * */
    void dealDeviceDestory(String deviceNo);
}
