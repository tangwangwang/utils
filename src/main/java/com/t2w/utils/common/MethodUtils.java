package com.t2w.utils.common;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 10:03
 * @name com.t2w.utils.common.MethodUtils.java
 * @see 方法工具类
 */
public class MethodUtils {

    /**
     * @param clazz Class对象
     * @return java.util.Set<java.lang.reflect.Method> 所有的 Method 对象
     * @date 2019-07-30 17:28
     * @see describing 获取 Class 对象中的所有方法，包括父类
     */
    public static Set<Method> getAllMethods(Class<?> clazz) {
        Set<Method> methods = new HashSet<Method>();
        do {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                methods.add(method);
            }
            clazz = clazz.getSuperclass(); // 得到父类,然后赋给自己
        } while (clazz != null);
        return methods;
    }

}
