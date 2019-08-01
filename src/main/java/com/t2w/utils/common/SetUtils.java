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

    public static <T> Set<T> asSet(T... t) {
        Set<T> set = new LinkedHashSet<T>();
        for (T temp : t) {
            set.add(temp);
        }
        return Collections.unmodifiableSet(set);
    }

}
