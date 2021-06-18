package cn.springmvc.jpa.repository.impl;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.springmvc.jpa.common.exception.DaoException;
import cn.springmvc.jpa.entity.Permission;
import cn.springmvc.jpa.entity.User;
import cn.springmvc.jpa.repository.BaseRepository;
import cn.springmvc.jpa.repository.PermissionRepository;

/**
 * @author Vincent.wang
 *
 */
@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

    private static final Logger log = LoggerFactory.getLogger(PermissionRepositoryImpl.class);

    @Autowired
    protected BaseRepository baseRepository;

    @Override
    public List<Permission> getPermissions(User user) {
        if (null == user) {
            log.warn("empty user used when trying to find permissions.");
            return null;
        }

        StringBuilder ql = new StringBuilder();
        ql.append(" select distinct p from cn.springmvc.jpa.entity.Permission p , ");
        ql.append(" cn.springmvc.jpa.entity.UserRole ur , cn.springmvc.jpa.entity.RolePermission rp ");
        ql.append(" where rp.permissionId = p.id ");
        ql.append("   and rp.roleId = ur.roleId ");
        ql.append("   and ur.userId = :userId ");
        ql.append(" order by p.parentKey asc , p.sort asc");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", user.getId());

        return baseRepository.query(ql.toString(), params);
    }

    @Override
    public void addPermission(Permission permission) {
        Permission permis = findPermissionByKey(permission.getKey());
        if (permis != null) {
            throw new DaoException("## 菜单Key已经存在，不能重复。");
        } else {
            baseRepository.save(permission);
        }
    }

    @Override
    public Permission findPermissionByKey(String permissionKey) {
        if (StringUtils.isBlank(permissionKey)) {
            return null;
        }
        String ql = " from cn.springmvc.jpa.entity.Permission where key=:key";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("key", permissionKey);
        return baseRepository.queryOne(ql, params);
    }

}
