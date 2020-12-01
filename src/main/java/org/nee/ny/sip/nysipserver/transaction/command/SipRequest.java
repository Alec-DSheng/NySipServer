package org.nee.ny.sip.nysipserver.transaction.command;


import javax.sip.message.Request;
import java.util.Optional;

/**
 * @Author: alec
 * Description:
 * @date: 13:16 2020-11-10
 */
public interface SipRequest {

    Optional<Request> createMessageRequest(CommandParams commandParams);

//    Optional<Request> createInviteRequest(PlayerCommandParams playerCommandParams, CommandTagEnums commandTagEnums);
//
//    Optional<Request> createRequest(Device device, CommandTagEnums commandTagEnums, String message);

}
