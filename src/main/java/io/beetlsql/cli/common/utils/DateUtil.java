package io.beetlsql.cli.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作用：时间操作工具类
 * 作者：Tiddler
 * 时间：2019/1/21 18：28
 * 类名： DateUtil
 **/
public class DateUtil extends Date {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 当前时间字符串
     *
     * @return
     */
    public static String nowDateString() {
        return format.format(new Date());
    }

    /**
     * Date转换为字符串,yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return format.format(date);
    }

    /**
     * 字符串转时间，yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @return
     */
    public static Date stringToDate(String dateStr) {
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
