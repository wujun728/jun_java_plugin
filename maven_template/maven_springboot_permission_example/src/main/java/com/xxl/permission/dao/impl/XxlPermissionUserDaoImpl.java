package com.jun.permission.dao.impl;

import com.jun.permission.core.model.junPermissionUser;
import com.jun.permission.dao.IjunPermissionUserDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class junPermissionUserDaoImpl implements IjunPermissionUserDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int add(junPermissionUser junPermissionUser) {
		return sqlSessionTemplate.insert("junPermissionUserMapper.add", junPermissionUser);
	}

	@Override
	public int delete(List<Integer> userIds) {
		return sqlSessionTemplate.delete("junPermissionUserMapper.delete", userIds);
	}

	@Override
	public int update(junPermissionUser junPermissionUser) {
		return sqlSessionTemplate.update("junPermissionUserMapper.update", junPermissionUser);
	}

	@Override
	public junPermissionUser loadUser(int id) {
		return sqlSessionTemplate.selectOne("junPermissionUserMapper.loadUser", id);
	}

	@Override
	public junPermissionUser findUserByUserName (String username) {
		return sqlSessionTemplate.selectOne("junPermissionUserMapper.findUserByUserName", username);
	}


	@Override
	public List<junPermissionUser> queryUser(int offset, int pagesize, String userName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", offset);
		params.put("pagesize", pagesize);
		params.put("userName", userName);
		
		return sqlSessionTemplate.selectList("junPermissionUserMapper.queryUser", params);
	}

	@Override
	public int queryUserCount(int offset, int pagesize, String userName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", offset);
		params.put("pagesize", pagesize);
		params.put("userName", userName);
		
		return sqlSessionTemplate.selectOne("junPermissionUserMapper.queryUserCount", params);
	}

	@Override
	public int bindUserRoles(int userId, Set<Integer> addRoldIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("addRoldIds", addRoldIds);
		return sqlSessionTemplate.delete("junPermissionUserMapper.bindUserRoles", params);
	}

	@Override
	public int unBindUserRoles(int userId, Set<Integer> delRoldIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("delRoldIds", delRoldIds);
		return sqlSessionTemplate.delete("junPermissionUserMapper.unBindUserRoles", params);
	}

	@Override
	public int unBindUserRoleAll(int[] userIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userIds", userIds);
		return sqlSessionTemplate.delete("junPermissionUserMapper.unBindUserRoleAll", params);
	}

}
