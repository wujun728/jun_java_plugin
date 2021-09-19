package com.jun.plugin.servlet.guice.core.db;

import java.sql.SQLException;
import java.util.List;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.jun.plugin.servlet.guice.user.entity.User;

public class QueryRunnerCRUDTest {

    /*
     *测试表
    CREATE TABLE `users` (
		`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
		`account` VARCHAR(50) NULL DEFAULT NULL,
		`user_id` BIGINT(20) NOT NULL,
		PRIMARY KEY (`id`)
	)
	COMMENT='user表'
	COLLATE='latin1_swedish_ci'
	ENGINE=InnoDB
	;
     */
    
    
    public void add() throws SQLException {
        //将数据源传递给QueryRunner，QueryRunner内部通过数据源获取数据库连接
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "INSERT INTO `test`.`users` (`account`, `user_id`) VALUES (?, ?);";
        Object params[] = {"hello world",2323};
  
        qr.update(sql, params);
    }
    
   
    
    public void delete() throws SQLException {
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "delete from users where id=?";
        qr.update(sql, 1);

    }

    
    public void update() throws SQLException {
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "update users set account=? where id=?";
        Object params[] = { "ddd", 2};
        qr.update(sql, params);
    }

    
    public void find() throws SQLException {
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "select * from users where id=?";
        Object params[] = {2};
        User user = (User) qr.query(sql, params, new BeanHandler(User.class));
        System.out.println(user.getId());
    }

    
    public void getAll() throws SQLException {
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "select * from users";
        List<User> list = (List<User>) qr.query(sql, new BeanListHandler(User.class));
        for(User u : list){
        	 System.out.println(u.getUser_id());
        }
       
    }
    
    public void testBatch() throws SQLException {
        QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "INSERT INTO `test`.`users` (`account`, `user_id`) VALUES (?, ?)";
        Object params[][] = new Object[10][];
        for (int i = 0; i < 10; i++) {
            params[i] = new Object[] {"123"+i, i};
        }
        qr.batch(sql, params);
    }
    
    public static void main(String[] args) throws Exception {
    	QueryRunnerCRUDTest t=new QueryRunnerCRUDTest();
    	t.add();
    	t.getAll();
    	//t.delete();
	}
    

}