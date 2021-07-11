package modisefileupload.java.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import modisefileupload.java.domain.User;

@Repository
public interface UserDAO extends CrudRepository<User, Long>{
	//no need to implement extra methods, will use the default save method provided
	//by the CrudRepository class
}
