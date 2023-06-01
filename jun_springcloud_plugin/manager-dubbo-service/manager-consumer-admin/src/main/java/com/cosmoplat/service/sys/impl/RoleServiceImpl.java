package com.cosmoplat.service.sys.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cosmoplat.entity.sys.SysRole;
import com.cosmoplat.service.sys.HttpSessionService;
import com.cosmoplat.service.sys.RoleProviderService;
import com.cosmoplat.service.sys.RoleService;
import com.cosmoplat.service.sys.UserRoleProviderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
public class RoleServiceImpl implements RoleService {

    @DubboReference
    RoleProviderService roleProviderService;
    @Resource
    HttpSessionService httpSessionService;
    @DubboReference
    UserRoleProviderService userRoleProviderService;

    @Override
    public IPage<SysRole> pageInfo(SysRole vo) {
        IPage<SysRole> iPage = roleProviderService.pageInfo(vo);
        return iPage;
    }

    @Override
    public SysRole addRole(SysRole vo) {
        return roleProviderService.addRole(vo);
    }

    @Override
    public void deletedRole(String id) {
        List<String> userIds = userRoleProviderService.getUserIdsByRoleId(id);
        roleProviderService.deletedRole(id);
        // 刷新权限
        if (!CollectionUtils.isEmpty(userIds)) {
            // 刷新权限
            userIds.parallelStream().forEach(httpSessionService::refreshUerId);
        }
    }

    @Override
    public void updateRole(SysRole vo) {
        roleProviderService.updateRole(vo);
        // 刷新权限
        httpSessionService.refreshRolePermission(vo.getId());
    }

    @Override
    public Object detailInfo(String id) {
        return roleProviderService.detailInfo(id);
    }
}
