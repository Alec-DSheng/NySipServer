package org.nee.ny.sip.nysipserver.transaction.response.impl;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.transaction.factory.ServerTransactionFactory;
import org.nee.ny.sip.nysipserver.transaction.response.MessageResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sip.*;
import javax.sip.message.Response;
import java.util.Optional;

/**
 * @Author: alec
 * Description:
 * @date: 16:38 2020-11-27
 */
@Service
@Slf4j
public class SipMessageResponseHandler implements MessageResponseHandler {

    private final SipProvider sipTcpProvider;

    private final SipProvider sipUdpProvider;

    public SipMessageResponseHandler(@Autowired @Qualifier(value = "sipTcpProvider") SipProvider sipTcpProvider,
                                     @Autowired @Qualifier(value = "sipUdpProvider") SipProvider sipUdpProvider) {
        this.sipTcpProvider = sipTcpProvider;
        this.sipUdpProvider = sipUdpProvider;
    }

    @Override
    public void sendResponse(RequestEvent requestEvent, Response response) {
        ServerTransaction serverTransaction = Optional.ofNullable(requestEvent.getServerTransaction())
                .orElseGet(() ->
                        ServerTransactionFactory.getInstance().getServerTransaction(sipTcpProvider,sipUdpProvider,
                                requestEvent.getRequest()));
        try {
            serverTransaction.sendResponse(response);
            log.info("send success {}", response);
        } catch (SipException | InvalidArgumentException e) {
            log.error("发送消息失败", e);
        }
    }
}
