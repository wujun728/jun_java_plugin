package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import po.Staff;
import service.StaffService;
import dao.StaffDao;
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
@Service("staffservice")
public class StaffServiceImpl implements StaffService{
	@Autowired
	private StaffDao staffDao;
	
	public StaffDao getStaffDao() {
		return staffDao;
	}

	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}

	public List<Staff> getStaffandstore(int a,int b) {
		List<Staff> s=staffDao.findByPage("from Staff", a, b);
		return s;
	}

	public int getstaffnum() {
		List<Staff> s=staffDao.find("from Staff");
		return s.size();
	}

}
