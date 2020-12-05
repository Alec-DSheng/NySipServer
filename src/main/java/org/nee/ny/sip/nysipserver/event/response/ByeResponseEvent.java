package org.nee.ny.sip.nysipserver.event.response;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.intefaces.MessageResponse;
import org.nee.ny.sip.nysipserver.event.MessageResponseAbstract;

/**
 * @Author: alec
 * Description:
 * @date: 13:20 2020-12-05
 */
@MessageResponse(name="BYE")
@Slf4j
public class ByeResponseEvent extends MessageResponseAbstract {
    @Override
    public void dealResponse() {

    }
}
