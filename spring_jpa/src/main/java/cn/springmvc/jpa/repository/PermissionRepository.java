package cn.springmvc.jpa.repository;

import java.util.List;

import cn.springmvc.jpa.entity.Permission;
import cn.springmvc.jpa.entity.User;

/**
 * @author Wujun
 *
 */
public interface PermissionRepository {

    /**
     * 查询用户所能访问的所有菜单
     *
     * @param user
     *            用户
     * @return permissions 菜单
     */
    public List<Permission> getPermissions(User user);

    /**
     * 添加 菜单
     *
     * @param permission
     *            菜单项
     */
    public void addPermission(Permission permission);

    /**
     * 根据菜单KEY查询菜单
     *
     * @param permissionKey
     *            菜单KEY
     * @return
     */
    public Permission findPermissionByKey(String permissionKey);
}
