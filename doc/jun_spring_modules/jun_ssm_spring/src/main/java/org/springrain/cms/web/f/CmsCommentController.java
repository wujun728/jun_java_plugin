package  org.springrain.cms.web.f;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.property.MessageUtils;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfigService;
import org.springrain.weixin.sdk.mp.api.IWxMpUserService;
import org.springrain.weixin.sdk.mp.bean.result.WxMpUser;


/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2017-02-21 16:32:10
 * @see org.springrain.cms.base.web.CmsComment
 */
@Controller
@RequestMapping(value="/f/{siteType}/{siteId}/{businessId}/comment")
public class CmsCommentController  extends BaseController {
	@Resource
	private ICmsCommentService cmsCommentService;
	@Resource
	IWxMpUserService wxMpUserService;
	@Resource
	IWxMpConfigService wxMpConfigService;
	private String listurl="/base/cmscomment/cmscommentList";
	
	
	   
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param cmsComment
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,CmsComment cmsComment) 
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, cmsComment);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param cmsComment
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	@ResponseBody 
	public ReturnDatas listjson(HttpServletRequest request, Model model,CmsComment cmsComment) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		List<CmsComment> datas=cmsCommentService.findListDataByFinder(null,page,CmsComment.class,cmsComment);
			returnObject.setQueryBean(cmsComment);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}
	
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,HttpServletResponse response, Model model,CmsComment cmsComment) throws Exception{
		// ==构造分页请求
		Page page = newPage(request);
	
		File file = cmsCommentService.findDataExportExcel(null,listurl, page,CmsComment.class,cmsComment);
		String fileName="cmsComment"+GlobalStatic.excelext;
		downFile(response, file, fileName,true);
		return;
	}
	
		/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/base/cmscomment/cmscommentLook";
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
		  CmsComment cmsComment = cmsCommentService.findCmsCommentById(id);
		   returnObject.setData(cmsComment);
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
	public ReturnDatas saveorupdate(Model model,CmsComment cmsComment,HttpServletRequest request,HttpServletResponse response,@PathVariable String siteId,@PathVariable String businessId) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			cmsComment.setSiteId(siteId);
			cmsComment.setBusinessId(businessId);
			java.lang.String id =cmsComment.getId();
			if(StringUtils.isBlank(id)){
			  cmsComment.setId(null);
			}
			/*String filePath = GlobalStatic.rootDir+"upload/sensitiveWords.txt";
			cmsComment.setContent(SensitiveWordUtils.validateBanWords(cmsComment.getContent(), filePath));*/
			if(cmsComment.getCreateDate() == null) {
                cmsComment.setCreateDate(new Date());
            }
			if(cmsComment.getType() == null) {
                cmsComment.setType(1);
            }
			cmsCommentService.saveorupdate(cmsComment);
			
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
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/base/cmscomment/cmscommentCru";
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
				cmsCommentService.deleteById(id,CmsComment.class);
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
			cmsCommentService.deleteByIds(ids,CmsComment.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
	}
	
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/attach/update")
	@ResponseBody 
	public ReturnDatas attachSaveorupdate(Model model,CmsComment cmsComment,HttpServletRequest request,HttpServletResponse response,@PathVariable String siteType,@PathVariable String siteId,@PathVariable String businessId) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			cmsComment.setSiteId(siteId);
			cmsComment.setBusinessId(businessId);
			java.lang.String id =cmsComment.getId();
			if(StringUtils.isBlank(id)){
			  cmsComment.setId(null);
			}
			/*String filePath = GlobalStatic.rootDir+"upload/sensitiveWords.txt";
			cmsComment.setContent(SensitiveWordUtils.validateBanWords(cmsComment.getContent(), filePath));*/
			if(StringUtils.isNotBlank(cmsComment.getBak1())){
				String filePath = cmsCommentService.saveWechatFile(cmsComment.getBak1(),siteType,siteId,businessId);
				cmsComment.setBak1(filePath);
			}
			if(StringUtils.isNotBlank(cmsComment.getBak2())){
				String filePath = cmsCommentService.saveWechatFile(cmsComment.getBak2(),siteType,siteId,businessId);
				cmsComment.setBak2(filePath);
			}
			
			
			if(cmsComment.getCreateDate() == null) {
                cmsComment.setCreateDate(new Date());
            }
			if(cmsComment.getType() == null) {
                cmsComment.setType(1);
            }
			cmsCommentService.saveorupdate(cmsComment);
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	
	}
	
	/**
	 * 微信用户评论（对内容的评论）
	 * @param req
	 * @param res
	 * @param model
	 * @param siteId
	 * @param businessId
	 * @return
	 */
	@RequestMapping("/ajax/commentFromWeiXin")
	@ResponseBody
	public ReturnDatas commentFromWeiXin(HttpServletRequest req,HttpServletResponse res,Model model,@PathVariable String siteId,
			@PathVariable String businessId,CmsComment cmsComment){
		ReturnDatas rd = ReturnDatas.getSuccessReturnDatas();
		try {
			String openId = String.valueOf(req.getSession().getAttribute("openId"));
			
			if(StringUtils.isEmpty(openId)){
				return new ReturnDatas(ReturnDatas.ERROR, "无法获取微信用户ID！");
			}
			
			// 获取用户信息
			IWxMpConfig wxmpconfig = wxMpConfigService.findWxMpConfigById(siteId);
			WxMpUser wxMpUser = wxMpUserService.userInfo(wxmpconfig, openId);
			if(wxMpUser == null){
				return new ReturnDatas(ReturnDatas.ERROR, "无法获取微信用户信息！");
			}
			if(StringUtils.isEmpty(siteId)){
				return new ReturnDatas(ReturnDatas.ERROR, "无法获取站点信息！");
			}
			if(StringUtils.isEmpty(businessId)){
				return new ReturnDatas(ReturnDatas.ERROR, "无法获取业务信息！");
			}
			cmsComment.setUserId(wxMpUser.getOpenId());
			cmsComment.setUserName(wxMpUser.getNickname());
			cmsComment.setSiteId(siteId);
			cmsComment.setBusinessId(businessId);
			cmsComment.setCreateDate(new Date());
			cmsComment.setType(1); // 对内容的直接评价（满意、不满意、基本                        
			
			cmsCommentService.saveorupdate(cmsComment);
			
			rd.setMessage(MessageUtils.ADD_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rd = new ReturnDatas(ReturnDatas.ERROR, MessageUtils.ADD_FAIL);
		}
		return rd;
	}
}
