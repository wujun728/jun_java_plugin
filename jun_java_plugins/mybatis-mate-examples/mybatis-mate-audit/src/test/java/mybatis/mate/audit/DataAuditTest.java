package mybatis.mate.audit;

import mybatis.mate.audit.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 字段加解密测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataAuditTest {
    @Autowired
    private UserService userService;

    @Test
    public void test() {
        userService.dataAudit();
    }
}
