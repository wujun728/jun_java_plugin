package com.cosmoplat.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cosmoplat.entity.sys.SysUserRole;
import com.cosmoplat.entity.sys.vo.req.UserRoleOperationReqVO;

import java.util.List;

/**
 * 用户角色 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface UserRoleProviderService extends IService<SysUserRole> {

    List<String> getRoleIdsByUserId(String userId);

    void addUserRoleInfo(UserRoleOperationReqVO vo);

    List<String> getUserIdsByRoleId(String roleId);

    void removeByUserId(String userId);
}
