package org.nee.ny.sip.nysipserver.event;

import lombok.Getter;
import javax.sip.ResponseEvent;

/**
 * @Author: alec
 * Description:
 * @date: 13:14 2020-12-05
 */
public class MessageResponseAbstract {

    @Getter
    public ResponseEvent responseEvent;
}
