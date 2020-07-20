package com.ic911.htools.persistence.security;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ic911.core.security.User;
/**
 * 登录用户的dao
 * @author caoyang
 */
public interface UserDao extends JpaRepository<User,Long>{
	
	@Transactional
	@Modifying
	@Query("UPDATE User user SET user.status='enabled' WHERE user.id=?1")
	public void enabledUser(Long id);
	
	@Transactional
	@Modifying
	@Query("UPDATE User user SET user.status='disabled' WHERE user.id=?1")
	public void disabledUser(Long id);
	
	User findUserByLoginName(String loginName);
	
	@Query("SELECT user.email FROM User user WHERE user.status='enabled' AND user.email != '' AND user.email IS NOT NULL")
	public Collection<String> findUserMails();
	
}

