package com.ic911.htools.service.security;

import java.util.Collection;

import javax.annotation.Resource;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ic911.core.commons.BeanValidators;
import com.ic911.core.security.SecurityAccountInterface;
import com.ic911.core.security.ShiroDbRealm;
import com.ic911.core.security.User;
import com.ic911.htools.persistence.security.UserDao;
/**
 * 账户管理
 * @author caoyang
 */
@Service
@Transactional
public class AccountService implements SecurityAccountInterface{
	
	@Resource
	private UserDao userDao;
	@Autowired
	private Validator validator;
	
	@Override
	public User findUserByLoginName(String loginName) {
		return userDao.findUserByLoginName(loginName);
	}
	
	public User findOne(Long id){
		return userDao.findOne(id);
	}
	
	public void saveOrUpdate(User user){
		BeanValidators.getValidationExcaption(validator,user);
		// 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
		if(user.getLoginName().equalsIgnoreCase("admin")){
			throw new RuntimeException("不能使用admin作为登录名!");
		}
		if(user.getId()==null){//判断接下来的操作是否为insert
			User userold = findUserByLoginName(user.getLoginName());
			if(userold!=null){
				throw new RuntimeException("登录名为 "+user.getLoginName()+" 的用户已经存在，请重新填写登录名！");
			}
		}
		if (StringUtils.isNotBlank(user.getPassword())) {
			ShiroDbRealm.entryptPassword(user);
		}
		userDao.save(user);
	}
	
	public void delete(Long id){
		userDao.delete(id);
	}
	
	public void disabledUser(Long id){
		userDao.disabledUser(id);
	}
	
	public void enabledUser(Long id){
		userDao.enabledUser(id);
	}
	
	public boolean updatePassword(String loginName,String oldPassword,String newPassword){
		User user = findUserByLoginName(loginName);
		if(user==null){
			return false;
		}
		String opwd = ShiroDbRealm.entryptPassword(oldPassword,user.getSalt());
		if(!opwd.equals(user.getPassword())){
			return false;
		}
		user.setPassword(newPassword);
		saveOrUpdate(user);
		return true;
	}
	
	public Page<User> findAll(Pageable pageable){
		return userDao.findAll(pageable);
	}
	
	public Collection<String> findMails(){
		return userDao.findUserMails();
	}
	
}

