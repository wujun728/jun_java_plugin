package org.springrain.cms.web.s;

import java.util.Arrays;
import java.util.Date;
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
import org.springrain.cms.entity.CmsComment;
import org.springrain.cms.service.ICmsCommentService;
import org.springrain.frame.util.Enumerations;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.property.MessageUtils;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfigService;
import org.springrain.weixin.sdk.mp.api.IWxMpUserService;
import org.springrain.weixin.sdk.mp.bean.result.WxMpUser;

@Controller
@RequestMapping(value="/s/{siteId}/{businessId}/comment")
public class CommentController extends SiteBaseController {
	
	@Resource
	private ICmsCommentService cmsCommentService;
	
	
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request, Model model,@PathVariable String siteId,@PathVariable String businessId,CmsComment cmsComment) throws Exception{
		ReturnDatas returnDatas = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		returnDatas.setPage(page);
		returnDatas.setQueryBean(cmsComment);
		model.addAttribute(GlobalStatic.returnDatas, returnDatas);
		return jump(siteId, businessId,Enumerations.CmsLinkModeType.站长后台列表.getType(), request, model);
	}
	
	
	
	@RequestMapping(value = "/update/pre")
	public String updatepre(Model model,HttpServletRequest request,HttpServletResponse response,@PathVariable String siteId,@PathVariable String businessId,CmsComment cmsComment) throws Exception{
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
	public ReturnDatas saveorupdate(Model model,CmsComment cmsComment,HttpServletRequest request,HttpServletResponse response,@PathVariable String siteId,@PathVariable String businessId) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		cmsComment.setSiteId(siteId);
		cmsComment.setBusinessId(businessId);
		try {
			java.lang.String id =cmsComment.getId();
			if(StringUtils.isBlank(id)){
				cmsComment.setId(null);
			  	cmsCommentService.save(cmsComment);
			}else{
				cmsCommentService.update(cmsComment);
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
			CmsComment cmsComment = cmsCommentService.findCmsCommentById(id);
			Map<String, Object> map = new HashMap<String, Object>();
			returnObject.setMap(map);
			returnObject.setData(cmsComment);
		}
		return returnObject;
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="/delete")
	@ResponseBody 
	public  ReturnDatas delete(HttpServletRequest request,@PathVariable String siteId,@PathVariable String businessId) throws Exception {

		// 执行删除
		try {
			java.lang.String id=request.getParameter("id");
			if(StringUtils.isNotBlank(id)){
				cmsCommentService.deleteCommentById(id,siteId,businessId);
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
	public ReturnDatas deleteMore(HttpServletRequest request, Model model,@PathVariable String siteId,@PathVariable String businessId) {
		String records = request.getParameter("records");
		if(StringUtils.isBlank(records)){
			 return new ReturnDatas(ReturnDatas.ERROR,MessageUtils.DELETE_ALL_FAIL);
		}
		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR,MessageUtils.DELETE_NULL_FAIL);
		}
		try {
			List<String> ids = Arrays.asList(rs);
			cmsCommentService.deleteByCommentIds(ids,siteId,businessId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
	}
	
	
}
