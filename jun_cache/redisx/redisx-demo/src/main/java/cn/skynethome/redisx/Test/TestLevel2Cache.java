package cn.skynethome.redisx.Test;

import cn.skynethome.redisx.j2cache.CacheChannel;
import cn.skynethome.redisx.j2cache.J2Cache;

public class TestLevel2Cache
{
    public static void main(String[] args)
    {
        CacheChannel cache = J2Cache.getChannel();
        cache.set("cache1","key1","OSChina.net");
        cache.get("cache1","key1");
    }
}
