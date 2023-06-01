package com.cosmoplat.service.sys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cosmoplat.entity.sys.SysUser;

import java.util.List;

/**
 * 用户 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface UserProviderService extends IService<SysUser> {
    List<SysUser> getUserListByDeptIds(List<Object> deptIds);

    SysUser selectByUsername(String username);

    IPage<SysUser> selectPage(SysUser vo);

    List<SysUser> listByCondition(SysUser vo);
}
