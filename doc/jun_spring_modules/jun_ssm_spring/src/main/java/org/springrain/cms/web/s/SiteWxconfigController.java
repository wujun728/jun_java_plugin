package org.springrain.cms.web.s;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.cms.entity.CmsSite;
import org.springrain.cms.service.ICmsSiteService;
import org.springrain.frame.util.Enumerations;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.property.MessageUtils;
import org.springrain.weixin.entity.WxMpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfigService;
import org.springrain.weixin.service.IWxMpServletService;

@Controller
@RequestMapping("/s/{siteId}/{businessId}/mp")
public class SiteWxconfigController extends SiteBaseController {
	@Resource
	//private ICmsSiteWxconfigService wxMpConfigService;
    private IWxMpConfigService wxMpConfigService;
	
	@Resource
	private IWxMpServletService wxMpServletService;
	
	@Resource
	private ICmsSiteService cmsSiteService;

	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param cmsCmsSite
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,WxMpConfig cmsSiteWxconfig,@PathVariable String siteId,@PathVariable String businessId) 
			throws Exception {
		ReturnDatas returnDatas = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		returnDatas.setPage(page);
		returnDatas.setQueryBean(cmsSiteWxconfig);
		model.addAttribute(GlobalStatic.returnDatas, returnDatas);
		return jump(siteId, businessId, Enumerations.CmsLinkModeType.站长后台列表.getType(), request, model);
	}
	
	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre")
	public String updatepre(Model model,HttpServletRequest request,HttpServletResponse response,WxMpConfig cmsSiteWxconfig,@PathVariable String siteId,@PathVariable String businessId)  throws Exception{
		String id = request.getParameter("id");
		model.addAttribute("id", id);
		return jump(siteId, businessId, Enumerations.CmsLinkModeType.站长后台更新.getType(), request, model);
	}
	
	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/menu/update/pre")
	public String menuUpdatepre(Model model,HttpServletRequest request,HttpServletResponse response,WxMpConfig cmsSiteWxconfig,@PathVariable String siteId,@PathVariable String businessId)  throws Exception{
		String id = request.getParameter("id");
		model.addAttribute("id", id);
		return jump(siteId, businessId, Enumerations.CmsLinkModeType.站长后台更新.getType(), request, model);
	}
	
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	@ResponseBody 
	public ReturnDatas saveorupdate(Model model,WxMpConfig cmsSiteWxconfig,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			java.lang.String id =cmsSiteWxconfig.getId();
			if(StringUtils.isBlank(id)){
				cmsSiteWxconfig.setId(null);
			}
			wxMpServletService.saveorupdate(cmsSiteWxconfig);
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}
	
	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model,HttpServletRequest request,HttpServletResponse response,CmsSite cmsSite,@PathVariable String siteId,@PathVariable String businessId)  throws Exception {
		return jump(siteId, businessId, Enumerations.CmsLinkModeType.站长后台查看.getType(), request, model);
	}
}
