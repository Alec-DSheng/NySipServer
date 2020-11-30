package org.nee.ny.sip.nysipserver.event;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.sip.RequestEvent;

/**
 * @Author: alec
 * Description:
 * 定义消息事件
 * @date: 14:07 2020-11-27
 */
@Slf4j
public abstract class MessageEventAbstract {

    @Getter
    public RequestEvent requestEvent;

    public abstract void load();


    public void init (RequestEvent requestEvent) {
        this.requestEvent = requestEvent;
        this.load();
    }
}
