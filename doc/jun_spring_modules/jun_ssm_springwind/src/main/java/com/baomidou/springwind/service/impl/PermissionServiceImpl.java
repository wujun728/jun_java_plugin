package com.baomidou.springwind.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.kisso.SSOAuthorization;
import com.baomidou.kisso.Token;
import com.baomidou.springwind.entity.Permission;
import com.baomidou.springwind.entity.vo.MenuVO;
import com.baomidou.springwind.mapper.PermissionMapper;
import com.baomidou.springwind.service.IPermissionService;
import com.baomidou.springwind.service.support.BaseServiceImpl;

/**
 * <p>
 * Permission 表数据服务层接口实现类
 * </p>
 * <p>
 * 实现权限接口 SSOAuthorization
 * </p>
 *
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission>
		implements IPermissionService, SSOAuthorization {

	@Cacheable(value = "permissionCache", key = "#userId")
	@Override
	public List<MenuVO> selectMenuVOByUserId(Long userId) {
		List<MenuVO> perList = baseMapper.selectMenuByUserId(userId, 0L);
		if (perList == null || perList.isEmpty()) {
			return null;
		}
		List<MenuVO> mvList = new ArrayList<MenuVO>();
		for (MenuVO mv : perList) {
			mv.setMvList(baseMapper.selectMenuByUserId(userId, mv.getId()));
			mvList.add(mv);
		}
		return mvList;
	}

	@Override
	public boolean isPermitted(Token token, String permission) {
		/**
		 * 
		 * 菜单级别、权限验证，生产环境建议加上缓存处理。
		 * 
		 */
		if (StringUtils.isNotBlank(permission)) {
			List<Permission> pl = this.selectAllByUserId(token.getId());
			if (pl != null) {
				for (Permission p : pl) {
					if (permission.equals(p.getPermCode())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Cacheable(value = "permissionCache", key = "#userId")
	@Override
	public List<Permission> selectAllByUserId(Long userId) {
		return baseMapper.selectAllByUserId(userId);
	}

	@Override
	public boolean isActionable( Token token, String permission ) {
		/**
		 * 
		 * 按钮级别、权限验证，生产环境建议加上缓存处理。
		 * <br>
		 * 演示  admin 返回 true
		 * 
		 */
		System.err.println(" isActionable =" + permission);
		if ( token.getId() == 1L ) {
			return true;
		}
		return false;
	}

}