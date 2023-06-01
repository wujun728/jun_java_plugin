package com.cosmoplat.provider.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cosmoplat.common.exception.BusinessException;
import com.cosmoplat.common.exception.code.BaseResponseCode;
import com.cosmoplat.entity.sys.SysRole;
import com.cosmoplat.entity.sys.SysRolePermission;
import com.cosmoplat.entity.sys.SysUserRole;
import com.cosmoplat.entity.sys.vo.req.RolePermissionOperationReqVO;
import com.cosmoplat.entity.sys.vo.resp.PermissionRespNode;
import com.cosmoplat.provider.mapper.SysRoleMapper;
import com.cosmoplat.service.sys.PermissionProviderService;
import com.cosmoplat.service.sys.RolePermissionProviderService;
import com.cosmoplat.service.sys.RoleProviderService;
import com.cosmoplat.service.sys.UserRoleProviderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@DubboService
@Slf4j
public class RoleProviderServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements RoleProviderService {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private UserRoleProviderService userRoleProviderService;
    @Resource
    private RolePermissionProviderService rolePermissionProviderService;
    @Resource
    private PermissionProviderService permissionProviderService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysRole addRole(SysRole vo) {

        sysRoleMapper.insert(vo);
        if (!CollectionUtils.isEmpty(vo.getPermissions())) {
            RolePermissionOperationReqVO reqVO = new RolePermissionOperationReqVO();
            reqVO.setRoleId(vo.getId());
            reqVO.setPermissionIds(vo.getPermissions());
            rolePermissionProviderService.addRolePermission(reqVO);
        }

        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRole(SysRole vo) {
        SysRole sysRole = sysRoleMapper.selectById(vo.getId());
        if (null == sysRole) {
            log.error("传入 的 id:{}不合法", vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        sysRoleMapper.updateById(vo);
        //删除角色权限关联
        rolePermissionProviderService.remove(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getRoleId, sysRole.getId()));
        if (!CollectionUtils.isEmpty(vo.getPermissions())) {
            RolePermissionOperationReqVO reqVO = new RolePermissionOperationReqVO();
            reqVO.setRoleId(sysRole.getId());
            reqVO.setPermissionIds(vo.getPermissions());
            rolePermissionProviderService.addRolePermission(reqVO);
        }
    }

    @Override
    public SysRole detailInfo(String id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        if (sysRole == null) {
            log.error("传入 的 id:{}不合法", id);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        LambdaQueryWrapper<SysRolePermission> queryWrapper = Wrappers.<SysRolePermission>lambdaQuery().select(SysRolePermission::getPermissionId).eq(SysRolePermission::getRoleId, sysRole.getId());
        List<Object> checkList =
                rolePermissionProviderService.listObjs(queryWrapper);
        if (!CollectionUtils.isEmpty(checkList)) {
            sysRole.setPermissions(checkList.parallelStream().map(Object::toString).collect(Collectors.toList()));
        }
        return sysRole;
    }


    @SuppressWarnings("unchecked")
    private void setChecked(List<PermissionRespNode> list, Set<Object> checkList) {
        for (PermissionRespNode node : list) {
            if (checkList.contains(node.getId())
                    && CollectionUtils.isEmpty(node.getChildren())) {
                node.setChecked(true);
            }
            setChecked((List<PermissionRespNode>) node.getChildren(), checkList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletedRole(String id) {
        //删除角色
        sysRoleMapper.deleteById(id);
        //删除角色权限关联
        rolePermissionProviderService.remove(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getRoleId, id));
        //删除角色用户关联
        userRoleProviderService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getRoleId, id));
    }

    @Override
    public List<SysRole> getRoleInfoByUserId(String userId) {

        List<String> roleIds = userRoleProviderService.getRoleIdsByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return sysRoleMapper.selectBatchIds(roleIds);
    }

    @Override
    public List<String> getRoleNames(String userId) {

        List<SysRole> sysRoles = getRoleInfoByUserId(userId);
        if (CollectionUtils.isEmpty(sysRoles)) {
            return Collections.emptyList();
        }
        return sysRoles.stream().map(SysRole::getName).collect(Collectors.toList());
    }

    @Override
    public List<SysRole> selectAllRoles() {
        return sysRoleMapper.selectList(Wrappers.emptyWrapper());
    }

    @Override
    public IPage<SysRole> pageInfo(SysRole vo) {
        Page<SysRole> page = new Page(vo.getPage(), vo.getLimit());
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(vo.getName())) {
            queryWrapper.like(SysRole::getName, vo.getName());
        }
        if (!StringUtils.isEmpty(vo.getStartTime())) {
            queryWrapper.gt(SysRole::getCreateTime, vo.getStartTime());
        }
        if (!StringUtils.isEmpty(vo.getEndTime())) {
            queryWrapper.lt(SysRole::getCreateTime, vo.getEndTime());
        }
        queryWrapper.orderByDesc(SysRole::getCreateTime);
        return sysRoleMapper.selectPage(page, queryWrapper);
    }
}
