package cn.skynethome.redisx.Test;

import cn.skynethome.redisx.RedisMasterSlaveSentinelUtil;

public class TestRedisMasterSlaveSentinelUtil
{
    public static void main(String[] args)
    {

        String key = "aaaa";
        RedisMasterSlaveSentinelUtil.setString(key, "TestRedisMasterSlaveSentinelUtil001");

        System.out.println(RedisMasterSlaveSentinelUtil.del(key));

    }
}
