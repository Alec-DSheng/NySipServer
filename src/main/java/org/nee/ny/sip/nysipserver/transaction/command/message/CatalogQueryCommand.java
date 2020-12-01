package org.nee.ny.sip.nysipserver.transaction.command.message;

import org.nee.ny.sip.nysipserver.domain.Device;
import org.nee.ny.sip.nysipserver.transaction.command.CommandParams;
import org.nee.ny.sip.nysipserver.transaction.command.SipServerCommandRequest;
import org.nee.ny.sip.nysipserver.transaction.response.MessageResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Author: alec
 * Description:
 * @date: 10:17 2020-12-01
 */
@Component
public class CatalogQueryCommand extends MessageCommandAbstract  implements MessageCommand{

    private final static String TYPE_CATE = "Catalog";

    @Autowired
    public CatalogQueryCommand(MessageResponseHandler messageResponseHandler,
                               SipServerCommandRequest sipServerCommandRequest ) {
        super(messageResponseHandler,sipServerCommandRequest);
    }

    @Override
    public CommandXmlQueryParams createCommandParams(Device device) {
        return new CommandXmlQueryParams(TYPE_CATE, device.getDeviceId());
    }

    @Override
    public String getViaTag() {
        return "ViaCatalogBranch";
    }

    @Override
    public String getFromTag() {
        return "FromCatalogTag";
    }

    @Override
    public String getToTag() {
        return "ToCatalogTag";
    }
}
