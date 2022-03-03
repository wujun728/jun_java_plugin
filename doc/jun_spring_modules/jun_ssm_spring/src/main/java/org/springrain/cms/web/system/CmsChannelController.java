package  org.springrain.cms.web.system;

import java.io.File;
import java.util.ArrayList;
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
import org.springrain.cms.entity.CmsChannel;
import org.springrain.cms.entity.CmsSite;
import org.springrain.cms.service.ICmsChannelService;
import org.springrain.cms.service.ICmsLinkService;
import org.springrain.cms.service.ICmsSiteService;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.property.MessageUtils;


/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2017-01-11 11:12:18
 * @see org.springrain.cms.entity.CmsChannel
 */
@Controller
@RequestMapping(value="/system/cms/channel")
public class CmsChannelController  extends BaseController {
	@Resource
	private ICmsChannelService cmsChannelService;
	@Resource
	private ICmsSiteService cmsSiteService;
	@Resource
	private ICmsLinkService cmsLinkService;
	
	private String listurl="/cms/channel/channelList";
	
	
	
	
	   
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param cmsChannel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,CmsChannel cmsChannel) 
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, cmsChannel);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param cmsChannel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	@ResponseBody 
	public ReturnDatas listjson(HttpServletRequest request, Model model,CmsChannel cmsChannel) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		List<CmsChannel> treeList = new ArrayList<>();
		//查询站点列表
		Page page = newPage(request);
		List<CmsSite> siteTreeList = cmsSiteService.findListDataByFinder(null, page, CmsSite.class, cmsChannel);
		for (CmsSite cmsSite : siteTreeList) {
			String siteId = cmsSite.getId();
			List<CmsChannel> channelList = cmsChannelService.findTreeByPid(null, siteId);
			if (channelList != null){
				treeList.addAll(channelList);
				for (CmsChannel channel : channelList) {
					if(StringUtils.isBlank(channel.getPid())) {
                        channel.setPid(siteId);
                    }
				}
			}
			
			//将site转为channel
			CmsChannel tmpChannel = new CmsChannel(siteId);
			tmpChannel.setName(cmsSite.getName());
			treeList.add(tmpChannel);
		}
		returnObject.setData(treeList);
		return returnObject;
	}
	
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,HttpServletResponse response, Model model,CmsChannel cmsChannel) throws Exception{
		// ==构造分页请求
		Page page = newPage(request);
	
		File file = cmsChannelService.findDataExportExcel(null,listurl, page,CmsChannel.class,cmsChannel);
		String fileName="cmsChannel"+GlobalStatic.excelext;
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
		return "/cms/channel/channelLook";
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
		  CmsChannel cmsChannel = cmsChannelService.findCmsChannelById(id);
		   returnObject.setData(cmsChannel);
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
	public ReturnDatas saveorupdate(Model model,CmsChannel cmsChannel,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
		
			java.lang.String id =cmsChannel.getId();
			if(StringUtils.isBlank(id)){
			  cmsChannel.setId(null);
			}
		
			cmsChannelService.saveorupdate(cmsChannel);
			
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
		map.put("siteList", cmsSiteService.findSiteByUserId(SessionUser.getUserId()));
		returnObject.setMap(map);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/cms/channel/channelCru";
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
				cmsChannelService.deleteById(id,CmsChannel.class);
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
			cmsChannelService.deleteByIds(ids,CmsChannel.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
	}
}
