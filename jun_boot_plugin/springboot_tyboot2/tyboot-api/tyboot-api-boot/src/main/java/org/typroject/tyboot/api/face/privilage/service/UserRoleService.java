package org.typroject.tyboot.api.face.privilage.service;

import org.springframework.stereotype.Service;
import org.typroject.tyboot.api.face.privilage.model.UserRoleModel;
import org.typroject.tyboot.api.face.privilage.orm.dao.UserRoleMapper;
import org.typroject.tyboot.api.face.privilage.orm.entity.UserRole;
import org.typroject.tyboot.core.rdbms.service.BaseService;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author Wujun
 * @since 2017-08-18
 */
@Service
public class UserRoleService extends BaseService<UserRoleModel,UserRole,UserRoleMapper> {


    public List<UserRoleModel> selectRoleByUser(String userId, String agencyCode) throws Exception
    {
        return this.queryForList(null,false,userId,agencyCode);
    }






}
