package org.nee.ny.sip.nysipserver.transaction.command.message;

import org.nee.ny.sip.nysipserver.transaction.command.Command;

import javax.sip.message.Request;

/**
 * @Author: alec
 * Description: 发送message 命令
 * @date: 10:11 2020-12-01
 */
public interface MessageCommand<T>  extends Command<T> {

    String getViaTag();

    String getFromTag();

    String getToTag();

    Request createMessageRequest(T t);
}
