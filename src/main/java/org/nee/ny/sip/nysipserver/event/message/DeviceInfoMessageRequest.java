package org.nee.ny.sip.nysipserver.event.message;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.DeviceInfo;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageReqeust;
import org.nee.ny.sip.nysipserver.utils.XmlObjectConvertUtil;
import org.slf4j.Logger;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Optional;

/**
 * @Author: alec
 * Description:
 * @date: 11:34 2020-11-30
 */
@MessageReqeust(name = "DeviceInfo")
@XmlRootElement(name = "Response")
@Slf4j
@ToString
public class DeviceInfoMessageRequest  extends MessageRequestAbstract  {

    private String deviceId;

    private String deviceName;

    private String sn;

    private String result;

    private String manufacturer;

    private String model;

    private String firmware;

    private Integer channel;

    @Getter
    private DeviceInfo deviceInfo;


    @Override
    public void load() {
        super.load();
        String content = this.content;
        DeviceInfoMessageRequest deviceInfoMessageRequest = (DeviceInfoMessageRequest) XmlObjectConvertUtil.xmlConvertObject(content, this);
        Optional.ofNullable(deviceInfoMessageRequest).ifPresent(device ->
                this.deviceInfo = DeviceInfo.builder().
                 code(device.getDeviceId())
                .channelNum(device.getChannel())
                .name(device.getDeviceName())
                .firmware(device.getFirmware())
                .manufacturer(device.getManufacturer())
                .model(device.getModel())
                .build());
        this.messageRequestAbstract = deviceInfoMessageRequest;
    }

    private String getDeviceId() {
        return deviceId;
    }

    @XmlElement(name = "DeviceID")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    private String getDeviceName() {
        return deviceName;
    }

    @XmlElement(name = "DeviceName")
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSn() {
        return sn;
    }

    @XmlElement(name = "SN")
    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getResult() {
        return result;
    }

    @XmlElement(name = "Result")
    public void setResult(String result) {
        this.result = result;
    }

    private String getManufacturer() {
        return manufacturer;
    }

    @XmlElement(name = "Manufacturer")
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    private String getModel() {
        return model;
    }

    @XmlElement(name = "Model")
    public void setModel(String model) {
        this.model = model;
    }

    private String getFirmware() {
        return firmware;
    }
    @XmlElement(name = "Firmware")
    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    private Integer getChannel() {
        return channel;
    }
    @XmlElement(name = "Channel")
    public void setChannel(Integer channel) {
        this.channel = channel;
    }
}
