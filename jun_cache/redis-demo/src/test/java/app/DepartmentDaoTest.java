package app;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;

import app.dao.DepartmentDao;
import app.entity.Department;

public class DepartmentDaoTest extends TestBase {

	@Autowired
	private DepartmentDao departmentDao;
	
	@Test
	public void testSave() {
		Department d = new Department();
		d.setId(3);
		d.setDepartmentName("外贸系3");
		departmentDao.save(d);
	}
	
	@Test
	public void testTotal() {
		Department d = new Department();
		System.out.println(departmentDao.getTotal(d.fatchTableName()));
	}
	
	@Test
	public void testGet() {
		Department d = new Department();
		d.setId(3);
		
		Department dd = departmentDao.get(d);
		System.out.println(dd.getDepartmentName());
	}
	
	@Test
	public void testFind() {
		Department d = new Department();
		List<Object> list = departmentDao.find(d.fatchTableName(), 1, 2);
		for (Object de : list) {
			Department dept = (Department)de;
			System.out.println(dept.getId() + "\t" + dept.getDepartmentName());
		}
		
	}
	
	@Test
	public void testUpdate() {
		Department d = new Department();
		d.setId(3);
		
		Department dd = departmentDao.get(d);
		System.out.println("修改前:" + dd.getDepartmentName());
		
		dd.setDepartmentName("外语系");
		departmentDao.update(dd);
		
		Department dAfter = departmentDao.get(d);
		System.out.println("修改后:" + dAfter.getDepartmentName());
	}
	
	
	@Test
	public void testDel() {
		Department d = new Department();
		d.setId(3);
		
		long retVal = departmentDao.del(d);
		System.out.println("retVal:" + retVal);
		
		Department dAfter = departmentDao.get(d);
		
		Assert.assertNull(dAfter);
	}
	
	
}
