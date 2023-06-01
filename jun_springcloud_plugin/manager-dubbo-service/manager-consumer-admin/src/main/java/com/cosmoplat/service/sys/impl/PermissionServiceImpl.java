package com.cosmoplat.service.sys.impl;

import com.cosmoplat.entity.sys.SysPermission;
import com.cosmoplat.entity.sys.vo.resp.PermissionRespNode;
import com.cosmoplat.service.sys.HttpSessionService;
import com.cosmoplat.service.sys.PermissionProviderService;
import com.cosmoplat.service.sys.PermissionService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wenbin
 * @date: 2020/8/26
 * Description: No Description
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @DubboReference
    PermissionProviderService permissionProviderService;
    @Resource
    HttpSessionService httpSessionService;

    @Override
    public void save(SysPermission vo) {
        permissionProviderService.save(vo);
    }

    @Override
    public void deleted(String id) {
        List<String> userIds = permissionProviderService.getUserIdsById(id);
        permissionProviderService.deleted(id);
        //刷新权限
        if (!CollectionUtils.isEmpty(userIds)) {
            //刷新权限
            userIds.parallelStream().forEach(httpSessionService::refreshUerId);
        }
    }

    @Override
    public SysPermission getById(String id) {
        return permissionProviderService.getById(id);
    }

    @Override
    public void updateById(SysPermission vo) {
        permissionProviderService.updateById(vo);
        //刷新权限
        httpSessionService.refreshPermission(vo.getId());
    }

    @Override
    public List<SysPermission> selectAll() {
        return permissionProviderService.selectAll();
    }

    @Override
    public List<PermissionRespNode> selectAllMenuByTree(String permissionId) {
        return permissionProviderService.selectAllMenuByTree(permissionId);
    }

    @Override
    public List<PermissionRespNode> selectAllByTree() {
        return permissionProviderService.selectAllByTree();
    }
}
