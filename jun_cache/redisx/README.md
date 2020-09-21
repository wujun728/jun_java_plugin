# RedisX

>>什么是redisx？

>1、包含几乎所有redis线程池实现以及用法；

>2、包含redis Master Slave 主备实现；

>3、包含redis 读写分离；

>4、包含redis Sentinel 线程池；

>5、包含redis Sentinel Salve 线程池；

>6、新增线程池对spring支持，和spring完美集成；

>7、包含redis Sentinel Sharded Master Slave 线程池；

>8、包含redis 对象序列化压缩算法，gz和lzma等；

>9、新增ehcache作为redisx一级缓存；

>10、支持ehcache多机器共享缓存；

# RedisX 单个Reids线程 用法
```
  public class TestRedis {
	public static void main(String[] args) {

		for (int i = 0; i < 5000; i++) {
			new RedisThread("threadredis" + i).start();

		}
	}
}

class RedisThread extends Thread {
	private String name;

	public RedisThread() {
		// TODO Auto-generated constructor stub
	}

	public RedisThread(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < 100; i++) {
				System.out.println(PowerfulRedisUtil.setString(this.name + "xxx" + i, "fdsfsdf" + i) + "=" + i + "="
						+ this.getName() + this.getId());

				sleep(200);

			}

			for (int i = 0; i < 50; i++) {
				System.out.println(PowerfulRedisUtil.del(this.name + "xxx" + i));

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
```
# RedisX SharedMasterSlaveSentinel 用法
```
public class TestRedisUtils
{
    public static String value="2017年春运来得早，又赶上火车票预售期由60天调整至30天，购票期相对集中。对准备回家过年的人们而言，回家的火车票还好买吗？";

    public static void main(String[] args) throws InterruptedException
    {
        
        for (int i = 0; i < 500; i++)
        {
            new ThreadTest("treahd"+i).start();

        }      
}

class ThreadTest extends Thread
{
    private String name;

    public ThreadTest()
    {
        super();
    }

    public ThreadTest(String name)
    {
        super();
        this.name = name;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 10; i++)
        {
            String string = RedisSharedMasterSlaveSentinelUtil.setObject(name+i,TestRedisUtils.value+name+i);             
                    System.out.println(string + "=" + i);

        }

        for (int i = 0; i < 10; i++)
        {
            String value = RedisSharedMasterSlaveSentinelUtil.getObject(name + i, String.class);
            if(null != value)
            {
                System.out.println(value);
            }
            long l = RedisSharedMasterSlaveSentinelUtil.del(name + i);
            System.out.println(l + "=" + i);

        }
    }
}


```
# RedisX Redis Cluster 线程池用法参考
```
public class TestRedisCluster {

	public static void main(String[] args) {
	    for(int i=0;i<100;i++)
	    {
	        new RedisClusterThread("RedisClusterThread"+i,100).start();
	    }
	    
	}
	
	
	
}


class RedisClusterThread extends Thread {
    private String name;
    private int size;

    public RedisClusterThread() {
        // TODO Auto-generated constructor stub
    }

    public RedisClusterThread(String name,int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void run() {
       // try {
            for (int i = 0; i < this.size; i++) {
                System.out.println(RedisClusterUtils.setString(this.name + "xxx" + i, "fdsfsdf" + i) + "=" + i + "="
                        + this.getName() + this.getId());

                //sleep(200);

            }

            for (int i = 0; i < this.size; i++) {
                System.out.println(RedisClusterUtils.del(this.name + "xxx" + i));

            }
       // } catch (InterruptedException e) {
            // TODO Auto-generated catch block
       //     e.printStackTrace();
      //  }
    }
}
```
#对spring的支持，包cn.skynethome.redisx.spring下提供spring的支持，用法参考，master slave 读写分离spring配置文件如下

`
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
 xmlns:context="http://www.springframework.org/schema/context"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://www.springframework.org/schema/beans
      http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
      http://www.springframework.org/schema/context
      http://www.springframework.org/schema/context/spring-context.xsd">
    
    <!-- 直接使用redisx线程池 -->
	<bean id="redisXMsterSlave"
		class="cn.skynethome.redisx.spring.RedisXMasterSlave">
		<!-- <property name="configFlag" value="false"></property> --> <!-- 可不配置或false  -->
		<property name="master" value="10.0.1.38:6379"></property>
		<property name="slave" value="10.0.1.38:6380"></property><!-- <property name="slave" value="10.0.1.38:6380,10.0.1.38:6381"></property> -->
		<property name="AUTH" value="123^xdxd_qew"></property><!-- 没有密码可配置为空 -->
		<property name="TEST_ON_BORROW" value="true"></property>
		<property name="MAX_ACTIVE" value="300"></property>
		<property name="MAX_IDLE" value="10"></property>
		<property name="MAX_WAIT" value="3000"></property>
		<property name="TIMEOUT" value="10000"></property>
	</bean>
    
    
    <!-- 指定配置文件配置redisx线程池 -->
	<bean id="redisXMsterSlaveOfConfig"
		class="cn.skynethome.redisx.spring.RedisXMasterSlave">
		<property name="configFlag" value="true"></property>
		<property name="configPath" value="properties/redisx_master_slave_spring.properties"></property>
	</bean> 
	
</beans>
`

## spring配置文件配置可以直接配置线程池或指定一个配置文件配置
#JAVA Spring 注解测试

```
 @RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:applicationContext_masterslave.xml") // 加载配置
public class SpringRedisXMasterSlaveTest 
{
    
	@Autowired
	private RedisXMasterSlave redisXMsterSlave;
	
	@Autowired
	private RedisXMasterSlave redisXMsterSlaveOfConfig;
	
	@Test
	public void TestRedisX()
	{
		String key = "key:_redisx_01_masterslave";
		
		//添加对象
		String r_ = redisXMsterSlave.setObject(key, "12356465");
		System.out.println("存入返回:"+r_);
		//获取对象
		String s = redisXMsterSlave.getObject(key, String.class);
		System.out.println("缓存取数据:"+ s);
		
		//删除对象
		long d_ = redisXMsterSlaveOfConfig.del(key);
		System.out.println("删除返回:"+ d_);
	}
}
```

RedisX交流群：

![输入图片说明](http://git.oschina.net/uploads/images/2017/0103/124918_80eebd6c_378203.png "在这里输入图片标题")

## 推荐开源项目[zbus](https://www.oschina.net/p/zbus),[talent-aio](https://www.oschina.net/p/talent-aio),[nredis-proxy](https://www.oschina.net/p/nredis-proxy)
