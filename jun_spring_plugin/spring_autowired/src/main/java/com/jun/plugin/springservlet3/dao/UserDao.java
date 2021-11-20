package com.jun.plugin.springservlet3.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jun.plugin.springservlet3.entity.User;

//@Repository, 如果在PersistenceConfig设置了@EnableJpaRepositories, 可以不加@Repository注解
public interface UserDao extends JpaRepository<User, Long> {

	User findOne(Long id);
}
