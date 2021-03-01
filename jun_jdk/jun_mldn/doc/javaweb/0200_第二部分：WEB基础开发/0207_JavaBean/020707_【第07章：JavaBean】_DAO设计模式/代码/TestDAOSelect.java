package cn.mldn.lxh.dao.test ;
import java.util.* ;
import cn.mldn.lxh.factory.DAOFactory ;
import cn.mldn.lxh.vo.* ;
public class TestDAOSelect{
	public static void main(String args[]) throws Exception{
		List<Emp> all = DAOFactory.getIEmpDAOInstance().findAll("") ;
		Iterator<Emp> iter = all.iterator() ;
		while(iter.hasNext()){
			Emp emp = iter.next() ;
			System.out.println(emp.getEmpno() + "¡¢" + emp.getEname() + " --> " + emp.getJob()) ;
		}
	}
}