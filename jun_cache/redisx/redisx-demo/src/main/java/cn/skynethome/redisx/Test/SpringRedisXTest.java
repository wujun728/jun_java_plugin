package cn.skynethome.redisx.Test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.skynethome.redisx.spring.RedisXImpl;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx]    
  * 文件名称:[SpringRedisXTest]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月4日 上午11:38:23]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月4日 上午11:38:23]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:applicationContext.xml") // 加载配置
public class SpringRedisXTest 
{
    
	@Autowired
	private RedisXImpl redisX;
	
	@Autowired
	private RedisXImpl redisXOfConfig;
	
	@Test
	public void TestRedisX()
	{
		String key = "key:_redisx_01";
		
		//添加对象
		String r_ = redisX.setObject(key, new User("12356465","pwd"));
		System.out.println("存入返回:"+r_);
		//获取对象
		User s = redisXOfConfig.getObject(key, User.class);
		System.out.println("缓存取数据:"+ s);
		
		for(int i = 0;i<1000;i++)
		{
		    new redisx_masterThread(redisXOfConfig).start();
		}
		
		//删除对象
		long d_ = redisXOfConfig.del(key);
		System.out.println("删除返回:"+ d_);
		
	}
}

class redisx_masterThread extends Thread 
{
    
    private RedisXImpl redisXOfConfig;
    
    public redisx_masterThread()
    {
        
    }
    
    public redisx_masterThread( RedisXImpl redisXOfConfig)
    {
        this.redisXOfConfig = redisXOfConfig;
    }
    
    @Override
    public void run()
    {
        for(int i=0; i<20; i++)
        {
            String key = "key:_redisx_01";
            long start = System.currentTimeMillis();
            //获取对象
            User s = redisXOfConfig.getObject(key, User.class);
            long end = System.currentTimeMillis();
            System.out.println("缓存取数据:"+ s +" = times:"+(end - start)+"ms");
        }

    }
}
