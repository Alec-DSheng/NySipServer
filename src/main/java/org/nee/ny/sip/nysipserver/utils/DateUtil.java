package org.nee.ny.sip.nysipserver.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: alec
 * Description:
 * @date: 19:10 2020-11-30
 */
@Slf4j
public class DateUtil {

    private static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getDateFormat()  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATA_FORMAT);
        return simpleDateFormat.format(new Date());
    }

    public static Date getDate(String dateVal) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATA_FORMAT);

        try {
            return simpleDateFormat.parse(dateVal);
        } catch (ParseException e) {
            log.error("转换日期错误", e);
        }
        return null;
    }

    public static long getDifferSeconds(Date beforeDate, Date afterDate) {
        long time = 1000 * 60;
        return (afterDate.getTime() - beforeDate.getTime())/time;
    }
}
