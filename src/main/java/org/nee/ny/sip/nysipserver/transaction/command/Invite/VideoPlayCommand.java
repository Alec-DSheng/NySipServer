package org.nee.ny.sip.nysipserver.transaction.command.Invite;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.domain.Device;
import org.nee.ny.sip.nysipserver.transaction.command.SipServerCommandRequest;
import org.nee.ny.sip.nysipserver.transaction.response.MessageResponseHandler;
import org.nee.ny.sip.nysipserver.transaction.session.StreamCodeSessionManager;
import org.nee.ny.sip.nysipserver.transaction.session.TransactionSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sip.SipException;
import javax.sip.address.SipURI;
import javax.sip.header.ViaHeader;
import javax.sip.message.Request;
import java.text.ParseException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Author: alec
 * Description:
 * @date: 09:34 2020-12-04
 */
@Component
@Slf4j
public class VideoPlayCommand extends InviteCommandAbstract {

    private final SipServerProperties sipServerProperties;

    private final TransactionSessionManager transactionSessionManager;

    private final MessageResponseHandler messageResponseHandler;

    @Autowired


    public VideoPlayCommand(MessageResponseHandler messageResponseHandler, SipServerCommandRequest sipServerCommandRequest,
                            StreamCodeSessionManager streamCodeSessionManager, SipServerProperties sipServerProperties,
                            TransactionSessionManager transactionSessionManager) {
        super(messageResponseHandler, sipServerCommandRequest,streamCodeSessionManager,sipServerProperties,transactionSessionManager);
        this.sipServerProperties = sipServerProperties;
        this.transactionSessionManager = transactionSessionManager;
        this.messageResponseHandler = messageResponseHandler;
    }

    @Override
    public PlayCommandParams createPlayCommandParams(Device device, String channelId, String code) {
        StringBuilder contentBuild = new StringBuilder(200);
        contentBuild.append("v=0\r\n")
                    .append("o=").append(channelId).append(" 0 0 IN IP4 ").append(sipServerProperties.getHost()).append("\r\n")
                    .append("s=Play\r\n")
                    .append("c=IN IP4 ").append(sipServerProperties.getMediaIp()).append("\r\n")
                    .append("t=0 0\r\n");

        if("TCP".equals(device.getTransport())) {
            contentBuild.append("m=video ").append(sipServerProperties.getMediaPort()).append(" TCP/RTP/AVP 96 98 97\r\n");
        }
        if("UDP".equals(device.getTransport())) {
            contentBuild.append("m=video ").append(sipServerProperties.getMediaPort()).append(" RTP/AVP 96 98 97\r\n");
        }
        contentBuild.append("a=recvonly\r\n")
            .append("a=fmtp:126 profile-level-id=42e01e\r\n")
            .append("a=rtpmap:126 H264/90000\r\n")
            .append("a=rtpmap:125 H264S/90000\r\n")
            .append("a=fmtp:125 profile-level-id=42e01e\r\n")
            .append("a=rtpmap:99 MP4V-ES/90000\r\n")
            .append("a=fmtp:99 profile-level-id=3\r\n")
            .append("a=rtpmap:96 PS/90000\r\n")
            .append("a=rtpmap:98 H264/90000\r\n")
            .append("a=rtpmap:97 MPEG4/90000\r\n");

        if("TCP".equals(device.getTransport())){
            contentBuild.append("a=setup:passive\r\n")
                .append("a=connection:new\r\n");
        }
        String streamCode = "0" + code;
        contentBuild.append("y=").append(streamCode).append("\r\n");

        PlayCommandParams playCommandParams = new PlayCommandParams();
        playCommandParams.setContent(contentBuild.toString());
        playCommandParams.setDevice(device);
        playCommandParams.setChannelId(channelId);
        playCommandParams.setStreamCode(streamCode);
        return playCommandParams;
    }

    public void stopPlayer(String streamCode) {
        transactionSessionManager.get(streamCode).ifPresent(clientTransaction -> {
            Optional.ofNullable(clientTransaction.getDialog()).ifPresent(dialog -> {
                try {
                    Request request = dialog.createRequest(Request.BYE);
                    SipURI sipURI = (SipURI) request.getRequestURI();
                    String via = clientTransaction.getRequest().getHeader(ViaHeader.NAME).toString();
                    Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)");
                    Matcher matcher = pattern.matcher(via);
                    if (matcher.find()) {
                        sipURI.setHost(matcher.group(1));
                    }
                    ViaHeader viaHeader = (ViaHeader) request.getHeader(ViaHeader.NAME);
                    String protocol = viaHeader.getTransport().toUpperCase();
                    messageResponseHandler.sendDialog(dialog, request, protocol);
                } catch (SipException e) {
                    log.error("获取dialog err",e);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        });

    }

    @Override
    public String getFromTag() {
        return "live";
    }
}
