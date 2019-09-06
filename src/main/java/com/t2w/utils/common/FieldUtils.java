package com.t2w.utils.common;

import com.t2w.utils.common.domain.FieldScope;
import com.t2w.utils.exception.FieldNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
@Slf4j
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
     * @param name  属性名
     * @param clazz 需要获取属性的 Class 对象
     * @return java.lang.Class<S> 属性类型对应的 Class 对象
     * @date 2019-08-01 10:25
     * @see describing 获取属性的类型 Class 对象
     */
    public static <S> Class<S> getFieldType(String name, Class clazz) {
        Set<Field> fields = getFields(clazz, FieldScope.ALL_FIELD_SCOPE);
        for (Field field : fields) {
            if (field.getName().equals(name))
                return (Class<S>) field.getType();
        }
        throw new FieldNotFoundException(clazz.getName() + " 未找到属性 " + name);
    }

    /**
     * @param name  属性名
     * @param clazz 需要获取属性的 Class 对象
     * @return java.lang.reflect.Field 属性对应的 Field 对象
     * @date 2019-08-19 12:42
     * @see describing 获取属性的 Field 对象
     */
    public static Field getField(String name, Class clazz) {
        Set<Field> fields = getFields(clazz, FieldScope.ALL_FIELD_SCOPE);
        for (Field field : fields) {
            if (field.getName().equals(name))
                return field;
        }
        throw new FieldNotFoundException(clazz.getName() + " 未找到属性 " + name);
    }

    /**
     * @param field  设置的属性 Field
     * @param object 需要设置值的对象
     * @param value  需要设置的值
     * @date 2019-09-04 16:53
     * @see describing 通过暴力反射进行设置对象的属性值
     */
    public static void set(Field field, Object object, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(object, value);
    }

    /**
     * @param field  获取的属性 Field
     * @param object 需要获取值的对象
     * @return java.lang.Object 对象属性的值
     * @date 2019-09-04 16:49
     * @see describing 通过暴力反射进行获取对象的属性值
     */
    public static Object get(Field field, Object object) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(object);
    }

}
