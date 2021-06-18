package com.ibeetl.admin.console.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibeetl.admin.console.service.OrgConsoleService;
import com.ibeetl.admin.console.service.UserConsoleService;
import com.ibeetl.admin.console.web.query.OrgQuery;
import com.ibeetl.admin.console.web.query.OrgUserQuery;
import com.ibeetl.admin.core.annotation.Function;
import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.entity.CoreOrg;
import com.ibeetl.admin.core.entity.CoreUser;
import com.ibeetl.admin.core.service.CorePlatformService;
import com.ibeetl.admin.core.util.AnnotationUtil;
import com.ibeetl.admin.core.util.ConvertUtil;
import com.ibeetl.admin.core.util.ValidateConfig;
import com.ibeetl.admin.core.web.JsonResult;

/**
 * 描述: 组织机构 controller
 * @author Wujun
 */
@Controller
public class OrgConsoleController {
    private final Log log = LogFactory.getLog(this.getClass());
    private static final String MODEL = "/admin/org";

    @Autowired
    private OrgConsoleService orgConsoleService;
    
    @Autowired
    UserConsoleService userConsoleService;

    @Autowired
    CorePlatformService platformService;
    
    
    
    /*页面*/
    
    @GetMapping(MODEL + "/index.do")
    @Function("org.query")
    public ModelAndView index() {
		ModelAndView view = new ModelAndView("/admin/org/index.html");
		view.addObject("search", OrgQuery.class.getName());
        return view;
    }
    
    @GetMapping(MODEL + "/edit.do")
    @Function("org.edit")
    public ModelAndView edit(String id) {
    	ModelAndView view = new ModelAndView("/admin/org/edit.html");
        CoreOrg org = orgConsoleService.queryById(id);
        view.addObject("org", org);
        return view;
    }


    @GetMapping(MODEL + "/add.do")
    @Function("org.add")
    public ModelAndView add() {
        ModelAndView view = new ModelAndView("/admin/org/add.html");

        return view;
    }
    
    
    @GetMapping(MODEL + "/user/list.do")
    @Function("org.query")
    public ModelAndView  getUsers(Long orgId) {
    	ModelAndView view = new ModelAndView("/admin/org/orgUser.html");
        CoreOrg org = orgConsoleService.queryById(orgId);
        view.addObject("org", org);
        view.addObject("search",OrgUserQuery.class.getName());
        return view;
    }
    
    /**
     * 组织机构列表  分页
     * @param condtion 查询条件
     * @return
     */
    @PostMapping(MODEL + "/list.json")
    @Function("org.query")
    @ResponseBody
    public JsonResult<PageQuery<CoreOrg>> list(OrgQuery condtion) {
        PageQuery page = condtion.getPageQuery();
        orgConsoleService.queryByCondtion(page);
        return JsonResult.success(page);
    }

    /**
     * 获取列表查询条件
     * @return
     */
    @PostMapping(MODEL + "/list/condition.json")
    @Function("org.query")
    @ResponseBody
    public JsonResult<List<Map<String, Object>>> listCondtion() {
        List<Map<String, Object>> list = AnnotationUtil.getInstance().getAnnotations(Query.class, OrgQuery.class);
        return JsonResult.success(list);
    }

    /**
     * 保存数据
     * @param org
     * @return
     */
    @PostMapping(MODEL + "/save.json")
    @Function("org.save")
    @ResponseBody
    public JsonResult<Long> save(@Validated(ValidateConfig.ADD.class) CoreOrg org, BindingResult result) {
        if (result.hasErrors()) {
        		return JsonResult.failMessage(result.toString());
           
        }

        org.setCreateTime(new Date());
        orgConsoleService.save(org);
        platformService.clearOrgCache();
        return JsonResult.success(org.getId());
    }


    /**
     * 更新数据
     * @param org
     * @return
     */
    @PostMapping(MODEL + "/update.json")
    @Function("org.update")
    @ResponseBody
    public JsonResult<String> update(@Validated(ValidateConfig.UPDATE.class) CoreOrg org) {
        boolean success = orgConsoleService.updateTemplate(org);
        if (success) {
        	platformService.clearOrgCache();
        	return JsonResult.successMessage("保存成功");
        } else {
            return JsonResult.failMessage("保存失败");
        }
    }


   


    /**
     * 删除组织机构
     * @param ids 组织id，多个用“,”隔开
     * @return
     */
    @PostMapping(MODEL + "/delete.json")
    @Function("org.delete")
    @ResponseBody
    public JsonResult delete(String ids) {
        if (ids.endsWith(",")) {
            ids = StringUtils.substringBeforeLast(ids, ",");
        }
        List<Long> idList = ConvertUtil.str2longs(ids);
        orgConsoleService.deleteById(idList);
        this.platformService.clearOrgCache();
        return new JsonResult().success();
    }

    
    @PostMapping(MODEL + "/user/list.json")
    @Function("org.query")
    @ResponseBody
    public JsonResult<PageQuery<CoreUser>> getUsers(OrgUserQuery userQuery) {
    	 PageQuery<CoreUser> page = userQuery.getPageQuery();
         orgConsoleService.queryUserByCondition(page);
         return JsonResult.success(page);
    }
   
}
