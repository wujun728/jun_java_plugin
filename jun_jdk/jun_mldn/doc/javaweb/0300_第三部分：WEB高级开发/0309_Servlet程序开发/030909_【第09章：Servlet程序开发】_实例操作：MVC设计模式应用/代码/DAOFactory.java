package org.lxh.mvcdemo.factory ;
import org.lxh.mvcdemo.dao.* ;
import org.lxh.mvcdemo.dao.proxy.* ;
public class DAOFactory {
	public static IUserDAO getIUserDAOInstance(){
		return new UserDAOProxy() ;
	}
}