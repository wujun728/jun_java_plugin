1、在maven项目pom.xml中添加如下依赖
注意：如果不添加commons-fileupload依赖，项目将会报java.lang.NoClassDefFoundError: org/apache/commons/fileupload/FileItemFactory错误


        <dependency>
            <groupId>org.springframework.session</groupId>
            <artifactId>spring-session-data-redis</artifactId>
            <version>1.2.2.RELEASE</version>
            <type>pom</type>
        </dependency>
        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.3.1</version>
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
    redis.timeout=3000
    redis.usePool=true
    
3、spring 主配置文件中添加如下

     <bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig"
              p:maxIdle="${redis.maxIdle}" p:maxWaitMillis="${redis.maxWaitMillis}" p:testOnBorrow="${redis.testOnBorrow}">
        </bean>
    
        <!-- 添加RedisHttpSessionConfiguration用于session共享 -->
        <bean class="org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration"/>
    
        <bean id="jedisConnectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory"
              p:hostName="${redis.host}" p:port="${redis.port}" p:password="${redis.pass}" p:poolConfig-ref="poolConfig"
              p:usePool="${redis.usePool}"
              p:database="${redis.database}"
              p:timeout="${redis.timeout}"/>
        
4、在web.xml添加filter

      <filter>
        <filter-name>springSessionRepositoryFilter</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
      </filter>
      <filter-mapping>
        <filter-name>springSessionRepositoryFilter</filter-name>
        <url-pattern>/*</url-pattern>
      </filter-mapping>