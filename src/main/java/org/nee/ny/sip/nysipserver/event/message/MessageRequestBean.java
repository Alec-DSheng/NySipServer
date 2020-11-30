package org.nee.ny.sip.nysipserver.event.message;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.nee.ny.sip.nysipserver.utils.XmlObjectConvertUtil;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author: alec
 * Description: 上报Message消息后消息为message请求解析
 * 解析bean数据,解析后做判断然后下发事件
 * @date: 09:56 2020-11-30
 */

@XmlRootElement(name = "Notify")
@ToString
public class MessageRequestBean {

    private String cmdType;

    private String serialNumber;

    private String deviceId;

    private String status;


    public String getCmdType() {
        return cmdType;
    }

    @XmlElement(name = "CmdType")
    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    @XmlElement(name = "SN")
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }

    @XmlElement(name = "DeviceID")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStatus() {
        return status;
    }

    @XmlElement(name = "Status")
    public void setStatus(String status) {
        this.status = status;
    }
}
