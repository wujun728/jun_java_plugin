package org.springrain.cms.web.f;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.SecUtils;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfigService;
import org.springrain.weixin.sdk.common.bean.menu.WxMenu;
import org.springrain.weixin.sdk.common.bean.menu.WxMenuButton;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.mp.api.IWxMpMenuService;
import org.springrain.weixin.sdk.mp.api.IWxMpService;
import org.springrain.weixin.sdk.mp.api.impl.WxMpMenuServiceImpl;
import org.springrain.weixin.sdk.mp.api.impl.WxMpServiceImpl;

@Controller
@RequestMapping(value="/f/{siteType}/{siteId}/{businessId}/file")
public class FrontFileUploadController {
	
	@Resource
	private IWxMpConfigService wxMpConfigService;
	/**
	 * 上传logo
	 * @throws IOException 
	 * */
	@RequestMapping("/upload")
	@ResponseBody 
	public  ReturnDatas logoUpload(HttpServletRequest request,@PathVariable String siteId,@PathVariable String businessId,@PathVariable String siteType) throws IOException{
		ReturnDatas returnDatas=ReturnDatas.getSuccessReturnDatas();
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
	    List<Map<String, String>> fileList = uploadFile(multiRequest,siteId,businessId,siteType);
		returnDatas.setData(fileList);
		return returnDatas;
	}

	private List<Map<String, String>> uploadFile(
			MultipartHttpServletRequest multiRequest, String siteId,String businessId,String siteType) throws IOException {
		Iterator<String> iter = multiRequest.getFileNames();
		List<Map<String, String>> fileList = new ArrayList<>(); 
		while(iter.hasNext()){
			MultipartFile tempFile = multiRequest.getFile(iter.next());
    		String[] fullFileName = StringUtils.split(tempFile.getOriginalFilename(), ".");
    		String fileName = fullFileName[0];
    		String prefix = fullFileName[1];
			
			//String path = "/upload/"+siteType+"/"+siteId+"/"+businessId+"/"+SecUtils.getUUID()+tempFile.getOriginalFilename();
			String path = "/upload/"+siteType+"/"+siteId+"/"+businessId+"/"+SecUtils.getUUID()+"."+prefix;
			
			
    		File file = new File(GlobalStatic.rootDir+path);
    		if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
    		if(!file.exists()){
    			boolean createNewFile = file.createNewFile();
    			if(!createNewFile){
    				return null;
    			}
    		}
    			
    		tempFile.transferTo(file);
    		Map<String, String> uploadFileMap = new HashMap<>();
    		uploadFileMap.put("name", fileName);
    		uploadFileMap.put("path", path);
    		uploadFileMap.put("size", String.valueOf(file.getTotalSpace()));
    		uploadFileMap.put("prefix", prefix);
    		
    		fileList.add(uploadFileMap);
		}
		
		return fileList;
	}
	
	@RequestMapping("/menu")
	@ResponseBody 
	public ReturnDatas createMenu(@PathVariable String siteId) throws WxErrorException {
		WxMenu menu = new WxMenu();
		List<WxMenuButton> buttons = new ArrayList<WxMenuButton>();
		WxMenuButton btn1 = new WxMenuButton();
		btn1.setName("警务公开");
		List<WxMenuButton> subButtons1 = new ArrayList<WxMenuButton>();
		WxMenuButton subBtn11 = new WxMenuButton();
		subBtn11.setType("view");
		subBtn11.setName("梧州警事");
		subBtn11.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10015");
		subButtons1.add(subBtn11);
		WxMenuButton subBtn12 = new WxMenuButton();
		subBtn12.setType("view");
		subBtn12.setName("警务资讯");
		subBtn12.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10020");
		subButtons1.add(subBtn12);
		WxMenuButton subBtn13 = new WxMenuButton();
		subBtn13.setType("view");
		subBtn13.setName("举报平台");
		subBtn13.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10091");
		subButtons1.add(subBtn13);
		WxMenuButton subBtn14 = new WxMenuButton();
		subBtn14.setType("view");
		subBtn14.setName("警务矩阵");
		subBtn14.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10030");
		subButtons1.add(subBtn14);
		WxMenuButton subBtn15 = new WxMenuButton();
		subBtn15.setType("view");
		subBtn15.setName("平安地图");
		subBtn15.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10018");
		subButtons1.add(subBtn15);
		btn1.setSubButtons(subButtons1);
		
		WxMenuButton btn2 = new WxMenuButton();
		btn2.setName("服务大厅");
		List<WxMenuButton> subButtons2 = new ArrayList<WxMenuButton>();
		WxMenuButton subBtn21 = new WxMenuButton();
		subBtn21.setType("view");
		subBtn21.setName("交管业务");
		subBtn21.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10057");
		subButtons2.add(subBtn21);
		WxMenuButton subBtn22 = new WxMenuButton();
		subBtn22.setType("view");
		subBtn22.setName("出入境业务");
		subBtn22.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10093");
		subButtons2.add(subBtn22);
		WxMenuButton subBtn23 = new WxMenuButton();
		subBtn23.setType("view");
		subBtn23.setName("户政业务");
		subBtn23.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10094");
		subButtons2.add(subBtn23);
		WxMenuButton subBtn24 = new WxMenuButton();
		subBtn24.setType("view");
		subBtn24.setName("治安业务");
		subBtn24.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10095");
		subButtons2.add(subBtn24);
		WxMenuButton subBtn25 = new WxMenuButton();
		subBtn25.setType("view");
		subBtn25.setName("其他业务");
		subBtn25.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10096");
		subButtons2.add(subBtn25);
		btn2.setSubButtons(subButtons2);
		
		WxMenuButton btn3 = new WxMenuButton();
		btn3.setName("平安义工");
		List<WxMenuButton> subButtons3 = new ArrayList<WxMenuButton>();
		WxMenuButton subBtn31 = new WxMenuButton();
		subBtn31.setType("view");
		subBtn31.setName("义工招募");
		subBtn31.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10101");
		subButtons3.add(subBtn31);
		WxMenuButton subBtn32 = new WxMenuButton();
		subBtn32.setType("view");
		subBtn32.setName("义工活动");
		subBtn32.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10016");
		subButtons3.add(subBtn32);
		WxMenuButton subBtn33 = new WxMenuButton();
		subBtn33.setType("view");
		subBtn33.setName("义工风采");
		subBtn33.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10029");
		subButtons3.add(subBtn33);
		WxMenuButton subBtn34 = new WxMenuButton();
		subBtn34.setType("view");
		subBtn34.setName("星级义工");
		subBtn34.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10102");
		subButtons3.add(subBtn34);
		WxMenuButton subBtn35 = new WxMenuButton();
		subBtn35.setType("view");
		subBtn35.setName("少年警校");
		subBtn35.setUrl("http://wzga.wxyapp.com/wzga_wx_sys/f/mp/s_10007/h_10081");
		subButtons3.add(subBtn35);
		btn3.setSubButtons(subButtons3);
		buttons.add(btn1);
		buttons.add(btn2);
		buttons.add(btn3);
		menu.setButtons(buttons);
		
		IWxMpService wxMpService = new WxMpServiceImpl(wxMpConfigService);
		IWxMpMenuService wxMpMenuService = new WxMpMenuServiceImpl(wxMpService);
		IWxMpConfig wxMpConfig = wxMpConfigService.findWxMpConfigById(siteId);
		wxMpMenuService.menuCreate(wxMpConfig, menu);
		return null;
	}
}
