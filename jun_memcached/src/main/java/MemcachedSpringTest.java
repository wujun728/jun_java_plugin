import com.whalin.MemCached.MemCachedClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xiao-kang on 2017/6/1.
 */
public class MemcachedSpringTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-memcached.xml");
        MemCachedClient client = (MemCachedClient) context.getBean("memcachedClient");
        client.add("test", "value");
        System.out.println(client.get("test"));
    }
}
