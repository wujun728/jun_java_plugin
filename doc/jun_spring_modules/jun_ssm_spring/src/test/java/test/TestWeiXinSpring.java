package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springrain.weixin.entity.WxMpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfigService;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.mp.api.IWxMpService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestWeiXinSpring {

	@Resource
	IWxMpService wxMpService;
	
	@Resource
	IWxMpConfigService wxMpConfigService;
	
	
	@Test
	public void testAccessToken(){
		
		
		//数据库查询
	    //WxMpConfig  wxmpconfig=wxMpConfigService.findWxMpConfigById("");
		
	      //测试
		IWxMpConfig  wxmpconfig=new WxMpConfig();
		wxmpconfig.setId("testId");		
		wxmpconfig.setAppId("");
		wxmpconfig.setSecret("");

		
		try {
			String accessToken = wxMpService.getAccessToken(wxmpconfig);
			System.out.println("accessToken:"+accessToken);
			
			
			wxmpconfig=wxMpConfigService.findWxMpConfigById("testId");
			
			String jsapiTicket = wxMpService.getJsApiTicket(wxmpconfig);
			System.out.println("jsapiTicket:"+jsapiTicket);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
