package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import po.Staff;
import service.UserService;
import dao.UserDao;
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
@Service("userservice")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	

	public UserDao getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public String getNameByPassword(String password) {
		List<Staff> list =userDao.find("from Staff as s where s.password='"+password+"'");
		Staff s;
		if(list.size()>0){
		s=list.get(0);
		return s.getUsername();
		}else
		return null;
	}


	public String getPWDByname(String name) {
		List<Staff> list =userDao.find("from Staff as s where s.username='"+name+"'");
		Staff s;
		if(list.size()>0){
		s=list.get(0);
		return s.getPassword();
		}else
		return null;
	}

}
