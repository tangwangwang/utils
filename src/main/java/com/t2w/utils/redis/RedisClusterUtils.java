package com.t2w.utils.redis;

import com.t2w.utils.common.FieldUtils;
import com.t2w.utils.common.StringUtils;
import com.t2w.utils.exception.RedisConfigurationException;
import com.t2w.utils.exception.RedisUninitializedException;
import com.t2w.utils.redis.configuration.RedisClusterConfiguration;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 10:49
 * @name com.t2w.utils.redis.RedisClusterUtils.java
 * @see Redis集群工具类（使用该用户类最好在项目启动时配置Redis环境） <br/>
 * 需要导入依赖【redis.clients.jedis】
 */
public class RedisClusterUtils {
    /** Jedis 连接池配置类 */
    private static JedisPoolConfig jedisPoolConfig;
    /** Jedis 集群对象（可像 Jedis 一样操作 Redis ） */
    private static JedisCluster cluster;
    /** Redis 集群配置类 */
    private static RedisClusterConfiguration configuration;

    /**
     * @param configuration Redis 集群配置对象
     * @date 2019-07-30 17:48
     * @see describing 初始化配置 Redis 集群工具包
     */
    public static void init(RedisClusterConfiguration configuration) {
        if (cluster == null) {
            synchronized (RedisUtils.class) {
                RedisClusterUtils.configuration = configuration;
            }
        }
        initJedisCluster();
    }

    /**
     * @date 2019-07-30 17:49
     * @see describing 初始化 Jedis 集群
     */
    private static void initJedisCluster() {
        if (configuration == null) {
            throw new RedisUninitializedException("RedisCluster未初始化，使用前需要进行初始化，仅初始化一次即可。");
        }
        if (jedisPoolConfig == null) {
            synchronized (RedisUtils.class) {
                if (jedisPoolConfig == null) {
                    jedisPoolConfig = new JedisPoolConfig();
                    Set<Object> keys = configuration.getKeys();
                    Set<String> fields = FieldUtils.getFieldNames(JedisPoolConfig.class);
                    for (Object key : keys) {
                        for (String field : fields) {
                            String keyField = key.toString().replaceAll("-", "").replaceAll("_", "");
                            if (keyField.equalsIgnoreCase(field)) {
                                Object value = configuration.get(key);
                                FieldUtils.setProperty(jedisPoolConfig, field, value);
                            }
                        }
                    }
                }
            }
        }
        if (StringUtils.isEmpty(configuration.getPassword())) {
            cluster = new JedisCluster(configuration.getNodes(), configuration.getConnectionTimeout(), configuration.getSoTimeout(), configuration.getMaxAttempts(), jedisPoolConfig);
        } else {
            cluster = new JedisCluster(configuration.getNodes(), configuration.getConnectionTimeout(), configuration.getSoTimeout(), configuration.getMaxAttempts(), configuration.getPassword(), configuration.getClientName(), jedisPoolConfig);
        }
    }

    /**
     * @param configurations Redis 集群配置对象
     * @return redis.clients.jedis.JedisCluster Jedis集群对象
     * @date 2019-07-30 17:49
     * @see describing 获取 Jedis 集群对象
     */
    public static JedisCluster getJedisCluster(RedisClusterConfiguration... configurations) {
        if (configurations.length > 1)
            throw new RedisConfigurationException("配置对象过多异常，配置对象最多传入一个，可不传。");
        if (configurations.length > 0 && configurations[0] != null)
            RedisClusterUtils.init(configurations[0]);
        else
            initJedisCluster();
        return cluster;
    }

    /**
     * @date 2019-07-30 17:50
     * @see describing 关闭 Jedis 集群连接
     */
    public static void closeJedisCluster() {
        if (cluster != null)
            cluster.close();
    }

}
