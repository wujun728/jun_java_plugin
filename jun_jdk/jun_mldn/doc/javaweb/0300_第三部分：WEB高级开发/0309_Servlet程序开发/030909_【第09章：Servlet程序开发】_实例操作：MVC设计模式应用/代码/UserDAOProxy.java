package org.lxh.mvcdemo.dao.proxy ;
import org.lxh.mvcdemo.vo.User ;
import org.lxh.mvcdemo.dbc.* ;
import org.lxh.mvcdemo.dao.* ;
import org.lxh.mvcdemo.dao.impl.* ;
import java.sql.* ;
public class UserDAOProxy implements IUserDAO {
	private DatabaseConnection dbc = null ;
	private IUserDAO dao = null ;
	public UserDAOProxy(){
		try{
			this.dbc = new DatabaseConnection() ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
		this.dao = new UserDAOImpl(dbc.getConnection()) ;
	}
	public boolean findLogin(User user) throws Exception{
		boolean flag = false ;
		try{
			flag = this.dao.findLogin(user) ;	// 调用真实主题，完成操作
		}catch(Exception e){
			throw e ;
		}finally{
			this.dbc.close() ;
		}
		return flag ;
	}
} 