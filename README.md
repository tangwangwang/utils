# utils
实用工具包
#### Redis 工具
##### RedisUtils 使用（Redis工具包）
使用前需要进行初始化：`RedisUtils.init(RedisConfiguration);`
- 使用 `Spring` 框架：<br/>
   可以将 `RedisConfiguration` 交由 `Spring` 容器管理，并同时对 `RedisUtils` 进行初始化
   ```java
  @Configuration
  public class RedisConfig{
      @Bean
      public RedisConfiguration redisConfiguration(){
          RedisConfiguration configuration = new RedisConfiguration();
          // 配置 Redis 基础参数        
          configuration.setHost(host).setPort(port);
          // 配置 Redis 其他参数（JedisPoolConfig中可配置的参数）
          configuration.put(key, value);
          // 初始化 Redis 工具类
          RedisUtils.init(configuration);
          return configuration;
      }
  }
   ```
  在其他类中使用 `Redis` 配置对象可以直接进行注入
  ```java
  @Autowired
  private RedisConfiguration configuration;
  ```
- 使用一般模式<br/>
  需要自己创建一个 `RedisConfiguration` 单例对象
  ```java
  public class RedisConfig{
      private RedisConfiguration configuration;
      public static RedisConfiguration getInstance(){
          if (configuration == null){
              synchronized (RedisConfig.class){
                  if (configuration == null){
                        configuration = new RedisConfiguration();
                         // 配置 Redis 基础参数        
                         configuration.setHost(host).setPort(port);
                         // 配置 Redis 其他参数（JedisPoolConfig中可配置的参数）
                         configuration.put(key, value);
                         return configuration;
                  }
              }
          }
      }
  }
  ```
  在使用 `Redis` 时可以通过以下方式使用
  ```java
  Jedis jedis = RedisUtils.getJedis(RedisConfig.getInstance());
  ```
##### JedisClusterUtils 使用（Redis集群工具包）
- 使用 `Spring` 框架：<br/>
   可以将 `RedisClusterConfiguration` 交由 `Spring` 容器管理，并同时对 `RedisUtils` 进行初始化
   ```java
  @Configuration
  public class RedisConfig{
      @Bean
      public RedisClusterConfiguration redisConfiguration(){
          RedisClusterConfiguration configuration = new RedisClusterConfiguration();
          // 配置 Redis 基础参数        
          configuration.addNode(host, port).addNode(host, port);
          configuration.setPassword(password);
          // 配置 Redis 其他参数（JedisPoolConfig中可配置的参数）
          configuration.put(key, value);
          // 初始化 Redis 工具类
          RedisClusterUtils.init(configuration);
          return configuration;
      }
  }
   ```
  在其他类中使用 `RedisCluster` 配置对象可以直接进行注入
  ```java
  @Autowired
  private RedisClusterConfiguration configuration;
  ```
- 使用一般模式<br/>
  需要自己创建一个 `RedisClusterConfiguration` 单例对象
  ```java
  public class RedisClusterConfig{
      private RedisClusterConfiguration configuration;
      public static RedisClusterConfiguration getInstance(){
          if (configuration == null){
              synchronized (RedisClusterConfig.class){
                  if (configuration == null){
                        configuration = new RedisClusterConfiguration();
                         // 配置 Redis 基础参数        
                         configuration.addNode(host, port).addNode(host, port);
                         configuration.setPassword(password);
                         // 配置 Redis 其他参数（JedisPoolConfig中可配置的参数）
                         configuration.put(key, value);
                         return configuration;
                  }
              }
          }
      }
  }
  ```
  在使用 `RedisCluster` 时可以通过以下方式使用
  ```java
  JedisCluster cluster = RedisClusterUtils.getJedisCluster(RedisClusterConfig.getInstance());
  ```