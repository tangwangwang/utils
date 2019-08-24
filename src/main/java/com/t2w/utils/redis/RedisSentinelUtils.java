package com.t2w.utils.redis;

import com.t2w.utils.common.FieldUtils;
import com.t2w.utils.common.MethodUtils;
import com.t2w.utils.common.SetUtils;
import com.t2w.utils.common.StringUtils;
import com.t2w.utils.exception.RedisConfigurationException;
import com.t2w.utils.exception.RedisException;
import com.t2w.utils.exception.RedisUninitializedException;
import com.t2w.utils.redis.configuration.RedisSentinelConfiguration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-08-16 20:01
 * @name com.t2w.utils.redis.RedisSentinelUtils.java
 * @see describing Redis 主从复制（哨兵）模式工具类
 */
public class RedisSentinelUtils {
    /** RedisSentinel 配置对象 */
    private static RedisSentinelConfiguration configuration;
    /** 主节点的连接池对象 */
    private static JedisPool master;
    /** 从节点的连接池对象集合 */
    private static Set<JedisPool> slaves;
    /** Jedis 连接池配置类 */
    private static JedisPoolConfig jedisPoolConfig;
    /** 主节点对象 */
    private static HostAndPort masterNode;
    /** 所有的节点对象(包括主从所有有效的节点) */
    private static Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>(0);
    /** 随机获取从节点的随机数对象 */
    private static Random random = new Random();

    /**
     * @param configuration 初始化配置 RedisSentinel 主从复制(哨兵)模式工具包
     * @return void
     * @date 2019-08-21 14:28
     * @see describing
     */
    public static void init(RedisSentinelConfiguration configuration) {
        if (master == null || slaves == null) {
            synchronized (RedisSentinelUtils.class) {
                RedisSentinelUtils.configuration = configuration;
            }
        }
        initJedisSentinel();
    }

    /**
     * @date 2019-08-21 14:26
     * @see describing 初始化 Redis Sentinel 主从复制(哨兵)模式
     */
    private static void initJedisSentinel() {
        if (configuration == null) {
            throw new RedisUninitializedException("Redis Sentinel未初始化，使用前需要进行初始化，仅初始化一次即可。");
        }
        if (jedisPoolConfig == null) {
            synchronized (RedisSentinelUtils.class) {
                if (jedisPoolConfig == null) {
                    jedisPoolConfig = new JedisPoolConfig();
                    Set<Object> keys = configuration.getKeys();
                    Set<String> fields = FieldUtils.getFieldNames(JedisPoolConfig.class);
                    for (Object key : keys) {
                        for (String field : fields) {
                            String keyField = key.toString().replaceAll("-", "").replaceAll("_", "");
                            if (keyField.equalsIgnoreCase(field)) {
                                Object value = configuration.get(key);
                                MethodUtils.setProperty(jedisPoolConfig, field, value);
                            }
                        }
                    }
                }
            }
        }
        if (slaves == null) {
            synchronized (RedisSentinelUtils.class) {
                if (slaves == null) {
                    nodes.addAll(configuration.getNodes());
                    slaves = new LinkedHashSet<JedisPool>(0);
                    for (HostAndPort hostAndPort : configuration.getNodes()) {
                        try {
                            JedisPool jedisPool = null;
                            if (StringUtils.isEmpty(configuration.getPassword())) {
                                jedisPool = new JedisPool(jedisPoolConfig, hostAndPort.getHost(), hostAndPort.getPort(), configuration.getTimeout(), configuration.isSsl());
                            } else {
                                jedisPool = new JedisPool(jedisPoolConfig, hostAndPort.getHost(), hostAndPort.getPort(), configuration.getTimeout(), configuration.getPassword(), configuration.isSsl());
                            }
                            Jedis jedis = jedisPool.getResource();
                            jedis.ping();
                            slaves.add(jedisPool);
                        } catch (Exception e) {
                            nodes.remove(hostAndPort);
                        }
                    }
                    // 构建主从哨兵模式（链式构建）
                    if (slaves == null || slaves.size() <= 0) {
                        slaves = null;
                        throw new RedisException("Redis 不存在节点， Redis 服务不可用。");
                    }
                    JedisPool[] jedisPools = slaves.toArray(new JedisPool[0]);
                    HostAndPort[] hostAndPorts = nodes.toArray(new HostAndPort[0]);
                    for (int index = 1; index < jedisPools.length; index++) {
                        Jedis jedis = jedisPools[index].getResource();
                        jedis.slaveof(hostAndPorts[index - 1].getHost(), hostAndPorts[index - 1].getPort());
                        close(jedis);
                    }
                }
            }
        }
        if (master == null) {
            electonMaster();
        }
    }

