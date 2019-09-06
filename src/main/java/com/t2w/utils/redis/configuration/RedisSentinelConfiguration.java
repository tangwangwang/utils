package com.t2w.utils.redis.configuration;

import lombok.Data;
import lombok.experimental.Accessors;
import redis.clients.jedis.HostAndPort;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-08-16 20:03
 * @name com.t2w.utils.redis.configuration.RedisSentinelConfiguration.java
 * @see describing Redis 主从复制模式配置类
 */
@Data
@Accessors(chain = true)
public class RedisSentinelConfiguration {

    /** Redis 主从的节点信息 */
    private Set<HostAndPort> nodes;
    /** Redis 连接超时 */
    private int timeout = 2000;
    /** Redis 密码 */
    private String password;
    /** Redis 是否开启ssl */
    private boolean ssl;
    /** 其他配置属性 */
    private Properties properties;

    /**
     * @param host Redis 节点主机地址
     * @param port Redis 节点端口号
     * @return com.t2w.utils.redis.configuration.RedisSentinelConfiguration 主从复制模式配置对象
     * @date 2019-08-16 20:07
     * @see describing 向 Redis Sentinel 配置类中加入节点
     */
    public RedisSentinelConfiguration addNode(String host, int port) {
        getNodes().add(new HostAndPort(host, port));
        return this;
    }

    /**
     * @param key   需要放入 properties 中的 key
     * @param value 需要放入 properties 中的 value
     * @return java.util.Properties properties 对象
     * @date 2019-08-16 20:09
     * @see describing 向 properties 中加入属性值
     */
    public Properties put(Object key, Object value) {
        if (properties == null) {
            synchronized (RedisConfiguration.class) {
                if (properties == null) {
                    properties = new Properties();
                }
            }
        }
        this.properties.put(key, value);
        return this.properties;
    }

    /**
     * @return java.util.Set<java.lang.Object> properties 中所有的 key
     * @date 2019-08-16 20:09
     * @see describing 获取 properties 中所有的 key
     */
    public Set<Object> getKeys() {
        if (properties == null) {
            return new HashSet<Object>();
        } else {
            return properties.keySet();
        }
    }

    /**
     * @param key properties 中的某个 key
     * @return java.lang.Object key 对应的 value 值
     * @date 2019-08-16 20:09
     * @see describing 获取 properties 中 key 对应的 value 值，key 不存在返回空
     */
    public Object get(Object key) {
        if (properties != null) {
            return this.properties.get(key);
        }
        return null;
    }

    /**
     * @return java.util.Set<redis.clients.jedis.HostAndPort> 所有的节点信息集合
     * @date 2019-08-16 20:28
     * @see describing 获取所有的节点信息
     */
    public Set<HostAndPort> getNodes() {
        if (nodes == null) {
            synchronized (RedisClusterConfiguration.class) {
                if (nodes == null) {
                    nodes = new LinkedHashSet<HostAndPort>(0);
                }
            }
        }
        return nodes;
    }
}
