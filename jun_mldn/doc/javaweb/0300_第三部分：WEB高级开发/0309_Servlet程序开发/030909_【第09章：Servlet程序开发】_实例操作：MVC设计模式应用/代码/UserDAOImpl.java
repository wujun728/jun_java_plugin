package org.lxh.mvcdemo.dao.impl ;
import org.lxh.mvcdemo.vo.User ;
import org.lxh.mvcdemo.dbc.* ;
import org.lxh.mvcdemo.dao.* ;
import java.sql.* ;
public class UserDAOImpl implements IUserDAO {
	private Connection conn = null ;
	private PreparedStatement pstmt = null ;
	public UserDAOImpl(Connection conn){
		this.conn = conn ;
	}
	public boolean findLogin(User user) throws Exception{
		boolean flag = false ;
		String sql = "SELECT name FROM user WHERE userid=? AND password=?" ;
		this.pstmt = this.conn.prepareStatement(sql) ;
		this.pstmt.setString(1,user.getUserid()) ;
		this.pstmt.setString(2,user.getPassword()) ;
		ResultSet rs = this.pstmt.executeQuery() ;
		if(rs.next()){
			user.setName(rs.getString(1)) ;	// 取出一个用户的真实姓名
			flag = true ;
		}
		this.pstmt.close() ;
		return flag ;
	}
} 