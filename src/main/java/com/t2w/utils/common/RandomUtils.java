package com.t2w.utils.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-08-08 09:56
 * @name com.t2w.utils.common.RandomUtils.java
 * @see describing 随机工具类
 */
public class RandomUtils {

    /** 随机基础字符串 */
    private static final String BASE_RANDOM = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random RANDOM = new Random();

    /**
     * 由时间进行命名
     * @return
     */
    public static String getStringByTime(int length) {
        StringBuilder builder = new StringBuilder();
        String date = new SimpleDateFormat("yyyy-MM-dd&HH-mm-ss").format(new Date());
        builder.append(date).append("&");
        long time = new Date().getTime();
        builder.append(time).append("&");
        for (int i = 0; i < length; i++) {
            builder.append(BASE_RANDOM.charAt(RANDOM.nextInt(BASE_RANDOM.length())));
        }
        return builder.toString();
    }

    public static String getTime() {
        StringBuilder builder = new StringBuilder();
        Date now = new Date();
        String date = new SimpleDateFormat("yyyy-MM-dd&HH-mm-ss").format(now);
        builder.append(date).append("&");
        long millisecond = now.getTime() % 1000;
        builder.append(millisecond);
        return builder.toString();
    }


}
