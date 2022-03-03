package  org.springrain.cms.web.system;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springrain.cms.entity.CmsLink;
import org.springrain.cms.entity.CmsSite;
import org.springrain.cms.entity.CmsTheme;
import org.springrain.cms.service.ICmsLinkService;
import org.springrain.cms.service.ICmsSiteService;
import org.springrain.cms.service.ICmsThemeService;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.Enumerations;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.property.MessageUtils;


/**
 *  站点管理类
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2017-01-10 15:15:36
 * @see org.springrain.cms.entity.CmsSite
 */
@Controller
@RequestMapping(value="/system/cms/site")
public class CmsSiteController  extends BaseController {
	@Resource
	private ICmsSiteService cmsSiteService;
	@Resource
	private ICmsLinkService cmsLinkService;
	@Resource
	private ICmsThemeService cmsThemeService;
	
	private String listurl="/cms/site/siteList";
	
	
	
	
	   
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
	public String list(HttpServletRequest request, Model model,CmsSite cmsSite) 
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, cmsSite);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param cmsSite
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	@ResponseBody 
	public ReturnDatas listjson(HttpServletRequest request, Model model,CmsSite cmsSite) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		List<CmsSite> datas=cmsSiteService.findListDataByFinder(null,page,CmsSite.class,cmsSite);
		for (CmsSite site : datas) {//站点数量不会太多，目前先用遍历设置link属性
			CmsLink cmsLink = cmsLinkService.findLinkBySiteBusinessIdModelType(site.getId(),site.getId(),Enumerations.CmsLinkModeType.站点.getType());
			if (cmsLink == null) {
				continue;
			}
			site.setLink(cmsLink.getLink());
		}
		returnObject.setQueryBean(cmsSite);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}
	
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,HttpServletResponse response, Model model,CmsSite cmsSite) throws Exception{
		// ==构造分页请求
		Page page = newPage(request);
	
		File file = cmsSiteService.findDataExportExcel(null,listurl, page,CmsSite.class,cmsSite);
		String fileName="CmsSite"+GlobalStatic.excelext;
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
		return "/base/CmsSite/CmsSiteLook";
	}

	
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	@ResponseBody 
	public ReturnDatas lookjson(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Map<String, Object>map=new HashMap<String, Object>();
		java.lang.String id=request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
		  CmsSite CmsSite = cmsSiteService.findCmsSiteById(id);
		   returnObject.setData(CmsSite);
		}else{
		returnObject.setStatus(ReturnDatas.ERROR);
		}
		//主题列表
		CmsTheme theme=new CmsTheme();
		theme.setActive(1);
		List<CmsTheme> themesList=cmsThemeService.getTrueCmsThemes();
		map.put("themesList", themesList);
		returnObject.setMap(map);
		return returnObject;
		
	}
	
	
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	@ResponseBody 
	public ReturnDatas saveorupdate(Model model,CmsSite cmsSite,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
		
			java.lang.String id =cmsSite.getId();
			if(StringUtils.isBlank(id)){
			  cmsSite.setId(null);
			}
		
			cmsSiteService.saveorupdate(cmsSite);
			
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
		return "/cms/site/siteCru";
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
				cmsSiteService.deleteById(id,CmsSite.class);
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
			cmsSiteService.deleteByIds(ids,CmsSite.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
	}
	
	/**
	 * 上传logo
	 * */
	@RequestMapping(value="/logoupload",method=RequestMethod.POST)
	@ResponseBody 
	public  ReturnDatas logoUpload(HttpServletRequest request){
		ReturnDatas returnDatas=ReturnDatas.getSuccessReturnDatas();
		//创建一个通用的多部分解析器
		List<String> logoIdList = new ArrayList<>();
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
	    if(multipartResolver.isMultipart(request)){
	    	 MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
	    	 //取得request中的所有文件名
	    	 Iterator<String> iter = multiRequest.getFileNames();
	    	 while(iter.hasNext()){
	    		 MultipartFile tempFile = multiRequest.getFile(iter.next());
	    		 String logoId;
				try {
					String siteId = request.getParameter("siteId");
					logoId = cmsSiteService.saveTmpLogo(tempFile,siteId);
					logoIdList.add(logoId);
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
					returnDatas.setMessage("系统异常");
					returnDatas.setStatus(ReturnDatas.ERROR);
					returnDatas.setStatusCode("500");
				}
	    		 
	    	 }
	    }
	    returnDatas.setData(logoIdList);
		return returnDatas;
	}
	
}
