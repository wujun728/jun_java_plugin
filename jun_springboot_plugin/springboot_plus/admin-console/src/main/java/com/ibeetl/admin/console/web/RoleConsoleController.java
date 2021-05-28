package com.ibeetl.admin.console.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibeetl.admin.console.service.FunctionConsoleService;
import com.ibeetl.admin.console.service.OrgConsoleService;
import com.ibeetl.admin.console.service.RoleConsoleService;
import com.ibeetl.admin.console.web.dto.RoleDataAccessFunction;
import com.ibeetl.admin.console.web.query.RoleQuery;
import com.ibeetl.admin.console.web.query.RoleUserQuery;
import com.ibeetl.admin.core.annotation.Function;
import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.entity.CoreRole;
import com.ibeetl.admin.core.entity.CoreUser;
import com.ibeetl.admin.core.service.CorePlatformService;
import com.ibeetl.admin.core.util.AnnotationUtil;
import com.ibeetl.admin.core.util.ConvertUtil;
import com.ibeetl.admin.core.util.ValidateConfig;
import com.ibeetl.admin.core.web.JsonResult;

/**
 * 角色
 */
@Controller
public class RoleConsoleController {

    private final Log log = LogFactory.getLog(this.getClass());
    private static final String MODEL = "/admin/role";

    @Autowired
    private RoleConsoleService roleConsoleService;

    @Autowired
    private FunctionConsoleService functionConsoleService;

    @Autowired
    CorePlatformService platformService;

    @Autowired
    private OrgConsoleService orgConsoleService;

    /* 页面 */

