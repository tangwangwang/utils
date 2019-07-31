package com.t2w.utils.redis.domain;

import com.t2w.utils.configuration.ConfigurationFile;
import lombok.Data;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-30 10:47
 * @name com.t2w.utils.redis.domain.RedisClusterConfiguration.java
 * @see Redis集群配置类
 */
@Data
public class RedisClusterConfiguration {
    /** Redis 集群连接尝试次数 */
    private int maxAttempts = 5;
    /** Redis 连接超时次数 */
    private int connectionTimeout = 2000;
    /** Redis 读取数据超时时间 */
    private int soTimeout;
    /** Redis 集群连接密码 */
    private String password;
    /** Redis 集群连接尝试次数 */
    private String clientName;
    /** 其他配置属性 */
    private Properties properties;
    /** Redis 集群的节点信息 */
    private Set<HostAndPort> nodes;

    /**
     * @param host Redis 集群节点主机地址
     * @param port Redis 集群节点端口号
     * @return com.t2w.utils.redis.domain.RedisClusterConfiguration Redis 集群配置对象
     * @see describing 向 RedisCluster 配置类中加入集群节点
     * @date 2019-07-30 16:42
     */
    public RedisClusterConfiguration addNode(String host, int port) {
        if (nodes == null) {
            synchronized (RedisClusterConfiguration.class) {
                if (nodes == null)
                    nodes = new HashSet<HostAndPort>();
            }
        }
        nodes.add(new HostAndPort(host, port));
        return this;
    }

    /**
     * @param key   需要放入 properties 中的 key
     * @param value 需要放入 properties 中的 value
     * @return java.util.Properties properties 对象
     * @date 2019-07-30 16:09
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
     * @return java.util.Set<java.lang.String> properties 中所有的 key
     * @date 2019-07-30 16:08
     * @see describing 获取 properties 中所有的 key
     */
    public Set<Object> getKeys() {
        if (properties == null)
            return new HashSet<Object>();
        else
            return properties.keySet();
    }

    /**
     * @param key properties 中的某个 key
     * @return java.lang.Object key 对应的 value 值
     * @date 2019-07-30 16:03
     * @see describing 获取 properties 中 key 对应的 value 值，key 不存在返回空
     */
    public Object get(Object key) {
        if (properties != null)
            return this.properties.get(key);
        return null;
    }

}
