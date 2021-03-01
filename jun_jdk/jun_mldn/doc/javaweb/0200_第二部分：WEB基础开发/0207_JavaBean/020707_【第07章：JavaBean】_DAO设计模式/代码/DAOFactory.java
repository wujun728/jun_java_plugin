package cn.mldn.lxh.factory ;
import cn.mldn.lxh.dao.IEmpDAO ;
import cn.mldn.lxh.dao.proxy.EmpDAOProxy ;
public class DAOFactory {
	public static IEmpDAO getIEmpDAOInstance() throws Exception{
		return new EmpDAOProxy() ;
	}
}