package org.nee.ny.sip.nysipserver.interfaces.webhook.kit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: alec
 * Description:
 * @date: 10:04 2020-12-10
 */
@Getter
@Setter
@ToString
public class ZlMediaKitRequest {
    private String app;
    private String id;
    private String ip;
    private String params;
    private Integer port;
    private String schema;
    private String stream;
    private String vhost;
    private String mediaServerId;

    //流注册或注销
    private boolean regist;

    public Boolean isRtp () {
        return schema.equals("rtsp");
    }
}
