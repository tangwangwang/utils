package com.t2w.utils.common;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-08-01 09:45
 * @name com.t2w.utils.common.SetUtils.java
 * @see describing Set工具类
 */
public class SetUtils {

    /**
     * @param t Set 集合中的元素
     * @return java.util.Set<T> 不可变的 Set 集合
     * @date 2019-08-03 09:41
     * @see describing 将传入的元素值变成不可变的 Set 集合，不传入值则返回一个不可变的空 Set 集合
     */
    public static <T> Set<T> asSet(T... t) {
        Set<T> set = new LinkedHashSet<T>();
        Collections.addAll(set, t);
        return Collections.unmodifiableSet(set);
    }

    /**
     * @param members 取交集的 Set 集合
     * @return java.util.Set<T> 不可变的交集集合
     * @date 2019-08-21 16:05
     * @see describing 取多个 Set 集合的交集
     */
    public static <T> Set<T> intersection(Set<T>... members) {
        Set<T> intersection = new LinkedHashSet<T>(0);
        Set<T> union = union(members);
        for (T t : union) {
            boolean contain = true;
            for (Set<T> member : members) {
                if (member != null) {
                    if (!member.contains(t)) {
                        contain = false;
                        break;
                    }
                }
            }
            if (contain) {
                intersection.add(t);
            }
        }
        return Collections.unmodifiableSet(intersection);
    }

    /**
     * @param master 需要取差集的主集合
     * @param slaves 需要取差集的从集合
     * @return java.util.Set<T> 不可变的差集集合
     * @date 2019-08-21 16:36
     * @see describing 取主集合减去所有从集合的差集
     */
    public static <T> Set<T> difference(Set<T> master, Set<T>... slaves) {
        Set<T> difference = new LinkedHashSet<T>(master);
        for (Set<T> slave : slaves) {
            if (slave != null) {
                for (T t : slave) {
                    difference.remove(t);
                }
            }
        }
        return Collections.unmodifiableSet(difference);
    }

    /**
     * @param members 取并集的 Set 集合
     * @return java.util.Set<T> 不可变的并集集合
     * @date 2019-08-21 16:05
     * @see describing 取多个 Set 集合的并集
     */
    public static <T> Set<T> union(Set<T>... members) {
        Set<T> union = new LinkedHashSet<T>(0);
        for (Set<T> member : members) {
            if (member != null) {
                union.addAll(member);
            }
        }
        return Collections.unmodifiableSet(union);
    }


}
