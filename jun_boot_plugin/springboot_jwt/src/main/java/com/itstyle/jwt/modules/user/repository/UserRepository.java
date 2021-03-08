package com.itstyle.jwt.modules.user.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.itstyle.jwt.modules.user.model.User;
/**
 * 用户管理
 * 创建者 小柒2012
 * 创建时间	2017年9月7日
 */
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
