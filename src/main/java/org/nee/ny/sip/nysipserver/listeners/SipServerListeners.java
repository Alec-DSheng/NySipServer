package org.nee.ny.sip.nysipserver.listeners;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.event.MessageEventAbstract;
import org.nee.ny.sip.nysipserver.listeners.factory.MessageEventFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.sip.*;
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

    private final ApplicationEventPublisher publisher;

    public SipServerListeners(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void processRequest(RequestEvent requestEvent) {
        Request request = requestEvent.getRequest();
        log.info("listeners request method {}", request.getMethod());
        MessageEventAbstract messageEventAbstract = MessageEventFactory.getInstance().getMessageEvent(requestEvent);
        publisher.publishEvent(messageEventAbstract);
    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {
        Response response = responseEvent.getResponse();
        log.info("listeners response StatusCode {}", response.getStatusCode());
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
