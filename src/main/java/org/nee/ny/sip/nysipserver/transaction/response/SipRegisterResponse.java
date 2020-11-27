package org.nee.ny.sip.nysipserver.transaction.response;

import javax.sip.message.Request;
import javax.sip.message.Response;

/**
 * @Author: alec
 * Description: Sip 请求认证响应
 * @date: 15:05 2020-11-27
 */
public interface SipRegisterResponse extends SipResponse {

    Response responseAuthenticationFailure(Request request);

    Response responseAuthenticationSuccess(Request request);
}
