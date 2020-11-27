package org.nee.ny.sip.nysipserver.listeners;

import org.springframework.stereotype.Component;

import javax.sip.*;

/**
 * @Author: alec
 * Description:
 * @date: 12:23 2020-11-27
 */
@Component
public class SipServerListeners  implements SipListener {


    @Override
    public void processRequest(RequestEvent requestEvent) {

    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {

    }

    @Override
    public void processTimeout(TimeoutEvent timeoutEvent) {

    }

    @Override
    public void processIOException(IOExceptionEvent ioExceptionEvent) {

    }

    @Override
    public void processTransactionTerminated(TransactionTerminatedEvent transactionTerminatedEvent) {

    }

    @Override
    public void processDialogTerminated(DialogTerminatedEvent dialogTerminatedEvent) {

    }
}