    @GetMapping(MODEL + "/index.do")
    @Function("role")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("/admin/role/index.html");
        view.addObject("search", RoleQuery.class.getName());
        return view;
    }

    @GetMapping(MODEL + "/edit.do")
    @Function("role.edit")
    public ModelAndView edit(String id) {
        ModelAndView view = new ModelAndView("/admin/role/edit.html");
        CoreRole role = roleConsoleService.queryById(id);
        view.addObject("role", role);
        return view;
    }

    @GetMapping(MODEL + "/add.do")
    @Function("role.add")
    public ModelAndView add() {
        ModelAndView view = new ModelAndView("/admin/role/add.html");
        return view;
    }

    @GetMapping(MODEL + "/user/list.do")
    @Function("role.user.query")
    public ModelAndView users(Long roleId) {
        CoreRole role = roleConsoleService.queryById(roleId);
        ModelAndView view = new ModelAndView("/admin/role/roleUser.html");
        view.addObject("role", role);
        view.addObject("search", RoleUserQuery.class.getName());
        return view;
    }

    @GetMapping(MODEL + "/function.do")
    @Function("role.function.query")
    public ModelAndView functions() {
        ModelAndView view = new ModelAndView("/admin/role/function.html");
        return view;
    }
    @GetMapping(MODEL + "/data.do")
    @Function("role.function.query")
    public ModelAndView  data() {
        ModelAndView view = new ModelAndView("/admin/role/data.html");
        return view;
    }

    /**
     * 列表页、 分页数据
     * 
     * @param condtion
     * @return
     */
    @PostMapping(MODEL + "/list.json")
    @Function("role.query")
    @ResponseBody
    public JsonResult<PageQuery> list(RoleQuery condtion) {
        PageQuery page = condtion.getPageQuery();
        roleConsoleService.queryByCondtion(page);
        return JsonResult.success(page);
    }

    @GetMapping(MODEL + "/all.json")
    @Function("role.query")
    @ResponseBody
    public JsonResult<List<CoreRole>> all() {
        List<CoreRole> list = roleConsoleService.queryAllPermissionList();
        return JsonResult.success(list);
    }

    /**
     * 获取列表查询条件
     * 
     * @return
     */
    @PostMapping(MODEL + "/list/condition.json")
    @Function("role.query")
    @ResponseBody
    public JsonResult<List<Map<String, Object>>> listCondtion() {
        List<Map<String, Object>> list = AnnotationUtil.getInstance().getAnnotations(Query.class, RoleQuery.class);
        return JsonResult.success(list);
    }

    /**
     * 保存
     * 
     * @return
     */
    @PostMapping(MODEL + "/add.json")
    @Function("role.add")
    @ResponseBody
    public JsonResult addRole(@Validated(ValidateConfig.ADD.class) CoreRole role) {
        CoreRole role1 = roleConsoleService.queryByCode(role.getCode());
        if (role1 != null) {
            return JsonResult.failMessage("用户编号已存在");
        }
        JsonResult result = new JsonResult();
        role.setCreateTime(new Date());
        roleConsoleService.save(role);
        platformService.clearFunctionCache();
        return result.success();
    }

    /**
     * 更新
     * 
     * @param role
     * @return
     */
    @PostMapping(MODEL + "/update.json")
    @Function("role.edit")
    @ResponseBody

    public JsonResult<String> update(@Validated(ValidateConfig.UPDATE.class) CoreRole role) {

        boolean success = roleConsoleService.update(role);

        if (success) {
            platformService.clearFunctionCache();
            return new JsonResult().success();
        } else {
            return JsonResult.failMessage("保存失败");
        }
    }

    /**
     * 查询角色信息
     * 
     * @param id
     * @return
     */
    @GetMapping(MODEL + "/view.json")
    @Function("role.query")
    @ResponseBody
    public JsonResult<CoreRole> queryInfo(Long id) {
        CoreRole role = roleConsoleService.queryById(id);
        return JsonResult.success(role);
    }

    /**
     * (批量)删除
     * 
     * @param ids
     *            角色id
     * @return
     */
    @PostMapping(MODEL + "/delete.json")
    @Function("role.delete")
    @ResponseBody
    public JsonResult delete(String ids) {
        if (ids.endsWith(",")) {
            ids = StringUtils.substringBeforeLast(ids, ",");
        }

        List<Long> idList = ConvertUtil.str2longs(ids);
        roleConsoleService.deleteById(idList);
        return new JsonResult().success();
    }

    /**
     * 查询角色下授权用户列表
     * 
     * @param queryCondtion
     *            查询条件
     * @return
     */
    @PostMapping(MODEL + "/user/list.json")
    @Function("role.user.query")
    @ResponseBody
    public JsonResult<PageQuery<CoreUser>> userList(RoleUserQuery query) {
        PageQuery page = query.getPageQuery();
        PageQuery<CoreUser> pageQuery = roleConsoleService.queryRoleUser(page);
        return JsonResult.success(page);
    }

    // /**
    // * 给角色添加用户
    // * @param userRole 角色用户关系
    // * @return
    // */
    // @PostMapping(MODEL + "/user/save.json")
    // @Function("role.user.save")
    // @ResponseBody
    // public JsonResult saveRoleUser(CoreUserRole userRole) {
    // userRole.setCreateTime(new Date());
    // userRoleConsoleService.saveSysUserRole(userRole);
    // platformService.clearFunctionCache();
    // return JsonResult.success();
    // }
    //
    //
    // /**
    // * 用户授权删除
    // * @param ids 记录id
    // * @return
    // */
    // @GetMapping(MODEL + "/user/delete.json")
    // @Function("role.user.delete")
    // @ResponseBody
    // public Object deleteRoleUser(String ids) {
    // List<Long> dels = ConvertUtil.str2longs(ids);
    // userRoleConsoleService.deleteUserRoles(dels);
    // platformService.clearFunctionCache();
    // return JsonResult.success();
    // }

    @PostMapping(MODEL + "/function/ids.json")
    @Function("role.function.query")
    @ResponseBody
    public JsonResult<List<Long>> getFunctionIdByRole(Long roleId) {
        List<Long> list = functionConsoleService.getFunctionByRole(roleId);
        return JsonResult.success(list);
    }

    @GetMapping(MODEL + "/function/queryFunction.json")
    @Function("role.function.query")
    @ResponseBody
    public JsonResult<List<RoleDataAccessFunction>> getQueryFunctionByRole(Long roleId) {
        List<RoleDataAccessFunction> list = functionConsoleService.getQueryFunctionByRole(roleId);
        return JsonResult.success(list);
    }

    @PostMapping(MODEL + "/function/update.json")
    @Function("role.function.edit")
    @ResponseBody
    public JsonResult updateFunction(Long roleId, String ids) {
        List<Long> all = ConvertUtil.str2longs(ids);
        List<Long> addIds = new ArrayList<Long>();
        List<Long> delIds = new ArrayList<Long>();
        List<Long> dbs = functionConsoleService.getFunctionByRole(roleId);
        Iterator<Long> it = all.iterator();
        for(Long id:all) {
            if(!dbs.contains(id)) {
                addIds.add(id);
            }
        }
        
        for(Long id:dbs) {
            if(!all.contains(id)) {
                delIds.add(id);
            }
        }
        functionConsoleService.updateSysRoleFunction(roleId, addIds, delIds);
        return JsonResult.success();
    }

    @PostMapping(MODEL + "/function/updateDataAccess.json")
    @Function("role.function.updateDataAccess")
    @ResponseBody
    public JsonResult updateFunctionDataAccess(Long roleId,Long fnId,Integer accessType) {
        RoleDataAccessFunction data = new RoleDataAccessFunction();
        data.setRoleId(roleId);
        data.setId(fnId);
        data.setDataAccessType(accessType);
        functionConsoleService.updateFunctionAccessByRole(Arrays.asList(data));
        return JsonResult.success();
    }
    
    /*后端模板渲染*/
    @PostMapping(MODEL + "/function/dataAccess.do")
    @Function("role.function.updateDataAccess")
    public ModelAndView datapage(Long roleId) {
        List<RoleDataAccessFunction> list =  functionConsoleService.getQueryFunctionByRole(roleId);
        ModelAndView view = new ModelAndView("/admin/role/dataConfigPart.html");
        view.addObject("list", list);
        return view;
    }

}
