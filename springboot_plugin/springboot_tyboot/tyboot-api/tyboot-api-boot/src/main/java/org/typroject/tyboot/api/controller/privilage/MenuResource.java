package org.typroject.tyboot.api.controller.privilage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.typroject.tyboot.api.face.privilage.model.MenuModel;
import org.typroject.tyboot.api.face.privilage.service.MenuService;
import org.typroject.tyboot.api.face.privilage.service.RoleMenuService;
import org.typroject.tyboot.api.face.privilage.service.UserRoleService;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.enumeration.UserType;
import org.typroject.tyboot.core.restful.doc.TycloudOperation;
import org.typroject.tyboot.core.restful.doc.TycloudResource;
import org.typroject.tyboot.core.restful.utils.ResponseHelper;
import org.typroject.tyboot.core.restful.utils.ResponseModel;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author Wujun
 * @since 2017-08-18
 */
@TycloudResource(module = "privilege",value = "menu")
@RequestMapping(value = "/v1/privilege/menu")
@Api(tags = "privilege-菜单权限")
@RestController
public class MenuResource {




    @Autowired
    MenuService menuService;

    @Autowired
    UserRoleService userRoleService;


    @Autowired
    RoleMenuService roleMenuService;




    @TycloudOperation( ApiLevel = UserType.AGENCY,needAuth = false)
    @ApiOperation(value="创建菜单权限")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseModel<MenuModel> createMenu(@RequestBody MenuModel menuModel)
    {

        menuModel.setAgencyCode(RequestContext.getAgencyCode());
        menuModel.setCreateUserId(RequestContext.getExeUserId());
        menuModel.setCreateTime(new Date());
        return ResponseHelper.buildResponse( menuService.createMenuList(menuModel));
    }




    @TycloudOperation( ApiLevel = UserType.AGENCY)
    @ApiOperation(value="查询单个权限菜单")
    @RequestMapping(value = "/{sequenceNBR}", method = RequestMethod.GET)
    public ResponseModel<MenuModel> seleteOne(@PathVariable Long sequenceNbr) throws Exception
    {
        return ResponseHelper.buildResponse(this.menuService.queryBySeq(sequenceNbr));
    }




    @TycloudOperation( ApiLevel = UserType.AGENCY)
    @ApiOperation(value="当前机构所有菜单权限")
    @RequestMapping(value = "/agency/menus", method = RequestMethod.GET)
    public ResponseModel<List<MenuModel>> selectByAgency() throws Exception
    {
        return ResponseHelper.buildResponse(this.menuService.queryForList(null, RequestContext.getAgencyCode(),null,null));
    }






    @TycloudOperation( ApiLevel = UserType.AGENCY)
    @ApiOperation(value="更新菜单权限")
    @RequestMapping(value = "/{sequenceNBR}", method = RequestMethod.PUT)
    public ResponseModel<MenuModel> updateMenu(@RequestBody MenuModel menuModel, @PathVariable Long sequenceNBR) throws Exception
    {
        menuModel.setSequenceNbr(sequenceNBR);
        return ResponseHelper.buildResponse(menuService.updateMenu(menuModel));
    }


    @TycloudOperation( ApiLevel = UserType.SUPER_ADMIN)
    @ApiOperation(value="删除权限菜单")
    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    public ResponseModel<String> deleteMenu(@PathVariable String ids) throws Exception
    {
        return ResponseHelper.buildResponse(menuService.deleteMenu(ids));
    }













}
