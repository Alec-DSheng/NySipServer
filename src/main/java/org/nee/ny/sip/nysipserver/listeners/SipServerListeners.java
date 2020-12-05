package org.nee.ny.sip.nysipserver.listeners;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.event.MessageEventAbstract;
import org.nee.ny.sip.nysipserver.event.MessageResponseAbstract;
import org.nee.ny.sip.nysipserver.listeners.factory.MessageEventFactory;
import org.nee.ny.sip.nysipserver.transaction.response.MessageResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.sip.*;
import javax.sip.header.CSeqHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

/**
 * @Author: alec
 * Description:
 * @date: 12:23 2020-11-27
 */
@Component
@Slf4j
public class SipServerListeners implements SipListener {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SipServerListeners(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void processRequest(RequestEvent requestEvent) {
        Request request = requestEvent.getRequest();
        log.info("listeners request method {}, {}", request.getMethod(), requestEvent.getDialog());
        MessageEventAbstract messageEventAbstract = MessageEventFactory.getInstance().
                getMessageEvent(requestEvent);
        applicationEventPublisher.publishEvent(messageEventAbstract);
    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {
        Response response = responseEvent.getResponse();
        int status = response.getStatusCode();
        if (status >= HttpStatus.OK.value() && status <= HttpStatus.MULTIPLE_CHOICES.value()) {
            CSeqHeader cseqHeader = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
            String method = cseqHeader.getMethod();
            log.info("响应恢复状态码 {} - {}", status, method);
            MessageResponseAbstract messageResponse = MessageEventFactory.getInstance().
                    getMessageResponse(method, responseEvent);
            applicationEventPublisher.publishEvent(messageResponse);
        }
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
