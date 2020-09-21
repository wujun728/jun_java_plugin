package cn.skynethome.redisx.Test;

import cn.skynethome.redisx.RedisSharedMasterSlaveSentinelUtil;

public class TestRedisSharedMasterSlaveSentinelUtil
{
    public static void main(String[] args) throws InterruptedException
    {
        String key = "aaaa";

        for (int i = 0; i < 20; i++)
        {
            new WriteRedisSharedMasterSlaveSentinelThread(key).start();
        }

    }
}

class WriteRedisSharedMasterSlaveSentinelThread extends Thread
{
    private String key;

    public WriteRedisSharedMasterSlaveSentinelThread(String key)
    {
        this.key = key;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 50; i++)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try
            {
                Thread.sleep(2000);
                RedisSharedMasterSlaveSentinelUtil.setString(key,
                        "TestRedisMasterSlaveSentinelUtil001" + Math.random() * 1000);
               // Thread.sleep(3000);
                //RedisSharedMasterSlaveSentinelUtil.del(key);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}