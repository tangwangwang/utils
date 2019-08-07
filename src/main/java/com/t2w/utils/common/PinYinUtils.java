package com.t2w.utils.common;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-08-06 20:42
 * @name com.t2w.utils.common.PinyinUtils.java
 * @see describing 拼音工具包 <br/>
 * 需要依赖 【com.belerweb.pinyin4j】
 */
public class PinYinUtils {

    /**
     * @param src 需要查找首字母的字符
     * @return char[] 源字符所有拼音中的首字母字符集合
     * @date 2019-08-07 10:03
     * @see describing 查找单个汉字字符的所有首字母字符集合（包含多音字的所有首字母，默认是大写）
     */
    public static char[] getAcronym(char src) {
        return getAcronym(src, true);
    }

    /**
     * @param src       需要查找首字母的字符
     * @param upperCase 首字母是否大写
     * @return char[]   源字符所有拼音中的首字母字符集合
     * @date 2019-08-07 09:35
     * @see describing  查找单个汉字字符的所有首字母字符集合（包含多音字的所有首字母）
     */
    public static char[] getAcronym(char src, boolean upperCase) {
        // 如果不是汉字直接返回
        if (src <= 128) {
            return new char[]{src};
        }
        // 获取该字符的所有拼音字符串（包含多音字）
        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(src);
        // 如果为汉字中的字符则直接返回
        if (pinyinArray == null || pinyinArray.length == 0) {
            return new char[]{src};
        }
        // 创建返回对象
        char[] acronymChars = new char[pinyinArray.length];
        // 截取返回的拼音数组里的所有首字符
        for (int index = 0; index < pinyinArray.length; index++) {
            if (pinyinArray[index] == null || pinyinArray[index].length() == 0) {
                continue;
            }
            char acronym = pinyinArray[index].charAt(0);
            // 首字母是否大写，默认是小写
            if (upperCase) {
                acronymChars[index] = Character.toUpperCase(acronym);
            } else {
                acronymChars[index] = acronym;
            }
        }
        return acronymChars;
    }

    /**
     * @param src 需要查找首字母的源字符串
     * @return java.lang.String[] 源字符串所有字符的首字母数组
     * @date 2019-08-07 11:03
     * @see describing 查找汉字字符串中的所有字符的首字母字符串集合（返回的每个字符串包含与源字符串对应字符的所有多音节的首字母）【默认没有分隔符分隔，首字母大写】
     */
    public static String[] getAcronym(String src) {
        return getAcronym(src, true);
    }

    /**
     * @param src       需要查找首字母的源字符串
     * @param separator 首字母为多音字时的分隔符
     * @return java.lang.String[] 源字符串所有字符的首字母数组
     * @date 2019-08-07 15:01
     * @see describing 查找汉字字符串中的所有字符的首字母字符串集合（返回的每个字符串包含源字符串对应字符的所有多音节的首字母）【默认首字母大写】
     */
    public static String[] getAcronym(String src, String separator) {
        return getAcronym(src, separator, true);
    }

    /**
     * @param src       需要查找首字母的源字符串
     * @param upperCase 首字母是否大写
     * @return java.lang.String[] 源字符串所有字符的首字母数组
     * @date 2019-08-07 10:21
     * @see describing 查找汉字字符串中的所有字符的首字母字符串集合（返回的每个字符串包含源字符串对应字符的所有多音节的首字母）【默认没有分隔符分隔】
     */
    public static String[] getAcronym(String src, boolean upperCase) {
        return getAcronym(src, null, upperCase);
    }

    /**
     * @param src       需要查找首字母的源字符串
     * @param upperCase 首字母是否大写
     * @param separator 首字母为多音字时的分隔符
     * @return java.lang.String[] 源字符串所有字符的首字母数组
     * @date 2019-08-07 09:29
     * @see describing  查找汉字字符串中的所有字符的首字母字符串集合（返回的每个字符串包含该对应字符的所有多音节的首字母）
     */
    public static String[] getAcronym(String src, String separator, boolean upperCase) {
        char[] chars = src.toCharArray();
        String[] acronymStrings = new String[chars.length];
        for (int index = 0; index < chars.length; index++) {
            char[] acronymChars = getAcronym(chars[index], upperCase);
            StringBuilder builder = new StringBuilder();
            for (int acronymIndex = 0; acronymIndex < acronymChars.length; acronymIndex++) {
                builder.append(acronymChars[acronymIndex]);
                // 如果有分隔符加上分隔符
                if (separator != null && acronymIndex != acronymChars.length - 1) {
                    builder.append(separator);
                }
            }
            acronymStrings[index] = builder.toString();
        }
        return acronymStrings;
    }

