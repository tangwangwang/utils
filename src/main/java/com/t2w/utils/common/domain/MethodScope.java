package com.t2w.utils.common.domain;

import com.t2w.utils.common.SetUtils;

import java.util.Set;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-08-01 11:00
 * @name com.t2w.utils.common.domain.MethodScope.java
 * @see describing Method 的范围枚举，包含所有可修饰 Method 的关键字以及父类的 Method
 */
public enum MethodScope {

    /** 使用 abstract 关键字修饰 */
    ABSTRACT,
    /** 使用 final 关键字修饰 */
    FINAL,
    /** 使用 native 关键字修饰 */
    NATIVE,
    /** 使用 private 关键字修饰 */
    PRIVATE,
    /** 使用 protected 关键字修饰 */
    PROTECTED,
    /** 使用 public 关键字修饰 */
    PUBLIC,
    /** 使用 static 关键字修饰 */
    STATIC,
    /** 使用 strictfp 关键字修饰 */
    STRICTFP,
    /** 使用 synchronized 关键字修饰 */
    SYNCHRONIZED,
    /** 表示父类的 Method 对象 */
    FATHER;

    /** 包含 PRIVATE 范围 */
    public final static Set<MethodScope> PRIVATE_METHOD_SCOPE;
    /** 包含 PROTECTED 范围 */
    public final static Set<MethodScope> PROTECTED_METHOD_SCOPE;
    /** 包含 PUBLIC 范围 */
    public final static Set<MethodScope> PUBLIC_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC 范围 */
    public final static Set<MethodScope> DEFAULT_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, ABSTRACT 范围 */
    public final static Set<MethodScope> ABSTRACT_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FINAL 范围 */
    public final static Set<MethodScope> FINAL_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, NATIVE 范围 */
    public final static Set<MethodScope> NATIVE_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, STATIC 范围 */
    public final static Set<MethodScope> STATIC_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, STRICTFP 范围 */
    public final static Set<MethodScope> STRICTFP_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, SYNCHRONIZED 范围 */
    public final static Set<MethodScope> SYNCHRONIZED_METHOD_SCOPE;
    /** 包含 PRIVATE, FATHER 范围 */
    public final static Set<MethodScope> FATHER_PRIVATE_METHOD_SCOPE;
    /** 包含 PROTECTED, FATHER 范围 */
    public final static Set<MethodScope> FATHER_PROTECTED_METHOD_SCOPE;
    /** 包含 PUBLIC, FATHER 范围 */
    public final static Set<MethodScope> FATHER_PUBLIC_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FATHER 范围 */
    public final static Set<MethodScope> FATHER_DEFAULT_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FATHER, ABSTRACT 范围 */
    public final static Set<MethodScope> FATHER_ABSTRACT_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FATHER, FINAL 范围 */
    public final static Set<MethodScope> FATHER_FINAL_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FATHER, NATIVE 范围 */
    public final static Set<MethodScope> FATHER_NATIVE_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FATHER, STATIC 范围 */
    public final static Set<MethodScope> FATHER_STATIC_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FATHER, STRICTFP 范围 */
    public final static Set<MethodScope> FATHER_STRICTFP_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FATHER, SYNCHRONIZED 范围 */
    public final static Set<MethodScope> FATHER_SYNCHRONIZED_METHOD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FATHER, ABSTRACT, FINAL, NATIVE, STATIC, STRICTFP, SYNCHRONIZED 范围 */
    public final static Set<MethodScope> ALL_METHOD_SCOPE;

    static {
        PRIVATE_METHOD_SCOPE = SetUtils.asSet(PRIVATE);
        PROTECTED_METHOD_SCOPE = SetUtils.asSet(PROTECTED);
        PUBLIC_METHOD_SCOPE = SetUtils.asSet(PUBLIC);
        DEFAULT_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC);
        ABSTRACT_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, ABSTRACT);
        FINAL_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FINAL);
        NATIVE_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, NATIVE);
        STATIC_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, STATIC);
        STRICTFP_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, STRICTFP);
        SYNCHRONIZED_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, SYNCHRONIZED);
        FATHER_PRIVATE_METHOD_SCOPE = SetUtils.asSet(PRIVATE, FATHER);
        FATHER_PROTECTED_METHOD_SCOPE = SetUtils.asSet(PROTECTED, FATHER);
        FATHER_PUBLIC_METHOD_SCOPE = SetUtils.asSet(PUBLIC, FATHER);
        FATHER_DEFAULT_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FATHER);
        FATHER_ABSTRACT_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FATHER, ABSTRACT);
        FATHER_FINAL_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FATHER, FINAL);
        FATHER_NATIVE_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FATHER, NATIVE);
        FATHER_STATIC_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FATHER, STATIC);
        FATHER_STRICTFP_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FATHER, STRICTFP);
        FATHER_SYNCHRONIZED_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FATHER, SYNCHRONIZED);
        ALL_METHOD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FATHER, ABSTRACT, FINAL, NATIVE, STATIC, STRICTFP, SYNCHRONIZED);
    }

}
