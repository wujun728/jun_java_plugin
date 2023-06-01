package com.cosmoplat.provider.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cosmoplat.entity.sys.SysUser;
import com.cosmoplat.provider.mapper.SysDeptMapper;
import com.cosmoplat.provider.mapper.SysUserMapper;
import com.cosmoplat.service.sys.UserProviderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@DubboService
@Slf4j
public class UserProviderServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserProviderService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<SysUser> getUserListByDeptIds(List<Object> deptIds) {
        return sysUserMapper.selectList(Wrappers.<SysUser>lambdaQuery().in(SysUser::getDeptId, deptIds));
    }

    @Override
    public SysUser selectByUsername(String username) {
        return sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
    }

    @Override
    public IPage<SysUser> selectPage(SysUser vo) {
        Page<SysUser> page = new Page(vo.getPage(), vo.getLimit());
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(vo.getUsername())) {
            queryWrapper.like(SysUser::getUsername, vo.getUsername());
        }
        if (!StringUtils.isEmpty(vo.getStartTime())) {
            queryWrapper.gt(SysUser::getCreateTime, vo.getStartTime());
        }
        if (!StringUtils.isEmpty(vo.getEndTime())) {
            queryWrapper.lt(SysUser::getCreateTime, vo.getEndTime());
        }
        if (!StringUtils.isEmpty(vo.getNickName())) {
            queryWrapper.like(SysUser::getNickName, vo.getNickName());
        }
        queryWrapper.orderByDesc(SysUser::getCreateTime);
        IPage<SysUser> iPage = sysUserMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    @Override
    public List<SysUser> listByCondition(SysUser vo) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();
        if (!StringUtils.isEmpty(vo.getUsername())) {
            queryWrapper.like(SysUser::getUsername, vo.getUsername());
        }
        if (!StringUtils.isEmpty(vo.getStartTime())) {
            queryWrapper.gt(SysUser::getCreateTime, vo.getStartTime());
        }
        if (!StringUtils.isEmpty(vo.getEndTime())) {
            queryWrapper.lt(SysUser::getCreateTime, vo.getEndTime());
        }
        if (!StringUtils.isEmpty(vo.getNickName())) {
            queryWrapper.like(SysUser::getNickName, vo.getNickName());
        }
        return sysUserMapper.selectList(queryWrapper);
    }


}