    /**
     * @param src 转化为拼音的源字符串
     * @return java.lang.String 转化后的拼音字符串
     * @date 2019-08-07 14:47
     * @see describing 将字符串转化为拼音【默认不分隔，连续拼接，小写字母，不含音调】
     */
    public static String toPinyin(String src) {
        return toPinyin(src, "", false, false);
    }

    /**
     * @param src       转化为拼音的源字符串
     * @param separator 每个汉字拼音中间的分割符
     * @return java.lang.String 转化后的拼音字符串
     * @date 2019-08-07 14:47
     * @see describing 将字符串转化为拼音【默认小写字母，不含音调】
     */
    public static String toPinyin(String src, String separator) {
        return toPinyin(src, separator, false, false);
    }

    /**
     * @param src       转化为拼音的源字符串
     * @param separator 每个汉字拼音中间的分割符
     * @param upperCase 拼音是否为大写字母
     * @return java.lang.String 转化后的拼音字符串
     * @date 2019-08-07 14:47
     * @see describing 将字符串转化为拼音【默认不含音调】
     */
    public static String toPinyin(String src, String separator, boolean upperCase) {
        return toPinyin(src, separator, upperCase, false);
    }

    /**
     * @param src       转化为拼音的源字符串
     * @param separator 每个汉字拼音中间的分割符
     * @param upperCase 拼音是否为大写字母
     * @param tone      是否含有声调（1-5，5为平声）
     * @return java.lang.String 转化后的拼音字符串
     * @date 2019-08-07 14:47
     * @see describing 将字符串转化为拼音
     */
    public static String toPinyin(String src, String separator, boolean upperCase, boolean tone) {
        // 创建汉语拼音处理类
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 设置大小写
        if (upperCase) {
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        } else {
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }
        // 设置音标方式
        if (tone) {
            defaultFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        } else {
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        }
        String pinyingStr = "";
        try {
            pinyingStr = PinyinHelper.toHanYuPinyinString(src, defaultFormat, separator, true);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return pinyingStr;
    }

    /**
     * @param src 源字符串
     * @return java.lang.String 转化为拼音后的字符串
     * @date 2019-08-07 15:20
     * @see describing 将单个字符按指定规则转化为字符串【默认不查多音字、无分隔符、小写、无音调】
     */
    public static String toPinyin(char src) {
        return toPinyin(src, false, null, false, false);
    }

    /**
     * @param src       源字符串
     * @param polyphone 是否查出多音字
     * @return java.lang.String 转化为拼音后的字符串
     * @date 2019-08-07 15:20
     * @see describing 将单个字符按指定规则转化为字符串【默认无分隔符、小写、无音调】
     */
    public static String toPinyin(char src, boolean polyphone) {
        return toPinyin(src, polyphone, null, false, false);
    }

    /**
     * @param src       源字符串
     * @param polyphone 是否查出多音字
     * @param separator 多音字拼音中间的分隔符
     * @return java.lang.String 转化为拼音后的字符串
     * @date 2019-08-07 15:20
     * @see describing 将单个字符按指定规则转化为字符串【默认小写、无音调】
     */
    public static String toPinyin(char src, boolean polyphone, String separator) {
        return toPinyin(src, polyphone, separator, false, false);
    }

    /**
     * @param src       源字符串
     * @param polyphone 是否查出多音字
     * @param separator 多音字拼音中间的分隔符
     * @param upperCase 是否大写
     * @return java.lang.String 转化为拼音后的字符串
     * @date 2019-08-07 15:20
     * @see describing 将单个字符按指定规则转化为字符串【默认无音调】
     */
    public static String toPinyin(char src, boolean polyphone, String separator, boolean upperCase) {
        return toPinyin(src, polyphone, separator, upperCase, false);
    }

    /**
     * @param src       源字符串
     * @param polyphone 是否查出多音字
     * @param separator 多音字拼音中间的分隔符
     * @param upperCase 是否大写
     * @param tone      是否包含音调
     * @return java.lang.String 转化为拼音后的字符串
     * @date 2019-08-07 15:14
     * @see describing 将单个字符按指定规则转化为字符串
     */
    public static String toPinyin(char src, boolean polyphone, String separator, boolean upperCase, boolean tone) {
        // 创建汉语拼音处理类
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 设置大小写
        if (upperCase) {
            defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        } else {
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }
        // 设置音标方式
        if (tone) {
            defaultFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        } else {
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        }
        StringBuffer buffer = new StringBuffer();
        // 判断是否为中文
        if (src <= 128) {
            buffer.append(src);
        } else {
            try {
                // 转换得出结果
                String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(src, defaultFormat);
                if (pinyins != null && pinyins.length != 0) {
                    // 判断是否查出多音字，默认使用第一个结果
                    if (polyphone) {
                        for (int index = 0; index < pinyins.length; index++) {
                            buffer.append(pinyins[index]);
                            // 判断是否加入分隔符
                            if (StringUtils.isNotEmpty(separator) && index != pinyins.length - 1) {
                                buffer.append(separator);
                            }
                        }
                    } else {
                        buffer.append(pinyins[0]);
                    }
                } else {
                    buffer.append(src);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }

    /**
     * @param src
     * @return java.lang.String[]
     * @date 2019-08-07 15:39
     * @see describing 查找汉字字符串中的所有字符的拼音字符串集合（返回的每个字符串为源字符串对应字符的拼音） <br/>
     * 【默认无多音字、小写、无音调】
     */
    public static String[] toPinyins(String src) {
        return toPinyins(src, false);
    }

    /**
     * @param src
     * @param polyphone
     * @return java.lang.String[]
     * @date 2019-08-07 15:39
     * @see describing 查找汉字字符串中的所有字符的拼音字符串集合（返回的每个字符串为源字符串对应字符的拼音） <br/>
     * 【默认多音字无分隔符、小写、无音调】
     */
    public static String[] toPinyins(String src, boolean polyphone) {
        return toPinyins(src, polyphone, null);
    }

    /**
     * @param src       源字符串
     * @param polyphone 是否查出多音字
     * @param separator 多音字拼音中间的分隔符
     * @return java.lang.String[] 源字符串中每个字符转化为拼音后的字符串数组
     * @date 2019-08-07 15:39
     * @see describing 查找汉字字符串中的所有字符的拼音字符串集合（返回的每个字符串为源字符串对应字符的拼音） <br/>
     * 【默认小写、无音调】
     */
    public static String[] toPinyins(String src, boolean polyphone, String separator) {
        return toPinyins(src, polyphone, separator, false);
    }

    /**
     * @param src       源字符串
     * @param polyphone 是否查出多音字
     * @param separator 多音字拼音中间的分隔符
     * @param upperCase 是否大写
     * @return java.lang.String[] 源字符串中每个字符转化为拼音后的字符串数组
     * @date 2019-08-07 15:39
     * @see describing 查找汉字字符串中的所有字符的拼音字符串集合（返回的每个字符串为源字符串对应字符的拼音） <br/>
     * 【默认无音调】
     */
    public static String[] toPinyins(String src, boolean polyphone, String separator, boolean upperCase) {
        return toPinyins(src, polyphone, separator, upperCase, false);
    }

    /**
     * @param src       源字符串
     * @param polyphone 是否查出多音字
     * @param separator 多音字拼音中间的分隔符
     * @param upperCase 是否大写
     * @param tone      是否包含音调
     * @return java.lang.String[] 源字符串中每个字符转化为拼音后的字符串数组
     * @date 2019-08-07 15:28
     * @see describing 查找汉字字符串中的所有字符的拼音字符串集合（返回的每个字符串为源字符串对应字符的拼音）
     */
    public static String[] toPinyins(String src, boolean polyphone, String separator, boolean upperCase, boolean tone) {
        // 判断字符串是否为空
        if (StringUtils.isEmpty(src)) {
            return null;
        }
        char[] chars = src.toCharArray();
        String[] target = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            target[i] = toPinyin(chars[i], polyphone, separator, upperCase, tone);
        }
        return target;
    }


}
