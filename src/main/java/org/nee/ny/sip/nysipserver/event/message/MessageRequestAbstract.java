package org.nee.ny.sip.nysipserver.event.message;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.nee.ny.sip.nysipserver.utils.XmlObjectConvertUtil;

import javax.sip.RequestEvent;
import javax.sip.message.Request;
import java.io.UnsupportedEncodingException;

/**
 * @Author: alec
 * Description: 上报Message消息后消息为message请求解析
 * 解析bean数据,解析后做判断然后下发事件
 * @date: 09:56 2020-11-30
 */

@Slf4j
public abstract class MessageRequestAbstract {

    @Getter
    public RequestEvent requestEvent;

    @Getter
    public String content;

    @Getter
    public MessageRequestAbstract messageRequestAbstract;


    public void load () {
        Request request = requestEvent.getRequest();
        try {
            this.content = new String(request.getRawContent(), "gbk");
        } catch (UnsupportedEncodingException e) {
            log.error("解析编码错误", e);
        }
    }

    public void init(RequestEvent requestEven) {
        this.requestEvent = requestEven;
        this.load();
    }
}
