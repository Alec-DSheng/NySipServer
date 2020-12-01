package org.nee.ny.sip.nysipserver.transaction.command.message;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author: alec
 * Description:
 * @date: 12:46 2020-12-01
 */
@XmlRootElement(name = "Query")
@NoArgsConstructor
public class CommandXmlQueryParams {

    private String cmdType;

    private Integer sn;

    private String deviceId;

    CommandXmlQueryParams(String comType, String deviceId) {
        this.cmdType = comType;
        this.deviceId = deviceId;
        this.sn = (int) ((Math.random() * 9 + 1) * 100000);
    }

    public String getCmdType() {
        return cmdType;
    }

    @XmlElement(name = "CmdType")
    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }

    public Integer getSn() {
        return sn;
    }

    @XmlElement(name = "SN")
    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getDeviceId() {
        return deviceId;
    }

    @XmlElement(name = "DeviceID")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
