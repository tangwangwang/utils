package com.t2w.utils.exception;

import redis.clients.jedis.exceptions.JedisException;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-30 11:26
 * @name com.t2w.utils.exception.RedisUninitializedException.java
 * @see
 */
public class RedisUninitializedException extends JedisException {

    public RedisUninitializedException(String message) {
        super(message);
    }

    public RedisUninitializedException(Throwable e) {
        super(e);
    }

    public RedisUninitializedException(String message, Throwable cause) {
        super(message, cause);
    }
}
