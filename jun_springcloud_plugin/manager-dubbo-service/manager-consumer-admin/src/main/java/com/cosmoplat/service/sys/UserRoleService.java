package com.cosmoplat.service.sys;

import com.cosmoplat.entity.sys.vo.req.UserRoleOperationReqVO;

import java.util.List;

/**
 * 用户角色管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface UserRoleService {
    void setUserOwnRole(String userId, List<String> roleIds);

    void addUserRoleInfo(UserRoleOperationReqVO vo);
}
