package org.typroject.tyboot.api.face.privilage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.typroject.tyboot.api.face.privilage.model.RoleModel;
import org.typroject.tyboot.api.face.privilage.orm.dao.RoleMapper;
import org.typroject.tyboot.api.face.privilage.orm.entity.Role;
import org.typroject.tyboot.core.foundation.constans.PropertyValueConstants;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.utils.Bean;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.core.restful.exception.instance.BadRequest;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Wujun
 * @since 2017-08-18
 */
@Service
public  class RoleService extends BaseService<RoleModel,Role,RoleMapper> {





    public RoleModel createRole(RoleModel model)throws Exception
    {
        return     this.createWithModel(model);
    }

    public RoleModel updateRole(RoleModel roleModel)throws Exception
    {

        RoleModel oldModel = this.queryBySeq(roleModel.getSequenceNbr());
        if(!ValidationUtil.isEmpty(oldModel) && RequestContext.getAgencyCode().equals(oldModel.getAgencyCode()))
        {
            oldModel = Bean.copyExistPropertis(roleModel,oldModel);
        }else{
            throw new BadRequest("数据不存在");
        }
        this.updateWithModel(oldModel);
        return oldModel;
    }


    public boolean deleteRole(Long seq)throws Exception

    {
        return this.deleteBySeq(seq);
    }



    public Page<RoleModel> qeuryByName(Page<RoleModel> page, String roleName ,
                                        String agencyCode) throws Exception
    {
       return this.queryForPage(page,null,false,roleName,agencyCode);
    }



    public List<RoleModel> selectByAgency(String agencyCode) throws Exception
    {
        return this.queryForList(null,false,agencyCode);
    }



    public RoleModel updateRoleLockStatus(Long  sequenceNbr) throws Exception
    {
        RoleModel role = this.queryBySeq(sequenceNbr);
        if(!ValidationUtil.isEmpty(role))
        {
            if(PropertyValueConstants.LOCK_STATUS_UNLOCK.equals(role.getLockStatus()))
            {
                role.setLockStatus(PropertyValueConstants.LOCK_STATUS_LOCK);
            }

            if(PropertyValueConstants.LOCK_STATUS_LOCK.equals(role.getLockStatus()))
            {
                role.setLockStatus(PropertyValueConstants.LOCK_STATUS_UNLOCK);
            }
            role.setLockUserId(RequestContext.getExeUserId());
            role.setLockDate(new Date());
        }
        return Bean.toModel(role,new RoleModel());
    }





	
}
