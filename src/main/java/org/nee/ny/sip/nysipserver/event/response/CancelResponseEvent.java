package org.nee.ny.sip.nysipserver.event.response;

import org.nee.ny.sip.nysipserver.domain.intefaces.MessageResponse;
import org.nee.ny.sip.nysipserver.event.MessageResponseAbstract;

/**
 * @Author: alec
 * Description:
 * @date: 13:20 2020-12-05
 */
@MessageResponse(name="CANCEL")
public class CancelResponseEvent  extends MessageResponseAbstract {

    @Override
    public void dealResponse() {

    }
}
