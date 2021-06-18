package org.typroject.tyboot.api.face.privilage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.typroject.tyboot.api.face.privilage.model.MenuModel;
import org.typroject.tyboot.api.face.privilage.model.RoleMenuModel;
import org.typroject.tyboot.api.face.privilage.model.RoleModel;
import org.typroject.tyboot.api.face.privilage.orm.dao.RoleMenuMapper;
import org.typroject.tyboot.api.face.privilage.orm.entity.RoleMenu;
import org.typroject.tyboot.core.foundation.constans.PropertyValueConstants;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.rdbms.service.BaseService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色与菜单关系表 服务实现类
 * </p>
 *
 * @author Wujun
 * @since 2017-08-18
 */
@Service
public class RoleMenuService extends BaseService<RoleMenuModel,RoleMenu,RoleMenuMapper>  {



    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    public List<RoleMenuModel> selectByRole(String roleSequenceNbr, String agencyCode ) throws Exception
    {
        return this.queryForList(null,false,roleSequenceNbr,agencyCode);
    }








    /**
     * 更新角色菜单权限
     */
    public  ArrayList<RoleMenuModel> updateByRole(String [] menuIds,Long  roleSequenceNbr,String agencyCode)  throws  Exception
    {

        ArrayList<RoleMenuModel> returnList = new ArrayList<>();

        //验证角色信息
        RoleModel roleModel                       = roleService.queryBySeq(roleSequenceNbr);
        if(!ValidationUtil.isEmpty(roleModel) && PropertyValueConstants.LOCK_STATUS_UNLOCK.equals(roleModel.getLockStatus()))
        {
            //删除已有菜单角色
            Map<String,Object> params   = this.assemblyMapParams(
                    Thread.currentThread().getStackTrace()[1].getMethodName(),
                    this.getClass(),
                    roleSequenceNbr,agencyCode);
            this.removeByMap(params);
            //保存新的菜单角色
            for(String menuId : menuIds)
            {
                MenuModel menuModel = this.menuService.queryBySeq(Long.parseLong(menuId));
                if(!ValidationUtil.isEmpty(menuModel) && menuModel.getAgencyCode().equals(agencyCode))
                {
                    returnList.add(this.createRoleMenu(roleSequenceNbr,Long.parseLong(menuId),agencyCode));
                }else{
                    throw new  Exception("菜单权限信息不存在.");
                }

            }
        }else{
            throw new  Exception("角色信息异常.");
        }
        return returnList;
    }

    public RoleMenuModel createRoleMenu(Long   roleSequenceNbr ,Long menuSequenceNbr,String agencyCode) throws Exception
    {

        RoleMenuModel newRoleMenuModel = new RoleMenuModel();
        newRoleMenuModel.setAgencyCode(agencyCode);
        newRoleMenuModel.setCreateTime(new Date());
        newRoleMenuModel.setCreateUserId(RequestContext.getExeUserId());
        newRoleMenuModel.setMenuSequenceNbr(menuSequenceNbr);
        newRoleMenuModel.setRoleSequenceNbr(roleSequenceNbr);
        return  this.createWithModel(newRoleMenuModel);

    }



}
