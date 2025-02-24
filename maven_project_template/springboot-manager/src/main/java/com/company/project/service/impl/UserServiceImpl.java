package com.company.project.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.common.utils.PasswordUtils;
import com.company.project.entity.SysDept;
import com.company.project.entity.SysRole;
import com.company.project.entity.SysUser;
import com.company.project.mapper.SysDeptMapper;
import com.company.project.mapper.SysUserMapper;
import com.company.project.service.PermissionService;
import com.company.project.service.RoleService;
import com.company.project.service.UserRoleService;
import com.company.project.service.UserService;
import com.company.project.vo.req.UserRoleOperationReqVO;
import com.company.project.vo.resp.LoginRespVO;
import com.company.project.vo.resp.UserOwnRoleRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Value("${spring.profiles.active}")
    private String env;

    @Override
    public void register(SysUser sysUser) {
        SysUser sysUserOne = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, sysUser.getUsername()));
        if (sysUserOne != null) {
            throw new BusinessException("用户名已存在！");
        }
        sysUser.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(sysUser.getPassword(), sysUser.getSalt());
        sysUser.setPassword(encode);
        sysUserMapper.insert(sysUser);
    }

    @Override
    public LoginRespVO login(SysUser vo) {
        SysUser sysUser = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, vo.getUsername()));
        if (null == sysUser) {
            throw new BusinessException(BaseResponseCode.NOT_ACCOUNT);
        }
        if (sysUser.getStatus() == 2) {
            throw new BusinessException(BaseResponseCode.USER_LOCK);
        }
        if (!PasswordUtils.matches(sysUser.getSalt(), vo.getPassword(), sysUser.getPassword())) {
            throw new BusinessException(BaseResponseCode.PASSWORD_ERROR);
        }
        LoginRespVO respVO = new LoginRespVO();
        BeanUtils.copyProperties(sysUser, respVO);

        if (StringUtils.isNotBlank(sysUser.getDeptId())) {
            SysDept sysDept = sysDeptMapper.selectById(sysUser.getDeptId());
            if (sysDept != null) {
                sysUser.setDeptNo(sysDept.getDeptNo());
            }
        }
        //saToken
        StpUtil.login(sysUser.getId());
        return respVO;
    }

    @Override
    public void updateUserInfo(SysUser vo) {


        SysUser sysUser = sysUserMapper.selectById(vo.getId());
        if (null == sysUser) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }

        //如果用户名变更
        if (!sysUser.getUsername().equals(vo.getUsername())) {
            SysUser sysUserOne = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, vo.getUsername()));
            if (sysUserOne != null) {
                throw new BusinessException("用户名已存在！");
            }
        }

        //如果用户名、密码、状态 变更，删除redis中用户绑定的角色跟权限
        if (!sysUser.getUsername().equals(vo.getUsername())
                || (!StringUtils.isEmpty(vo.getPassword())
                && !sysUser.getPassword().equals(PasswordUtils.encode(vo.getPassword(), sysUser.getSalt())))
                || !sysUser.getStatus().equals(vo.getStatus())) {
        }

        if (!StringUtils.isEmpty(vo.getPassword())) {
            String newPassword = PasswordUtils.encode(vo.getPassword(), sysUser.getSalt());
            vo.setPassword(newPassword);
        } else {
            vo.setPassword(null);
        }
        sysUserMapper.updateById(vo);

    }

    @Override
    public void updateUserInfoMy(SysUser vo) {
        SysUser user = sysUserMapper.selectById(StpUtil.getLoginIdAsString());
        if (null == user) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        if (!StringUtils.isEmpty(vo.getPassword())) {
            String newPassword = PasswordUtils.encode(vo.getPassword(), user.getSalt());
            vo.setPassword(newPassword);
        } else {
            vo.setPassword(null);
        }
        vo.setUpdateId(StpUtil.getLoginIdAsString());
        sysUserMapper.updateById(vo);

    }

    @Override
    public IPage<SysUser> pageInfo(SysUser vo) {
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
        if (null != vo.getStatus()) {
            queryWrapper.eq(SysUser::getStatus, vo.getStatus());
        }
        if (!StringUtils.isEmpty(vo.getDeptNo())) {
            LambdaQueryWrapper<SysDept> queryWrapperDept = Wrappers.lambdaQuery();
            queryWrapperDept.select(SysDept::getId).like(SysDept::getRelationCode, vo.getDeptNo());
            List<Object> list = sysDeptMapper.selectObjs(queryWrapperDept);
            queryWrapper.in(SysUser::getDeptId, list);
        }
        queryWrapper.orderByDesc(SysUser::getCreateTime);
        IPage<SysUser> iPage = sysUserMapper.selectPage(vo.getQueryPage(), queryWrapper);
        if (!CollectionUtils.isEmpty(iPage.getRecords())) {
            for (SysUser sysUser : iPage.getRecords()) {
                SysDept sysDept = sysDeptMapper.selectById(sysUser.getDeptId());
                if (sysDept != null) {
                    sysUser.setDeptName(sysDept.getName());
                }
            }
        }
        return iPage;
    }

    @Override
    public void addUser(SysUser vo) {

        SysUser sysUserOne = sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, vo.getUsername()));
        if (sysUserOne != null) {
            throw new BusinessException("用户已存在，请勿重复添加！");
        }
        vo.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(vo.getPassword(), vo.getSalt());
        vo.setPassword(encode);
        vo.setStatus(1);
        vo.setCreateWhere(1);
        sysUserMapper.insert(vo);
        if (!CollectionUtils.isEmpty(vo.getRoleIds())) {
            UserRoleOperationReqVO reqVO = new UserRoleOperationReqVO();
            reqVO.setUserId(vo.getId());
            reqVO.setRoleIds(vo.getRoleIds());
            userRoleService.addUserRoleInfo(reqVO);
        }
    }

    @Override
    public void updatePwd(SysUser vo) {

        SysUser sysUser = sysUserMapper.selectById(vo.getId());
        if (sysUser == null) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        if ("test".equals(env) && "guest".equals(sysUser.getUsername())) {
            throw new BusinessException("演示环境禁止修改演示账号密码");
        }

        if (!PasswordUtils.matches(sysUser.getSalt(), vo.getOldPwd(), sysUser.getPassword())) {
            throw new BusinessException(BaseResponseCode.OLD_PASSWORD_ERROR);
        }
        if (sysUser.getPassword().equals(PasswordUtils.encode(vo.getNewPwd(), sysUser.getSalt()))) {
            throw new BusinessException("新密码不能与旧密码相同");
        }
        sysUser.setPassword(PasswordUtils.encode(vo.getNewPwd(), sysUser.getSalt()));
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public UserOwnRoleRespVO getUserOwnRole(String userId) {
        List<String> roleIdsByUserId = userRoleService.getRoleIdsByUserId(userId);
        List<SysRole> list = roleService.list();
        UserOwnRoleRespVO vo = new UserOwnRoleRespVO();
        vo.setAllRole(list);
        vo.setOwnRoles(roleIdsByUserId);
        return vo;
    }
}
