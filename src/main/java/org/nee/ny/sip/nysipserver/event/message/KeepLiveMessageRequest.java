package org.nee.ny.sip.nysipserver.event.message;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageReqeust;
import org.nee.ny.sip.nysipserver.utils.XmlObjectConvertUtil;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @Author: alec
 * Description:
 * @date: 11:33 2020-11-30
 */
@MessageReqeust(name = "Keepalive")
@XmlRootElement(name = "Notify")
@Slf4j
@ToString
public class KeepLiveMessageRequest extends MessageRequestAbstract {

    private String cmdType;

    private String serialNumber;

    private String deviceId;

    private String status;

    @Override
    public void load() {
        super.load();
        String content = this.content;
        this.messageRequestAbstract = (KeepLiveMessageRequest) XmlObjectConvertUtil.xmlConvertObject(content, this);
    }


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
