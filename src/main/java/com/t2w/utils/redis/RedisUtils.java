package com.t2w.utils.redis;

import com.t2w.utils.common.FieldUtils;
import com.t2w.utils.common.StringUtils;
import com.t2w.utils.exception.RedisConfigurationException;
import com.t2w.utils.exception.RedisUninitializedException;
import com.t2w.utils.redis.configuration.RedisConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 10:48
 * @name com.t2w.utils.redis.RedisUtils.java
 * @see Redis工具类（使用该用户类最好在项目启动时配置Redis环境）
 */
public class RedisUtils {
    /** Jedis 连接池配置类 */
    private static JedisPoolConfig jedisPoolConfig;
    /** Jedis 连接池 */
    private static JedisPool jedisPool;
    /** Redis 配置类 */
    private static RedisConfiguration configuration;

    /**
     * @param configuration Redis 配置对象
     * @date 2019-07-30 11:29
     * @see describing 初始化配置 Redis 工具包
     */
    public static void init(RedisConfiguration configuration) {
        if (jedisPool == null) {
            synchronized (RedisUtils.class) {
                RedisUtils.configuration = configuration;
            }
        }
        initJedisPool();
    }

    /**
     * @date 2019-07-30 11:30
     * @see describing 初始化 Jedis 连接池
     */
    private static void initJedisPool() {
        if (configuration == null) {
            throw new RedisUninitializedException("Redis未初始化，使用前需要进行初始化，仅初始化一次即可。");
        }
        if (jedisPoolConfig == null) {
            synchronized (RedisUtils.class) {
                if (jedisPoolConfig == null) {
                    jedisPoolConfig = new JedisPoolConfig();
                    Set<String> keys = configuration.getKeys();
                    Set<String> fields = FieldUtils.getFieldNames(JedisPoolConfig.class);
                    for (String key : keys) {
                        for (String field : fields) {
                            String keyField = key.replaceAll("-", "").replaceAll("_", "");
                            if (keyField.equalsIgnoreCase(field)) {
                                Object value = configuration.get(key);
                                FieldUtils.setProperty(jedisPoolConfig, field, value);
                            }
                        }
                    }
                }
            }
        }
        if (jedisPool == null || jedisPool.isClosed()) {
            if (StringUtils.isEmpty(configuration.getPassword())) {
                jedisPool = new JedisPool(jedisPoolConfig, configuration.getHost(), configuration.getPort(), configuration.getTimeout(), configuration.isSsl());
            } else {
                jedisPool = new JedisPool(jedisPoolConfig, configuration.getHost(), configuration.getPort(), configuration.getTimeout(), configuration.getPassword(), configuration.isSsl());
            }
        }
    }

    /**
     * @param configurations Redis 配置对象
     * @return redis.clients.jedis.Jedis Jedis连接对象
     * @date 2019-07-30 11:30
     * @see describing 获取 Jedis 连接
     */
    public static Jedis getJedis(RedisConfiguration... configurations) {
        if (configurations.length > 1)
            throw new RedisConfigurationException("配置对象过多异常，配置对象最多传入一个，可不传。");
        if (configurations.length > 0 && configurations[0] != null)
            RedisUtils.init(configurations[0]);
        else
            initJedisPool();
        return jedisPool.getResource();
    }


    /**
     * @param jedis 需要关闭的 Jedis 连接
     * @date 2019-07-30 11:31
     * @see describing 关闭 Jedis 连接
     */
    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * @date 2019-07-30 11:31
     * @see describing 关闭 Jedis 连接池
     */
    public static void closePool() {
        if (jedisPool != null) {
            jedisPool.close();
        }
    }

}
