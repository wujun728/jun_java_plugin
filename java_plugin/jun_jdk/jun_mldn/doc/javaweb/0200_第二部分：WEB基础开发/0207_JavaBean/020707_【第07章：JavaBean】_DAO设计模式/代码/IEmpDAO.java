package cn.mldn.lxh.dao ;
import java.util.* ;
import cn.mldn.lxh.vo.* ;
public interface IEmpDAO {
	public boolean doCreate(Emp emp) throws Exception ;
	public List<Emp> findAll(String keyWord) throws Exception ;
	public Emp findById(int empno) throws Exception ;
}