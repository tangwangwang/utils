package com.t2w.utils.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 09:51
 * @name com.t2w.utils.common.StringUtils.java
 * @see 字符串工具类
 */
public class StringUtils {

    /**
     * @param src 需要转化的源字符串
     * @return java.lang.String 转化完成后的字符串
     * @date 2019-07-29 10:12
     * @see 字符串首字母大写（name ---> Name）
     */
    public static String acronymUppercase(String src) {
        if (StringUtils.isEmpty(src))
            return "";
        byte[] items = src.getBytes();
        if (items[0] >= 'a' && items[0] <= 'z') {
            items[0] = (byte) ((char) items[0] - 'a' + 'A');
        }
        return new String(items);
    }

    /**
     * @param src 需要转化的源字符串
     * @return java.lang.String 转化完成后的字符串
     * @date 2019-08-19 15:07
     * @see describing 字符串首字母小写（Name ---> name）
     */
    public static String acronymLowercase(String src) {
        if (StringUtils.isEmpty(src))
            return "";
        byte[] items = src.getBytes();
        if (items[0] >= 'A' && items[0] <= 'Z') {
            items[0] = (byte) ((char) items[0] - 'A' + 'a');
        }
        return new String(items);
    }

    /**
     * @param src 源字符串
     * @return java.lang.String 转换后的字符串
     * @date 2019-07-29 10:14
     * @see 驼峰法转下划线
     */
    public static String camel2Underline(String src) {
        if (isEmpty(src)) {
            return "";
        }
        src = String.valueOf(src.charAt(0)).toUpperCase().concat(src.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(src);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == src.length() ? "" : "_");
        }
        return sb.toString();
    }

    /**
     * @param src        源字符串
     * @param greatCamel 大小驼峰,是否为小驼峰(驼峰，第一个字符是大写还是小写)
     * @return java.lang.String 转换后的字符串
     * @date 2019-07-29 10:14
     * @see 下划线转驼峰法(默认小驼峰)
     */
    public static String underline2Camel(String src, boolean... greatCamel) {
        if (isEmpty(src)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(src);
        //匹配正则表达式
        while (matcher.find()) {
            String word = matcher.group();
            //当 greatCamel 是 空 或 是 false 时，则为小驼峰
            if ((greatCamel.length == 0 || !greatCamel[0]) && matcher.start() == 0) {
                sb.append(Character.toLowerCase(word.charAt(0)));
            } else {
                sb.append(Character.toUpperCase(word.charAt(0)));
            }
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * @param src 源字符串
     * @return boolean 是否为 null 或 ""
     * @date 2019-07-29 10:16
     * @see 为 null 或 "" 字符串则返回 true ,否则返回 false
     */
    public static boolean isEmpty(String src) {
        return src == null || "".equals(src);
    }

    /**
     * @param src 源字符串
     * @return boolean 是否不为 null 或 ""
     * @date 2019-07-29 10:17
     * @see 为 null 或 "" 字符串则返回 false ,否则返回 true
     */
    public static boolean isNotEmpty(String src) {
        return !isEmpty(src);
    }

    /**
     * @param src 源字符串
     * @return java.lang.String 字符串的后缀
     * @date 2019-07-29 10:18
     * @see 返回字符串的后缀名，如果没有则返回 ""
     */
    public static String getSuffix(String src) {
        if (isEmpty(src))
            return "";
        int index = src.lastIndexOf('.');
        if (index == -1)
            return "";
        else
            return src.substring(++index);
    }

    /**
     * @param src 传入字符串
     * @return boolean 是整数返回true,否则返回false
     * @date 2019-07-29 10:19
     * @see 判断字符是否为数字
     */
    public static boolean isInteger(String src) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(src).matches();
    }

    /**
     * @param src       源字符串数组
     * @param separator 各个字符串之间的分隔符
     * @return java.lang.String 转化后的字符串
     * @date 2019-08-07 11:20
     * @see describing 将字符串数组按指定分隔符拼接为字符串
     */
    public static String toString(String[] src, String separator) {
        StringBuffer buffer = new StringBuffer();
        for (int index = 0; index < src.length; index++) {
            buffer.append(src[index]);
            if (isNotEmpty(separator) && index != src.length - 1) {
                buffer.append(separator);
            }
        }
        return buffer.toString();
    }

    /**
     * @param src 源字符串数组
     * @return java.lang.String 转化后的字符串
     * @date 2019-08-07 11:21
     * @see describing 将字符串数组拼接为字符串
     */
    public static String toString(String[] src) {
        return toString(src, null);
    }

    /**
     * @param src       源字符数组
     * @param separator 各个字符之间的分隔符
     * @return java.lang.String 转化后的字符串
     * @date 2019-08-07 11:25
     * @see describing 将字符数组按指定分隔符拼接为字符串
     */
    public static String toString(char[] src, String separator) {
        StringBuffer buffer = new StringBuffer();
        for (int index = 0; index < src.length; index++) {
            buffer.append(src[index]);
            if (isNotEmpty(separator) && index != src.length - 1) {
                buffer.append(separator);
            }
        }
        return buffer.toString();
    }

    /**
     * @param src 源字符数组
     * @return java.lang.String 转化后的字符串
     * @date 2019-08-07 11:24
     * @see describing 将字符数组拼接为字符串
     */
    public static String toString(char[] src) {
        return toString(src, null);
    }

    /**
     * @param bytes 二进制 byte 数组
     * @return java.lang.String 16进制形式的字符串
     * @date 2019-08-08 20:32
     * @see describing 将二进制转换成16进制
     */
    public static String toHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte temp : bytes) {
            String hex = Integer.toHexString(temp & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            builder.append(hex.toUpperCase());
        }
        return builder.toString();
    }

    /**
     * @param hexString 16进制形式的字符串
     * @return byte[] 二进制 byte 数组
     * @date 2019-08-08 20:41
     * @see describing 将16进制转换为二进制（字符串长度必须为 2 的倍数）;
     */
    public static byte[] toBytes(String hexString) {
        if (hexString.length() < 1)
            return new byte[0];
        if (hexString.length() % 2 == 1)
            return new byte[0];
        byte[] result = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length() / 2; i++) {
            int high = Integer.parseInt(hexString.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexString.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
