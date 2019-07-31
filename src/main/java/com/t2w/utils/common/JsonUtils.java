package com.t2w.utils.common;

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
    public static String formatString2Json(String src) {
        return formatString2Json(src, DEFAULT_SPACE, false);
    }

    /**
     * @param src      未格式化的 json 串
     * @param space    缩进
     * @param fullLine 是否在 : 后面的 '{' '[' 进行换行输出，不填默认在一行
     * @return java.lang.String
     * @date 2019-07-29 14:18
     * @see 将字符串格式化为 Json 格式
     */
    public static String formatString2Json(String src, String space, boolean... fullLine) {
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
                if (fullLine.length > 0 && fullLine[0]) {
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
                if (key == '\t' || key == '\n' || key == ' ')
                    continue;
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
    public static String formatJson2String(String src) {
        StringBuffer result = new StringBuffer();
        char[] chars = src.toCharArray();
        boolean content = false;
        for (char ch : chars) {
            // 1、 如果是 " 号，则里面为内容，两个双引号为1对
            if (ch == '"') {
                content = !content;
            }
            // 2、 如果不在两个双引号之间，则代表是缩进，去除
            if (!content) {
                if (ch == '\t' || ch == '\n' || ch == ' ')
                    continue;
            }
            result.append(ch);
        }
        return result.toString();
    }

    /**
     * @param number 缩进次数
     * @param space  缩进
     * @return java.lang.String 指定缩进次数的字符串
     * @date 2019-07-29 14:20
     * @see 返回指定次数的缩进字符串
     */
    private static String indent(int number, String space) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < number; i++) {
            result.append(space);
        }
        return result.toString();
    }

}
