package modisefileupload.java.service;

import java.util.List;

import modisefileupload.java.domain.User;

public interface UserService {
	//needed for saving data to the database
	void saveUser(User user);
	List<User> listAllUsers();
	void deleteUser(long id);
}
