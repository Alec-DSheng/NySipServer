package org.nee.ny.sip.nysipserver.event;

import gov.nist.javax.sip.header.CSeq;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageHandler;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import javax.sip.message.Request;

/**
 * @Author: alec
 * Description:
 * @date: 08:47 2020-11-30
 */
@Slf4j
@MessageHandler(name = "ACK")
public class AckEvent extends MessageEventAbstract {

    @Override
    public void load() {
//        CSeq csReq = (CSeq) requestEvent.getRequest().getHeader(CSeq.NAME);
//        try {
//            log.info("{}", requestEvent.getDialog());
//            log.info("{}", csReq);
//            this.ackRequest = requestEvent.getDialog().createAck(csReq.getSeqNumber());
//        } catch (InvalidArgumentException | SipException e) {
//            e.printStackTrace();
//            log.error("ack error ", e);
//        }
    }
}
