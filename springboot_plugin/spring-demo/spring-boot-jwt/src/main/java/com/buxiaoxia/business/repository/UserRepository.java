package com.buxiaoxia.business.repository;


import com.buxiaoxia.business.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xw on 2017/1/5.
 * 2017-01-05 16:20
 */
public interface UserRepository extends JpaRepository<User,Integer>{

	User findByLoginNameAndPassword(String loginName, String password);

	User findByLoginName(String loginName);
}
