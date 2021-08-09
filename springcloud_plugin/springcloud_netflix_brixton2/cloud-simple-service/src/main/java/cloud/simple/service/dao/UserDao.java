package cloud.simple.service.dao;

import java.util.List;

import cloud.simple.service.model.User;


public interface UserDao {

	List<User> findAll();
}
