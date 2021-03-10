package cn.springmvc.jpa.util.memcached;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.springmvc.jpa.common.memcached.MemcachedFactory;

/**
 * @author Vincent.wang
 *
 *         production为生产环境，development为测试环境
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml", "classpath:/spring/applicationContext-dao.xml", "classpath:/spring/applicationContext-memcached.xml" })
@ActiveProfiles("development")
public class MemcachedUtilTest {

    private static final Logger log = LoggerFactory.getLogger(MemcachedUtilTest.class);

    @Autowired
    private MemcachedFactory memcachedFactory;

    @Test
    public void memcachedTest() {
        try {
            String key = "username";
            int exp = 3600;
            String value = "张三";

            // MemcachedFactory memcachedFactory = ComponentRegistry.getMemcachedFactory();

            memcachedFactory.set(key, exp, value);
            Object temp = memcachedFactory.get(key);
            log.warn("## value = {}", temp);
            System.out.println(temp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
