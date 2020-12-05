package org.nee.ny.sip.nysipserver.transaction.command.Invite;

import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.domain.Device;
import org.nee.ny.sip.nysipserver.transaction.command.SipServerCommandRequest;
import org.nee.ny.sip.nysipserver.transaction.response.MessageResponseHandler;
import org.nee.ny.sip.nysipserver.transaction.session.StreamCodeSessionManager;
import org.nee.ny.sip.nysipserver.transaction.session.TransactionSessionManager;
import org.springframework.stereotype.Component;


/**
 * @Author: alec
 * Description:
 * @date: 09:34 2020-12-04
 */
@Component
public class VideoPlayCommand extends InviteCommandAbstract {

    private final SipServerProperties sipServerProperties;

    public VideoPlayCommand(MessageResponseHandler messageResponseHandler, SipServerCommandRequest sipServerCommandRequest,
                            StreamCodeSessionManager streamCodeSessionManager, SipServerProperties sipServerProperties,
                            TransactionSessionManager transactionSessionManager) {
        super(messageResponseHandler, sipServerCommandRequest,streamCodeSessionManager,sipServerProperties,transactionSessionManager);
        this.sipServerProperties = sipServerProperties;
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


    @Override
    public String getFromTag() {
        return "live";
    }
}
