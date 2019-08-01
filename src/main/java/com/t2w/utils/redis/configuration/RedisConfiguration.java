package com.t2w.utils.redis.configuration;

import lombok.Data;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-07-29 17:49
 * @name com.t2w.utils.redis.domain.RedisConfiguration.java
 * @see Redis 配置信息类
 */
@Data
public class RedisConfiguration {

    /** Redis 主机 */
    private String host = "localhost";
    /** Redis 端口号 */
    private int port = 6379;
    /** Redis 连接超时 */
    private int timeout = 2000;
    /** Redis 密码 */
    private String password;
    /** Redis 是否开启ssl */
    private boolean ssl;
    /** 其他配置属性 */
    private Properties properties;

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
    public Set<String> getKeys() {
        if (properties == null)
            return new HashSet<String>();
        else
            return properties.stringPropertyNames();
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
