package mybatis.mate.dict;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import mybatis.mate.dict.entity.User;
import mybatis.mate.dict.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 字典测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DictTest {
    @Resource
    private UserMapper mapper;

    @Test
    public void test() {
        User user = new User();
        user.setId(IdWorker.getId());
        user.setUsername("小羊肖恩");
        user.setSex(1);
        user.setStatus(1);
        assertThat(mapper.insert(user)).isGreaterThan(0);
        assertThat(user.getId()).isNotNull();
        System.out.println(mapper.selectById(user.getId()));
        mapper.selectList(null).forEach(System.out::println);
    }
}
