package org.nee.ny.sip.nysipserver.event;

import gov.nist.javax.sip.header.CSeq;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageHandler;

import javax.sip.Dialog;
import javax.sip.message.Request;

/**
 * @Author: alec
 * Description:
 * @date: 08:47 2020-11-30
 */
@Slf4j
@MessageHandler(name = "ACK")
public class AckEvent extends MessageEventAbstract {

    @Getter
    private long seqNumber;

    @Getter
    private Dialog dialog;

    @Override
    public void load() {
        Request request = requestEvent.getRequest();
        this.dialog = requestEvent.getDialog();
        CSeq csReq = (CSeq) request.getHeader(CSeq.NAME);
        seqNumber = csReq.getSeqNumber();
    }
}
