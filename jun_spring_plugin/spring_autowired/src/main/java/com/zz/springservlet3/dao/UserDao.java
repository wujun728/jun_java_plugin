package com.zz.springservlet3.dao;

import com.zz.springservlet3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository, 如果在PersistenceConfig设置了@EnableJpaRepositories, 可以不加@Repository注解
public interface UserDao extends JpaRepository<User, Long> {

	User findOne(Long id);
}
