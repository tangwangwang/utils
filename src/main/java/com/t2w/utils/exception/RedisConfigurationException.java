package com.t2w.utils.exception;

import redis.clients.jedis.exceptions.JedisException;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-30 14:06
 * @name com.t2w.utils.exception.RedisConfigurationException.java
 * @see Redis 配置对象异常
 */
public class RedisConfigurationException extends JedisException {
    public RedisConfigurationException(String message) {
        super(message);
    }

    public RedisConfigurationException(Throwable e) {
        super(e);
    }

    public RedisConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
