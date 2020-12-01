package org.nee.ny.sip.nysipserver.configuration;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.listeners.SipServerListeners;
import org.nee.ny.sip.nysipserver.utils.IntentAddressUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sip.*;
import javax.sip.address.AddressFactory;
import javax.sip.header.HeaderFactory;
import javax.sip.message.MessageFactory;
import java.net.Inet4Address;
import java.rmi.UnknownHostException;
import java.util.Properties;
import java.util.TooManyListenersException;

/**
 * @Author: alec
 * Description:
 * @date: 13:12 2020-11-27
 */
@Configuration
@Slf4j
public class SipServerConfiguration {

    private final SipServerProperties sipServerProperties;

    public final SipServerListeners sipServerListeners;


    public SipServerConfiguration(SipServerProperties sipServerProperties, SipServerListeners sipServerListeners) {
        this.sipServerProperties = sipServerProperties;
        this.sipServerListeners = sipServerListeners;
    }

    @Bean
    public SipFactory sipFactory() {
        SipFactory sipFactory =  SipFactory.getInstance();
        log.info("sip factory name {}", sipFactory.getPathName());
        return sipFactory;
    }

    @Bean
    public HeaderFactory headerFactory (SipFactory sipFactory) throws PeerUnavailableException {
        return sipFactory.createHeaderFactory();
    }

    @Bean
    public AddressFactory addressFactory (SipFactory sipFactory) throws PeerUnavailableException {
        return sipFactory.createAddressFactory();
    }

    @Bean
    public MessageFactory messageFactory (SipFactory sipFactory) throws PeerUnavailableException {
        return sipFactory.createMessageFactory();
    }

    @Bean
    public SipStack sipStack (SipFactory sipFactory) throws PeerUnavailableException {
        Properties properties = new Properties();
        properties.setProperty("javax.sip.STACK_NAME", "GB28181_SIP");
        properties.setProperty("javax.sip.IP_ADDRESS", sipServerProperties.getHost());
        return sipFactory.createSipStack(properties);
    }

    @Bean
    public SipProvider sipTcpProvider(SipStack sipStack) throws InvalidArgumentException,
            TransportNotSupportedException, ObjectInUseException, TooManyListenersException {
        ListeningPoint tcpListeningPoint = sipStack.createListeningPoint(sipServerProperties.getHost(),
                sipServerProperties.getPort(), ListeningPoint.TCP);
        SipProvider sipProvider = sipStack.createSipProvider(tcpListeningPoint);
        sipProvider.addSipListener(sipServerListeners);
        log.info("Sip Server TCP 启动成功 ip {} port {}" , tcpListeningPoint.getIPAddress() , tcpListeningPoint.getPort());
        return sipProvider;
    }
    @Bean
    public SipProvider sipUdpProvider(SipStack sipStack) throws InvalidArgumentException,
            TransportNotSupportedException, ObjectInUseException, TooManyListenersException {
        ListeningPoint udpListeningPoint = sipStack.createListeningPoint(sipServerProperties.getHost(),
                sipServerProperties.getPort(), ListeningPoint.UDP);
        SipProvider sipProvider = sipStack.createSipProvider(udpListeningPoint);
        sipProvider.addSipListener(sipServerListeners);
        log.info("Sip Server UDP 启动成功 ip {}, port {}" , udpListeningPoint.getIPAddress(),udpListeningPoint.getPort());
        return sipProvider;
    }

}
