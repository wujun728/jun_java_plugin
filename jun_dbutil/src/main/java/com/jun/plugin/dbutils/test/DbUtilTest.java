package com.jun.plugin.dbutils.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.jun.plugin.dbutils.dto.UserDto;
import com.jun.plugin.dbutils.utils.DbBuilder;

public class DbUtilTest {
	

	public static void main(String[] args) {
		try {
			System.out.println(DbBuilder.getCount("select count(*) from T_USER"));

			// 返回ArrayHandler结果,第一行结果：Object[]
			System.out.println("返回ArrayHandler结果......");
			Object[] arrayResult = DbBuilder.getFirstRowArray("select * from T_USER where id=2");
			QueryRunner runner = DbBuilder.getQueryRunner();
			for (int i = 0; i < arrayResult.length; i++) {
				System.out.print(arrayResult[i] + "    ");
			}
			System.out.println();

			// 返回ArrayListHandler结果
			System.out.println("返回ArrayListHandler结果.........");
			List<Object[]> arrayListResult = DbBuilder.getListArray("select * from T_USER");
			for (int i = 0; i < arrayListResult.size(); i++) {
				for (int j = 0; j < arrayListResult.get(i).length; j++) {
					System.out.print(arrayListResult.get(i)[j] + "    ");
				}
				System.out.println();
			}
			System.out.println();

			// 返回bean
			UserDto user = DbBuilder.getBean("select * from T_USER where Id=?", UserDto.class, 1);
			System.out.println(user.getUsername());

			// 返回beanlist
			System.out.println("返回BeanList结果......");
			List<UserDto> beanListResult = DbBuilder.getListBean("select * from T_USER", UserDto.class);
			Iterator<UserDto> iter_beanList = beanListResult.iterator();
			while (iter_beanList.hasNext()) {
				System.out.println(iter_beanList.next().getUsername());
			}

			// 返回指定列
			System.out.println("返回ColumnList结果......");
			List<Object> columnResult = runner.query("select * from T_USER", new ColumnListHandler<Object>("username"));
			Iterator<Object> iter = columnResult.iterator();
			while (iter.hasNext()) {
				System.out.println(iter.next());
			}

			// 返回KeyedHandler结果：Map<Object,Map<String,Object>>：map的key为KeyedHandler指定
			System.out.println("返回KeyedHandler结果.........");
			Map<Object, Map<String, Object>> keyedResult = runner.query("select * from T_USER", new KeyedHandler<Object>(
					"username"));
			System.out.println(keyedResult.get("username"));

			// MapHandler
			System.out.println("返回MapHandler结果.........");
			Map<String, Object> mapResult = DbBuilder.getFirstRowMap("select * from T_USER");
			Iterator<String> iter_mapResult = mapResult.keySet().iterator();
			while (iter_mapResult.hasNext()) {
				System.out.print(mapResult.get(iter_mapResult.next()) + "   ");
			}
			System.out.println();

			// 返回MapListHandler结果
			System.out.println("返回MapListHandler结果.........");
			List<Map<String, Object>> mapListResult = DbBuilder.getListMap("select * from T_USER");
			for (int i = 0; i < mapListResult.size(); i++) {
				Iterator<String> values = mapListResult.get(i).keySet().iterator();
				while (values.hasNext()) {
					System.out.print(mapListResult.get(i).get(values.next()) + "   ");
				}
				System.out.println();
			}
			Connection conn = DbBuilder.getConnection();
			DbBuilder.beginTransaction(conn);
			System.out.println(DbBuilder.save(conn,"insert into t_user(username,password) values(?,?)", "demo", "demo"));
			DbBuilder.commit(conn);
			DbBuilder.close(conn);
			Object increaseId = runner.query("select last_insert_id()", new ScalarHandler<Object>());
			System.out.println(increaseId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
