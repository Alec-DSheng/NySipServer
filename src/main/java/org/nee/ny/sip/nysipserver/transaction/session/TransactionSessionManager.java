package org.nee.ny.sip.nysipserver.transaction.session;

import org.springframework.stereotype.Component;

import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: alec
 * Description: 管理Session
 * @date: 15:05 2020-12-04
 */
@Component
public class TransactionSessionManager {

    private final static Map<String, ClientTransaction> TRANSACTION_MAP = new ConcurrentHashMap<>();


    public void put(String streamCode, ClientTransaction clientTransaction) {
        TRANSACTION_MAP.put(streamCode, clientTransaction);
    }

    public Optional<ClientTransaction> get(String streamCode) {
        return Optional.ofNullable(TRANSACTION_MAP.get(streamCode));
    }

    public void destroy(String streamCode) {
        get(streamCode).ifPresent(clientTransaction -> {
            Optional.ofNullable(clientTransaction.getDialog()).ifPresent(Dialog::delete);
            TRANSACTION_MAP.remove(streamCode);
        });
    }
}
