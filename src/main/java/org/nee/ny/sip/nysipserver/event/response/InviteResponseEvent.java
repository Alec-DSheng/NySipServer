package org.nee.ny.sip.nysipserver.event.response;

import gov.nist.javax.sip.header.AddressParametersHeader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.DeviceChannel;
import org.nee.ny.sip.nysipserver.domain.VideoPlayer;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageResponse;
import org.nee.ny.sip.nysipserver.event.MessageResponseAbstract;
import org.nee.ny.sip.nysipserver.utils.XmlObjectConvertUtil;

import javax.sip.address.SipURI;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: alec
 * Description:
 * @date: 13:20 2020-12-05
 */
@MessageResponse(name="INVITE")
@Slf4j
public class InviteResponseEvent extends MessageResponseAbstract {

    @Getter
    private VideoPlayer videoPlayer;

    @Override
    public void dealResponse() {
        responseAck();
        log.info("content {}", getContent());
        AddressParametersHeader header = (AddressParametersHeader) response.getHeader("To");
        SipURI sipURI = (SipURI)header.getAddress().getURI();
        String user = sipURI.getUser();
        log.info("user {}", user);
        Map<String, String> contentMap = XmlObjectConvertUtil.convertStreamCode(getContent());
        if (Objects.nonNull(contentMap)) {
            String streamField = "y";
            String streamCode = contentMap.get(streamField);
            String deviceField = "o";
            String deviceId = contentMap.get(deviceField).substring(0,20);
            videoPlayer = VideoPlayer.builder().channelId(user).deviceId(deviceId)
                    .streamCode(streamCode).build();
        }
    }
}
