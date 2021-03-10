# lock-spring-boot-starter

#### 介绍
基于redisson实现的spring boot starter分布式锁框架,实现了可重入锁、公平锁、联锁、红锁、读写锁等常用锁的方式，并支持集群模式下的redis

#### 使用说明

1. 创建Spring Boot项目
2. 引入maven依赖
   ```  
    <dependency>
        <groupId>io.gitee.tooleek</groupId>
        <artifactId>lock-spring-boot-starter</artifactId>
        <version>1.1.0</version>
    </dependency>
   ```
3. 在Spring Boot的项目配置文件application.yml中添加相应的配置，如：
   ```
    lock-config: 
        pattern: single #redis模式配置，single：单机模式，cluster:集群模式，replicated:云托管模式,sentinel:哨兵模式，master_slave：主从模式
        # 不同的redis模式对应不同的配置方式，single-server对应的就是单机模式，具体参数意义可参考redisson的配置参数说明
        single-server: 
            address: 127.0.0.1
            port: 6379
            password: 123456
   ```
4. 在需要使用分布式锁的方法上面使用@Lock注解，锁的关键字使用@Key，如:
   ```
    @Lock
	public void hello(String ces, @Key String orderNo) {
		System.out.println("hello");
	}
   ```
   如果需要配置不同类型的锁，可以直接变更@Lock的参数值即可，默认是可重入锁
   > @Lock提供四个参数可以配置：
   > lockType:锁类型
   > leaseTime:加锁时间
   > waitTime:最长等待时间
   > timeUnit:锁时长单位

    DEMO地址如下：https://gitee.com/tooleek/lock-spring-boot-starter-demo
#### QQ交流群
群号：779169894  
加群链接：https://jq.qq.com/?_wv=1027&k=5ziKU1r

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request