package com.jun.plugin.servlet.guice.user.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;

import com.google.inject.Singleton;
import com.jun.plugin.servlet.guice.core.db.ConnectionContext;
import com.jun.plugin.servlet.guice.core.db.dao.impl.BaseDaoImpl;
import com.jun.plugin.servlet.guice.core.db.model.Page;
import com.jun.plugin.servlet.guice.user.dao.UserDao;
import com.jun.plugin.servlet.guice.user.entity.User;

@Singleton
public class UserDaoImpl extends BaseDaoImpl<User,Integer> implements UserDao {
	

	@Override
	public void add(String name, int userId) throws SQLException {
		String sql = "INSERT INTO `test`.`users` (`account`, `user_id`) VALUES (?, ?);";
		Object params[] = { name, userId };
		//qr.update(ConnectionContext.getInstance().getCon(), sql, params);
		super.add(sql, params);
		
		String tableName="users";
		List<Integer> ids=new ArrayList<>();
		ids.add(2);
		ids.add(3);
        List<User> list = super.findByIds(tableName, ids);
        for(User u : list){
        	 System.out.println(u.getId()+" "+u.getAccount()+" "+u.getUser_id());
        }
        Map<String, Object> map=new HashMap<>();
        map.put("id", 2);
       /* map.put("account", "chidongxie");
        Page<User> pageList=super.findByPage(tableName, 0, 10, map);
        System.out.println(pageList.getTotalCount());
        System.out.println(pageList.getResults().size());*/
        
        String dataSql="select * from users limit 0,20";
        String countSql="select count(1) as count from users";
        Page<User> pageUserlist=super.findByPage(dataSql, countSql);
        for(User u :pageUserlist.getResults()){
        	System.out.println(u.getAccount()+" "+u.getUser_id()+" "+new Date());
        }

	}

	@Override
	public void addTest() throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "INSERT INTO `test`.`users` (`account`, `user_id`) VALUES (?, ?);";
		Object params[] = { "test", new Date()};
		qr.update(ConnectionContext.getInstance().getCon(), sql, params);
	}
	
	

}
