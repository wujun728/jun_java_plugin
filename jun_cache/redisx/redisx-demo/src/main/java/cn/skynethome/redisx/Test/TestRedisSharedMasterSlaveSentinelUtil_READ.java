package cn.skynethome.redisx.Test;

import cn.skynethome.redisx.RedisSharedMasterSlaveSentinelUtil;

public class TestRedisSharedMasterSlaveSentinelUtil_READ
{
    public static void main(String[] args) throws InterruptedException
    {
        String key = "aaaa";

        for (int i = 0; i < 50; i++)
        {
            new ReadRedisSharedMasterSlaveSentinelThread(key).start();
        }

    }
}

class ReadRedisSharedMasterSlaveSentinelThread extends Thread
{
    private String key;
    public ReadRedisSharedMasterSlaveSentinelThread(String key)
    {
        this.key = key;
    }
    
    @Override
    public void run()
    {
        for(int i=0;i<50;i++)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            System.out.println(RedisSharedMasterSlaveSentinelUtil.getString(key));
        }
       
    }
}
