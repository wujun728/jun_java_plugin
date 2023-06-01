package com.cosmoplat.service.sys.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cosmoplat.entity.sys.vo.req.UserRoleOperationReqVO;
import com.cosmoplat.service.sys.UserRoleProviderService;
import com.cosmoplat.service.sys.UserRoleService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @DubboReference
    UserRoleProviderService userRoleProviderService;

    @Override
    public void setUserOwnRole(String userId, List<String> roleIds) {

        userRoleProviderService.removeByUserId(userId);
        if (null != roleIds && !CollectionUtils.isEmpty(roleIds)) {
            UserRoleOperationReqVO reqVO = new UserRoleOperationReqVO();
            reqVO.setUserId(userId);
            reqVO.setRoleIds(roleIds);
            userRoleProviderService.addUserRoleInfo(reqVO);
        }
    }

    @Override
    public void addUserRoleInfo(UserRoleOperationReqVO vo) {
        userRoleProviderService.addUserRoleInfo(vo);
    }
}
