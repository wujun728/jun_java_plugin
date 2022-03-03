package org.springrain.cms.web.s;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.cms.entity.CmsContent;
import org.springrain.cms.service.ICmsContentService;
import org.springrain.cms.service.ICmsLinkService;
import org.springrain.cms.util.SiteUtils;
import org.springrain.frame.util.Enumerations;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.InputSafeUtils;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.property.MessageUtils;

@Controller
@RequestMapping(value="/s/{siteId}/{businessId}/content")
public class ContentController extends SiteBaseController{
	@Resource
	private ICmsLinkService cmsLinkService;
	@Resource
	private ICmsContentService cmsContentService;
	
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request, Model model,@PathVariable String siteId,@PathVariable String businessId,CmsContent cmsContent) throws Exception{
		ReturnDatas returnDatas = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		returnDatas.setPage(page);
		returnDatas.setQueryBean(cmsContent);
		model.addAttribute(GlobalStatic.returnDatas, returnDatas);
		return jump(siteId, businessId,Enumerations.CmsLinkModeType.站长后台列表.getType(), request, model);
	}
	
	
	
	@RequestMapping(value = "/update/pre")
	public String updatepre(Model model,HttpServletRequest request,HttpServletResponse response,@PathVariable String siteId,@PathVariable String businessId,CmsContent cmsContent) throws Exception{
		String id = request.getParameter("id");
		model.addAttribute("id", id);
		return jump(siteId,businessId,Enumerations.CmsLinkModeType.站长后台更新.getType(),request,model);
	}
	
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	@ResponseBody 
	public ReturnDatas saveorupdate(Model model,CmsContent cmsContent,HttpServletRequest request,HttpServletResponse response,@PathVariable String siteId,@PathVariable String businessId) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		cmsContent.setSiteId(siteId);
		cmsContent.setChannelId(businessId);
		String content=cmsContent.getContent();
		cmsContent.setContent(InputSafeUtils.filterRichTextContent(content, SiteUtils.getBaseURL(request)));
		try {
			java.lang.String id =cmsContent.getId();
			if(StringUtils.isBlank(id)){
				cmsContent.setId(null);
			  	cmsContentService.saveContent(cmsContent);
			}else{
				cmsContentService.updateCmsContent(cmsContent);
			}
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
	public String look(Model model,HttpServletRequest request,HttpServletResponse response,@PathVariable String siteId,@PathVariable String businessId)  throws Exception {
		return jump(siteId,businessId,Enumerations.CmsLinkModeType.站长后台查看.getType(),request,model);
	}
	
	@RequestMapping(value = "/look/json")
	@ResponseBody 
	public ReturnDatas lookjson(Model model,HttpServletRequest request,HttpServletResponse response,@PathVariable String siteId,@PathVariable String businessId) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		java.lang.String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			CmsContent cmsContent = cmsContentService.findCmsContentById(siteId,id);
			Map<String, Object> map = new HashMap<String, Object>();
			returnObject.setMap(map);
			returnObject.setData(cmsContent);
		}
		return returnObject;
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="/delete")
	@ResponseBody 
	public  ReturnDatas delete(HttpServletRequest request,@PathVariable String siteId) throws Exception {

			// 执行删除
		try {
		java.lang.String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
				cmsContentService.deleteById(id,siteId);
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
	public ReturnDatas deleteMore(HttpServletRequest request, Model model,@PathVariable String siteId) {
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
			cmsContentService.deleteByIds(ids,siteId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
	}
	
	/**
	 * 通过ajax获取内容列表
	 * @param request
	 * @param model
	 * @param siteId
	 * @param buinessId
	 * @return
	 */
	@RequestMapping("/ajax/list")
	public String ajaxList(HttpServletRequest request,Model model,CmsContent cmsContent,
			@PathVariable String siteId,@PathVariable String businessId){
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("orderBy",request.getParameter("orderBy"));
		paramsMap.put("pageSize", request.getParameter("pageSize"));
//		paramsMap.put("returnDatas.page", newPage(request));
		model.addAttribute("paramsMap", paramsMap);
		
		ReturnDatas returnDatas = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		returnDatas.setPage(page);
		returnDatas.setQueryBean(cmsContent);
		model.addAttribute(GlobalStatic.returnDatas, returnDatas);
		return "/u/" + siteId + "/f/activitySummary/activitySummaryListAjax";
	}
}
