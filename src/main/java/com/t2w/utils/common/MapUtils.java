package com.t2w.utils.common;

import com.t2w.utils.common.domain.FieldScope;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-09-04 16:36
 * @name com.t2w.utils.common.MapUtils.java
 * @see describing Map工具类
 */
@Slf4j
public class MapUtils {

    /**
     * @param object 需要转化为 Map 集合的对象
     * @return java.util.Map<java.lang.String, java.lang.Object> 转化后的 Map 对象
     * @date 2019-09-06 16:28
     * @see describing 将实体对象里的属性和值转化为 Map 对象(属性值为 null 不加入 Map 对象中)
     */
    public static Map<String, Object> toMap(Object object) {
        return toMap(object, false);
    }

    /**
     * @param object     需要转化为 Map 集合的对象
     * @param allowEmpty 是否加入为空的属性
     * @return java.util.Map<java.lang.String, java.lang.Object> 转化后的 Map 对象
     * @date 2019-09-06 16:29
     * @see describing 将实体对象里的属性和值转化为 Map 对象
     */
    public static Map<String, Object> toMap(Object object, boolean allowEmpty) {
        Map<String, Object> map = new HashMap<String, Object>();
        Set<Field> fields = FieldUtils.getFields(object.getClass(), FieldScope.ALL_FIELD_SCOPE);
        for (Field field : fields) {
            String name = field.getName();
            try {
                Object value = FieldUtils.get(field, object);
                if (value != null || allowEmpty) {
                    map.put(name, value);
                }
            } catch (IllegalAccessException e) {
                log.error("获取对象的属性:{}发生异常, 异常信息为: {}", name, e.toString());
            }
        }
        return map;
    }

    /**
     * @param json 需要转化为 Map 集合的 Json 字符串
     * @return java.util.Map<java.lang.String, java.lang.Object> 转化后的 Map 集合对象, 转化失败为 null
     * @date 2019-09-06 16:26
     * @see describing 将 json 格式的字符串转化为 Map 集合
     */
    public static Map<String, Object> toMap(String json) {
        if (JsonUtils.isNotJson(json)) { return null; }
        List<String> entrys = JsonUtils.getEntrys(json);
        if (entrys == null) { return null; }
        Map<String, Object> map = new LinkedHashMap<String, Object>(0);
        for (String entry : entrys) {
            String key = JsonUtils.getKey(entry);
            if (StringUtils.isBlank(key)) { return null; }
            String valueString = JsonUtils.getValue(entry);
            Object value = null;
            if (StringUtils.isBlank(valueString)) { return null; }
            switch (valueString.charAt(0)) {
                case '"':
                    value = valueString.substring(1, valueString.length() - 1);
                    break;
                case '{':
                    value = toMap(valueString);
                    break;
                case '[':
                    value = ListUtils.toList(valueString);
                    break;
                default:
                    value = valueString;
            }
            map.put(key.substring(1, key.length() - 1), value);
        }
        return map;
    }

}
