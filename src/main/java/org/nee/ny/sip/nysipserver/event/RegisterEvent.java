package org.nee.ny.sip.nysipserver.event;


import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.header.Expires;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageHandler;
import org.nee.ny.sip.nysipserver.event.MessageEventAbstract;
import org.nee.ny.sip.nysipserver.utils.MD5Util;

import javax.sip.header.AuthorizationHeader;
import javax.sip.header.ExpiresHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.Request;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author: alec
 * Description: 注册事件
 * @date: 14:08 2020-11-27
 */
@Slf4j
@Setter
@Getter
@MessageHandler(name = "REGISTER")
public class RegisterEvent extends MessageEventAbstract {

    private Boolean destroy = false;

    private  AuthorizationHeader authHeader;

    private String deviceId;

    private String host;

    private Integer port;

    private String transport;


    @Override
    public void load() {
        Request request = requestEvent.getRequest();
        //加载注册相关属性事件
        this.authHeader = (AuthorizationHeader) request.getHeader(AuthorizationHeader.NAME);
        FromHeader fromHeader = (FromHeader) request.getHeader(FromHeader.NAME);
        ViaHeader viaHeader = (ViaHeader) request.getHeader(ViaHeader.NAME);
        SipUri uri = (SipUri)  fromHeader.getAddress().getURI();
        this.deviceId = uri.getUser();
        this.host = viaHeader.getHost();
        this.port = viaHeader.getPort();
        this.transport = viaHeader.getTransport();

        ExpiresHeader expiresHeader = (ExpiresHeader) request.getHeader(Expires.NAME);
        int expires  = Optional.of(expiresHeader.getExpires()).orElse(1);
        this.destroy = expires ==0;
    }

    public Boolean validateAuthorization(String password) {
        if (Objects.isNull(authHeader)) {
            return false;
        }
        String validateFrom = String.format("%s:%s:%s", authHeader.getUsername(), authHeader.getRealm(), password);
        String hlxFrom = MD5Util.md5Password(validateFrom);

        String valueTo = String.format("%s:%s", requestEvent.getRequest().getMethod(), authHeader.getURI().toString());
        String hlxTo = MD5Util.md5Password(valueTo);

        String response = String.format("%s:%s:%s",hlxFrom, authHeader.getNonce(),hlxTo);
        String hlxResponse = MD5Util.md5Password(response);
        log.info("response {}, {} ", hlxResponse, authHeader.getResponse());
        return hlxResponse.equals(authHeader.getResponse());
    }
}
