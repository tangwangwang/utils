package com.t2w.utils.exception;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-08-17 09:17
 * @name com.t2w.utils.exception.RedisException.java
 * @see describing
 */
public class RedisException extends RuntimeException {
    public RedisException() {
    }

    public RedisException(String message) {
        super(message);
    }
}
