package modisefileupload.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import modisefileupload.java.dao.UserDAO;
import modisefileupload.java.domain.User;
import modisefileupload.java.service.UserService;

public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDao;

	@Override
	public void saveUser(User user) {
		//using the CrudRepository we can easily save the user data to the database
		//no need to implemenent our own save method
		userDao.save(user);
	}

	@Override
	public List<User> listAllUsers() {
		//again we use the default method of CrudRepository to get all users
		return (List<User>) userDao.findAll();
	}

	@Override
	public void deleteUser(long id) {
		userDao.delete(id);
	}

}
