package com.t2w.utils.common;

import com.t2w.utils.exception.FieldNotFoundException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 09:40
 * @name com.t2w.utils.common.FieldUtils.java
 * @see 字段工具类
 */
public class FieldUtils {

    /**
     * @param clazz 需要获取属性的Class对象
     * @return java.util.Set<java.lang.String> 所有的字段的集合(不包含父类属性 ， 不包括 final 修饰的属性)
     * @see
     * @date 2019-07-29 10:06
     */
    public static <T> Set<String> stringFields(Class<T> clazz) {
        return stringFields(clazz, false, false, true, true, true, true, true, true);
    }

    /**
     * @param clazz 需要获取属性的Class对象
     * @return java.util.Set<java.lang.String> 所有的字段的集合(包含父类属性 ， 不包括 final 修饰的属性)
     * @see
     * @date 2019-07-29 10:06
     */
    public static Set<String> stringAllFields(Class<?> clazz) {
        return stringFields(clazz, true, false, true, true, true, true, true, true);
    }

    /**
     * @param clazz
     * @param containsFather
     * @param containsFinal
     * @param containsPrivate
     * @param containsProtected
     * @param containsPublic
     * @param containsStatic
     * @param containsTransient
     * @param containsVolatile
     * @return java.util.Set<java.lang.String>
     * @see
     * @date 2019-07-29 10:07
     */
    public static Set<String> stringFields(Class<?> clazz, boolean containsFather, boolean containsFinal
            , boolean containsPrivate, boolean containsProtected, boolean containsPublic
            , boolean containsStatic, boolean containsTransient, boolean containsVolatile) {
        Set<String> fields = new HashSet<String>();
        do {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                boolean isFinal = Modifier.isFinal(field.getModifiers());
                boolean isPrivate = Modifier.isPrivate(field.getModifiers());
                boolean isProtected = Modifier.isProtected(field.getModifiers());
                boolean isPublic = Modifier.isPublic(field.getModifiers());
                boolean isStatic = Modifier.isStatic(field.getModifiers());
                boolean isTransient = Modifier.isTransient(field.getModifiers());
                boolean isVolatile = Modifier.isVolatile(field.getModifiers());
                boolean add = true;
                if (!containsFinal && isFinal)
                    add = false;
                if (!containsPrivate && isPrivate)
                    add = false;
                if (!containsProtected && isProtected)
                    add = false;
                if (!containsPublic && isPublic)
                    add = false;
                if (!containsStatic && isStatic)
                    add = false;
                if (!containsTransient && isTransient)
                    add = false;
                if (!containsVolatile && isVolatile)
                    add = false;
                if (add)
                    fields.add(field.getName());
            }
            clazz = clazz.getSuperclass(); // 得到父类,然后赋给自己
        } while (containsFather && clazz != null);
        return fields;
    }

    /**
     * @param clazz
     * @return java.util.Set<java.lang.reflect.Field>
     * @see
     * @date 2019-07-29 10:07
     */
    public static <T> Set<Field> getFields(Class<T> clazz) {
        return getFields(clazz, false, false, true, true, true, true, true, true);
    }

    /**
     * @param clazz 需要获取属性的Class对象
     * @return java.util.Set<java.lang.reflect.Field> 所有的字段的集合(包含父类属性 ， 不包括 final 修饰的属性)
     * @see
     * @date 2019-07-29 10:09
     */
    public static Set<Field> getAllFields(Class<?> clazz) {
        return getFields(clazz, true, false, true, true, true, true, true, true);
    }

    /**
     * @param clazz
     * @param containsFather
     * @param containsFinal
     * @param containsPrivate
     * @param containsProtected
     * @param containsPublic
     * @param containsStatic
     * @param containsTransient
     * @param containsVolatile
     * @return java.util.Set<java.lang.reflect.Field>
     * @see
     * @date 2019-07-29 10:09
     */
    public static Set<Field> getFields(Class<?> clazz, boolean containsFather, boolean containsFinal
            , boolean containsPrivate, boolean containsProtected, boolean containsPublic
            , boolean containsStatic, boolean containsTransient, boolean containsVolatile) {
        Set<Field> fields = new HashSet<Field>();
        do {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                boolean isFinal = Modifier.isFinal(field.getModifiers());
                boolean isPrivate = Modifier.isPrivate(field.getModifiers());
                boolean isProtected = Modifier.isProtected(field.getModifiers());
                boolean isPublic = Modifier.isPublic(field.getModifiers());
                boolean isStatic = Modifier.isStatic(field.getModifiers());
                boolean isTransient = Modifier.isTransient(field.getModifiers());
                boolean isVolatile = Modifier.isVolatile(field.getModifiers());
                boolean add = true;
                if (!containsFinal && isFinal)
                    add = false;
                if (!containsPrivate && isPrivate)
                    add = false;
                if (!containsProtected && isProtected)
                    add = false;
                if (!containsPublic && isPublic)
                    add = false;
                if (!containsStatic && isStatic)
                    add = false;
                if (!containsTransient && isTransient)
                    add = false;
                if (!containsVolatile && isVolatile)
                    add = false;
                if (add)
                    fields.add(field);
            }
            clazz = clazz.getSuperclass(); // 得到父类,然后赋给自己
        } while (containsFather && clazz != null);
        return fields;
    }

    /**
     * @param clazz
     * @param fieldName
     * @return java.lang.Class<S>
     * @see
     * @date 2019-07-29 10:09
     */
    public static <S> Class<S> getFieldClass(Class clazz, String fieldName) {
        Set<Field> fields = getFields(clazz, true, true, true, true, true, true, true, true);
        for (Field field : fields) {
            if (field.getName().equals(fieldName))
                return (Class<S>) field.getType();
        }
        throw new FieldNotFoundException(clazz.getName() + " 未找到属性 " + fieldName);
    }

    /**
     * @param clazz 对象的Class
     * @param field 属性名
     * @return S 属性的值
     * @see 通过属性的get方法获取属性的值
     * @date 2019-07-29 10:09
     */
    public static <T, S> S getProperty(Class<T> clazz, String field) {
        String prefix = "get";
        Method[] methods = clazz.getMethods();
        Class<S> fieldType = FieldUtils.getFieldClass(clazz, field);
        if (fieldType == boolean.class)
            prefix = "is";
        try {
            for (Method method : methods) {
                if ((prefix + StringUtils.capitalized(field)).equals(method.getName())) {
                    return fieldType.cast(method.invoke(clazz.newInstance()));
                }
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param t 需要赋值的对象
     * @param field 需要设置值的属性
     * @param value 需要设置属性的值
     * @see 通过属性的set方法设置属性的值
     * @date 2019-07-29 10:10
     */
    public static <T> void setProperty(T t, String field, Object value) {
        String prefix = "set";
        Class fieldType = getFieldClass(t.getClass(), field);
        String methodName = prefix + StringUtils.capitalized(field);
        try {
            Method method = t.getClass().getDeclaredMethod(methodName, fieldType);
            method.invoke(t, castType(fieldType, value));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param clazz class类型
     * @param value 值
     * @return java.lang.Object 转化后的对象
     * @see 通过class类型获取获取对应类型的值
     * @date 2019-07-29 10:11
     */
    private static <T> Object castType(Class<T> clazz, Object value) {
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
                return ' ';
            }
            return value.toString().charAt(0);
        } else if (clazz == boolean.class || clazz == Boolean.class || value instanceof Boolean) {
            if (null == value) {
                return true;
            }
            return Boolean.valueOf(value.toString());
        } else if (clazz == String.class || value instanceof String) {
            if (null == value) {
                return "";
            }
            return value.toString();
        } else if (clazz == BigDecimal.class || value instanceof BigDecimal) {
            if (null == value) {
                return new BigDecimal(0);
            }
            return new BigDecimal(value.toString());
        } else {
            return clazz.cast(value);
        }
    }

}
