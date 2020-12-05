package org.nee.ny.sip.nysipserver.event;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.sip.Dialog;
import javax.sip.InvalidArgumentException;
import javax.sip.ResponseEvent;
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
 * @date: 13:14 2020-12-05
 */
@Slf4j
public abstract class MessageResponseAbstract {

    @Getter
    private ResponseEvent responseEvent;

    public void init(ResponseEvent responseEvent) {
        this.responseEvent = responseEvent;
//        this.dialog = responseEvent.getDialog();
//        Response response = responseEvent.getResponse();
//        CSeqHeader cseq = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
//        try {
//            this.reqAck = dialog.createAck(cseq.getSeqNumber());
//            SipURI requestURI = (SipURI) reqAck.getRequestURI();
//            ViaHeader viaHeader = (ViaHeader) response.getHeader(ViaHeader.NAME);
//            requestURI.setHost(viaHeader.getHost());
//            requestURI.setPort(viaHeader.getPort());
//            reqAck.setRequestURI(requestURI);
//        } catch (InvalidArgumentException | SipException |ParseException e) {
//            log.error("get request ack error ", e);
//        }
    }
}
