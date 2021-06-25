import com.gs.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2017/11/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/config/spring-context.xml",
        "classpath:/config/spring-redis-single.xml"})
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void testRedis() {
        redisService.getById(1);
    }

    @Test
    public void testRedisList() {
        redisService.listAll();
    }
}
