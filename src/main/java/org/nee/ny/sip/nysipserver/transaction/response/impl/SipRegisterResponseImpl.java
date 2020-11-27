package org.nee.ny.sip.nysipserver.transaction.response.impl;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.transaction.response.SipRegisterResponse;
import org.nee.ny.sip.nysipserver.utils.UniqueCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sip.header.ContactHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.header.WWWAuthenticateHeader;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import static gov.nist.javax.sip.clientauthutils.DigestServerAuthenticationHelper.DEFAULT_ALGORITHM;
import static gov.nist.javax.sip.clientauthutils.DigestServerAuthenticationHelper.DEFAULT_SCHEME;

/**
 * @Author: alec
 * Description:
 * @date: 15:07 2020-11-27
 */
@Service
@Slf4j
public class SipRegisterResponseImpl implements SipRegisterResponse {

    private final MessageFactory messageFactory;

    private final HeaderFactory headerFactory;

    private final SipServerProperties sipServerProperties;

    public SipRegisterResponseImpl(MessageFactory messageFactory, HeaderFactory headerFactory,
                                   SipServerProperties sipServerProperties) {
        this.messageFactory = messageFactory;
        this.headerFactory = headerFactory;
        this.sipServerProperties = sipServerProperties;
    }

    @Override
    public Response responseAuthenticationFailure(Request request) {
        Response response = null;
        try {
            response = messageFactory.createResponse(Response.UNAUTHORIZED, request);
            setResponseHeader(response);
        } catch (ParseException e) {
            log.error("create failure response error ", e);
        }
        return response;
    }

    @Override
    public Response responseAuthenticationSuccess(Request request) {
        Response response = null;
        try {
            response = messageFactory.createResponse(Response.OK, request);
            //添加date头
            response.addHeader(headerFactory.createDateHeader(Calendar.getInstance(Locale.ENGLISH)));
            //添加Contact头
            response.addHeader(request.getHeader(ContactHeader.NAME));
            //添加失效时间
            response.addHeader(request.getExpires());
        } catch (ParseException e) {
            log.error("create success response error ", e);
        }
        return response;
    }

    private void setResponseHeader(Response response) throws ParseException {
        WWWAuthenticateHeader wwwAuthenticateHeader = headerFactory.createWWWAuthenticateHeader(DEFAULT_SCHEME);
        wwwAuthenticateHeader.setNonce(UniqueCodeUtil.base64Code());
        wwwAuthenticateHeader.setDomain(sipServerProperties.getDomain());
        wwwAuthenticateHeader.setRealm(sipServerProperties.getDomain());
        wwwAuthenticateHeader.setStale(false);
        wwwAuthenticateHeader.setAlgorithm(DEFAULT_ALGORITHM);
        response.setHeader(wwwAuthenticateHeader);

    }
}
