package org.nee.ny.sip.nysipserver.transaction.command.message;

import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.domain.Device;
import org.nee.ny.sip.nysipserver.transaction.command.CommandParams;
import org.nee.ny.sip.nysipserver.transaction.command.SipServerCommandRequest;
import org.nee.ny.sip.nysipserver.transaction.response.MessageResponseHandler;
import org.nee.ny.sip.nysipserver.utils.XmlObjectConvertUtil;

import javax.sip.message.Request;

/**
 * @Author: alec
 * Description:
 * @date: 10:42 2020-12-01
 */
@Slf4j
public abstract class MessageCommandAbstract implements MessageCommand<CommandParams> {

    private MessageResponseHandler messageResponseHandler;

    private SipServerCommandRequest sipServerCommandRequest;


    public MessageCommandAbstract (MessageResponseHandler messageResponseHandler, SipServerCommandRequest sipServerCommandRequest) {
        this.messageResponseHandler = messageResponseHandler;
        this.sipServerCommandRequest = sipServerCommandRequest;
    }

    public abstract CommandXmlQueryParams createCommandParams(Device device);

    @Override
    public Request createMessageRequest(CommandParams commandParams) {
        commandParams.setFrom(getFromTag());
        commandParams.setTo(getToTag());
        commandParams.setVia(getViaTag());
        return sipServerCommandRequest.createMessageRequest(commandParams).orElseThrow(RuntimeException::new);
    }

    @Override
    public void sendCommand(CommandParams commandParams) {
        String transport = commandParams.getDevice().getTransport();
        messageResponseHandler.sendResponse(transport, createMessageRequest(commandParams));
    }

    public void sendCommand(Device device) {
        CommandXmlQueryParams commandXmlQueryParams = createCommandParams(device);
        CommandParams commandParams = CommandParams.builder()
                .device(device)
                .content(XmlObjectConvertUtil.objectConvertXml(commandXmlQueryParams)).build();
        sendCommand(commandParams);
    }
}
