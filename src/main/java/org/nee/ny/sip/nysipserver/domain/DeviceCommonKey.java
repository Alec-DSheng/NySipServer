package org.nee.ny.sip.nysipserver.domain;

/**
 * @Author: alec
 * Description:
 * @date: 22:59 2020-11-27
 */
public interface DeviceCommonKey {

    String deviceNo = "D_NO:"; //记录设备初次注册

    String heart = "H_NO:"; //记录设备心跳

    String stream = "SSCODE:";
}
