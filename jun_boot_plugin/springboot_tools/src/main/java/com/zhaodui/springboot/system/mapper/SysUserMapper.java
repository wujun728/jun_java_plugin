package com.zhaodui.springboot.system.mapper;

import com.zhaodui.springboot.common.mapper.BaseMapper;
import com.zhaodui.springboot.common.mdoel.Page;
import com.zhaodui.springboot.system.model.SysUser;
import com.zhaodui.springboot.system.model.SysUserSysDepartModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 通过用户账号查询用户信息
     * @param username
     * @return
     */
    public SysUser getUserByName(@Param("username") String username);

    /**
     *  根据部门Id查询用户信息
     * @param page
     * @param departId
     * @return
     */
    Page<SysUser> getUserByDepId(Page page, @Param("departId") String departId, @Param("username") String username);

    /**
     * 根据角色Id查询用户信息
     * @param page
     * @param
     * @return
     */
    Page<SysUser> getUserByRoleId(Page page, @Param("roleId") String roleId, @Param("username") String username);

    /**
     * 根据用户名设置部门ID
     * @param username
     * @param departId
     */
    void updateUserDepart(@Param("username") String username,@Param("orgCode") String orgCode);

    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    public SysUser getUserByPhone(@Param("phone") String phone);


    /**
     * 根据邮箱查询用户信息
     * @param email
     * @return
     */
    public SysUser getUserByEmail(@Param("email")String email);

    /**
     * 根据 orgCode 查询用户，包括子部门下的用户
     *
     * @param page 分页对象, xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param orgCode
     * @param userParams 用户查询条件，可为空
     * @return
     */
    List<SysUserSysDepartModel> getUserByOrgCode(Page page, @Param("orgCode") String orgCode, @Param("userParams") SysUser userParams);


    /**
     * 查询 getUserByOrgCode 的Total
     *
     * @param orgCode
     * @param userParams 用户查询条件，可为空
     * @return
     */
    Integer getUserByOrgCodeTotal(@Param("orgCode") String orgCode, @Param("userParams") SysUser userParams);


    void deleteBathRoleUserRelation(@Param("roleIdArray") String[] roleIdArray);


    void deleteBathRolePermissionRelation(@Param("roleIdArray") String[] roleIdArray);
}