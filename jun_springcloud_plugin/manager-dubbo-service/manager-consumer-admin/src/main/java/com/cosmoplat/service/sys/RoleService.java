package com.cosmoplat.service.sys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cosmoplat.entity.sys.SysRole;

/**
 * 角色管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface RoleService {

    IPage<SysRole> pageInfo(SysRole vo);

    SysRole addRole(SysRole vo);

    void deletedRole(String id);

    void updateRole(SysRole vo);

    Object detailInfo(String id);
}
