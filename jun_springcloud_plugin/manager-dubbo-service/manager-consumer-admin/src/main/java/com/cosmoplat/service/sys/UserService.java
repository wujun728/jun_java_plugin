package com.cosmoplat.service.sys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cosmoplat.entity.sys.SysUser;
import com.cosmoplat.entity.sys.vo.req.LoginReqVO;
import com.cosmoplat.entity.sys.vo.req.UpdatePasswordReqVO;
import com.cosmoplat.entity.sys.vo.resp.LoginRespVO;
import com.cosmoplat.entity.sys.vo.resp.UserOwnRoleRespVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface UserService {

    LoginRespVO login(LoginReqVO vo, HttpServletRequest httpServletRequest);

    void updateUserInfo(SysUser vo, String operationId);

    IPage<SysUser> pageInfo(SysUser vo);

    void addUser(SysUser vo);

    void logout();

    void updatePwd(UpdatePasswordReqVO vo, String userId);


    UserOwnRoleRespVO getUserOwnRole(String userId);

    void updateUserInfoMy(SysUser vo, String userId);

    SysUser getById(String userId);

    void removeByIds(List<String> userIds);

    LoginRespVO getUserInfoByUserId(String currentUserId);

    /**
     * 保存或更新用户信息
     *
     * @param user
     * @return
     */
    SysUser saveOrUpdateLoginUser(SysUser user);

    List<SysUser> listAll(SysUser vo);

}
