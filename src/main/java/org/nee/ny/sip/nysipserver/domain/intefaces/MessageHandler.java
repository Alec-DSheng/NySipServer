package org.nee.ny.sip.nysipserver.domain.intefaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author: alec
 * Description:
 * @date: 09:03 2020-11-30
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageHandler {

    String name();
}
