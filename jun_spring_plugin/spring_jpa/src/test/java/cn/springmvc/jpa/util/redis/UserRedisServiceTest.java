package cn.springmvc.jpa.util.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.springmvc.jpa.common.redis.UserRedisService;
import cn.springmvc.jpa.common.utils.fmt.FormatFactory;
import cn.springmvc.jpa.entity.User;

/**
 * @author Vincent.wang
 *
 *         production为生产环境，development为测试环境
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml", "classpath:/spring/applicationContext-dao.xml", "classpath:/spring/applicationContext-redis.xml" })
@ActiveProfiles("development")
public class UserRedisServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserRedisServiceTest.class);

    @Autowired
    private UserRedisService redisService;

    @Test
    public void testRedis() {
        try {
            String id = "000000001";
            User user = new User();
            user.setId(id);
            user.setName("admin");
            user.setEmail("infowangxin@163.com");
            user.setTrueName("管理员");
            user.setPassword("123456");

            redisService.add(user);

            User u = redisService.getUser(id);
            log.warn("## {} ", FormatFactory.objectToJson(u));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
