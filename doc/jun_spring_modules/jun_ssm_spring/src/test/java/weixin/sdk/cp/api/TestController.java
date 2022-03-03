package weixin.sdk.cp.api;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springrain.frame.controller.BaseController;
import org.springrain.weixin.entity.WxCpConfig;
import org.springrain.weixin.sdk.common.api.IWxCpConfig;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.cp.api.IWxCpService;
import org.springrain.weixin.sdk.cp.bean.WxCpUser;
import org.springrain.weixin.sdk.cp.util.crypto.WxCpCryptUtil;

@Controller
@RequestMapping(value="/weixin/test")
public class TestController extends BaseController {
	
	@Resource
	IWxCpService wxCpService;
	
	private String corpId = "";
	private String secret = "";
	private String token = "";
	private String aesKey = "";
	
	/**
	 * 消息回调测试
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/callback")
	public void testCallBack(HttpServletRequest request,HttpServletResponse response) throws IOException{
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		config.setToken(token);
		config.setAesKey(aesKey);
		
		String msgSignature = request.getParameter("msg_signature");
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		String echostr = request.getParameter("echostr");

		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		if (StringUtils.isNotBlank(echostr)) {
		  if (!wxCpService.checkSignature(config,msgSignature, timestamp, nonce, echostr)) {
		    // 消息签名不正确，说明不是公众平台发过来的消息
		    response.getWriter().println("非法请求");
		    return;
		  }
		  WxCpCryptUtil cryptUtil = new WxCpCryptUtil(config);
		  String plainText = cryptUtil.decrypt(echostr);
		  // 企业号应用在开启回调模式的时候，会传递加密echostr给服务器，需要解密并echo才能接入成功
		  response.getWriter().println(plainText);
		  return;
		}

		/*// 如果没有echostr，则说明传递过来的用户消息，在解密方法里会自动验证消息是否合法
		WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(request.getInputStream(), config, timestamp, nonce, msgSignature);
		
		
		WxCpXmlOutMessage outMessage = wxCpMessageRouter.route(inMessage);

		if (outMessage != null) {
		  // 将需要同步回复的消息加密，然后再返回给微信平台
		  response.getWriter().write(outMessage.toEncryptedXml(wxCpConfigStorage));
		}*/
	}
	
	/**
	 * 测试企业号获取用户信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/tooauth")
	public String testOauthRedirect(HttpServletRequest request,HttpServletResponse response){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		
		String rediretUrl = wxCpService.oauth2buildAuthorizationUrl(config, "http://t78uitdfh2.proxy.qqbrowser.cc/springrain/weixin/test/oauth", "test");
		return redirect+rediretUrl;
	}
	
	/**
	 * 获取用户信息，用户userid和openid互转
	 * @param request
	 * @param response
	 * @return
	 * @throws WxErrorException
	 */
	@RequestMapping("/oauth")
	public String oauthRedirect(HttpServletRequest request,HttpServletResponse response) throws WxErrorException{
		IWxCpConfig config = new WxCpConfig();
		String code = request.getParameter("code");
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		String[] userInfo = wxCpService.oauth2getUserInfo(config, code);
		WxCpUser wxCpUser = wxCpService.userGet(config, userInfo[0]);
		System.out.println("用户信息："+wxCpUser.toJson());
		System.out.println("设备id:"+userInfo[1]);
		String jmpUrl = "http://www.baidu.com";
		return redirect+jmpUrl;
	}
	
	
}
