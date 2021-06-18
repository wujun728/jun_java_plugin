package cn.kiwipeach.demo.mapper;

import cn.kiwipeach.demo.SpringJunitBase;
import cn.kiwipeach.demo.domain.Employ;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.*;

import static org.junit.Assert.*;

/**
 * ${DESCRIPTION}
 *
 * @author liuburu
 * @create 2018/01/08
 **/
public class EmployMapperTest extends SpringJunitBase {


    @Autowired
    private DataSource dataSource;

    @Test
    public void testDruidDatesource() throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = "select * from emp where empno = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "7777");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
        }
        connection.close();
    }


    @Autowired
    private EmployMapper employMapper;

    @Test
    public void selectByPrimaryKey() throws Exception {
        Employ employ = employMapper.selectByPrimaryKey(new BigDecimal(7369));
        System.out.println(JSON.toJSONString(employ));
    }


    @Test
    public void testConnection() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.10.50:1521:sgsb1", "dbquery_new", "sbggcx_yw3edc");
        System.out.println(connection);
    }

}