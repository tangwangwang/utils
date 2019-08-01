package com.t2w.utils.exception;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-08-01 10:35
 * @name com.t2w.utils.exception.MethodNotFoundException.java
 * @see describing
 */
public class MethodNotFoundException extends RuntimeException {
    public MethodNotFoundException() {
    }

    public MethodNotFoundException(String message) {
        super(message);
    }
}
