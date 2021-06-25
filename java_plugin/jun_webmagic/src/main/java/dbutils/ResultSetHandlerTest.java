package dbutils;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

/**
* @ClassName: ResultSetHandlerTest
* @Description:测试dbutils各种类型的处理器
* @date: 2014-10-6 上午8:39:14
*
*/ 
public class ResultSetHandlerTest {

    @Test
    public void testArrayHandler() throws SQLException{
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from users";
        Object result[] = (Object[]) qr.query(sql, new ArrayHandler());
        System.out.println(Arrays.asList(result));  //list  toString()
    }
    
    @Test
    public void testArrayListHandler() throws SQLException{
        
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from users";
        List<Object[]> list = (List) qr.query(sql, new ArrayListHandler());
        for(Object[] o : list){
            System.out.println(Arrays.asList(o));
        }
    }
    
    @Test
    public void testColumnListHandler() throws SQLException{
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from users";
        List list = (List) qr.query(sql, new ColumnListHandler("id"));
        System.out.println(list);
    }
    
    @Test
    public void testKeyedHandler() throws Exception{
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from users";
        
        Map<Integer,Map> map = (Map) qr.query(sql, new KeyedHandler("id"));
        for(Map.Entry<Integer, Map> me : map.entrySet()){
            int  id = me.getKey();
            Map<String,Object> innermap = me.getValue();
            for(Map.Entry<String, Object> innerme : innermap.entrySet()){
                String columnName = innerme.getKey();
                Object value = innerme.getValue();
                System.out.println(columnName + "=" + value);
            }
            System.out.println("----------------");
        }
    }
    
    @Test
    public void testMapHandler() throws SQLException{
        
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from users";
        
        Map<String,Object> map = (Map) qr.query(sql, new MapHandler());
        for(Map.Entry<String, Object> me : map.entrySet())
        {
            System.out.println(me.getKey() + "=" + me.getValue());
        }
    }
    
    
    @Test
    public void testMapListHandler() throws SQLException{
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from users";
        List<Map> list = (List) qr.query(sql, new MapListHandler());
        for(Map<String,Object> map :list){
            for(Map.Entry<String, Object> me : map.entrySet())
            {
                System.out.println(me.getKey() + "=" + me.getValue());
            }
        }
    }
    
    @Test
    public void testScalarHandler() throws SQLException{
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select count(*) from users";  //[13]  list[13]
        int count = ((Long)qr.query(sql, new ScalarHandler(1))).intValue();
        System.out.println(count);
    }
}