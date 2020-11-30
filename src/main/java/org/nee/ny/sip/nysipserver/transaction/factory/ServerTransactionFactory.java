package org.nee.ny.sip.nysipserver.transaction.factory;

import gov.nist.javax.sip.SipStackImpl;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.stack.SIPServerTransaction;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.enums.TransportType;

import javax.sip.*;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.Request;
import java.rmi.NotBoundException;
import java.util.Optional;

/**
 * @Author: alec
 * Description:
 * @date: 16:31 2020-11-27
 */
@Slf4j
public class ServerTransactionFactory {

    private ServerTransactionFactory () {}

    public static ServerTransactionFactory getInstance() {
        return new ServerTransactionFactory();
    }

    public ServerTransaction getServerTransaction(SipProvider sipTcpProvider, SipProvider sipUdpProvider, Request request) {
        ViaHeader reqViaHeader = (ViaHeader) request.getHeader(ViaHeader.NAME);
        String transport = reqViaHeader.getTransport();
        if (TransportType.TCP.name().equals(transport)) {
            return getServerTransaction(sipTcpProvider, request);
        } else if (TransportType.UDP.name().equals(transport)) {
            return getServerTransaction(sipUdpProvider, request);
        } else {
            return null;
        }

    }


    private ServerTransaction getServerTransaction (SipProvider sipProvider, Request request) {
        SipStackImpl sipStack =  (SipStackImpl)sipProvider.getSipStack();
        return Optional.ofNullable((SIPServerTransaction) sipStack.findTransaction((SIPRequest) request, true)).orElseGet(() -> {
            try {
                return (SIPServerTransaction) sipProvider.getNewServerTransaction(request);
            } catch (TransactionAlreadyExistsException | TransactionUnavailableException e) {
                log.error("get transaction error", e);
                return null;
            }
        });
    }

    public ClientTransaction getClientTransaction(SipProvider sipTcpProvider, SipProvider sipUdpProvider,
                                                  String transport, Request request) throws TransactionUnavailableException {
        if (TransportType.TCP.name().equals(transport)) {
            return sipTcpProvider.getNewClientTransaction(request);
        }
        return sipUdpProvider.getNewClientTransaction(request);
    }

    public CallIdHeader getCallIdHeader(SipProvider sipTcpProvider, SipProvider sipUdpProvider, String transport) {
        if (TransportType.TCP.name().equals(transport)) {
            return sipTcpProvider.getNewCallId();
        }
        return sipUdpProvider.getNewCallId();
    }
}
