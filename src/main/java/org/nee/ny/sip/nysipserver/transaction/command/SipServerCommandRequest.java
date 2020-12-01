package org.nee.ny.sip.nysipserver.transaction.command;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.domain.Device;
import org.nee.ny.sip.nysipserver.transaction.factory.ServerTransactionFactory;
import org.springframework.stereotype.Component;

import javax.sip.InvalidArgumentException;
import javax.sip.SipProvider;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.address.SipURI;
import javax.sip.header.*;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import java.text.ParseException;
import java.util.Collections;
import java.util.Optional;

/**
 * @Author: alec
 * Description: 创建下发消息请求request
 *
 * @date: 13:17 2020-11-10
 */
@Component
@Slf4j
public class SipServerCommandRequest implements SipRequest {

    private final HeaderFactory headerFactory;

    private final AddressFactory addressFactory;

    private final SipProvider sipTcpProvider;

    private final SipProvider sipUdpProvider;

    private final SipServerProperties sipServerProperties;

    private final MessageFactory messageFactory;



    public SipServerCommandRequest(HeaderFactory headerFactory, AddressFactory addressFactory,
                                   SipProvider sipTcpProvider,
                                   SipProvider sipUdpProvider, SipServerProperties sipServerProperties,
                                   MessageFactory messageFactory) {
        this.headerFactory = headerFactory;
        this.addressFactory = addressFactory;
        this.sipTcpProvider = sipTcpProvider;
        this.sipUdpProvider = sipUdpProvider;
        this.sipServerProperties = sipServerProperties;
        this.messageFactory = messageFactory;
    }

    @Override
    public Optional<Request> createMessageRequest(CommandParams playerCommandParams) {
        try {
            Device device = playerCommandParams.getDevice();
            String transport = playerCommandParams.getDevice().getTransport();
            String host = device.getHost() + ":" + device.getPort();
            log.info("=====request params host : {}, transport : {} =======", host,transport);
            SipURI sipURI = addressFactory.createSipURI(device.getDeviceId(), host );
            CallIdHeader callIdHeader = ServerTransactionFactory.getInstance().getCallIdHeader(sipTcpProvider,
                    sipUdpProvider, transport);
            CSeqHeader cSeqHeader = headerFactory.createCSeqHeader(1L, Request.MESSAGE);
            MaxForwardsHeader maxForwards = headerFactory.createMaxForwardsHeader(70);
            FromHeader fromHeader = getFromHeaderAddress(playerCommandParams.getFrom());
            ToHeader toHeader = getToHeaderAddress(device.getDeviceId(), playerCommandParams.getTo());
            ViaHeader viaHeader = getViaHeader(transport, playerCommandParams.getVia());
            ContentTypeHeader contentTypeHeader = headerFactory.createContentTypeHeader("Application", "MANSCDP+xml");
            Request request = messageFactory.createRequest(sipURI,Request.MESSAGE, callIdHeader, cSeqHeader,
                    fromHeader, toHeader, Collections.singletonList(viaHeader), maxForwards);
            request.setContent(playerCommandParams.getContent(), contentTypeHeader);
            return Optional.of(request);
        } catch (ParseException  | InvalidArgumentException e) {
            log.error("ParseException", e);
        }
        return Optional.empty();
    }

    private FromHeader getFromHeaderAddress(String tag) throws ParseException {
        String host = sipServerProperties.getHost() + ":" + sipServerProperties.getPort();
        SipURI sipUrl = addressFactory.createSipURI(sipServerProperties.getId(), host);
        Address address = addressFactory.createAddress(sipUrl);
        log.info("getFromHeaderAddress{}, {}, {}, {}",host, sipServerProperties.getId(),
                sipUrl, tag);
        return headerFactory.createFromHeader(address, tag);
    }

    private FromHeader getFromHeaderAddressChannel(String tag) throws ParseException {
        SipURI sipUrl = addressFactory.createSipURI(sipServerProperties.getId(), sipServerProperties.getDomain());
        Address address = addressFactory.createAddress(sipUrl);
        log.info("getFromHeaderAddressChannel{}, {}, {}, {}",sipServerProperties.getId(), sipServerProperties.getDomain(),
                sipUrl, tag);
        return headerFactory.createFromHeader(address, tag);
    }

    private ToHeader getToHeaderAddress(String code, String tag) throws ParseException {
        SipURI sipUrl = addressFactory.createSipURI(code, sipServerProperties.getDomain());
        Address address = addressFactory.createAddress(sipUrl);
        return headerFactory.createToHeader(address, tag);
    }

    private ViaHeader getViaHeader(String transport, String tag) throws ParseException, InvalidArgumentException {
        ViaHeader viaHeader = headerFactory.createViaHeader(sipServerProperties.getHost(), sipServerProperties.getPort(),
                transport, tag);
        viaHeader.setRPort();
        return viaHeader;
    }
//
//    private ViaHeader getViaHeaderChannel(PlayerCommandParams playerCommandParams, String tag) throws ParseException, InvalidArgumentException {
//        ViaHeader viaHeader = headerFactory.createViaHeader(playerCommandParams.getHost(),playerCommandParams.getPort(),
//                playerCommandParams.getTransport(), tag);
//        viaHeader.setRPort();
//        return viaHeader;
//    }
}
