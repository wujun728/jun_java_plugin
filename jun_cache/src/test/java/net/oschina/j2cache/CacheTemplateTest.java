package net.oschina.j2cache;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author Wujun
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-cache-test.xml"})
public class CacheTemplateTest {

    @Autowired
    private CacheTemplate cacheTemplate;

    @Test
    public void test() {
        cacheTemplate.clear("testRegion");

        cacheTemplate.set("testRegion", "testKey", "I'm a hds boy!!");

        CacheObject co = cacheTemplate.get("testRegion", "testKey");

        Assert.assertEquals("I'm a hds boy!!", co.getValue());

        cacheTemplate.evict("testRegion", "testKey");

        co = cacheTemplate.get("testRegion", "testKey");

        Assert.assertNull(co.getValue());
    }

}
