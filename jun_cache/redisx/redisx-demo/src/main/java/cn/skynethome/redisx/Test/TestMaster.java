package cn.skynethome.redisx.Test;

import cn.skynethome.redisx.RedisMasterSlaveSentinelUtil;
import cn.skynethome.redisx.RedisSharedMasterSlaveSentinelUtil;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx]    
  * 文件名称:[TestMaster]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月3日 上午11:43:43]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月3日 上午11:43:43]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class TestMaster
{
    public static void main(String[] args)
    {

        /*
         * JedisPoolConfig config = new JedisPoolConfig();
         * config.setMaxTotal(600); config.setMaxIdle(300);
         * config.setMaxWaitMillis(1000); config.setTestOnBorrow(true);
         * 
         * Set<String> sentinels = new LinkedHashSet<String>();
         * sentinels.add("192.168.1.16:63791");
         * sentinels.add("192.168.1.17:63791"); MasterSlaveJedisSentinelPool
         * masterSlaveJedisPool = new MasterSlaveJedisSentinelPool("master",
         * sentinels,config,"123^xdxd_qew"); MasterSlaveJedis masterSlaveJedis =
         * masterSlaveJedisPool.getResource();
         * masterSlaveJedis.auth("123^xdxd_qew"); //>>> masterSlaveJedis =
         * MasterSlaveJedis {master=192.168.137.101:6379,
         * slaves=[192.168.137.101:6380, 192.168.137.101:6381]}
         * System.out.println(">>> masterSlaveJedis = " + masterSlaveJedis);
         * 
         * masterSlaveJedis.set("nowTime", "2015-03-16 15:34:55"); // The
         * underlying actually call the master.set("nowTime",
         * "2015-03-16 15:34:55");
         * 
         * LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(200));
         * 
         * String slaveHolder1 = "myslave1"; Jedis slave1 =
         * masterSlaveJedis.opsForSlave(slaveHolder1); // if no any slave found,
         * opsForSlave() will return master as a slave to be use
         * System.out.println(">>> nowTime = " + slave1.get("nowTime")); //>>>
         * nowTime = 2015-03-16 15:34:55
         * 
         * String slaveHolder2 = "myslave1"; Jedis slave2 =
         * masterSlaveJedis.opsForSlave(slaveHolder2); System.out.println(
         * ">>> nowTime = " + slave2.get("nowTime")); //>>> nowTime = 2015-03-16
         * 15:34:55
         * 
         * System.out.println(slave1.equals(slave2)); // must be true if
         * slaveHolder1 equals slaveHolder2
         * 
         * masterSlaveJedisPool.returnResource(masterSlaveJedis);
         */
//        RedisMasterSlaveUtil.setObject("test", new Date());
//        Date date = RedisMasterSlaveUtil.getObject("test", Date.class);
//        System.out.println(date);
        
        //RedisAPI.getPool().getResource().set("xxx", "dddfxxxf");
        
//        TestMaster testMaster = new TestMaster();
//        for(int i = 0;i < 500;i++)
//        {
//           testMaster.new TestThred().start();
//        }
        
        String key = "RedisSharedMasterSlaveSentinelUtilkeys";
        
        RedisSharedMasterSlaveSentinelUtil.setObject(key, key+System.currentTimeMillis());
        System.out.println(RedisSharedMasterSlaveSentinelUtil.getObject(key,String.class));
        System.out.println(RedisSharedMasterSlaveSentinelUtil.del(key));
        
        //System.out.println(RedisSharedMasterSlaveSentinelUtil.getString("xxx"));
        
        

    }

    class TestThred extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                while (true)
                {

                    String teString = RedisSharedMasterSlaveSentinelUtil.getString("xxx");
                    //String teString = RedisAPI.get("xxx");
                    System.out.println(teString);

                    teString = RedisMasterSlaveSentinelUtil.getString("xxx");
                    //String teString = RedisAPI.get("xxx");
                    System.out.println(teString);

                    
                    sleep(100);

                }
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
