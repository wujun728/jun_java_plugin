package com.jun.plugin.jpa.service.impl;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.jpa.common.exception.BusinessException;
import com.jun.plugin.jpa.entity.Permission;
import com.jun.plugin.jpa.entity.Role;
import com.jun.plugin.jpa.entity.User;
import com.jun.plugin.jpa.repository.PermissionRepository;
import com.jun.plugin.jpa.repository.RoleRepository;
import com.jun.plugin.jpa.service.RoleService;

/**
 * @author Vincent.wang
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

	private final static Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	public void addRole(Role role) {
		if (role == null || StringUtils.isBlank(role.getName())) {
			return;
		}
		if (log.isDebugEnabled()) {
			log.debug("## 添加角色 : {}", role.getName());
		}
		roleRepository.addRole(role);

	}

	@Override
	public Role findRoleByCode(String code) {
		if (log.isDebugEnabled()) {
			log.debug("## 根据编码查询角色 :　{}", code);
		}
		return roleRepository.findRoleByCode(code);
	}

	@Override
	public Collection<Role> findRoles(User user) {
		return roleRepository.findRoles(user.getId());
	}

	@Override
	public void addRolePermission(String roleCode, String permissionKey) {
		Role role = findRoleByCode(roleCode);
		if (role == null) {
			throw new BusinessException("## 给角色授权失败， 角色编码错误");
		}
		Permission permis = permissionRepository.findPermissionByKey(permissionKey);
		if (permis == null) {
			throw new BusinessException("## 给角色授权失败， 授权KEY错误");
		}

		roleRepository.addRolePermission(role, permis);

	}
}
