package org.nee.ny.sip.nysipserver.transaction.command;


import org.nee.ny.sip.nysipserver.transaction.command.Invite.PlayCommandParams;

import javax.sip.message.Request;
import java.util.Optional;

/**
 * @Author: alec
 * Description:
 * @date: 13:16 2020-11-10
 */
public interface SipRequest {

    Optional<Request> createMessageRequest(CommandParams commandParams);

    Optional<Request> createInviteRequest(PlayCommandParams playerCommandParams);
//
//    Optional<Request> createRequest(Device device, CommandTagEnums commandTagEnums, String message);

}
