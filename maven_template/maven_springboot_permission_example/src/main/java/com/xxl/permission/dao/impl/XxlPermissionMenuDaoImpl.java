package com.jun.permission.dao.impl;

import com.jun.permission.core.model.junPermissionMenu;
import com.jun.permission.dao.IjunPermissionMenuDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wujun
 */
@Repository
public class junPermissionMenuDaoImpl implements IjunPermissionMenuDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int add(junPermissionMenu junPermissionMenu) {
		return sqlSessionTemplate.insert("junPermissionMenuMapper.add", junPermissionMenu);
	}

	@Override
	public int delete(int id) {
		return sqlSessionTemplate.delete("junPermissionMenuMapper.delete", id);
	}

	@Override
	public int update(junPermissionMenu junPermissionMenu) {
		return sqlSessionTemplate.update("junPermissionMenuMapper.update", junPermissionMenu);
	}

	@Override
	public junPermissionMenu load(int id) {
		return sqlSessionTemplate.selectOne("junPermissionMenuMapper.load", id);
	}

	@Override
	public List<junPermissionMenu> getAllMenus() {
		return sqlSessionTemplate.selectList("junPermissionMenuMapper.getAllMenus");
	}

	@Override
	public List<junPermissionMenu> getMenusByRoleId(int roleId) {
		return sqlSessionTemplate.selectList("junPermissionMenuMapper.getMenusByRoleId", roleId);
	}

	@Override
	public List<junPermissionMenu> getMenusByParentId(int parentId) {
		return sqlSessionTemplate.selectList("junPermissionMenuMapper.getMenusByParentId", parentId);
	}

	@Override
	public int findBindRoleCount(int menuId) {
		return sqlSessionTemplate.selectOne("junPermissionMenuMapper.findBindRoleCount", menuId);
	}

}
