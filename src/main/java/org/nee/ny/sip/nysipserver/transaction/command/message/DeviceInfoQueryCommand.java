package org.nee.ny.sip.nysipserver.transaction.command.message;

import org.nee.ny.sip.nysipserver.domain.Device;
import org.nee.ny.sip.nysipserver.transaction.command.SipServerCommandRequest;
import org.nee.ny.sip.nysipserver.transaction.response.MessageResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Author: alec
 * Description:
 * @date: 10:16 2020-12-01
 */
@Component
public class DeviceInfoQueryCommand extends MessageCommandAbstract implements MessageCommand{
    private final static String TYPE_INFO = "DeviceInfo";

    @Autowired
    public DeviceInfoQueryCommand(MessageResponseHandler messageResponseHandler, SipServerCommandRequest sipServerCommandRequest ) {
        super(messageResponseHandler,sipServerCommandRequest);
    }

    @Override
    public CommandXmlQueryParams createCommandParams(Device device) {
        return new CommandXmlQueryParams(TYPE_INFO, device.getDeviceId());
    }


    @Override
    public String getViaTag() {
        return "ViaDeviceInfoBranch";
    }

    @Override
    public String getFromTag() {
        return "FromDeviceInfoTag";
    }

    @Override
    public String getToTag() {
        return "ToDeviceInfoTag";
    }


}
