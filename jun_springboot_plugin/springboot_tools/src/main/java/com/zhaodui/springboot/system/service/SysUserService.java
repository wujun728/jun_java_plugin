package com.zhaodui.springboot.system.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhaodui.springboot.common.config.CacheConstant;
import com.zhaodui.springboot.common.config.CommonConstant;
import com.zhaodui.springboot.common.mdoel.Page;
import com.zhaodui.springboot.common.mdoel.Result;
import com.zhaodui.springboot.common.service.AbstractService;
import com.zhaodui.springboot.system.mapper.SysUserDepartMapper;
import com.zhaodui.springboot.system.mapper.SysUserMapper;
import com.zhaodui.springboot.system.mapper.SysUserRoleMapper;
import com.zhaodui.springboot.system.model.SysUser;
import com.zhaodui.springboot.system.model.SysUserDepart;
import com.zhaodui.springboot.system.model.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysUserService extends AbstractService<SysUser> {
    @Autowired(required = false)
    private SysUserMapper sysUserMapper;
    @Autowired(required = false)
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired(required = false)
    private SysUserDepartMapper sysUserDepartMapper;
    public SysUser getUserByName(String username) {
        return sysUserMapper.getUserByName(username);
    }

    @CacheEvict(value= {CacheConstant.SYS_USERS_CACHE}, key="#username")
    public void updateUserDepart(String username,String orgCode) {
        sysUserMapper.updateUserDepart(username, orgCode);
    }
    /**
     * 校验用户是否有效
     * @param sysUser
     * @return
     */
     public Result<?> checkUserIsEffective(SysUser sysUser) {
        Result<?> result = new Result<Object>();
        //情况1：根据用户信息查询，该用户不存在
        if (sysUser == null) {
            result.error500("该用户不存在，请注册");
            return result;
        }
        //情况2：根据用户信息查询，该用户已注销
        if (CommonConstant.DEL_FLAG_1.toString().equals(sysUser.getDelFlag())) {
            result.error500("该用户已注销");
            return result;
        }
        //情况3：根据用户信息查询，该用户已冻结
        if (CommonConstant.USER_FREEZE.equals(sysUser.getStatus())) {
            result.error500("该用户已冻结");
            return result;
        }
        return result;
    }
     @Transactional
    public void addUserWithRole(SysUser user, String roles) {
        sysUserMapper.insert(user);
        if(!StringUtils.isEmpty(roles)) {
            String[] arr = roles.split(",");
            for (String roleId : arr) {
                SysUserRole userRole = new SysUserRole(user.getId(), roleId);
                sysUserRoleMapper.insert(userRole);
            }
        }
    }
    @Transactional
    public void addUserWithDepart(SysUser user, String selectedParts) {
//		this.save(user);  //保存角色的时候已经添加过一次了
        if(!StringUtils.isEmpty(selectedParts)) {
            String[] arr = selectedParts.split(",");
            for (String deaprtId : arr) {
                SysUserDepart userDeaprt = new SysUserDepart(user.getId(), deaprtId);
                sysUserDepartMapper.insert(userDeaprt);
            }
        }
    }

    @CacheEvict(value={CacheConstant.SYS_USERS_CACHE}, allEntries=true)
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(String userId) {
        //1.删除用户
        sysUserMapper.deleteByPrimaryKey(userId);
        //2.删除用户部门关联关系
        //3.删除用户角色关联关系
        //TODO
        return false;
    }
    @CacheEvict(value= {CacheConstant.SYS_USERS_CACHE}, allEntries=true)
    @Transactional
    public void editUserWithRole(SysUser user, String roles) {
        sysUserMapper.updateByPrimaryKey(user);
        //先删后加
        //删除待写
        if(!StringUtils.isEmpty(roles)) {
            String[] arr = roles.split(",");
            for (String roleId : arr) {
                SysUserRole userRole = new SysUserRole(user.getId(), roleId);
                sysUserRoleMapper.insert(userRole);
            }
        }
    }

    @Transactional
    @CacheEvict(value={CacheConstant.SYS_USERS_CACHE}, allEntries=true)
    public void editUserWithDepart(SysUser user, String departs) {
        sysUserMapper.updateByPrimaryKey(user);
        //先删后加
        //删除待写

        if(!StringUtils.isEmpty(departs)) {
            String[] arr = departs.split(",");
            for (String departId : arr) {
                SysUserDepart userDepart = new SysUserDepart(user.getId(), departId);
                sysUserDepartMapper.insert(userDepart);
            }
        }
    }

}
