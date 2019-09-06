package com.t2w.utils.common;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 14:06
 * @name com.t2w.utils.common.JsonUtils.java
 * @see Json格式转换工具类
 */
public class JsonUtils {

    private static final String DEFAULT_SPACE = "\t";

    /**
     * @param src 未格式化的 json 串
     * @return java.lang.String 格式化后的 Json 字符串
     * @date 2019-07-29 14:16
     * @see 将字符串格式化为 Json 格式
     */
    public static String toJson(String src) {
        return toJson(src, DEFAULT_SPACE);
    }

    /**
     * @param src   未格式化的 json 串
     * @param space 缩进
     * @return java.lang.String
     * @date 2019-07-29 14:18
     * @see 将字符串格式化为 Json 格式
     */
    public static String toJson(String src, String space) {
        return toJson(src, space, false);
    }

    /**
     * @param src      未格式化的 json 串
     * @param space    缩进
     * @param fullLine 是否在 : 后面的 '{' '[' 进行换行输出，不填默认在一行
     * @return java.lang.String
     * @date 2019-07-29 14:18
     * @see 将字符串格式化为 Json 格式
     */
    public static String toJson(String src, String space, boolean fullLine) {
        StringBuffer result = new StringBuffer();
        int length = src.length();
        int number = 0;
        char key = 0;
        boolean content = false;
        // 遍历输入字符串。
        for (int i = 0; i < length; i++) {
            // 1、获取当前字符。
            key = src.charAt(i);
            // 2、如果当前字符是前方括号、前花括号并且不是 "" 中的内容做如下处理：
            if ((key == '[' || key == '{') && !content) {
                // （1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。
                //  该方法会让 [ { 等符号在 : 号的下方，如果需要 [ { 等在 : 的后面，需要注释掉这段代码
                if (fullLine) {
                    if ((i - 1 > 0) && (src.charAt(i - 1) == ':')) {
                        result.append('\n');
                        result.append(indent(number, space));
                    }
                }
                // （2）打印：当前字符。
                result.append(key);
                // （3）前方括号、前花括号，的后面必须换行。打印：换行。
                result.append('\n');
                // （4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
                number++;
                result.append(indent(number, space));
                // （5）进行下一次循环。
                continue;
            }
            // 3、如果当前字符是后方括号、后花括号并且不是 "" 中的内容做如下处理：
            if ((key == ']' || key == '}') && !content) {
                // （1）后方括号、后花括号，的前面必须换行。打印：换行。
                result.append('\n');
                // （2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
                number--;
                result.append(indent(number, space));
                // （3）打印：当前字符。
                result.append(key);
                // （4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。
                if (((i + 1) < length) && (src.charAt(i + 1) != ',')) {
                    result.append('\n');
                }
                // （5）继续下一次循环。
                continue;
            }
            // 4、如果当前字符是逗号并且不是 "" 中的内容。逗号后面换行，并缩进，不改变缩进次数。
            if (key == ',' && !content) {
                result.append(key);
                result.append('\n');
                result.append(indent(number, space));
                continue;
            }
            // 5、遇到 " 代表进入内容，再遇到 " 代表退出内容，内容外的所有空格都过滤
            if (key == '"' && ((i - 1) < 0 || src.charAt(i - 1) != '\\')) {
                content = !content;
            }
            if (!content) {
                if (key == '\t' || key == '\n' || key == ' ') { continue; }
            }
            // 6、打印：当前字符。
            result.append(key);
            // 7、如果当前字符是 : 并且不是 "" 中的内容，空一格
            if (key == ':' && !content) {
                result.append(" ");
            }
        }
        return result.toString();
    }

    /**
     * @param src Json 格式的字符串
     * @return java.lang.String 无缩进形式的字符串格式
     * @date 2019-07-29 14:19
     * @see 将 Json 格式转化为（无缩进）字符串形式
     */
    public static String toString(String src) {
        if (src == null) { return null; }
        StringBuilder builder = new StringBuilder();
        char[] chars = src.toCharArray();
        boolean isContent = false;
        for (int index = 0; index < chars.length; index++) {
            // 1、 判断是否为开启的 "
            if (!isContent && chars[index] == '"') {
                isContent = true;
            }
            // 2、 判断是否为关闭的 "
            if (index > 1 && isContent && chars[index] == '"' && chars[index - 1] != '\\') {
                isContent = false;
            }
            // 3、 如果不在两个双引号之间，则代表是缩进，去除
            if (!isContent) {
                if (Character.isWhitespace(chars[index])) { continue; }
            }
            builder.append(chars[index]);
        }
        return builder.toString();
    }

