package com.t2w.utils.common.domain;

import com.t2w.utils.common.SetUtils;

import java.util.Set;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-30 17:51
 * @name com.t2w.utils.common.domain.FieldScope.java
 * @see describing Field 的范围枚举，包含所有可修饰 Field 的关键字以及父类的 Filed
 */
public enum FieldScope {

    /** 使用 final 关键字修饰 */
    FINAL,
    /** 使用 private 关键字修饰 */
    PRIVATE,
    /** 使用 protected 关键字修饰 */
    PROTECTED,
    /** 使用 public 关键字修饰 */
    PUBLIC,
    /** 使用 static 关键字修饰 */
    STATIC,
    /** 使用 transient 关键字修饰 */
    TRANSIENT,
    /** 使用 volatile 关键字修饰 */
    VOLATILE,
    /** 表示父类的 Field 对象 */
    FATHER;
    /** 包含 PRIVATE 范围 */
    public final static Set<FieldScope> PRIVATE_FIELD_SCOPE;
    /** 包含 PROTECTED 范围 */
    public final static Set<FieldScope> PROTECTED_FIELD_SCOPE;
    /** 包含 PUBLIC 范围 */
    public final static Set<FieldScope> PUBLIC_FIELD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC 范围 */
    public final static Set<FieldScope> DEFAULT_FIELD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FINAL 范围 */
    public final static Set<FieldScope> FINAL_FIELD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, STATIC 范围 */
    public final static Set<FieldScope> STATIC_FIELD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, TRANSIENT 范围 */
    public final static Set<FieldScope> TRANSIENT_FIELD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, VOLATILE 范围 */
    public final static Set<FieldScope> VOLATILE_FIELD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FATHER 范围 */
    public final static Set<FieldScope> FATHER_DEFAULT_FIELD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FATHER, FINAL 范围 */
    public final static Set<FieldScope> FATHER_FINAL_FIELD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FATHER, STATIC 范围 */
    public final static Set<FieldScope> FATHER_STATIC_FIELD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FATHER, TRANSIENT 范围 */
    public final static Set<FieldScope> FATHER_TRANSIENT_FIELD_SCOPE;
    /** 包含 PRIVATE, PROTECTED, PUBLIC, FATHER, VOLATILE 范围 */
    public final static Set<FieldScope> FATHER_VOLATILE_FIELD_SCOPE;
    /** 包含 FINAL, PRIVATE, PROTECTED, PUBLIC, STATIC, TRANSIENT, VOLATILE, FATHER 范围 */
    public final static Set<FieldScope> ALL_FIELD_SCOPE;

    static {
        PRIVATE_FIELD_SCOPE = SetUtils.asSet(PRIVATE);
        PROTECTED_FIELD_SCOPE = SetUtils.asSet(PROTECTED);
        PUBLIC_FIELD_SCOPE = SetUtils.asSet(PUBLIC);
        DEFAULT_FIELD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC);
        FINAL_FIELD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FINAL);
        STATIC_FIELD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, STATIC);
        TRANSIENT_FIELD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, TRANSIENT);
        VOLATILE_FIELD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, VOLATILE);
        FATHER_DEFAULT_FIELD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FATHER);
        FATHER_FINAL_FIELD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FATHER, FINAL);
        FATHER_STATIC_FIELD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FATHER, STATIC);
        FATHER_TRANSIENT_FIELD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FATHER, TRANSIENT);
        FATHER_VOLATILE_FIELD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FATHER, VOLATILE);
        ALL_FIELD_SCOPE = SetUtils.asSet(PRIVATE, PROTECTED, PUBLIC, FINAL, STATIC, TRANSIENT, VOLATILE, FATHER);
    }
}
