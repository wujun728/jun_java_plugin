package com.caland.core.service.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caland.common.page.Pagination;
import com.caland.core.bean.User;
import com.caland.core.dao.UserDao;
import com.caland.core.query.UserQuery;
import com.caland.core.service.UserService;
/**
 * 
 * @author lixu
 * @Date [2014-3-27 下午03:31:57]
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Log log = LogFactory.getLog(UserServiceImpl.class);

	@Resource
	UserDao userDao;

	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addUser(User user) {
		try {
			return userDao.addUser(user);
		} catch (SQLException e) {
			log.error("dao addUser error.:" + e.getMessage(), e);
		}
		return 0;
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public User getUserByKey(Integer id) {
		try {
			return userDao.getUserByKey(id);
		} catch (SQLException e) {
			log.error("dao getUserbyKey error.:" + e.getMessage(), e);
		}
		return null;
	}
	@Transactional(readOnly = true)
	public List<User> getUserByKeys(List<Integer> idList) {
		try {
			return userDao.getUserByKeys(idList);
		} catch (SQLException e) {
			log.error("dao getUsersByKeys erorr." + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		try {
			return userDao.deleteByKey(id);
		} catch (SQLException e) {
			log.error("dao deleteByKey error. :" + e.getMessage(), e);
		}
		return -1;
	}

	public Integer deleteByKeys(List<Integer> idList) {
		try {
			return userDao.deleteByKeys(idList);
		} catch (SQLException e) {
			log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
		}
		return -1;
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateUserByKey(User User) {
		try {
			return userDao.updateUserByKey(User);
		} catch (SQLException e) {
			log.error("dao updateUser error.User:" + e.getMessage(), e);
		}
		return -1;
	}
	@Transactional(readOnly = true)
	public Pagination getUserListWithPage(UserQuery UserQuery) {
		try {
			return userDao.getUserListWithPage(UserQuery);
		} catch (Exception e) {
			log.error("get User pagination error." + e.getMessage(), e);
		}

		return new Pagination();
	}
	@Transactional(readOnly = true)
	public List<User> getUserList(UserQuery UserQuery) {
		try {
			return userDao.getUserList(UserQuery);
		} catch (SQLException e) {
			log.error("get User list error." + e.getMessage(), e);
		}
		return Collections.emptyList();
	}
}
