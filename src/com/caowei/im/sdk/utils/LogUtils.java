package com.caowei.im.sdk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {
    public static void log(String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println(sdf.format(new Date()) + "：" + string);
    }
}
