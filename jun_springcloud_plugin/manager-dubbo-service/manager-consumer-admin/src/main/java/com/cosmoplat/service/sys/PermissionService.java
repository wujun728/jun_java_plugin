package com.cosmoplat.service.sys;

import com.cosmoplat.entity.sys.SysPermission;
import com.cosmoplat.entity.sys.vo.resp.PermissionRespNode;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wenbin
 * @date: 2020/8/26
 * Description: No Description
 */
public interface PermissionService {
    void save(SysPermission vo);

    void deleted(String id);

    SysPermission getById(String id);

    void updateById(SysPermission vo);

    List<SysPermission> selectAll();

    List<PermissionRespNode> selectAllMenuByTree(String permissionId);

    List<PermissionRespNode> selectAllByTree();
}
