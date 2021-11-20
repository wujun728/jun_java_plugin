1、在maven项目pom.xml中添加如下依赖
注：如果项目已经依赖spring-orm，可以把spring-data-redis中的spring-orm exclusion掉


        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-redis</artifactId>
            <version>1.7.2.RELEASE</version>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework</groupId>
                    <artifactId>spring-orm</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
            <version>2.7.3</version>
        </dependency>
        
2、设置redis配置属性(默认你已经安装 redis)
添加redis.properties

    redis.host=192.168.1.112
    redis.port=6379
    redis.pass=123456
    
    #控制一个pool最少有多少个状态为idle的jedis实例
    redis.minIdle=3
    #控制一个pool最多有多少个状态为idle的jedis实例
    redis.maxIdle=300
    #表示当borrow一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException
    redis.maxWaitMillis=5000
    #在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的
    redis.testOnBorrow=true
    
    redis.database=2
    redis.timeout=0
    redis.usePool=true
    redis.enableTransactionSupport=true
    
3、spring 主配置文件中添加如下

     <bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig"
               p:maxIdle="${redis.maxIdle}" p:maxWaitMillis="${redis.maxWaitMillis}" p:testOnBorrow="${redis.testOnBorrow}">
         </bean>
     
         <bean id="jdkSerializationRedisSerializer"
               class="org.springframework.data.redis.serializer.JdkSerializationRedisSerializer"/>
     
         <bean id="jedisConnFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory"
               p:hostName="${redis.host}" p:port="${redis.port}" p:password="${redis.pass}" p:poolConfig-ref="poolConfig"
               p:usePool="${redis.usePool}"
               p:database="${redis.database}"
               p:timeout="${redis.timeout}"/>
     
         <bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate"
               p:defaultSerializer-ref="jdkSerializationRedisSerializer"
               p:keySerializer-ref="jdkSerializationRedisSerializer"
               p:valueSerializer-ref="jdkSerializationRedisSerializer"
               p:connectionFactory-ref="jedisConnFactory"
               p:enableTransactionSupport="${redis.enableTransactionSupport}"
         />
        
4、直接在spring管理的bean中引入RedisTemplate

接口

    public interface IRedisDAO {
        void addScore(String key,int id,double score);
        
        void updateScore(String key,int id,double score);
    
        public Set getTop(String key, int top);
    
        Set getTopWithScore(String key, int i);
    
        Set getTopWithScore(String key,int start, int limit);
    }
    
接口的实现

    @Repository("redisDAO")
    public class RedisDAOImpl implements IRedisDAO {
    
        @Autowired
        private RedisTemplate redisTemplate;
        @Override
            public void addScore(String key, int id, double score) {
                ZSetOperations zSet = redisTemplate.opsForZSet();
                zSet.incrementScore(key,id,score);
            }
        
            @Override
            public void updateScore(String key, int id, double score) {
                ZSetOperations zSet = redisTemplate.opsForZSet();
                zSet.add(key,id,score);
            }
        
            @Override
            public Set getTop(String key,int top) {
                ZSetOperations zSet = redisTemplate.opsForZSet();
                Set set = zSet.reverseRange(key,0,top-1);
                return set;
            }
        
            @Override
            public Set getTopWithScore(String key,int top) {
                ZSetOperations zSet = redisTemplate.opsForZSet();
                Set set = zSet.reverseRangeWithScores(key,0,top-1);
                return set;
            }
        
            @Override
            public Set getTopWithScore(String key, int start, int limit) {
                ZSetOperations zSet = redisTemplate.opsForZSet();
                Set set = zSet.reverseRangeWithScores(key,start,start+limit-1);
                return set;
            }
    }
    
5、项目中地址介绍
    
    1、http://localhost:8080/getTopScore/100
    获得积分前100名的榜单
    2、http://localhost:8080/add/3/33
    给id为3的用户添加33分积分
    3、http://localhost:8080/getTopScore/32/21
    从32名开始，获取21位的积分排名


      