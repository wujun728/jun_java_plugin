package com.cosmoplat.service.sys.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.cosmoplat.common.UucConstant;
import com.cosmoplat.common.exception.BusinessException;
import com.cosmoplat.common.exception.code.BaseResponseCode;
import com.cosmoplat.common.utils.Constant;
import com.cosmoplat.common.utils.PasswordUtils;
import com.cosmoplat.entity.sys.SysRole;
import com.cosmoplat.entity.sys.SysUser;
import com.cosmoplat.entity.sys.vo.req.LoginReqVO;
import com.cosmoplat.entity.sys.vo.req.UpdatePasswordReqVO;
import com.cosmoplat.entity.sys.vo.req.UserRoleOperationReqVO;
import com.cosmoplat.entity.sys.vo.resp.LoginRespVO;
import com.cosmoplat.entity.sys.vo.resp.UserOwnRoleRespVO;
import com.cosmoplat.service.sys.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @DubboReference
    UserProviderService userProviderService;
    @DubboReference
    RoleProviderService roleProviderService;
    @DubboReference
    UserRoleProviderService userRoleProviderService;
    @DubboReference
    PermissionProviderService permissionService;
    @Resource
    HttpSessionService httpSessionService;
    @Resource
    private PermissionProviderService permissionProviderService;
    @Autowired
    UucConstant uucConstant;

    @Override
    public LoginRespVO login(LoginReqVO vo, HttpServletRequest httpServletRequest) {

        SysUser sysUser = userProviderService.selectByUsername(vo.getUsername());
        //如果本地数据库为空，直接调用uuc的登录， 如果用户来源是2， 那么是uuc注册进来的

        if (!PasswordUtils.matches(sysUser.getSalt(), vo.getPassword(), sysUser.getPassword())) {
            throw new BusinessException(BaseResponseCode.PASSWORD_ERROR);
        }
        if (!Constant.UNABLE_FLAG_Y.equals(sysUser.getUnableFlag())) {
            throw new BusinessException(BaseResponseCode.USER_LOCK);
        }
        LoginRespVO respVO = new LoginRespVO();
        BeanUtils.copyProperties(sysUser, respVO);

        String token = httpSessionService.createTokenAndUser(sysUser, getRolesByUserId(sysUser.getId()), getPermissionsByUserId(sysUser.getId()), null);
        respVO.setAccessToken(token);
        return respVO;
    }

    private List<String> getRolesByUserId(String userId) {
        return roleProviderService.getRoleNames(userId);
    }

    private Set<String> getPermissionsByUserId(String userId) {
        return permissionService.getPermissionsByUserId(userId);
    }

    private List<Map<String, String>> getUrlsByUserId(String userId) {
        return permissionService.getUrlsByUserId(userId);
    }

    @Override
    public void updateUserInfo(SysUser vo, String operationId) {


        SysUser sysUser = userProviderService.getById(vo.getId());
        if (null == sysUser) {
            log.error("传入 的 id:{}不合法", vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }

        //如果用户名变更
        if (!sysUser.getUsername().equals(vo.getUsername())) {
            SysUser sysUserOne = userProviderService.selectByUsername(vo.getUsername());
            if (sysUserOne != null) {
                throw new BusinessException("用户名已存在！");
            }
        }

        //如果用户名、密码、状态 变更，删除redis中用户绑定的角色跟权限
        if (!sysUser.getUsername().equals(vo.getUsername())
                || (!StringUtils.isEmpty(vo.getPassword())
                && !sysUser.getPassword().equals(PasswordUtils.encode(vo.getPassword(), sysUser.getSalt())))
                || !sysUser.getUnableFlag().equals(vo.getUnableFlag())) {
            httpSessionService.abortUserById(vo.getId());
        }

        BeanUtils.copyProperties(vo, sysUser);
        if (!StringUtils.isEmpty(vo.getPassword())) {
            String newPassword = PasswordUtils.encode(vo.getPassword(), sysUser.getSalt());
            sysUser.setPassword(newPassword);
        } else {
            sysUser.setPassword(null);
        }
        sysUser.setUpdateId(operationId);
        userProviderService.updateById(sysUser);

    }

    @Override
    public void updateUserInfoMy(SysUser vo, String operationId) {


        SysUser sysUser = userProviderService.getById(vo.getId());
        if (null == sysUser) {
            log.error("传入 的 id:{}不合法", vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }

        BeanUtils.copyProperties(vo, sysUser);
        if (!StringUtils.isEmpty(vo.getPassword())) {
            String newPassword = PasswordUtils.encode(vo.getPassword(), sysUser.getSalt());
            sysUser.setPassword(newPassword);
        } else {
            sysUser.setPassword(null);
        }
        sysUser.setUpdateId(operationId);
        userProviderService.updateById(sysUser);

    }

    @Override
    public SysUser getById(String userId) {
        return userProviderService.getById(userId);
    }


    @Override
    public void removeByIds(List<String> userIds) {
        httpSessionService.abortUserByUserIds(userIds);
        userProviderService.removeByIds(userIds);
    }

    @Override
    public LoginRespVO getUserInfoByUserId(String currentUserId) {
        SysUser sysUser = userProviderService.getById(currentUserId);
        LoginRespVO respVO = new LoginRespVO();
        BeanUtils.copyProperties(sysUser, respVO);
        respVO.setMenus(permissionProviderService.permissionTreeList(sysUser.getId()));
        respVO.setPermissions(getPermissionsByUserId(sysUser.getId()));
        respVO.setUrls(getUrlsByUserId(sysUser.getId()));
        return respVO;

    }

    @Override
    public IPage<SysUser> pageInfo(SysUser vo) {

        return userProviderService.selectPage(vo);
    }

    @Override
    public void addUser(SysUser vo) {

        SysUser sysUserOne = userProviderService.selectByUsername(vo.getUsername());
        if (sysUserOne != null) {
            throw new BusinessException("用户已存在，请勿重复添加！");
        }

        vo.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(vo.getPassword(), vo.getSalt());
        vo.setPassword(encode);
        vo.setCreateWhere(1);
        Long id = IdWorker.getId();
        vo.setId(String.valueOf(id));
        userProviderService.save(vo);
        if (!CollectionUtils.isEmpty(vo.getRoleIds())) {
            UserRoleOperationReqVO reqVO = new UserRoleOperationReqVO();
            reqVO.setUserId(vo.getId());
            reqVO.setRoleIds(vo.getRoleIds());
            userRoleProviderService.addUserRoleInfo(reqVO);
        }
    }

    @Override
    public void logout() {
        httpSessionService.abortUserByToken();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @Override
    public void updatePwd(UpdatePasswordReqVO vo, String userId) {

        SysUser sysUser = userProviderService.getById(userId);
        if (sysUser == null) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        if (!PasswordUtils.matches(sysUser.getSalt(), vo.getOldPwd(), sysUser.getPassword())) {
            throw new BusinessException(BaseResponseCode.OLD_PASSWORD_ERROR);
        }
        if (sysUser.getPassword().equals(PasswordUtils.encode(vo.getNewPwd(), sysUser.getSalt()))) {
            throw new BusinessException("新密码不能与旧密码相同");
        }
        sysUser.setPassword(PasswordUtils.encode(vo.getNewPwd(), sysUser.getSalt()));
        userProviderService.updateById(sysUser);

        httpSessionService.abortAllUserByToken();

    }

    @Override
    public SysUser saveOrUpdateLoginUser(SysUser user) {
        SysUser sysUser = userProviderService.selectByUsername(user.getUsername());
        if (sysUser != null) {
            //更新
            user.setId(sysUser.getId());
            userProviderService.saveOrUpdate(user);
        } else {
            //保存
            userProviderService.save(user);

        }
        return user;
    }

    @Override
    public List<SysUser> listAll(SysUser vo) {
        return userProviderService.listByCondition(vo);
    }


    @Override
    public UserOwnRoleRespVO getUserOwnRole(String userId) {
        List<String> roleIdsByUserId = userRoleProviderService.getRoleIdsByUserId(userId);
        List<SysRole> list = roleProviderService.selectAllRoles();
        UserOwnRoleRespVO vo = new UserOwnRoleRespVO();
        vo.setAllRole(list);
        vo.setOwnRoles(roleIdsByUserId);
        return vo;
    }

}
