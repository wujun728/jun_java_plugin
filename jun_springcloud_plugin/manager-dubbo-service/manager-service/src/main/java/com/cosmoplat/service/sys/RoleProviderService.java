package com.cosmoplat.service.sys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cosmoplat.entity.sys.SysRole;

import java.util.List;

/**
 * 角色
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface RoleProviderService extends IService<SysRole> {

    SysRole addRole(SysRole vo);

    void updateRole(SysRole vo);

    SysRole detailInfo(String id);

    void deletedRole(String id);

    List<SysRole> getRoleInfoByUserId(String userId);

    List<String> getRoleNames(String userId);

    List<SysRole> selectAllRoles();

    IPage<SysRole> pageInfo(SysRole vo);
}
