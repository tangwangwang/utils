package com.t2w.utils.common;

import java.math.BigDecimal;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-08-19 10:26
 * @name com.t2w.utils.common.ObjectUtils.java
 * @see describing 对象工具类
 */
public class ObjectUtils {

    /**
     * @param value 值
     * @param clazz 需要转化的 class 类型
     * @return java.lang.Object 转化后的对象
     * @date 2019-07-29 10:11
     * @see describing 通过class类型获取获取对应类型的值
     */
    public static <T> Object cast(Object value, Class<T> clazz) {
        if (clazz == byte.class || clazz == Byte.class || value instanceof Byte) {
            if (null == value) {
                return 0;
            }
            return Byte.valueOf(value.toString());
        } else if (clazz == short.class || clazz == Short.class || value instanceof Short) {
            if (null == value) {
                return 0;
            }
            return Short.valueOf(value.toString());
        } else if (clazz == int.class || clazz == Integer.class || value instanceof Integer) {
            if (null == value) {
                return 0;
            }
            return Integer.valueOf(value.toString());
        } else if (clazz == long.class || clazz == Long.class || value instanceof Long) {
            if (null == value) {
                return 0;
            }
            return Long.valueOf(value.toString());
        } else if (clazz == float.class || clazz == Float.class || value instanceof Float) {
            if (null == value) {
                return 0;
            }
            return Float.valueOf(value.toString());
        } else if (clazz == double.class || clazz == Double.class || value instanceof Double) {
            if (null == value) {
                return 0;
            }
            return Double.valueOf(value.toString());
        } else if (clazz == char.class || clazz == Character.class || value instanceof Character) {
            if (null == value) {
                return 0;
            }
            return value.toString().charAt(0);
        } else if (clazz == boolean.class || clazz == Boolean.class || value instanceof Boolean) {
            if (null == value) {
                return false;
            }
            return Boolean.valueOf(value.toString());
        } else if (clazz == String.class || value instanceof String) {
            if (null == value) {
                return null;
            }
            return value.toString();
        } else if (clazz == BigDecimal.class || value instanceof BigDecimal) {
            if (null == value) {
                return null;
            }
            return new BigDecimal(value.toString());
        } else {
            return clazz.cast(value);
        }
    }

}
