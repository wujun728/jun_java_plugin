package org.typroject.tyboot.api.face.privilage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.typroject.tyboot.api.face.privilage.model.MenuModel;
import org.typroject.tyboot.api.face.privilage.orm.dao.MenuMapper;
import org.typroject.tyboot.api.face.privilage.orm.entity.Menu;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.utils.Bean;
import org.typroject.tyboot.core.foundation.utils.StringUtil;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.core.restful.exception.instance.BadRequest;
import org.typroject.tyboot.core.restful.exception.instance.DataNotFound;

import java.util.List;


@Service
public class MenuService extends BaseService<MenuModel,Menu,MenuMapper> {

    public MenuModel createMenu(MenuModel model)
    {
        return this.createWithModel(model);
    }

    @Transactional
    public MenuModel createMenuList(MenuModel model)
    {
        return this.createMenu(model);
    }

    public MenuModel updateMenu(MenuModel menuModel)throws Exception
    {

        MenuModel oldModel = this.queryByModel(menuModel);
        if(!ValidationUtil.isEmpty(oldModel) && RequestContext.getAgencyCode().equals(oldModel.getAgencyCode()))
        {
            oldModel = Bean.copyExistPropertis(menuModel,oldModel);
        }else{
            throw new Exception("数据不存在");
        }
        this.updateWithModel(oldModel);
        return oldModel;
    }



    public String  deleteMenu(String ids)
    {

        String [] deleteIds = StringUtil.string2Array(ids);

        for(String id:deleteIds)
        {
            Menu menu = this.baseMapper.selectById(id);
            if(!ValidationUtil.isEmpty(menu))
            {
                List<MenuModel> childs = this.queryForList(null,null,id,null);
                if(ValidationUtil.isEmpty(childs))
                {
                    this.baseMapper.deleteById(id);
                }else{
                    throw new BadRequest("请先删除其子节点[menuName:"+menu.getMenuName()+"].");
                }
            }else{
                throw new DataNotFound("data not found.");
            }

        }
        return ids;
    }



    public List<MenuModel> queryForList(String menuName, String agencyCode, String parentId, String menuType)
    {
        return this.queryForList(null,false,
                 menuName, agencyCode, parentId, menuType);
    }









}