    /**
     * @date 2019-08-21 14:26
     * @see describing 选举主节点，按从节点的顺序选举，知道选出主节点为止
     */
    private static void electonMaster() {
        master = null;
        JedisPool[] jedisPools = slaves.toArray(new JedisPool[0]);
        HostAndPort[] hostAndPorts = nodes.toArray(new HostAndPort[0]);
        if (jedisPools.length == 0) {
            throw new RedisException("Redis 所有节点都已 down， Redis 服务不可用。");
        }
        for (int index = 0; index < jedisPools.length; index++) {
            try {
                Jedis jedis = jedisPools[index].getResource();
                String pong = jedis.ping();
                if ("PONG".equalsIgnoreCase(pong)) {
                    master = jedisPools[index];
                    masterNode = hostAndPorts[index];
                    slaves.remove(jedisPools[index]);
                    jedis.slaveofNoOne();
                    close(jedis);
                    break;
                } else {
                    slaves.remove(jedisPools[index]);
                    nodes.remove(hostAndPorts[index]);
                }
            } catch (Exception e) {
                slaves.remove(jedisPools[index]);
                nodes.remove(hostAndPorts[index]);
            }
        }
        if (master == null) {
            throw new RedisException("Redis 所有节点都已 down， Redis 服务不可用。");
        }
    }

    /**
     * @param configurations Redis Sentinel 配置对象
     * @return redis.clients.jedis.Jedis
     * @date 2019-08-21 14:25
     * @see describing 获取主节点的 Jedis 对象
     */
    public static Jedis getMaster(RedisSentinelConfiguration... configurations) {
        if (configurations.length > 1)
            throw new RedisConfigurationException("配置对象过多异常，配置对象最多传入一个，已初始化后可不传。");
        if (configurations.length > 0 && configurations[0] != null)
            RedisSentinelUtils.init(configurations[0]);
        if (master == null) {
            initJedisSentinel();
        }
        Jedis jedis = null;
        try {
            jedis = master.getResource();
            jedis.ping();
        } catch (Exception e) {
            nodes.remove(masterNode);
            electonMaster();
            jedis = getMaster(configurations);
        }
        new Thread(new TestConnection()).start();
        return jedis;
    }

    /**
     * @param configurations Redis Sentinel 配置对象
     * @return redis.clients.jedis.Jedis
     * @date 2019-08-21 14:25
     * @see describing 获取从节点的 Jedis 对象
     */
    public static Jedis getSlave(RedisSentinelConfiguration... configurations) {
        if (configurations.length > 1)
            throw new RedisConfigurationException("配置对象过多异常，配置对象最多传入一个，已初始化后可不传。");
        if (configurations.length > 0 && configurations[0] != null)
            RedisSentinelUtils.init(configurations[0]);
        if (slaves == null || slaves.size() <= 0) {
            slaves = null;
            initJedisSentinel();
        }
        Jedis jedis = null;
        try {
            JedisPool[] jedisPools = slaves.toArray(new JedisPool[0]);
            jedis = jedisPools[random.nextInt(jedisPools.length)].getResource();
            jedis.ping();
        } catch (Exception e) {
            electonMaster();
            jedis = getSlave(configurations);
        }
        new Thread(new TestConnection()).start();
        return jedis;
    }

    /**
     * @param jedis 需要关闭的 Jedis 连接
     * @date 2019-08-17 10:54
     * @see describing 关闭 Jedis 连接
     */
    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * @date 2019-08-19 16:40
     * @see describing 清空 Redis 所有节点的主从配置
     */
    public static void closeRedisSentinel() {
        try {
            Jedis jedis = master.getResource();
            jedis.slaveofNoOne();
            close(jedis);
            master.close();
        } catch (Exception ignored) { }
        for (JedisPool slave : slaves) {
            try {
            Jedis jedis = slave.getResource();
            jedis.slaveofNoOne();
            close(jedis);
            slave.close();
            } catch (Exception ignored) { }
        }
        nodes.clear();
        master = null;
        slaves = null;
    }

    /**
     * @date 2019-08-21 14:24
     * @see describing 清空 Redis 中所有的数据
     */
    public static void flushRedisSentinel() {
        Jedis master = getMaster();
        master.flushAll();
        close(master);
    }

    /**
     * @date 2019-08-21 14:16
     * @see describing 测试是否有节点重启，有节点重启则重新加入 Redis Sentinel 模式中，每次 getMaster 和 getSlave 时会创建一个新进程进行测试
     */
    private static class TestConnection implements Runnable {

        /** 互斥锁，每个时刻只能执行一个测试线程 */
        private static Lock lock = new ReentrantLock();

        @Override
        public void run() {
            if (lock.tryLock()) {
                try {
                    Set<HostAndPort> difference = SetUtils.difference(configuration.getNodes(), nodes);
                    for (HostAndPort hostAndPort : difference) {
                        try {
                            JedisPool jedisPool = null;
                            if (StringUtils.isEmpty(configuration.getPassword())) {
                                jedisPool = new JedisPool(jedisPoolConfig, hostAndPort.getHost(), hostAndPort.getPort(), configuration.getTimeout(), configuration.isSsl());
                            } else {
                                jedisPool = new JedisPool(jedisPoolConfig, hostAndPort.getHost(), hostAndPort.getPort(), configuration.getTimeout(), configuration.getPassword(), configuration.isSsl());
                            }
                            HostAndPort[] nodeArray = nodes.toArray(new HostAndPort[0]);
                            HostAndPort lastNode = nodeArray[nodeArray.length - 1];
                            Jedis jedis = jedisPool.getResource();
                            jedis.ping();
                            jedis.slaveof(lastNode.getHost(), lastNode.getPort());
                            close(jedis);
                            nodes.add(hostAndPort);
                            slaves.add(jedisPool);
                        } catch (Exception ignored) { }
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

}
