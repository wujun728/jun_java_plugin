import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sam.demo.redis.Main;
import com.sam.demo.redis.RedisOperation;


@RunWith(value=SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
public class TestRedisOperation {

	@Autowired
	private RedisOperation redisOperation;
	
	@Test
	public void saveString(){
		redisOperation.saveStringValue("china", "chongqing");
	}
	@Test
	public void getString(){
		String value = redisOperation.getStringValue("china");
		System.out.println(value);
	}
}
