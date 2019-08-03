package com.t2w.utils.common;

import com.t2w.utils.common.domain.FieldScope;
import com.t2w.utils.common.domain.MethodScope;
import com.t2w.utils.exception.FieldNotFoundException;
import com.t2w.utils.exception.MethodNotFoundException;

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
 * @see describing 类字段工具类
 */
public class FieldUtils {

    /**
     * @param clazz 需要获取属性名的 Class 对象
     * @return java.util.Set<java.lang.String> 该对象含有的 Filed 的名字的集合
     * @date 2019-07-31 20:55
     * @see describing 获取该 Class 对象中所有以 public protected private 修饰的字段的名字集合
     */
    public static Set<String> getFieldNames(Class<?> clazz) {
        return getFieldNames(clazz, FieldScope.DEFAULT_FIELD_SCOPE);
    }

    /**
     * @param clazz 需要获取属性名的 Class 对象
     * @return java.util.Set<java.lang.String> 该对象含有的 Filed  的名字的集合
     * @date 2019-07-31 21:00
     * @see describing 获取该 Class 对象以及其父类中所有以 public protected private 修饰的字段的名字集合
     */
    public static Set<String> getAllFieldNames(Class<?> clazz) {
        return getFieldNames(clazz, FieldScope.FATHER_DEFAULT_FIELD_SCOPE);
    }

