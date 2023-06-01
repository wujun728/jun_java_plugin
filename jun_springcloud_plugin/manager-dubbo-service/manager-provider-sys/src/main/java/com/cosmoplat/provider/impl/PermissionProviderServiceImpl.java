package com.cosmoplat.provider.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cosmoplat.common.exception.BusinessException;
import com.cosmoplat.common.exception.code.BaseResponseCode;
import com.cosmoplat.entity.sys.SysPermission;
import com.cosmoplat.entity.sys.SysRolePermission;
import com.cosmoplat.entity.sys.SysUserRole;
import com.cosmoplat.entity.sys.vo.resp.PermissionRespNode;
import com.cosmoplat.provider.mapper.SysPermissionMapper;
import com.cosmoplat.service.sys.PermissionProviderService;
import com.cosmoplat.service.sys.RolePermissionProviderService;
import com.cosmoplat.service.sys.UserRoleProviderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * 菜单权限
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@DubboService
@Slf4j
public class PermissionProviderServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements PermissionProviderService {
    @Resource
    private UserRoleProviderService userRoleProviderService;
    @Resource
    private RolePermissionProviderService rolePermissionProviderService;
    @Resource
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 根据用户查询拥有的权限
     * 先查出用户拥有的角色
     * 再去差用户拥有的权限
     * 也可以多表关联查询
     */
    @Override
    public List<SysPermission> getPermission(String userId) {
        List<String> roleIds = userRoleProviderService.getRoleIdsByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        List<Object> permissionIds = rolePermissionProviderService.listObjs(Wrappers.<SysRolePermission>lambdaQuery().select(SysRolePermission::getPermissionId).in(SysRolePermission::getRoleId, roleIds));
        if (CollectionUtils.isEmpty(permissionIds)) {
            return Collections.emptyList();
        }
        //组装数据，pidName
        LambdaQueryWrapper<SysPermission> queryWrapper = Wrappers.<SysPermission>lambdaQuery().in(SysPermission::getId, permissionIds).orderByAsc(SysPermission::getOrderNum);
        List<SysPermission> list = sysPermissionMapper.selectList(queryWrapper);
        list.parallelStream().forEach(one -> {
            Optional<SysPermission> optional = list.parallelStream().filter(o -> one.getPid().equals(o.getId())).findFirst();
            if (optional.isPresent() && !StringUtils.isEmpty(optional.get().getName())) {
                one.setPidName(optional.get().getName());
            }
        });
        return list;
    }

