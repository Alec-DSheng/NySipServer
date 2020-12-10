package org.nee.ny.sip.nysipserver.interfaces.webhook.kit;

import lombok.*;

/**
 * @Author: alec
 * Description:
 * @date: 10:08 2020-12-10
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ZlMediaKitResponse {

    private final static Integer SUCCESS_CODE = 0;

    private Integer code;
    private String msg;
    private Boolean enableHls;
    private Boolean enableMP4;

    //无人观看是否关闭流
    private Boolean close;


    private ZlMediaKitResponse(Integer code) {
        this.code = code;
    }

    private ZlMediaKitResponse(Integer code, boolean close) {
        this.code = code;
        this.close = close;
    }

    private ZlMediaKitResponse(Integer code, boolean enableHls, boolean enableMP4) {
        this.code = code;
        this.msg = "success";
        this.enableHls = enableHls;
        this.enableMP4 = enableMP4;
    }

    public static ZlMediaKitResponse responseSuccess () {
        return new ZlMediaKitResponse(SUCCESS_CODE);
    }


    public static ZlMediaKitResponse responsePublishSuccess () {
        return new ZlMediaKitResponse(SUCCESS_CODE, true, true);
    }

    public static ZlMediaKitResponse reposeNoReader(boolean close) {
        return new ZlMediaKitResponse(SUCCESS_CODE, close);
    }
}
