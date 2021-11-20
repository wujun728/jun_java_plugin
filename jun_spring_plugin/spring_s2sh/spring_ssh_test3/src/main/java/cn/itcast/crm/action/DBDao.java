package cn.itcast.crm.action;

import java.sql.SQLException;

/**
 * 文件名字:DBDao.java<br>
 * 文件作用:获取数据库的连接<br>
 */
public class DBDao {
	public void getConn() {
		try {
			DBUtil.getConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
