package org.nee.ny.sip.nysipserver.listeners;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.event.response.InviteResponseEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.sip.Dialog;
import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import javax.sip.address.SipURI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.text.ParseException;

/**
 * @Author: alec
 * Description:
 * @date: 13:42 2020-12-05
 */
@Component
@Slf4j
public class ResponseListeners {

    @EventListener
    public void responseInvite(InviteResponseEvent inviteResponseEvent) {
        Response response =   inviteResponseEvent.getResponseEvent().getResponse();
        int statusCode = response.getStatusCode();
        log.info("响应状态码 {}", statusCode);
        if (response.getStatusCode() == HttpStatus.OK.value()) {
            Dialog dialog = inviteResponseEvent.getResponseEvent().getDialog();
            CSeqHeader cseq = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
            Request reqAck;
            try {
                //updateStream(response);
                reqAck = dialog.createAck(cseq.getSeqNumber());
                SipURI requestURI = (SipURI) reqAck.getRequestURI();
                ViaHeader viaHeader = (ViaHeader) response.getHeader(ViaHeader.NAME);
                requestURI.setHost(viaHeader.getHost());
                requestURI.setPort(viaHeader.getPort());
                reqAck.setRequestURI(requestURI);
                dialog.sendAck(reqAck);
            } catch (InvalidArgumentException | SipException | ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
