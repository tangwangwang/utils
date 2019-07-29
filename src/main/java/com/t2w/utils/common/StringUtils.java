package com.t2w.utils.common;

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
    public static String capitalized(String src) {
        if (StringUtils.isEmpty(src))
            return "";
        byte[] items = src.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
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
     * @param smallCamel 大小驼峰,是否为小驼峰(驼峰，第一个字符是大写还是小写)
     * @return java.lang.String 转换后的字符串
     * @date 2019-07-29 10:14
     * @see 下划线转驼峰法(默认小驼峰)
     */
    public static String underline2Camel(String src, boolean... smallCamel) {
        if (isEmpty(src)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(src);
        //匹配正则表达式
        while (matcher.find()) {
            String word = matcher.group();
            //当是true 或则是空的情况
            if ((smallCamel.length == 0 || smallCamel[0]) && matcher.start() == 0) {
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
        if (src == null || "".equals(src))
            return true;
        else
            return false;
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

}
