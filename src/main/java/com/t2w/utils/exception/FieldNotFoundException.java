package com.t2w.utils.exception;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 09:41
 * @name com.t2w.utils.exception.FieldNotFoundException.java
 * @see 属性为找到异常
 */
public class FieldNotFoundException extends RuntimeException {

    public FieldNotFoundException() {
    }

    public FieldNotFoundException(String message) {
        super(message);
    }

}
