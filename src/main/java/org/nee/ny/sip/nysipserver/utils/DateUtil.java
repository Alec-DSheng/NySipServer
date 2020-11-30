package org.nee.ny.sip.nysipserver.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: alec
 * Description:
 * @date: 19:10 2020-11-30
 */
public class DateUtil {

    private static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getDateFormat()  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATA_FORMAT);
        return simpleDateFormat.format(new Date());
    }
}
