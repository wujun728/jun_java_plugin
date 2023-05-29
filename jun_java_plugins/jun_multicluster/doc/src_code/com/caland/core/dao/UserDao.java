package com.caland.core.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.caland.common.page.Pagination;
import com.caland.core.bean.User;
import com.caland.core.query.UserQuery;

@Repository
public class UserDao {

	@Resource
	SqlMapClientTemplate sqlMapClientTemplate;

	public Integer addUser(User user) throws SQLException {
		return (Integer) this.sqlMapClientTemplate.insert("User.insertUser", user);
	}

	/**
	 * 根据主键查找
	 * 
	 * @throws SQLException
	 */
	public User getUserByKey(Integer id) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		User result = (User) this.sqlMapClientTemplate.queryForObject(
				"User.getUserByKey", params);
		return result;
	}

	/**
	 * 根据主键批量查找
	 * 
	 * @throws SQLException
	 */
	public List<User> getUserByKeys(List<Integer> idList) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keys", idList);
		return (List<User>) this.sqlMapClientTemplate.queryForList(
				"User.getUsersByKeys", params);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Integer deleteByKey(Integer id) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Integer row = (Integer) this.sqlMapClientTemplate.delete(
				"User.deleteByKey", params);
		return row;
	}

	/**
	 * 根据主键批量删除
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Integer deleteByKeys(List<Integer> idList) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keys", idList);
		Integer row = (Integer) this.sqlMapClientTemplate.delete(
				"User.deleteByKeys", params);
		return row;
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Integer updateUserByKey(User user) throws SQLException {
		return (Integer) this.sqlMapClientTemplate.update(
				"User.updateUserByKey", user);
	}

	@SuppressWarnings("unchecked")
	public Pagination getUserListWithPage(UserQuery userQuery) {
		Pagination p = null;
		try {
			p = new Pagination(userQuery.getPage(), userQuery.getPageSize(), (Integer) this.sqlMapClientTemplate.queryForObject(
					"User.getUserListCount", userQuery));
			if (userQuery.getFields() != null && userQuery.getFields() != "") {
				p.setList((List<User>) this.sqlMapClientTemplate.queryForList(
						"User.getUserListWithPageFields", userQuery));
			} else {
				p.setList((List<User>) this.sqlMapClientTemplate.queryForList(
						"User.getUserListWithPage", userQuery));
			}
		} catch (Exception e) {

		}
		return p;
	}

	@SuppressWarnings("unchecked")
	public List<User> getUserList(UserQuery userQuery) throws SQLException {
		if (userQuery.getFields() != null && userQuery.getFields() != "") {
			return (List<User>) this.sqlMapClientTemplate.queryForList(
					"User.getUserListFields", userQuery);
		}
		return (List<User>) this.sqlMapClientTemplate.queryForList(
				"User.getUserList", userQuery);
	}
}
