package  org.springrain.system.web;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.property.MessageUtils;
import org.springrain.system.entity.Org;
import org.springrain.system.service.IOrgService;
import org.springrain.system.service.IUserOrgService;


/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author weicms.net<Auto generate>
 * @version  2013-07-29 11:36:46
 * @see org.springrain.springrain.web.Org
 */
@Controller
@RequestMapping(value="/system/org")
public class OrgController  extends BaseController {
	
	@Resource
	private IOrgService orgService;
	@Resource
	private IUserOrgService userOrgService;
	
	private String listurl="/system/org/orgList";
	
	/** binder用于bean属性的设置 */
	@Override
    @InitBinder  
	public void initBinder(WebDataBinder binder) {
	          super.initBinder(binder);
	}
	   
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,Org org) 
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, org);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	@ResponseBody 
	public ReturnDatas listjson(HttpServletRequest request, Model model,Org org) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		//Page page = newPage(request);
		Page page = null;
		// ==执行分页查询
		List<Org> datas=orgService.findListDataByFinder(null,page,Org.class,org);
			returnObject.setQueryBean(org);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}
	

	
	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/org/orgLook";
	}

	
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	@ResponseBody 
	public ReturnDatas lookjson(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		java.lang.String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
		  Org org = orgService.findOrgById(id);
		   returnObject.setData(org);
		}else{
		returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
		
	}
	
	
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	@ResponseBody 
	public ReturnDatas saveorupdate(Model model,Org org,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		
			try {
				String id=org.getId();
				String pid=org.getPid();
				if(StringUtils.isBlank(pid)){
					org.setPid(null);
				}
				
				if(StringUtils.isBlank(id)){
					org.setId(null);
					orgService.saveOrg(org);
				}else{
					orgService.updateOrg(org);
				}
				
			
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
		
	
		 
		
	}
	
	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre")
	public String edit(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/org/orgCru";
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="/delete")
	@ResponseBody 
	public  ReturnDatas destroy(HttpServletRequest request) throws Exception {
		ReturnDatas errorReturnDatas=ReturnDatas.getErrorReturnDatas();
			// 执行删除
		try {
		java.lang.String id=request.getParameter("id");
		
		if(StringUtils.isBlank(id)){
			errorReturnDatas.setMessage("部门Id不能为空!");
			return errorReturnDatas;
		}
		Integer userCountByOrgId = userOrgService.findAllUserCountByOrgId(id);
		if(userCountByOrgId!=null&&userCountByOrgId>0){
			errorReturnDatas.setMessage("部门下还有人员,不能删除!");
			return errorReturnDatas;
		}
		  orgService.deleteOrgById(id);
				
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return errorReturnDatas;
		}
		return ReturnDatas.getSuccessReturnDatas();
	}
	
	
}
