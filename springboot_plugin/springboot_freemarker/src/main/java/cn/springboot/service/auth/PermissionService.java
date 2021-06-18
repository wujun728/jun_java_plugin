package cn.springboot.service.auth;

import java.util.List;

import cn.springboot.config.shiro.vo.PermissionVo;
import cn.springboot.model.auth.Permission;

public interface PermissionService {

    /**
     * 查询用户所能访问的所有菜单
     *
     * @param userId
     *            用户ID
     * @return permissions 菜单
     */
    public List<PermissionVo> getPermissions(String userId);

    /**
     * 添加 菜单
     *
     * @param permission
     *            菜单项
     */
    public void addPermission(Permission permission);
}
