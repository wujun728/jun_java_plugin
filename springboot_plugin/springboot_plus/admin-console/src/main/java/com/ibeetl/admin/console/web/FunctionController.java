package com.ibeetl.admin.console.web;

import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibeetl.admin.console.service.FunctionConsoleService;
import com.ibeetl.admin.console.web.query.FunctionQuery;
import com.ibeetl.admin.core.annotation.Function;
import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.entity.CoreFunction;
import com.ibeetl.admin.core.rbac.tree.FunctionItem;
import com.ibeetl.admin.core.service.CorePlatformService;
import com.ibeetl.admin.core.util.AnnotationUtil;
import com.ibeetl.admin.core.util.ConvertUtil;
import com.ibeetl.admin.core.util.FormFieldException;
import com.ibeetl.admin.core.util.PlatformException;
import com.ibeetl.admin.core.util.ValidateConfig;
import com.ibeetl.admin.core.web.JsonResult;
import com.ibeetl.admin.core.web.dto.FunctionNodeView;

/**
 * 描述:  功能点管理
 *
 * @author Wujun
 */
@Controller
public class FunctionController {
	
	private  final Log log  = LogFactory.getLog(this.getClass());

    private static final String MODEL = "/admin/function";
    @Autowired
    CorePlatformService platformService;
    @Autowired
    private FunctionConsoleService functionConsoleService;

    
    /*页面*/
     
     @GetMapping(MODEL + "/index.do")
     @Function("function")
     public ModelAndView index() {
 		ModelAndView view = new ModelAndView("/admin/function/index.html");
 		view.addObject("search", FunctionQuery.class.getName());
         return view;
     }
     
     @GetMapping(MODEL + "/add.do")
     @Function("function.add")
     public ModelAndView add() {
    	 ModelAndView view = new ModelAndView("/admin/function/add.html");
    	 return view;
     }
     @GetMapping(MODEL + "/edit.do")
     @Function("function.edit")
     public ModelAndView edit(Integer id) {
    	 ModelAndView view = new ModelAndView("/admin/function/edit.html");
    	 CoreFunction function = functionConsoleService.queryById(id);
         view.addObject("function", function);
    	 return view;
     }
     
     /*Json*/
     
    @RequestMapping(MODEL + "/add.json")
    @Function("function.add")
    @ResponseBody
    public JsonResult<CoreFunction> addFunction(@Validated(ValidateConfig.ADD.class) CoreFunction function) {
		String code = function.getCode();
		CoreFunction dbFunction = functionConsoleService.getFunction(code);
		if(dbFunction!=null){
			throw new FormFieldException(CoreFunction.class.getName(),"code","已经存在");
		}
		
		if(function.getParentId()==null){
			function.setParentId(0l);
		}
		function.setCreateTime(new Date());
		functionConsoleService.saveFunction(function);
		return JsonResult.success(function);
    }
    
    
    @RequestMapping(MODEL + "/update.json")
    @Function("function.update")
    @ResponseBody
    public JsonResult<?> updateFunction(@Validated(ValidateConfig.UPDATE.class) CoreFunction function) {
		CoreFunction dbFunction = functionConsoleService.getFunction(function.getCode());
		if(dbFunction!=null&&!dbFunction.getId().equals(function.getId())){			
			throw new FormFieldException(CoreFunction.class.getName(),"code","已经存在");
		}
		
		if(function.getParentId()==null){
			function.setParentId(0l);
		}
//		function.setCreateTime(dbFunction.getCreateTime());
		functionConsoleService.updateFunction(function);
    	return JsonResult.success();
    }
    
    @RequestMapping(MODEL + "/view.json")
    @Function("function.query")
    @ResponseBody
    public JsonResult<CoreFunction> getFunction(Long id) {
    		CoreFunction function =  functionConsoleService.getFunction(id);

    		if (function.hasParent()){
    			CoreFunction parent = functionConsoleService.getFunction(function.getParentId());
    			function.set("parentName",parent.getName());
			}else {
				function.set("parentName","");
			}
			functionConsoleService.queryEntityAfter(function);
    		return JsonResult.success(function);
    }
    

    @RequestMapping(MODEL + "/delete.json")
    @Function("function.delete")
    @ResponseBody
    public JsonResult deleteFunction(Long id) {
     	CoreFunction fun = functionConsoleService.queryById(id);
        if (fun == null) {        	
            throw new PlatformException("删除失败,没有找到Function "+id+"!");
        }
       //删除功能和所有子功能
       functionConsoleService.deleteFunction(id);
       return new JsonResult().success();
    }
    
    
    /**
     * 字典列表  分页
     *
     * @param condtion
     * @return
     */
    @RequestMapping(MODEL + "/list.json")
    @Function("function.query")
    @ResponseBody
    public JsonResult<PageQuery<CoreFunction>> list(FunctionQuery condtion) {
     
        PageQuery page = condtion.getPageQuery();
        functionConsoleService.queryByCondtion(page);
        return JsonResult.success(page);
     
    }
    
    
    @PostMapping(MODEL + "/list/condition.json")
    @Function("function.query")
    @ResponseBody
    public JsonResult<List<Map<String, Object>>> listCondtion() {
    		List<Map<String, Object>> list = AnnotationUtil.getInstance().getAnnotations(Query.class, FunctionQuery.class);
    		return JsonResult.success(list);
    }
    
  
    private List<Long> findChildFunctionIds(Long funtionId) {
       
        FunctionItem funItem = platformService.buildFunction().findChild(funtionId);
        List<Long> children =  funItem.findAllChildrenId();
        children.add(funtionId);
        return  children;
    }

 


    @RequestMapping(MODEL + "/batchDel.json")
    @Function("function.delete")
    @ResponseBody
    public JsonResult batchDel(String ids) {
    		List<Long> dels = ConvertUtil.str2longs(ids);
        functionConsoleService.batchDeleteFunction(dels);
        return new JsonResult().success();
       
    }
    
    @PostMapping(MODEL + "/tree.json")
    @Function("function.query")
    @ResponseBody
    public JsonResult<List<FunctionNodeView> > tree() {
    		FunctionItem root = this.platformService.buildFunction();
    		List<FunctionNodeView> tree = buildFunctionTree(root);
    		return JsonResult.success(tree);
       
    }
    
    
    private List<FunctionNodeView> buildFunctionTree(FunctionItem node){
  		List<FunctionItem> list = node.getChildren();
      	if(list.size()==0){
      		return Collections.EMPTY_LIST;
      	}
      	List<FunctionNodeView> views = new ArrayList<FunctionNodeView>(list.size());
      	for(FunctionItem item :list){
      		FunctionNodeView view = new FunctionNodeView();
      		view.setCode(item.getData().getCode());
      		view.setName(item.getData().getName());
      		view.setId(item.getData().getId());
      		view.setAccessUrl(item.getData().getAccessUrl());
      		List<FunctionNodeView> children = this.buildFunctionTree(item);
      		view.setChildren(children);
      		views.add(view);
      	}
      	return views;
     }
    

    
}