    /**
     * @param number 缩进次数
     * @param space  缩进
     * @return java.lang.String 指定缩进次数的字符串
     * @date 2019-07-29 14:20
     * @see 返回指定次数的缩进字符串
     */
    private static String indent(int number, String space) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            builder.append(space);
        }
        return builder.toString();
    }

    /**
     * @param json 需要验证的字符串
     * @return boolean 有效: ture, 无效: false
     * @date 2019-09-06 09:25
     * @see describing 验证一个字符串是否是有效的 JSON 串
     */
    public static boolean isJson(String json) {
        json = toString(json);
        if (StringUtils.isBlank(json)) { return false; }
        char first = json.charAt(0);
        if (first == '{') { return isMap(json); }
        if (first == '[') { return isList(json); }
        return false;
    }

    /**
     * @param json 需要验证的字符串
     * @return boolean 有效: false, 无效: true
     * @date 2019-09-06 09:25
     * @see describing 验证一个字符串是否是无效的 JSON 串
     */
    public static boolean isNotJson(String json) {
        return !isJson(json);
    }

    /**
     * @param json 需要验证的字符串
     * @return boolean 有效: ture, 无效: false
     * @date 2019-09-06 16:15
     * @see describing 验证一个 json 串是否是符合 List 集合的格式
     */
    public static boolean isList(String json) {
        List<String> members = getMembers(json);
        if (members == null) { return false; }
        for (String member : members) {
            if (StringUtils.isBlank(member)) { return false; }
            switch (member.charAt(0)) {
                case '[':
                    if (!isList(member)) { return false; }
                    break;
                case '{':
                    if (!isMap(member)) { return false; }
                    break;
                default:
                    if (!isValue(member)) { return false; }
            }
        }
        return true;
    }

    /**
     * @param json 需要验证的字符串
     * @return boolean 有效: ture, 无效: false
     * @date 2019-09-06 16:17
     * @see describing 验证一个 json 串是否符合 Map 集合的格式
     */
    public static boolean isMap(String json) {
        List<String> entrys = getEntrys(json);
        if (entrys == null) { return false; }
        for (String entry : entrys) {
            String key = getKey(entry);
            if (!isKey(key)) { return false; }
            String value = getValue(entry);
            if (StringUtils.isBlank(value)) { return false; }
            switch (value.charAt(0)) {
                case '[':
                    if (!isList(value)) { return false; }
                    break;
                case '{':
                    if (!isMap(value)) { return false; }
                    break;
                default:
                    if (!isValue(value)) { return false; }
            }
        }
        return true;
    }

    /**
     * @param value 需要验证的字符串
     * @return boolean 有效: ture, 无效: false
     * @date 2019-09-06 16:17
     * @see describing 验证一个 value 值是否符合 json 的标准
     */
    public static boolean isValue(String value) {
        List<String> fixeds = Arrays.asList("true", "false", "null");
        if (StringUtils.isBlank(value)) { return false; }
        for (String fixed : fixeds) {
            if (fixed.equals(value)) { return true; }
        }
        if (StringUtils.isNumber(value)) { return true; }
        return StringUtils.isString(value);
    }

    /**
     * @param key 需要验证的字符串
     * @return boolean 有效: ture, 无效: false
     * @date 2019-09-06 16:19
     * @see describing 验证一个 key 值是否符合 json 的标准
     */
    public static boolean isKey(String key) {
        if (StringUtils.isBlank(key)) { return false; }
        return StringUtils.isString(key);
    }

    /**
     * @param entry 符合标准 json 的 entry 类型的字符串
     * @return java.lang.String entry 串中的 key, 无效的 entry 串返回 null
     * @date 2019-09-06 16:23
     * @see describing 获取符合标准 json 的 entry 类型的字符串中的 key
     */
    public static String getKey(String entry) {
        entry = JsonUtils.toString(entry);
        CharacterIterator it = new StringCharacterIterator(entry);
        if (StringUtils.isBlank(entry)) { return null; }
        if (entry.charAt(0) != '"') { return null; }
        StringBuilder builder = new StringBuilder("\"");
        for (int index = 1; index < it.getEndIndex() - 1; index++) {
            char charIndex = it.setIndex(index);
            if (charIndex == '"' && it.previous() != '\\') { return builder.append("\"").toString(); }
            builder.append(charIndex);
        }
        return null;
    }

    /**
     * @param entry 符合标准 json 的 entry 类型的字符串
     * @return java.lang.String entry 串中的 value, 无效的 entry 串返回 null
     * @date 2019-09-06 16:24
     * @see describing 获取符合标准 json 的 entry 类型的字符串中的 value
     */
    public static String getValue(String entry) {
        entry = JsonUtils.toString(entry);
        String key = getKey(entry);
        if (key == null) { return null; }
        int index = entry.indexOf(":", entry.indexOf(key) + key.length());
        if (index == -1) { return null; }
        return entry.substring(++index);
    }

    /**
     * @param json 需要转化为 Map 集合的 Json 字符串
     * @return java.util.List<java.lang.String> 该 Map 字符串中所有的键值对
     * @date 2019-09-06 13:33
     * @see describing 获取 json 格式的字符串中的所有键值对
     */
    public static List<String> getEntrys(String json) {
        json = JsonUtils.toString(json);
        CharacterIterator it = new StringCharacterIterator(json);
        char last = it.last();
        int lastIndex = it.getIndex();
        char first = it.first();
        if (first == '{' && last == '}') {
            List<String> entrys = new ArrayList<String>(0);
            Stack<Character> borders = new Stack<Character>();
            StringBuilder builder = new StringBuilder();
            boolean isContent = false;
            boolean isKey = true;
            for (int index = 1; index < lastIndex; index++) {
                char charIndex = it.setIndex(index);
                if (isKey) {
                    if (!isContent && charIndex == '"') { isContent = true; }
                    if (isContent && charIndex == '"' && it.previous() != '\\') { isContent = false; }
                    if (charIndex == ':' && !isContent) { isKey = false; }
                    builder.append(charIndex);
                    if (index == lastIndex - 1) { return null; }
                } else {
                    if (!isContent) {
                        if (charIndex == '{' || charIndex == '[') { borders.push(charIndex); }
                        if (charIndex == ']' && (borders.empty() || borders.pop() != '[')) { return null; }
                        if (charIndex == '}' && (borders.empty() || borders.pop() != '{')) { return null; }
                        if (charIndex == '"') { isContent = true; }
                    }
                    if (isContent && charIndex == '"' && it.previous() != '\\') { isContent = false; }
                    if (charIndex == ',' && borders.empty() && !isContent) {
                        entrys.add(builder.toString());
                        isKey = true;
                        builder.delete(0, builder.length());
                    } else { builder.append(charIndex); }
                    if (index == lastIndex - 1) {
                        if (charIndex == ',' || !borders.empty() || isContent) { return null; }
                        entrys.add(builder.toString());
                    }
                }
            }
            return entrys;
        }
        return null;
    }

    /**
     * @param json 需要转化为 List 集合的 Json 字符串
     * @return java.util.List<java.lang.String> 该 List 字符串中所有的元素
     * @date 2019-09-06 13:33
     * @see describing 获取 json 格式的字符串中的所有元素
     */
    public static List<String> getMembers(String json) {
        json = JsonUtils.toString(json);
        CharacterIterator it = new StringCharacterIterator(json);
        char last = it.last();
        int lastIndex = it.getIndex();
        char first = it.first();
        if ((first == '[' && last == ']')) {
            List<String> members = new ArrayList<String>(0);
            Stack<Character> borders = new Stack<Character>();
            StringBuilder builder = new StringBuilder(0);
            boolean isContent = false;
            for (int index = 1; index < lastIndex; index++) {
                char charIndex = it.setIndex(index);
                if (!isContent) {
                    if (charIndex == '{' || charIndex == '[') { borders.push(charIndex); }
                    if (charIndex == ']' && (borders.empty() || borders.pop() != '[')) { return null; }
                    if (charIndex == '}' && (borders.empty() || borders.pop() != '{')) { return null; }
                    if (charIndex == '"') { isContent = true; }
                }
                if (isContent && charIndex == '"' && it.previous() != '\\') { isContent = false; }
                if (charIndex == ',' && borders.empty() && !isContent) {
                    members.add(builder.toString());
                    builder.delete(0, builder.length());
                } else { builder.append(charIndex); }
                if (index == lastIndex - 1) {
                    if (charIndex == ',' || !borders.empty() || isContent) { return null; }
                    members.add(builder.toString());
                }
            }
            return members;
        }
        return null;
    }

}
