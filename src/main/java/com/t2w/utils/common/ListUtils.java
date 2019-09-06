package com.t2w.utils.common;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-09-05 17:43
 * @name com.t2w.utils.common.ListUtils.java
 * @see describing List 工具类
 */
public class ListUtils {

    /**
     * @param json 需要转化为 List 集合的 Json 字符串
     * @return java.util.List<java.lang.Object> 转化后的 List 集合对象, 转化失败为 null
     * @date 2019-09-05 20:10
     * @see describing 将 json 格式的字符串转化为 List 集合
     */
    public static List<Object> toList(String json) {
        if (JsonUtils.isNotJson(json)) { return null; }
        json = JsonUtils.toString(json);
        List<String> members = JsonUtils.getMembers(json);
        if (members == null) { return null; }
        List<Object> list = new ArrayList<Object>(0);
        for (String member : members) {
            char index = member.charAt(0);
            switch (index) {
                case '"':
                    list.add(member.substring(1, member.length() - 1));
                    break;
                case '[':
                    list.add(toList(member));
                    break;
                case '{':
                    list.add(MapUtils.toMap(member));
                    break;
                default:
                    list.add(member);
            }
        }
        return list;
    }

}
