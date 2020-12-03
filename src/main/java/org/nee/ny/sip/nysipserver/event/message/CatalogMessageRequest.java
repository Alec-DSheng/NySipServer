package org.nee.ny.sip.nysipserver.event.message;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.DeviceChannel;
import org.nee.ny.sip.nysipserver.domain.enums.ChannelStatus;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageReqeust;
import org.nee.ny.sip.nysipserver.utils.XmlObjectConvertUtil;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: alec
 * Description:
 * @date: 11:34 2020-11-30
 */
@MessageReqeust(name = "Catalog")
@XmlRootElement(name = "Response")
@Slf4j
@ToString
public class CatalogMessageRequest  extends MessageRequestAbstract   {

    private String deviceId;

    private Integer sumNum;

    private String sn;

    private DeviceList deviceList;

    @Getter
    private List<DeviceChannel> deviceChannel;

    @Override
    public void load() {
        super.load();
        String content = this.content;
        log.info("接收到 catalog {}", content);
        CatalogMessageRequest catalogMessageRequest = (CatalogMessageRequest) XmlObjectConvertUtil.xmlConvertObject(content, this);
        try {
            Optional.ofNullable(catalogMessageRequest).ifPresent(channel -> {
                deviceChannel =  channel.getDeviceList().getItem().stream().map(item ->
                        DeviceChannel.builder()
                                .address(item.getAddress())
                                .civilCode(item.getCivilCode())
                                .code(item.getDeviceId())
                                .deviceId(item.getParentId())
                                .manufacturer(item.getManufacturer())
                                .model(item.getModel())
                                .name(item.getName())
                                .owner(item.getOwner())
                                .registerWay(item.getRegisterWay())
                                .secret(item.getSecrecy())
                                .status(ChannelStatus.codeByName(item.getStatus()).orElseThrow(NullPointerException::new))
                                .build()).collect(Collectors.toList());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.messageRequestAbstract = catalogMessageRequest;
    }

    public String getDeviceId() {
        return deviceId;
    }

    @XmlElement(name = "DeviceID")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getSumNum() {
        return sumNum;
    }

    @XmlElement(name = "SumNum")
    public void setSumNum(Integer sumNum) {
        this.sumNum = sumNum;
    }

    public String getSn() {
        return sn;
    }
    @XmlElement(name = "SN")
    public void setSn(String sn) {
        this.sn = sn;
    }

    public DeviceList getDeviceList() {
        return deviceList;
    }
    @XmlElement(name = "DeviceList")
    public void setDeviceList(DeviceList deviceList) {
        this.deviceList = deviceList;
    }

    public List<Item> getItems() {
        return this.deviceList.getItem();
    }

    static class DeviceList {

        private Integer num;

        private List<Item> item;

        public Integer getNum() {
            return num;
        }
        @XmlAttribute(name = "NUM")
        public void setNum(Integer num) {
            this.num = num;
        }

        public List<Item> getItem() {
            return item;
        }
        @XmlElement(name = "Item")
        public void setItem(List<Item> item) {
            this.item = item;
        }
    }

    static class Item {

       private String  deviceId;

        private String name;

        private String manufacturer;

        private String model ;

        private String  owner;

        private String  civilCode;

        private String   address;

        private Integer  parental;

        private String  parentId;

        private Integer  registerWay;

        private Integer  secrecy;

        private String  status;

        public String getDeviceId() {
            return deviceId;
        }

        @XmlElement(name = "DeviceID")
        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getName() {
            return name;
        }

        @XmlElement(name = "Name")
        public void setName(String name) {
            this.name = name;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        @XmlElement(name = "Manufacturer")
        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getModel() {
            return model;
        }

        @XmlElement(name = "Model")
        public void setModel(String model) {
            this.model = model;
        }

        public String getOwner() {
            return owner;
        }

        @XmlElement(name = "Owner")
        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getCivilCode() {
            return civilCode;
        }

        @XmlElement(name = "CivilCode")
        public void setCivilCode(String civilCode) {
            this.civilCode = civilCode;
        }

        public String getAddress() {
            return address;
        }

        @XmlElement(name = "Address")
        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getParental() {
            return parental;
        }

        @XmlElement(name = "Parental")
        public void setParental(Integer parental) {
            this.parental = parental;
        }

        public String getParentId() {
            return parentId;
        }

        @XmlElement(name = "ParentID")
        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public Integer getRegisterWay() {
            return registerWay;
        }

        @XmlElement(name = "RegisterWay")
        public void setRegisterWay(Integer registerWay) {
            this.registerWay = registerWay;
        }

        public Integer getSecrecy() {
            return secrecy;
        }

        @XmlElement(name = "Secrecy")
        public void setSecrecy(Integer secrecy) {
            this.secrecy = secrecy;
        }

        public String getStatus() {
            return status;
        }

        @XmlElement(name = "Status")
        public void setStatus(String status) {
            this.status = status;
        }
    }
}
