package cn.centychen.springboot.starter.xxljob;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Create by cent at 2019-05-08.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class})
public class XxlJobStarterTest {

    /**
     * For test ...
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        Thread.sleep(10 * 1000L);
    }
}
