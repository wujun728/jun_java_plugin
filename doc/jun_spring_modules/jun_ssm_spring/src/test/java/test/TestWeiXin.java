package test;

import java.util.List;

import org.junit.Test;
import org.springrain.weixin.entity.WxMpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfigService;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.mp.api.IWxMpService;
import org.springrain.weixin.sdk.mp.api.impl.WxMpServiceImpl;
import org.springrain.weixin.service.impl.WxMpConfigServiceImpl;


public class TestWeiXin {

	IWxMpConfigService wxMpConfigService=new WxMpConfigServiceImpl();
	IWxMpService wxMpService=new WxMpServiceImpl(wxMpConfigService);
	
	
	public void testAccessToken() {
		//数据库查询
		//WxMpConfig  wxmpconfig=wxMpConfigService.findWxMpConfigById("");
		
		//测试
		IWxMpConfig  wxmpconfig=new WxMpConfig();
		
		
		wxmpconfig.setAppId("");
		wxmpconfig.setSecret("");
		
		try {
			String accessToken = wxMpService.getAccessToken(wxmpconfig);
			System.out.println("accessToken:"+accessToken);
			String jsapiTicket = wxMpService.getJsApiTicket(wxmpconfig);
			System.out.println("jsapiTicket:"+jsapiTicket);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testweixinIP() throws WxErrorException{
		//测试
		IWxMpConfig  wxmpconfig=new WxMpConfig();
		List<String> callbackIP = wxMpService.getCallbackIP(wxmpconfig);
		System.out.println(callbackIP);
		
		
	}
	
	
	
	
	
}
