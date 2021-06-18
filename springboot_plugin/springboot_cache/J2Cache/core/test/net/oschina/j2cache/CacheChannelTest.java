package net.oschina.j2cache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.*;

public class CacheChannelTest {

    CacheChannel channel;

    @Before
    public void setUp() {
        channel = J2Cache.getChannel();
        System.out.println("init j2cache and clear all data.");
    }

    @After
    public void tearDown() {
        for(CacheChannel.Region rgn : channel.regions())
            channel.clear(rgn.getName());
        channel.close();
        System.out.println("close j2cache " + Thread.activeCount());
    }

    @Test
    public void get() {
        String region = "Users";
        String key = "gitee";
        int[] array = new int[]{1,2,3,4,5};
        assertNull(channel.get(region,key, true).getValue());

        CacheObject co = channel.get(region, key, true);
        assertNull(co.getValue());
        assertEquals(co.getLevel(), CacheObject.LEVEL_1);

        channel.set(region, key, array);
        assertArrayEquals((int[])channel.get(region,key).getValue(), array);
    }

    @Test
    public void get1() {
        String region = "Users";
        String key = "gitee";
        assertEquals(key, channel.get(region, key, (k) -> k, false).asString());
    }

    @Test
    public void get2() {
        String region = "Users";
        for(int i=1;i<=5;i++)
            channel.set(region, String.valueOf(i), String.valueOf(i));
        Map<String, CacheObject> cos = channel.get(region, Arrays.asList("1","2","3","4"));
        for(int i=1;i<5;i++)
            assertEquals(cos.get(String.valueOf(i)).asString(), String.valueOf(i));
    }

    @Test
    public void get3() {
        String region = "Users";
        Map<String, CacheObject> cos = channel.get(region, Arrays.asList("1","2","3","4"), (k) -> k, false);
        for(int i=1;i<5;i++)
            assertEquals(cos.get(String.valueOf(i)).asString(), String.valueOf(i));
    }

    @Test
    public void exists() {
        String region = "Users";
        channel.set(region, "ld", "Winter Lau");
        channel.set(region, "zt", "Zhu tao");
        assertTrue(channel.exists(region, "zt"));
    }

    @Test
    public void testCaffeineExpire () {
        String region = "Users";
        String key = "CaffeineNeverExpire";
        channel.set(region,key,"OSChina.net");
        assertTrue(CacheProviderHolder.getLevel1Cache(region).keys().contains(key));
        assertTrue(CacheProviderHolder.getLevel2Cache(region).keys().contains(key));
        assertTrue(channel.exists(region, key));
    }

    @Test
    public void check() {
        String region = "Users";
        channel.set(region, "ld", "Winter Lau");
        channel.set(region, "zt", "Zhu tao");
        assertEquals(channel.check(region, "ld"), 1);
    }

    @Test
    public void set() {
    }

    @Test
    public void set1() {
    }

    @Test
    public void set2() {
    }

    @Test
    public void set3() {
    }

    @Test
    public void set4() {
    }

    @Test
    public void set5() {
    }

    @Test
    public void set6() {
    }

    @Test
    public void set7() {
    }

    @Test
    public void evict() {
    }

    @Test
    public void clear() {
        String region = "Users";
        int count = 10;
        for(int i=0;i<count;i++)
            channel.set(region, String.valueOf(i), i);
        assertEquals(channel.keys(region).size(), count);
        channel.clear(region);
        assertEquals(channel.keys(region).size(), 0);
    }

    @Test
    public void regions() {
        int count = 10;
        for(int i=0;i<count;i++)
            channel.set("R"+i, "1", "1000");
        Collection<CacheChannel.Region> regions = channel.regions();
        assertEquals(count, regions.size());

        for(CacheChannel.Region region : regions) {
            String rn = region.getName();
            assertTrue(rn.charAt(0) == 'R');
            assertTrue(rn.charAt(1)>='0' && rn.charAt(1)<='9');
        }
    }

    @Test
    public void keys() {
        int count = 10;
        for(int i=0;i<count;i++)
            channel.set("Users", String.valueOf(i), i);
        Collection<String> keys = channel.keys("Users");
        assertEquals(keys.size(), count);
        for(String key : keys) {
            assertTrue(Integer.parseInt(key) < count);
        }
    }
}