    /**
     * 删除菜单权限
     * 判断是否 有角色关联
     * 判断是否有子集
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleted(String permissionId) {
        SysPermission sysPermission = sysPermissionMapper.selectById(permissionId);
        if (null == sysPermission) {
            log.error("传入 的 id:{}不合法", permissionId);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        //获取下一级
        List<SysPermission> childs = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery().eq(SysPermission::getPid, permissionId));
        if (!CollectionUtils.isEmpty(childs)) {
            throw new BusinessException(BaseResponseCode.ROLE_PERMISSION_RELATION);
        }
        sysPermissionMapper.deleteById(permissionId);
        //删除和角色关联
        rolePermissionProviderService.remove(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getPermissionId, permissionId));
    }


    /**
     * 获取所有菜单权限
     */
    @Override
    public List<SysPermission> selectAll() {
        List<SysPermission> result = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery().orderByAsc(SysPermission::getOrderNum));
        if (!CollectionUtils.isEmpty(result)) {
            for (SysPermission sysPermission : result) {
                SysPermission parent = sysPermissionMapper.selectById(sysPermission.getPid());
                if (parent != null) {
                    sysPermission.setPidName(parent.getName());
                }
            }
        }
        return result;
    }

    /**
     * 获取权限标识
     */
    @Override
    public Set<String> getPermissionsByUserId(String userId) {

        List<SysPermission> list = getPermission(userId);
        Set<String> permissions = new HashSet<>();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptySet();
        }
        for (SysPermission sysPermission : list) {
            if (!StringUtils.isEmpty(sysPermission.getPerms())) {
                permissions.add(sysPermission.getPerms());
            }

        }
        return permissions;
    }

    @Override
    public List<Map<String, String>> getUrlsByUserId(String userId) {
        List<SysPermission> list = getPermission(userId);
        List<Map<String, String>> r = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        for (SysPermission sysPermission : list) {
            if (!StringUtils.isEmpty(sysPermission.getUrl())) {
                Map<String, String> map = new HashMap<>(3);
                map.put("path", sysPermission.getUrl());
                map.put("name", sysPermission.getName());
                map.put("pidName", sysPermission.getPidName());
                r.add(map);
            }
        }
        return r;
    }

    /**
     * 以树型的形式把用户拥有的菜单权限返回给客户端
     */
    @Override
    public List<PermissionRespNode> permissionTreeList(String userId) {
        List<SysPermission> list = getPermission(userId);
        return getTree(list, true);
    }

    /**
     * 递归获取菜单树
     */
    private List<PermissionRespNode> getTree(List<SysPermission> all, boolean type) {

        List<PermissionRespNode> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(all)) {
            return list;
        }
        for (SysPermission sysPermission : all) {
            if ("0".equals(sysPermission.getPid())) {
                PermissionRespNode permissionRespNode = new PermissionRespNode();
                BeanUtils.copyProperties(sysPermission, permissionRespNode);
                if (type) {
                    permissionRespNode.setChildren(getChildExcBtn(sysPermission.getId(), all));
                } else {
                    permissionRespNode.setChildren(getChildAll(sysPermission.getId(), all));
                }
                list.add(permissionRespNode);
            }
        }
        return list;
    }

    /**
     * 递归遍历所有
     */
    private List<PermissionRespNode> getChildAll(String id, List<SysPermission> all) {

        List<PermissionRespNode> list = new ArrayList<>();
        for (SysPermission sysPermission : all) {
            if (sysPermission.getPid().equals(id)) {
                PermissionRespNode permissionRespNode = new PermissionRespNode();
                BeanUtils.copyProperties(sysPermission, permissionRespNode);
                permissionRespNode.setChildren(getChildAll(sysPermission.getId(), all));
                list.add(permissionRespNode);
            }
        }
        return list;
    }

    /**
     * 只递归获取目录和菜单
     */
    private List<PermissionRespNode> getChildExcBtn(String id, List<SysPermission> all) {

        List<PermissionRespNode> list = new ArrayList<>();
        for (SysPermission sysPermission : all) {
            if (sysPermission.getPid().equals(id) && sysPermission.getType() != 3) {
                PermissionRespNode permissionRespNode = new PermissionRespNode();
                BeanUtils.copyProperties(sysPermission, permissionRespNode);
                permissionRespNode.setChildren(getChildExcBtn(sysPermission.getId(), all));
                list.add(permissionRespNode);
            }
        }
        return list;
    }

    /**
     * 获取所有菜单权限按钮
     */
    @Override
    public List<PermissionRespNode> selectAllByTree() {

        List<SysPermission> list = selectAll();
        return getTree(list, false);
    }

    /**
     * 获取所有的目录菜单树排除按钮
     * 因为不管是新增或者修改
     * 选择所属菜单目录的时候
     * 都不可能选择到按钮
     * 而且编辑的时候 所属目录不能
     * 选择自己和它的子类
     */
    @Override
    public List<PermissionRespNode> selectAllMenuByTree(String permissionId) {

        List<SysPermission> list = selectAll();
        if (!CollectionUtils.isEmpty(list) && !StringUtils.isEmpty(permissionId)) {
            for (SysPermission sysPermission : list) {
                if (sysPermission.getId().equals(permissionId)) {
                    list.remove(sysPermission);
                    break;
                }
            }
        }
        List<PermissionRespNode> result = new ArrayList<>();
        //新增顶级目录是为了方便添加一级目录
        PermissionRespNode respNode = new PermissionRespNode();
        respNode.setId("0");
        respNode.setSpread(true);
        respNode.setChildren(getTree(list, true));
        result.add(respNode);
        return result;
    }

    @Override
    public List getUserIdsById(String id) {
        //根据权限id，获取所有角色id
        //根据权限id，获取所有角色id
        List<Object> roleIds = rolePermissionProviderService.listObjs(Wrappers.<SysRolePermission>lambdaQuery().select(SysRolePermission::getRoleId).eq(SysRolePermission::getPermissionId, id));
        if (!CollectionUtils.isEmpty(roleIds)) {
            //根据角色id， 获取关联用户
            return userRoleProviderService.listObjs(Wrappers.<SysUserRole>lambdaQuery().select(SysUserRole::getUserId).in(SysUserRole::getRoleId, roleIds));
        }
        return Collections.emptyList();
    }
}
