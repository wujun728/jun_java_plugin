import com.objcoding.jdbc.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by chenghui.zhang on 2018/2/10.
 */
public class HandlerTest {

    /**
     * BeanListHandler的应用，它是多行处理器
     * 每行对象一个Stu对象！
     *
     * @throws Exception
     */
    @Test
    public void fun3() throws Exception {
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "select * from user";
        List<User> stuList = qr.query(sql, new BeanListHandler<>(User.class));

        System.out.println(stuList);
    }

    /**
     * MapHandler的应用，它是单行处理器，把一行转换成一个Map对象
     *
     * @throws SQLException
     */
    @Test
    public void fun4() throws SQLException {
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "select * from user where uid=?";
        Object[] params = {1001};
        Map map = qr.query(sql, new MapHandler(), params);

        System.out.println(map);
    }

    /**
     * MapListHandler，它是多行处理器，把每行都转换成一个Map，即List<Map>
     *
     * @throws SQLException
     */
    @Test
    public void fun5() throws SQLException {
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "select * from user";
        List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());

        System.out.println(mapList);
    }

    /**
     * ScalarHandler，它是单行单列时使用，最为合适！
     *
     * @throws SQLException
     */
    @Test
    public void fun6() throws SQLException {
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "select count(*) from user";
      /*
       * Integer、Long、BigInteger
       */
        Number cnt = (Number) qr.query(sql, new ScalarHandler());

        long c = cnt.longValue();
        System.out.println(c);
    }
}