    /**
     * @param clazz       需要获取属性名的 Class 对象
     * @param fieldScopes 字段的修饰限定符的集合（需要查询的字段的修饰限定符）
     * @return java.util.Set<java.lang.String> 该对象含有的 Filed  的名字的集合
     * @date 2019-07-31 21:02
     * @see describing 获取该 Class 对象中含有指定（fieldScopes 中所包含的）修饰限定符的字段名字集合
     */
    public static Set<String> getFieldNames(Class<?> clazz, Set<FieldScope> fieldScopes) {
        Set<String> fieldNames = new HashSet<String>(0);
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
                if (!fieldScopes.contains(FieldScope.FINAL) && isFinal)
                    add = false;
                if (!fieldScopes.contains(FieldScope.PRIVATE) && isPrivate)
                    add = false;
                if (!fieldScopes.contains(FieldScope.PROTECTED) && isProtected)
                    add = false;
                if (!fieldScopes.contains(FieldScope.PUBLIC) && isPublic)
                    add = false;
                if (!fieldScopes.contains(FieldScope.STATIC) && isStatic)
                    add = false;
                if (!fieldScopes.contains(FieldScope.TRANSIENT) && isTransient)
                    add = false;
                if (!fieldScopes.contains(FieldScope.VOLATILE) && isVolatile)
                    add = false;
                if (add)
                    fieldNames.add(field.getName());
            }
            clazz = clazz.getSuperclass(); // 得到父类,然后赋给自己
        } while (fieldScopes.contains(FieldScope.FATHER) && clazz != null);
        return fieldNames;
    }

    /**
     * @param clazz 需要获取属性的 Class 对象
     * @return java.util.Set<java.lang.reflect.Field> 该对象含有的 Filed 的集合
     * @date 2019-07-31 20:55
     * @see describing 获取该 Class 对象中所有以 public protected private 修饰的字段的集合
     */
    public static Set<Field> getFields(Class<?> clazz) {
        return getFields(clazz, FieldScope.DEFAULT_FIELD_SCOPE);
    }

    /**
     * @param clazz 需要获取属性的 Class 对象
     * @return java.util.Set<java.lang.reflect.Field> 该对象含有的 Filed 的集合
     * @date 2019-07-31 21:00
     * @see describing 获取该 Class 对象以及其父类中所有以 public protected private 修饰的字段的集合
     */
    public static Set<Field> getAllFields(Class<?> clazz) {
        return getFields(clazz, FieldScope.FATHER_DEFAULT_FIELD_SCOPE);
    }

    /**
     * @param clazz       需要获取属性的 Class 对象
     * @param fieldScopes 字段的修饰限定符的集合（需要查询的字段的修饰限定符）
     * @return java.util.Set<java.lang.reflect.Field> 该对象含有的 Filed 的集合
     * @date 2019-07-31 21:02
     * @see describing 获取该 Class 对象中含有指定（fieldScopes 中所包含的）修饰限定符的字段集合
     */
    public static Set<Field> getFields(Class<?> clazz, Set<FieldScope> fieldScopes) {
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
                if (!fieldScopes.contains(FieldScope.FINAL) && isFinal)
                    add = false;
                if (!fieldScopes.contains(FieldScope.PRIVATE) && isPrivate)
                    add = false;
                if (!fieldScopes.contains(FieldScope.PROTECTED) && isProtected)
                    add = false;
                if (!fieldScopes.contains(FieldScope.PUBLIC) && isPublic)
                    add = false;
                if (!fieldScopes.contains(FieldScope.STATIC) && isStatic)
                    add = false;
                if (!fieldScopes.contains(FieldScope.TRANSIENT) && isTransient)
                    add = false;
                if (!fieldScopes.contains(FieldScope.VOLATILE) && isVolatile)
                    add = false;
                if (add)
                    fields.add(field);
            }
            clazz = clazz.getSuperclass(); // 得到父类,然后赋给自己
        } while (fieldScopes.contains(FieldScope.FATHER) && clazz != null);
        return fields;
    }

    /**
     * @param clazz     需要获取属性的 Class 对象
     * @param fieldName 属性名
     * @return java.lang.Class<S> 属性类型对应的 Class 对象
     * @date 2019-08-01 10:25
     * @see describing 获取属性的类型 Class 对象
     */
    public static <S> Class<S> getFieldClass(Class clazz, String fieldName) {
        Set<Field> fields = getFields(clazz, FieldScope.ALL_FIELD_SCOPE);
        for (Field field : fields) {
            if (field.getName().equals(fieldName))
                return (Class<S>) field.getType();
        }
        throw new FieldNotFoundException(clazz.getName() + " 未找到属性 " + fieldName);
    }

    /**
     * @param clazz     需要获取属性值的 Class 对象
     * @param fieldName 属性名
     * @return S 属性的值
     * @date 2019-08-01 10:27
     * @see describing 通过属性的get方法获取属性的值
     */
    public static <T, S> S getProperty(Class<T> clazz, String fieldName) {
        String prefix = "get";
        Method[] methods = clazz.getMethods();
        Class<S> fieldType = FieldUtils.getFieldClass(clazz, fieldName);
        if (fieldType == boolean.class)
            prefix = "is";
        try {
            for (Method method : methods) {
                if ((prefix + StringUtils.capitalized(fieldName)).equals(method.getName())) {
                    return fieldType.cast(method.invoke(clazz.newInstance()));
                }
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param t         需要赋值的对象
     * @param fieldName 需要设置值的属性名
     * @param value     需要设置属性的值
     * @date 2019-08-01 10:29
     * @see describing 通过属性的set方法设置属性的值
     */
    public static <T> void setProperty(T t, String fieldName, Object value) {
        String prefix = "set";
        Class fieldType = getFieldClass(t.getClass(), fieldName);
        String methodName = prefix + StringUtils.capitalized(fieldName);
        try {
            Set<Method> methods = MethodUtils.getMethods(t.getClass(), MethodScope.ALL_METHOD_SCOPE);
            for (Method method : methods) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (method.getName().equals(methodName) && parameterTypes.length == 1 && parameterTypes[0].getName().equals(fieldType.getName())) {
                    method.invoke(t, castType(fieldType, value));
                    return;
                }
            }
            throw new MethodNotFoundException(t.getClass() + "未找到" + methodName + "方法异常");
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param clazz class类型
     * @param value 值
     * @return java.lang.Object 转化后的对象
     * @date 2019-07-29 10:11
     * @see describing 通过class类型获取获取对应类型的值
     */
    public static <T> Object castType(Class<T> clazz, Object value) {
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
