package com.t2w.utils.common;

import com.t2w.utils.common.domain.MethodScope;
import com.t2w.utils.exception.MethodNotFoundException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 10:03
 * @name com.t2w.utils.common.MethodUtils.java
 * @see 类方法工具类
 */
public class MethodUtils {

    /**
     * @param clazz 需要获取方法名的 Class 对象
     * @return java.util.Set<java.lang.String> 该对象含有的 Method 的名字的集合
     * @date 2019-08-03 10:12
     * @see describing 获取该 Class 对象中所有以 public protected private 修饰的方法名字集合
     */
    public static Set<String> getMethodNames(Class<?> clazz) {
        return getMethodNames(clazz, MethodScope.DEFAULT_METHOD_SCOPE);
    }

    /**
     * @param clazz 需要获取方法名的 Class 对象
     * @return java.util.Set<java.lang.String> 该对象含有的 Method 的名字的集合
     * @date 2019-08-03 10:12
     * @see describing 获取该 Class 对象以及其父类中所有以 public protected private 修饰的方法的名字集合
     */
    public static Set<String> getAllMethodNames(Class<?> clazz) {
        return getMethodNames(clazz, MethodScope.FATHER_DEFAULT_METHOD_SCOPE);
    }

    /**
     * @param clazz        需要获取方法名的 Class 对象
     * @param methodScopes 方法的修饰限定符的集合（需要查询的方法的修饰限定符）
     * @return java.util.Set<java.lang.String> 该对象含有的 Method 的名字的集合
     * @date 2019-08-03 10:12
     * @see describing 获取该 Class 对象中含有指定（methodScopes 中所包含的）修饰限定符的方法名字集合
     */
    public static Set<String> getMethodNames(Class<?> clazz, Set<MethodScope> methodScopes) {
        Set<String> methodNames = new HashSet<String>(0);
        do {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                boolean isAbstract = Modifier.isAbstract(method.getModifiers());
                boolean isFinal = Modifier.isFinal(method.getModifiers());
                boolean isNative = Modifier.isNative(method.getModifiers());
                boolean isPrivate = Modifier.isPrivate(method.getModifiers());
                boolean isProtected = Modifier.isProtected(method.getModifiers());
                boolean isPublic = Modifier.isPublic(method.getModifiers());
                boolean isStatic = Modifier.isStatic(method.getModifiers());
                boolean isStrictfp = Modifier.isStrict(method.getModifiers());
                boolean isSynchronized = Modifier.isSynchronized(method.getModifiers());
                boolean add = true;
                if (!methodScopes.contains(MethodScope.ABSTRACT) && isAbstract)
                    add = false;
                if (!methodScopes.contains(MethodScope.FINAL) && isFinal)
                    add = false;
                if (!methodScopes.contains(MethodScope.NATIVE) && isNative)
                    add = false;
                if (!methodScopes.contains(MethodScope.PRIVATE) && isPrivate)
                    add = false;
                if (!methodScopes.contains(MethodScope.PROTECTED) && isProtected)
                    add = false;
                if (!methodScopes.contains(MethodScope.PUBLIC) && isPublic)
                    add = false;
                if (!methodScopes.contains(MethodScope.STATIC) && isStatic)
                    add = false;
                if (!methodScopes.contains(MethodScope.STRICTFP) && isStrictfp)
                    add = false;
                if (!methodScopes.contains(MethodScope.SYNCHRONIZED) && isSynchronized)
                    add = false;
                if (add)
                    methodNames.add(method.getName());
            }
            clazz = clazz.getSuperclass(); // 得到父类,然后赋给自己
        } while (methodScopes.contains(MethodScope.FATHER) && clazz != null);
        return methodNames;
    }

    /**
     * @param clazz 需要获取方法的 Class 对象
     * @return java.util.Set<java.lang.reflect.Method> 对象含有的 Method 的集合
     * @date 2019-08-03 10:20
     * @see describing 获取该 Class 对象中所有以 public protected private 修饰的方法集合
     */
    public static Set<Method> getMethods(Class<?> clazz) {
        return getMethods(clazz, MethodScope.DEFAULT_METHOD_SCOPE);
    }

    /**
     * @param clazz 需要获取方法的 Class 对象
     * @return java.util.Set<java.lang.reflect.Method> 对象含有的 Method 的集合
     * @date 2019-08-03 10:19
     * @see describing 获取该 Class 对象以及其父类中所有以 public protected private 修饰的方法的集合
     */
    public static Set<Method> getAllMethods(Class<?> clazz) {
        return getMethods(clazz, MethodScope.ALL_METHOD_SCOPE);
    }

    /**
     * @param clazz        需要获取方法的 Class 对象
     * @param methodScopes 方法的修饰限定符的集合（需要查询的方法的修饰限定符）
     * @return java.util.Set<java.lang.reflect.Method> 该对象含有的 Method 的集合
     * @date 2019-08-03 10:18
     * @see describing 获取该 Class 对象中含有指定（methodScopes 中所包含的）修饰限定符的方法集合
     */
    public static Set<Method> getMethods(Class<?> clazz, Set<MethodScope> methodScopes) {
        Set<Method> methods = new HashSet<Method>(0);
        do {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                boolean isAbstract = Modifier.isAbstract(method.getModifiers());
                boolean isFinal = Modifier.isFinal(method.getModifiers());
                boolean isNative = Modifier.isNative(method.getModifiers());
                boolean isPrivate = Modifier.isPrivate(method.getModifiers());
                boolean isProtected = Modifier.isProtected(method.getModifiers());
                boolean isPublic = Modifier.isPublic(method.getModifiers());
                boolean isStatic = Modifier.isStatic(method.getModifiers());
                boolean isStrictfp = Modifier.isStrict(method.getModifiers());
                boolean isSynchronized = Modifier.isSynchronized(method.getModifiers());
                boolean add = true;
                if (!methodScopes.contains(MethodScope.ABSTRACT) && isAbstract)
                    add = false;
                if (!methodScopes.contains(MethodScope.FINAL) && isFinal)
                    add = false;
                if (!methodScopes.contains(MethodScope.NATIVE) && isNative)
                    add = false;
                if (!methodScopes.contains(MethodScope.PRIVATE) && isPrivate)
                    add = false;
                if (!methodScopes.contains(MethodScope.PROTECTED) && isProtected)
                    add = false;
                if (!methodScopes.contains(MethodScope.PUBLIC) && isPublic)
                    add = false;
                if (!methodScopes.contains(MethodScope.STATIC) && isStatic)
                    add = false;
                if (!methodScopes.contains(MethodScope.STRICTFP) && isStrictfp)
                    add = false;
                if (!methodScopes.contains(MethodScope.SYNCHRONIZED) && isSynchronized)
                    add = false;
                if (add)
                    methods.add(method);
            }
            clazz = clazz.getSuperclass(); // 得到父类,然后赋给自己
        } while (methodScopes.contains(MethodScope.FATHER) && clazz != null);
        return methods;
    }

    /**
     * @param name  需要获取属性值的属性名
     * @param clazz 需要获取属性值的 Class 对象
     * @return S 属性的值
     * @date 2019-08-01 10:27
     * @see describing 通过属性的get方法获取属性的值
     */
    public static <T, S> S getProperty(String name, Class<T> clazz) {
        Field field = FieldUtils.getField(name, clazz);
        return getProperty(field, clazz);
    }

    /**
     * @param field 需要获取属性值的 Field 对象
     * @param clazz 需要获取属性值的 Class 对象
     * @return S 属性的值
     * @date 2019-08-19 12:47
     * @see describing 通过属性的get方法获取属性的值
     */
    public static <T, S> S getProperty(Field field, Class<T> clazz) {
        String prefix = "get";
        Method[] methods = clazz.getMethods();
        Class<S> type = (Class<S>) field.getType();
        if (type == boolean.class)
            prefix = "is";
        try {
            for (Method method : methods) {
                if ((prefix + StringUtils.acronymUppercase(field.getName())).equals(method.getName())) {
                    return type.cast(method.invoke(clazz.newInstance()));
                }
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param t     需要赋值的对象
     * @param name  需要设置值的属性名
     * @param value 需要设置属性的值
     * @date 2019-08-01 10:29
     * @see describing 通过属性的set方法设置属性的值
     */
    public static <T> void setProperty(T t, String name, Object value) {
        Field field = FieldUtils.getField(name, t.getClass());
        setProperty(t, field, value);
    }

    /**
     * @param t     需要赋值的对象
     * @param field 需要赋值的属性的 Field 对象
     * @param value 需要设置属性的值
     * @see describing 通过属性的set方法设置属性的值
     * @date 2019-08-19 14:09
     */
    public static <T> void setProperty(T t, Field field, Object value) {
        String prefix = "set";
        Class type = field.getType();
        String methodName = prefix + StringUtils.acronymUppercase(field.getName());
        try {
            Set<Method> methods = MethodUtils.getMethods(t.getClass(), MethodScope.ALL_METHOD_SCOPE);
            for (Method method : methods) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (method.getName().equals(methodName) && parameterTypes.length == 1 && parameterTypes[0].getName().equals(type.getName())) {
                    method.invoke(t, ObjectUtils.cast(value, type));
                    return;
                }
            }
            throw new MethodNotFoundException(t.getClass() + "未找到" + methodName + "方法异常");
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
