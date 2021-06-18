package com.ibeetl.admin.console.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.ibeetl.admin.console.service.MenuConsoleService;
import com.ibeetl.admin.console.web.query.FunctionQuery;
import com.ibeetl.admin.console.web.query.MenuQuery;
import com.ibeetl.admin.core.annotation.Function;
import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.entity.CoreFunction;
import com.ibeetl.admin.core.entity.CoreMenu;
import com.ibeetl.admin.core.rbac.tree.MenuItem;
import com.ibeetl.admin.core.service.CorePlatformService;
import com.ibeetl.admin.core.util.AnnotationUtil;
import com.ibeetl.admin.core.util.ConvertUtil;
import com.ibeetl.admin.core.web.JsonResult;

/**
 * @author Wujun
 */
@Controller
public class MenuController {


    private static final String MODEL = "/admin/menu";
    private final Log log = LogFactory.getLog(this.getClass());


    @Autowired
    MenuConsoleService menuService;

    @Autowired
    CorePlatformService platformService;

    /*页面*/
    
    @GetMapping(MODEL + "/index.do")
    @Function("menu")
    public ModelAndView index() {
		ModelAndView view = new ModelAndView("/admin/menu/index.html");
		view.addObject("search", MenuQuery.class.getName());
        return view;
    }
    
    @GetMapping(MODEL + "/add.do")
    @Function("menu.add")
    public ModelAndView add() {
   	 ModelAndView view = new ModelAndView("/admin/menu/add.html");
   	 return view;
    }
    @GetMapping(MODEL + "/edit.do")
    @Function("menu.edit")
    public ModelAndView edit(Integer id) {
   	 ModelAndView view = new ModelAndView("/admin/menu/edit.html");
   	 CoreMenu menu = menuService.queryById(id);
     view.addObject("menu", menu);
   	 return view;
    }
    
    /*Json*/
    
    /**
     * 查询
     * @param menu
     * @return
     */
    @PostMapping(MODEL + "/list/condition.json")
    @Function("menu.query")
    @ResponseBody
    public JsonResult condition() {
    	   List<Map<String, Object>> list = AnnotationUtil.getInstance().getAnnotations(Query.class, MenuQuery.class);
       return JsonResult.success(list);
    }
    
    
    @PostMapping(MODEL + "/list.json")
    @Function("menu.query")
    @ResponseBody
    public JsonResult<PageQuery> list(MenuQuery condtion) {
        PageQuery page = condtion.getPageQuery();
        menuService.queryByCondtion(page);
        return JsonResult.success(page);
    }
    

    /**
     * 添加
     * @param menu
     * @return
     */
    @PostMapping(MODEL + "/save.json")
    @Function("menu.save")
    @ResponseBody
    public JsonResult save(@Validated CoreMenu menu) {
        menu.setCreateTime(new Date());
        Long id = menuService.saveMenu(menu);
        return JsonResult.success(id);
    }

    /**
     * 更新
     * @param fun
     * @return
     */
    @PostMapping(MODEL + "/update.json")
    @Function("menu.update")
    @ResponseBody
    public JsonResult update(CoreMenu fun) {
        menuService.updateMenu(fun);
        return new JsonResult().success();
    }

    /**
     * 根据id查询菜单信息
     * @param id 菜单Id
     * @return
     */
    @PostMapping(MODEL + "/view.json")
    @Function("menu.query")
    @ResponseBody
    public JsonResult<CoreMenu> view(Long id) {
        CoreMenu fun = menuService.queryById(id);
        MenuItem root =  this.platformService.buildMenu();
        MenuItem child =  root.findChild(fun.getId());
        CoreMenu parent = child.getParent().getData();
        fun.set("parentMenuName", parent.getName());
        return JsonResult.success(fun);
    }

    /**
     * 删除
     * @param id 菜单id
     * @return
     */
    @PostMapping(MODEL + "/delete.json")
    @Function("menu.delete")
    @ResponseBody
    public JsonResult delete(Long id) {
        menuService.deleteMenu(id);
        return new JsonResult().success();
    }
    
    /**
     * 批量删除
     * @param ids 菜单id集合
     * @return
     */
    @PostMapping(MODEL + "/batchDel.json")
    @Function("menu.delete")
    @ResponseBody
    public JsonResult delete(String ids) {
    	List<Long> dels = ConvertUtil.str2longs(ids);
    	menuService.batchDeleteMenuId(dels);
    	return new JsonResult().success();
    }
    
    


}


