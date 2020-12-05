package org.nee.ny.sip.nysipserver.transaction.command.Invite;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.configuration.SipServerProperties;
import org.nee.ny.sip.nysipserver.domain.Device;
import org.nee.ny.sip.nysipserver.transaction.command.SipServerCommandRequest;
import org.nee.ny.sip.nysipserver.transaction.command.message.MessageCommand;
import org.nee.ny.sip.nysipserver.transaction.response.MessageResponseHandler;
import org.nee.ny.sip.nysipserver.transaction.session.StreamCodeSessionManager;
import org.nee.ny.sip.nysipserver.transaction.session.TransactionSessionManager;

import javax.sip.ClientTransaction;
import javax.sip.message.Request;

/**
 * @Author: alec
 * Description:
 * @date: 09:34 2020-12-04
 */
@Slf4j
public abstract class InviteCommandAbstract implements MessageCommand<PlayCommandParams>  {

    private MessageResponseHandler messageResponseHandler;

    private SipServerCommandRequest sipServerCommandRequest;

    private StreamCodeSessionManager streamCodeSessionManager;

    private SipServerProperties sipServerProperties;

    private TransactionSessionManager transactionSessionManager;



    InviteCommandAbstract(MessageResponseHandler messageResponseHandler, SipServerCommandRequest sipServerCommandRequest,
                          StreamCodeSessionManager streamCodeSessionManager,SipServerProperties sipServerProperties,
                          TransactionSessionManager transactionSessionManager) {
        this.messageResponseHandler = messageResponseHandler;
        this.sipServerCommandRequest = sipServerCommandRequest;
        this.streamCodeSessionManager = streamCodeSessionManager;
        this.sipServerProperties = sipServerProperties;
        this.transactionSessionManager = transactionSessionManager;
    }

    public abstract PlayCommandParams createPlayCommandParams(Device device,  String channelId, String code);

    @Override
    public Request createMessageRequest(PlayCommandParams commandParams) {
        commandParams.setFrom(getFromTag());
        return sipServerCommandRequest.createInviteRequest(commandParams).orElseThrow(RuntimeException::new);
    }

    @Override
    public void sendCommand(PlayCommandParams commandParams) {
        String transport = commandParams.getDevice().getTransport();
        ClientTransaction clientTransaction = messageResponseHandler.sendResponse(transport, createMessageRequest(commandParams));
        transactionSessionManager.put(commandParams.getStreamCode(), clientTransaction);
    }

    public String sendCommand(Device device,  String channelId) {
        String code = streamCodeSessionManager.createStreamCode();
        String streamCode = String.format("%s%s%s", "0", sipServerProperties.getDomain().substring(3, 8), code);
        log.info("分配推流code {}", streamCode);
        PlayCommandParams playCommandParams =  createPlayCommandParams(device, channelId, streamCode);
        sendCommand(playCommandParams);
        return streamCode;
    }

    @Override
    public String getToTag() {
        return null;
    }

    @Override
    public String getViaTag() {
        return null;
    }
}
