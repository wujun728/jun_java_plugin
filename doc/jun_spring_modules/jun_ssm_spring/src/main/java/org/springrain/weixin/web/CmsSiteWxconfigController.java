package  org.springrain.weixin.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.cms.service.ICmsSiteService;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.property.MessageUtils;
import org.springrain.weixin.entity.WxMenu;
import org.springrain.weixin.entity.WxMpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfigService;
import org.springrain.weixin.service.IWxMenuService;
import org.springrain.weixin.service.IWxMpServletService;


/**
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2017-02-06 11:38:43
 * @see org.springrain.cms.base.web.WxMpConfig
 */
@Controller
@RequestMapping(value="/system/cms/wxconfig")
public class CmsSiteWxconfigController  extends BaseController {
	@Resource
	private IWxMpConfigService wxMpConfigService;
	
	@Resource
	private IWxMpServletService wxMpServletService;
	
	@Resource
	private ICmsSiteService cmsSiteService;
	@Resource
	private IWxMenuService wxMenuService;
	private String listurl="/weixin/mp/conf/confList";

	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param WxMpConfig
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,WxMpConfig wxMpConfig) 
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, wxMpConfig);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param WxMpConfig
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	@ResponseBody 
	public ReturnDatas listjson(HttpServletRequest request, Model model,WxMpConfig wxMpConfig) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		List<WxMpConfig> datas=wxMpServletService.findListDataByFinder(null,page,WxMpConfig.class,wxMpConfig);
		returnObject.setQueryBean(wxMpConfig);
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
		return "/weixin/mp/conf/confLook";
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
		  IWxMpConfig WxMpConfig = wxMpConfigService.findWxMpConfigById(id);
		   returnObject.setData(WxMpConfig);
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
	public ReturnDatas saveorupdate(Model model,WxMpConfig wxMpConfig,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
		
			java.lang.String id =wxMpConfig.getId();
			if(StringUtils.isBlank(id)){
				wxMpConfig.setId(wxMpConfig.getSiteId());
				wxMpServletService.saveWxMpConfig(wxMpConfig);
			} else {
				wxMpServletService.updateWxMpConfig(wxMpConfig);
			}
		
			//wxMpServletService.saveorupdate(wxMpConfig);
			
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
	public String updatepre(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		ReturnDatas returnObject = lookjson(model, request, response);
		Map<String, Object> map = new HashMap<>();
		map.put("siteList", cmsSiteService.findMpSiteByUserId(SessionUser.getUserId()));
		returnObject.setMap(map);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/weixin/mp/conf/confCru";
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="/delete")
	@ResponseBody 
	public  ReturnDatas delete(HttpServletRequest request) throws Exception {

			// 执行删除
		try {
		java.lang.String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			wxMpServletService.deleteById(id,WxMpConfig.class);
				return new ReturnDatas(ReturnDatas.SUCCESS,
						MessageUtils.DELETE_SUCCESS);
			} else {
				return new ReturnDatas(ReturnDatas.WARNING,
						MessageUtils.DELETE_WARNING);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ReturnDatas(ReturnDatas.WARNING, MessageUtils.DELETE_WARNING);
	}
	
	/**
	 * 删除多条记录
	 * 
	 */
	@RequestMapping("/delete/more")
	@ResponseBody 
	public ReturnDatas deleteMore(HttpServletRequest request, Model model) {
		String records = request.getParameter("records");
		if(StringUtils.isBlank(records)){
			 return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_NULL_FAIL);
		}
		try {
			List<String> ids = Arrays.asList(rs);
			wxMpServletService.deleteByIds(ids,WxMpConfig.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
	}
	
	/**
	 * 映射菜单管理列表页面
	 * @throws Exception 
	 * */
	@RequestMapping("/mpList")
	public String mpList(HttpServletRequest request, Model model,WxMpConfig WxMpConfig) throws Exception{
		ReturnDatas returnObject = listjson(request, model, WxMpConfig);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/weixin/mp/menu/mpList";
	}
	
	/**
	 * 映射菜单设置页面
	 * @throws Exception
	 * */
	@RequestMapping("/menu/update/pre")
	public String menuUpdate(HttpServletRequest request, Model model,WxMpConfig WxMpConfig) throws Exception{
		ReturnDatas returnDatas=ReturnDatas.getSuccessReturnDatas();
		
		String siteId = WxMpConfig.getSiteId();
		List<WxMenu> datas = wxMenuService.findParentMenuList(siteId);
		if (datas != null){
			for (WxMenu cmsWxMenu : datas) {
				cmsWxMenu.setChildMenuList(wxMenuService.findChildMenuListByPid(cmsWxMenu.getId()));
			}
		}
		returnDatas.setData(datas);
		model.addAttribute(GlobalStatic.returnDatas, returnDatas);
		return "/weixin/mp/menu/menuCru";
	}
}
