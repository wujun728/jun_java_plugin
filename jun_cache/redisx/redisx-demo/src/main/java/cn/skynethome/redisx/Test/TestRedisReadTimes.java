package cn.skynethome.redisx.Test;

import cn.skynethome.redisx.RedisSharedMasterSlaveSentinelUtil;

public class TestRedisReadTimes
{
    public static volatile int j = 0;

    public static void main(String[] args) throws InterruptedException
    {
        RedisSharedMasterSlaveSentinelUtil.del("aaaabbbb");

        long s = System.currentTimeMillis();
        RedisSharedMasterSlaveSentinelUtil.setObject("aaaabbbb", "fsdfsdfasdgdfgdsfgdgfd");
        long e = System.currentTimeMillis();
        System.out.println("存储对象耗时：" + (e - s) + "ms");
        Thread.sleep(5000);
        
        for (int i = 0; i < 3500; i++)
        {
            new TimeRedis().start();
        }

        // RedisSharedMasterSlaveSentinelUtil.del("aaaabbbb");

    }
}

class TimeRedis extends Thread
{
    @Override
    public void run()
    {
        int i = 20;
        while (true)
        {
            // try
            // {
            // Thread.sleep(200);
            // }
            // catch (InterruptedException e)
            // {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }

            long start = System.currentTimeMillis();
            String str = (String) RedisSharedMasterSlaveSentinelUtil.getObject("aaaabbbb");
            long end = System.currentTimeMillis();
            long time = end - start;
            System.out.println("Connection:" + RedisSharedMasterSlaveSentinelUtil.getActiveConnections() + " ***** "
                    + TestRedisReadTimes.j + "===" + this.getId() + "=" + (time) + "ms");

            if (i <= 0)
            {
                break;
            }
            if (time >= 10)
            {
                TestRedisReadTimes.j++;
            }

            // RedisSharedMasterSlaveSentinelUtil.del("aaaabbbb");

            i--;
        }

    }
}