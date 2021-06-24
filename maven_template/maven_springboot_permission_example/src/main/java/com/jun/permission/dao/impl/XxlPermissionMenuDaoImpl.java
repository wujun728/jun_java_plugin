package com.jun.permission.dao.impl;

import com.jun.permission.core.model.XxlPermissionMenu;
import com.jun.permission.dao.IXxlPermissionMenuDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wuXxl
 */
@Repository
public class XxlPermissionMenuDaoImpl implements IXxlPermissionMenuDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int add(XxlPermissionMenu XxlPermissionMenu) {
		return sqlSessionTemplate.insert("XxlPermissionMenuMapper.add", XxlPermissionMenu);
	}

	@Override
	public int delete(int id) {
		return sqlSessionTemplate.delete("XxlPermissionMenuMapper.delete", id);
	}

	@Override
	public int update(XxlPermissionMenu XxlPermissionMenu) {
		return sqlSessionTemplate.update("XxlPermissionMenuMapper.update", XxlPermissionMenu);
	}

	@Override
	public XxlPermissionMenu load(int id) {
		return sqlSessionTemplate.selectOne("XxlPermissionMenuMapper.load", id);
	}

	@Override
	public List<XxlPermissionMenu> getAllMenus() {
		return sqlSessionTemplate.selectList("XxlPermissionMenuMapper.getAllMenus");
	}

	@Override
	public List<XxlPermissionMenu> getMenusByRoleId(int roleId) {
		return sqlSessionTemplate.selectList("XxlPermissionMenuMapper.getMenusByRoleId", roleId);
	}

	@Override
	public List<XxlPermissionMenu> getMenusByParentId(int parentId) {
		return sqlSessionTemplate.selectList("XxlPermissionMenuMapper.getMenusByParentId", parentId);
	}

	@Override
	public int findBindRoleCount(int menuId) {
		return sqlSessionTemplate.selectOne("XxlPermissionMenuMapper.findBindRoleCount", menuId);
	}

}
