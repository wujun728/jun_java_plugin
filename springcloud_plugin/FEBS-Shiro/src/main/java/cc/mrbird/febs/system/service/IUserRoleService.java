package cc.mrbird.febs.system.service;

import cc.mrbird.febs.system.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * @author MrBird
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 通过角色ID删除
     *
     * @param roleIds 角色ID
     */
    void deleteUserRolesByRoleId(List<String> roleIds);

    /**
     * 通过用户ID删除
     *
     * @param userIds 用户ID
     */
    void deleteUserRolesByUserId(List<String> userIds);

    /**
     * 通过角色ID查找关联的用户ID
     *
     * @param roleId 角色ID
     * @return 用户ID集合
     */
    Set<Long> findUserIdByRoleId(Long roleId);

    /**
     * 通过角色ID集合查找关联的用户ID
     *
     * @param roleIds 角色ID集合
     * @return 用户ID集合
     */
    Set<Long> findUserIdByRoleIds(List<String> roleIds);
}
