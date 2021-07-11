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

    redis.host=192.168.1.1
    redis.port=6379
    redis.pass=mypass
      
      
    redis.maxIdle=300
    redis.maxWaitMillis=1000
    redis.testOnBorrow=true
    redis.database=1
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
        Object get(String name);
    
        Object getList(String name);
    
        Object getSet(String name);
    
        Object getZSet(String name);
    
        void setValue(String key, Object value);
    
        void setHash(String key, Map<String,? extends Object> map);
    
        Object getHashValue(String mapName,String key);
    
        Map<String,? extends Object> getHash(String key);
    
        void setList(String key, List<? extends Object> o);
    
        void setSet(String name, User user);
    
        void setZSet(String name, List<User> users);
    }
    
接口的实现

    @Repository("redisDAO")
    public class RedisDAOImpl implements IRedisDAO {
    
        @Autowired
        private RedisTemplate redisTemplate;
    
        @Override
        public Object get(String key) {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Object o = valueOperations.get(key);
            return o;
        }
    
        @Override
        public Object getSet(String name) {
            return null;
        }
    
        @Override
        public Object getZSet(String name) {
            return null;
        }
    
        @Override
        public void setValue(String key, Object value) {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value);
        }
    
        @Override
        public void setHash(String key, Map<String, ? extends Object> map) {
            HashOperations hashOperations = redisTemplate.opsForHash();
            hashOperations.putAll(key, map);
        }
    
        @Override
        public Object getHashValue(String mapName, String key) {
            HashOperations hashOperations = redisTemplate.opsForHash();
            Object o = null;
            if (hashOperations.hasKey(mapName, key)) {
                o = hashOperations.get(mapName, key);
            }
            return o;
        }
    
        @Override
        public Map<String, ? extends Object> getHash(String key) {
            HashOperations hashOperations = redisTemplate.opsForHash();
            Map<String, Object> map = hashOperations.entries(key);
            return map;
        }
    
        @Override
        public void setList(String key, List<? extends Object> os) {
            ListOperations<String,Object>  listOperations = redisTemplate.opsForList();
            for (Object o : os) {
                listOperations.leftPush(key, o);
            }
        }
    
        @Override
        public List getList(String key) {
            ListOperations listOperations = redisTemplate.opsForList();
            List o = null;
            if (listOperations.size(key) > 0) {
                o = (List) listOperations.range(key, 0, -1);
            }
            return o;
        }
    
        @Override
        public void setSet(String name, User user) {
    
        }
    
        @Override
        public void setZSet(String name, List<User> users) {
    
        }

实现中仅写了几个简单的方法，更多的方法请参考org.springframework.data.redis.core.RedisTemplate，都很简单
      