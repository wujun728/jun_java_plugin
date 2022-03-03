package org.springrain.cms.web.f;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springrain.cms.entity.CmsLink;
import org.springrain.cms.service.ICmsLinkService;
import org.springrain.frame.controller.BaseController;


public class FrontBaseController extends BaseController {
	@Resource
	private ICmsLinkService cmsLinkService;
	
	/**
	 * 
	 * @param siteId  站点Id
	 * @param defaultLink  默认cmslink 的跳转链接  
	 * @param siteType 站点类型
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public  String jumpByDefaultLink( String siteId,String defaultLink,Integer siteType,HttpServletRequest request, Model model) 
			throws Exception {
			CmsLink   cmsLink=cmsLinkService.findLinkByLink(siteId, defaultLink);
			model.addAttribute("defaultLink", defaultLink);
			return jump(siteType, cmsLink, request, model);
	}
	
	
	public  String jumpByLink( String siteId,String link,Integer siteType,HttpServletRequest request, Model model) 
			throws Exception {
			CmsLink   cmsLink=cmsLinkService.findLinkByLink(siteId, link);
			model.addAttribute("link", link);
			return jump(siteType, cmsLink, request, model);
	}
	
	
	/**
	 * 
	 * @param siteId  站点Id
	 * @param businessId  业务ID，可为栏目，可为内容
	 * @param siteType 站点类型
	 * @param request
	 * @param model
	 * @param modelType  链接类型 见  CmsLinkModeType枚举
	 * @return
	 * @throws Exception
	 */
	public  String jump( String siteId,String businessId,Integer siteType,HttpServletRequest request, Model model,Integer modelType) 
			throws Exception {
		    CmsLink   cmsLink=cmsLinkService.findLinkBySiteBusinessIdModelType(siteId, businessId, modelType);
	        return jump(siteType, cmsLink, request, model);
	}
	
	
	/**
	 * 
	 * @param siteType 站点类型
	 * @param cmsLink  cmsLink对象
	 * @param request  
	 * @param model
	 * @return
	 */
	private  String jump(Integer siteType,CmsLink   cmsLink,HttpServletRequest request, Model model){
		String link=cmsLink.getLink();
	    String ftlFile=cmsLink.getFtlfile();
	    Integer jumpType=cmsLink.getJumpType();
		
		addModelParameter(request, model);
		
		model.addAttribute("siteId", cmsLink.getSiteId());
		model.addAttribute("businessId", cmsLink.getBusinessId());
		model.addAttribute("siteType", siteType);
		
		
		if(link==null||jumpType==null||jumpType==0){
			return ftlFile;
		}else if(jumpType==1){
			return redirect+link;
		}else if(jumpType==2){
			return forward+link;
		}
		
		return ftlFile;
	}
	
	
	
	
}
