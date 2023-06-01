package com.cosmoplat.provider.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cosmoplat.entity.sys.SysUserRole;
import com.cosmoplat.entity.sys.vo.req.UserRoleOperationReqVO;
import com.cosmoplat.provider.mapper.SysUserRoleMapper;
import com.cosmoplat.service.sys.UserRoleProviderService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@DubboService
public class UserRoleProviderServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements UserRoleProviderService {
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List getRoleIdsByUserId(String userId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.<SysUserRole>lambdaQuery().select(SysUserRole::getRoleId).eq(SysUserRole::getUserId, userId);
        return sysUserRoleMapper.selectObjs(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserRoleInfo(UserRoleOperationReqVO vo) {
        if (CollectionUtils.isEmpty(vo.getRoleIds())) {
            return;
        }
        List<SysUserRole> list = new ArrayList<>();
        for (String roleId : vo.getRoleIds()) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(vo.getUserId());
            sysUserRole.setRoleId(roleId);
            list.add(sysUserRole);
        }
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, vo.getUserId()));
        //批量插入
        this.saveBatch(list);
    }

    @Override
    public List getUserIdsByRoleId(String roleId) {
        return sysUserRoleMapper.selectObjs(Wrappers.<SysUserRole>lambdaQuery().select(SysUserRole::getUserId).eq(SysUserRole::getRoleId, roleId));
    }

    @Override
    public void removeByUserId(String userId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        sysUserRoleMapper.delete(queryWrapper);
    }
}
