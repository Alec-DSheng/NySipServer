package org.nee.ny.sip.nysipserver.transaction.response;

import javax.sip.RequestEvent;
import javax.sip.message.Response;

/**
 * @Author: alec
 * Description:
 * @date: 16:37 2020-11-27
 */
public interface MessageResponseHandler {

    void sendResponse(RequestEvent requestEvent, Response response);
}
