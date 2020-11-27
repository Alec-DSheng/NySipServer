package org.nee.ny.sip.nysipserver.event;

import lombok.Getter;

import javax.sip.RequestEvent;

/**
 * @Author: alec
 * Description: 消息事件
 * @date: 14:07 2020-11-27
 */
public abstract class MessageEvent {

    @Getter
    public RequestEvent requestEvent;

    public abstract void load();

    public void init (RequestEvent requestEvent) {
        this.requestEvent = requestEvent;
        this.load();
    }
}
