package org.nee.ny.sip.nysipserver.transaction.command;

/**
 * @Author: alec
 * Description: 定义执行命令接口
 * @date: 10:08 2020-12-01
 */
public interface Command {

    void sendCommand(CommandParams commandParams);

}
