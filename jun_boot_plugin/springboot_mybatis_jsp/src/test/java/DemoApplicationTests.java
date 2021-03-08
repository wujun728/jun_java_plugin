import cn.kiwipeach.mapper.business.EmployMapper;
import cn.kiwipeach.model.business.Employ;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunApplication.class)
public class DemoApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Autowired
    private EmployMapper employMapper;

	@Test
	public void contextLoads() throws SQLException {
	    //1.数据源测试
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        //2.数据库查询测试
        Employ employ = employMapper.selectByPrimaryKey(new BigDecimal(7777));
        System.out.println(employ);
    }

}
