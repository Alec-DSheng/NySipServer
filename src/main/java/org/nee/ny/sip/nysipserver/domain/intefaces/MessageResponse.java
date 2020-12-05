package org.nee.ny.sip.nysipserver.domain.intefaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author: alec
 * Description:
 * @date: 13:35 2020-12-05
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageResponse {

    String name();
}
