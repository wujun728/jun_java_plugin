package cn.springmvc.jpa.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.springmvc.jpa.common.exception.DaoException;
import cn.springmvc.jpa.entity.Permission;
import cn.springmvc.jpa.entity.Role;
import cn.springmvc.jpa.entity.RolePermission;
import cn.springmvc.jpa.repository.BaseRepository;
import cn.springmvc.jpa.repository.RoleRepository;

/**
 * @author Vincent.wang
 *
 */
@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private static final Logger log = LoggerFactory.getLogger(RoleRepositoryImpl.class);

    @Autowired
    protected BaseRepository baseRepository;

    @Override
    public void addRole(Role role) {
        if (role == null || StringUtils.isBlank(role.getName())) {
            throw new DaoException(" 添加角色失败，角色对象数据不完整。");
        }

        String ql = "from cn.springmvc.jpa.entity.Role where code=:code";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", role.getCode());
        Role r = baseRepository.queryOne(ql, params);
        if (null == r) {
            baseRepository.save(role);
        }
    }

    @Override
    public Role findRoleByCode(String code) {
        String ql = "from cn.springmvc.jpa.entity.Role where code=:code";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", code);
        return baseRepository.queryOne(ql, params);
    }

    @Override
    public List<Role> findRoles(String userId) {
        if (StringUtils.isBlank(userId)) {
            log.warn("empty user used when trying to find roles.");
            return null;
        }
        String ql = "select r from cn.springmvc.jpa.entity.Role r ,  cn.springmvc.jpa.entity.UserRole ur where r.id = ur.roleId and ur.userId=:userId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return baseRepository.query(ql, params);
    }

    @Override
    public void addRolePermission(Role role, Permission permis) {
        if (null != role && null != permis) {
            String ql = "from cn.springmvc.jpa.entity.RolePermission where roleId=:roleId and permissionId=:permissionId";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("roleId", role.getId());
            params.put("permissionId", permis.getId());
            RolePermission rolePermis = baseRepository.queryOne(ql, params);
            if (rolePermis == null) {
                rolePermis = new RolePermission();
                rolePermis.setRoleId(role.getId());
                rolePermis.setPermissionId(permis.getId());
                baseRepository.save(rolePermis);
            }
        }
    }

    @Override
    public Role findRoleById(String id) {
        return baseRepository.getByPrimaryKey(Role.class, id);
    }

}
