package com.cosmoplat.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cosmoplat.entity.sys.SysPermission;
import com.cosmoplat.entity.sys.vo.resp.PermissionRespNode;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单权限
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface PermissionProviderService extends IService<SysPermission> {

    List<SysPermission> getPermission(String userId);

    void deleted(String permissionId);

    List<SysPermission> selectAll();

    Set<String> getPermissionsByUserId(String userId);

    List<Map<String, String>> getUrlsByUserId(String userId);

    List<PermissionRespNode> permissionTreeList(String userId);

    List<PermissionRespNode> selectAllByTree();

    List<PermissionRespNode> selectAllMenuByTree(String permissionId);

    List<String> getUserIdsById(String permissionId);
}
