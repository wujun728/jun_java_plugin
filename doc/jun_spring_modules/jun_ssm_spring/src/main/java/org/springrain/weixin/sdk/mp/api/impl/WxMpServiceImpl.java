package org.springrain.weixin.sdk.mp.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.frame.util.HttpClientUtils;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfigService;
import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.bean.WxAccessToken;
import org.springrain.weixin.sdk.common.bean.WxJsApiSignature;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.RandomUtils;
import org.springrain.weixin.sdk.common.util.crypto.SHA1;
import org.springrain.weixin.sdk.common.util.http.RequestExecutor;
import org.springrain.weixin.sdk.common.util.http.SimpleGetRequestExecutor;
import org.springrain.weixin.sdk.common.util.http.SimplePostRequestExecutor;
import org.springrain.weixin.sdk.common.util.http.URIUtil;
import org.springrain.weixin.sdk.common.util.json.WxGsonBuilder;
import org.springrain.weixin.sdk.mp.api.IWxMpService;
import org.springrain.weixin.sdk.mp.bean.WxMpMassNews;
import org.springrain.weixin.sdk.mp.bean.WxMpMassOpenIdsMessage;
import org.springrain.weixin.sdk.mp.bean.WxMpMassPreviewMessage;
import org.springrain.weixin.sdk.mp.bean.WxMpMassTagMessage;
import org.springrain.weixin.sdk.mp.bean.WxMpMassVideo;
import org.springrain.weixin.sdk.mp.bean.WxMpSemanticQuery;
import org.springrain.weixin.sdk.mp.bean.result.WxMpMassSendResult;
import org.springrain.weixin.sdk.mp.bean.result.WxMpMassUploadResult;
import org.springrain.weixin.sdk.mp.bean.result.WxMpOAuth2AccessToken;
import org.springrain.weixin.sdk.mp.bean.result.WxMpSemanticQueryResult;
import org.springrain.weixin.sdk.mp.bean.result.WxMpUser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class WxMpServiceImpl implements IWxMpService {
  private static final JsonParser JSON_PARSER = new JsonParser();

  private final Logger logger = LoggerFactory.getLogger(getClass());
  
  //生产环境应该是spring注入
  private IWxMpConfigService wxMpConfigService;
  
  public WxMpServiceImpl(){
	  
  }
  
  public WxMpServiceImpl(IWxMpConfigService wxMpConfigService){
	  this.wxMpConfigService=wxMpConfigService;
  }
  
  
  //private IWxMpConfigService wxMpConfigService=new WxMpConfigServiceImpl();

  //private WxMpConfigStorage configStorage;

  //private WxMpKefuService kefuService = new WxMpKefuServiceImpl(this);

  //private WxMpMaterialService materialService = new WxMpMaterialServiceImpl(this);

  //private WxMpMenuService menuService = new WxMpMenuServiceImpl(this);

  //private WxMpUserService userService = new WxMpUserServiceImpl(this);

  //private WxMpUserTagService tagService = new WxMpUserTagServiceImpl(this);

  //private WxMpQrcodeService qrCodeService = new WxMpQrcodeServiceImpl(this);

  //private WxMpCardService cardService = new WxMpCardServiceImpl(this);

  //private WxMpPayService payService = new WxMpPayServiceImpl(this);

  //private WxMpStoreService storeService = new WxMpStoreServiceImpl(this);

  //private WxMpDataCubeService dataCubeService = new WxMpDataCubeServiceImpl(this);

  //private WxMpUserBlacklistService blackListService = new WxMpUserBlacklistServiceImpl(this);

  //private WxMpTemplateMsgService templateMsgService = new WxMpTemplateMsgServiceImpl(this);

  //private CloseableHttpClient httpClient;

  //private HttpHost httpProxy;

 // private int retrySleepMillis = 1000;

  //private int maxRetryTimes = 5;

 // protected WxSessionManager sessionManager = new StandardSessionManager();


  /**
   * <pre>
   * 验证消息的确来自微信服务器
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319&token=&lang=zh_CN
   * </pre>
   */
  @Override
  public boolean checkSignature(IWxMpConfig wxmpconfig,String timestamp, String nonce, String signature) {
    try {
      return SHA1.gen(wxmpconfig.getToken(), timestamp, nonce)
          .equals(signature);
    } catch (Exception e) {
    	logger.error(e.getMessage(), e);
      return false;
    }
  }


  /**
   * 获取access_token, 不强制刷新access_token
   *
   * @see #getAccessToken(boolean)
   */
  @Override
  public String getAccessToken(IWxMpConfig wxmpconfig) throws WxErrorException {
    return getAccessToken(wxmpconfig,false);
  }

  /**
   * <pre>
   * 获取access_token，本方法线程安全
   * 且在多线程同时刷新时只刷新一次，避免超出2000次/日的调用次数上限
   *
   * 另：本service的所有方法都会在access_token过期是调用此方法
   *
   * 程序员在非必要情况下尽量不要主动调用此方法
   *
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183&token=&lang=zh_CN
   * </pre>
   *
   * @param forceRefresh 强制刷新
   */
  @Override
  public String getAccessToken(IWxMpConfig wxmpconfig,boolean forceRefresh) throws WxErrorException {

      if (forceRefresh) {
    	  wxMpConfigService.expireAccessToken(wxmpconfig);
      }

      if (!wxmpconfig.isAccessTokenExpired()) {
    	  return wxmpconfig.getAccessToken();
      }
      
      WxAccessToken accessToken=wxMpConfigService.getCustomAPIAccessToken(wxmpconfig);
      if(accessToken==null){
	         String url = WxConsts.mpapiurl+"/cgi-bin/token?grant_type=client_credential" +"&appid=" + wxmpconfig.getAppId() + "&secret=" + wxmpconfig.getSecret();
	          HttpGet httpGet = new HttpGet(url);
	          if (wxmpconfig.getHttpProxyHost() != null) {
	            RequestConfig config = RequestConfig.custom().setProxy(new HttpHost(wxmpconfig.getHttpProxyHost() , wxmpconfig.getHttpProxyPort() )).build();
	            httpGet.setConfig(config);
	          }
	         
	            String resultContent = HttpClientUtils.sendHttpGet(httpGet);
	            WxError error = WxError.fromJson(resultContent);
	            if (error.getErrorCode() != 0) {
	              throw new WxErrorException(error);
	            }
	            //accessToken = WxAccessToken.fromJson(resultContent);
	            accessToken= WxGsonBuilder.create().fromJson(resultContent, WxAccessToken.class);
         }
      
      if(accessToken==null){
    	  return null;
      }
      
            wxmpconfig.setAccessToken(accessToken.getAccessToken());
            wxmpconfig.setAccessTokenExpiresTime(Long.valueOf(accessToken.getExpiresIn()));
            wxMpConfigService.updateAccessToken(wxmpconfig);
        
           return wxmpconfig.getAccessToken();
  }

  /**
   * 获得jsapi_ticket,不强制刷新jsapi_ticket
   *
   * @see #getJsApiTicket(boolean)
   */
  @Override
  public String getJsApiTicket(IWxMpConfig wxmpconfig) throws WxErrorException {
    return getJsApiTicket(wxmpconfig,false);
  }

  /**
   * <pre>
   * 获得jsapi_ticket
   * 获得时会检查jsapiToken是否过期，如果过期了，那么就刷新一下，否则就什么都不干
   *
   * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN
   * </pre>
   *
   * @param forceRefresh 强制刷新
   */
  @Override
  public String getJsApiTicket(IWxMpConfig wxmpconfig,boolean forceRefresh) throws WxErrorException {

      if (forceRefresh) {
    	  wxMpConfigService.expireJsApiTicket(wxmpconfig);
      }
      
      if (!wxmpconfig.isJsApiTicketExpired()) {
    	  return wxmpconfig.getJsApiTicket();
      }
      

        String url = WxConsts.mpapiurl+"/cgi-bin/ticket/getticket?type=jsapi";
        String responseContent = execute( wxmpconfig,new SimpleGetRequestExecutor(), url, null);
        JsonElement tmpJsonElement = JSON_PARSER.parse(responseContent);
        JsonObject tmpJsonObject = tmpJsonElement.getAsJsonObject();
        String jsapiTicket = tmpJsonObject.get("ticket").getAsString();
        Long expiresInSeconds = tmpJsonObject.get("expires_in").getAsLong();
        
        wxmpconfig.setJsApiTicket(jsapiTicket);
        wxmpconfig.setJsApiTicketExpiresTime(expiresInSeconds);
        wxMpConfigService.updateJsApiTicket(wxmpconfig);
        
    return wxmpconfig.getJsApiTicket();
  }

  /**
   * <pre>
   * 创建调用jsapi时所需要的签名
   *
   * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN
   * </pre>
   */
  @Override
  public WxJsApiSignature createJsApiSignature(IWxMpConfig wxmpconfig,String url) throws WxErrorException {
    long timestamp = System.currentTimeMillis() / 1000;
    String noncestr = RandomUtils.getRandomStr();
    String jsapiTicket = getJsApiTicket(wxmpconfig,false);
    String signature = SHA1.genWithAmple("jsapi_ticket=" + jsapiTicket,
        "noncestr=" + noncestr, "timestamp=" + timestamp, "url=" + url);
    WxJsApiSignature jsapiSignature = new WxJsApiSignature();
    jsapiSignature.setAppid(wxmpconfig.getAppId());
    jsapiSignature.setTimestamp(timestamp);
    jsapiSignature.setNoncestr(noncestr);
    jsapiSignature.setUrl(url);
    jsapiSignature.setSignature(signature);
    return jsapiSignature;
  }


  /**
   * <pre>
   * 上传群发用的图文消息，上传后才能群发图文消息
   *
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN
   * </pre>
   *
   * @see #massGroupMessageSend(org.springrain.weixin.sdk.mp.bean.WxMpMassTagMessage)
   * @see #massOpenIdsMessageSend(org.springrain.weixin.sdk.mp.bean.WxMpMassOpenIdsMessage)
   */
  @Override
  public WxMpMassUploadResult massNewsUpload(IWxMpConfig wxmpconfig,WxMpMassNews news) throws WxErrorException {
    String url = WxConsts.mpapiurl+"/cgi-bin/media/uploadnews";
    String responseContent = post(wxmpconfig,url, news.toJson());
    return WxMpMassUploadResult.fromJson(responseContent);
  }

  /**
   * <pre>
   * 上传群发用的视频，上传后才能群发视频消息
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN
   * </pre>
   *
   * @see #massGroupMessageSend(org.springrain.weixin.sdk.mp.bean.WxMpMassTagMessage)
   * @see #massOpenIdsMessageSend(org.springrain.weixin.sdk.mp.bean.WxMpMassOpenIdsMessage)
   */
  @Override
  public WxMpMassUploadResult massVideoUpload(IWxMpConfig wxmpconfig,WxMpMassVideo video) throws WxErrorException {
    String url = WxConsts.mpapiurl+"/cgi-bin/media/uploadvideo";
    String responseContent = post(wxmpconfig,url, video.toJson());
    return WxMpMassUploadResult.fromJson(responseContent);
  }

  /**
   * <pre>
   * 分组群发消息
   * 如果发送图文消息，必须先使用 {@link #massNewsUpload(org.springrain.weixin.sdk.mp.bean.WxMpMassNews)} 获得media_id，然后再发送
   * 如果发送视频消息，必须先使用 {@link #massVideoUpload(org.springrain.weixin.sdk.mp.bean.WxMpMassVideo)} 获得media_id，然后再发送
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN
   * </pre>
   */
  @Override
  public WxMpMassSendResult massGroupMessageSend(IWxMpConfig wxmpconfig,WxMpMassTagMessage message) throws WxErrorException {
    String url = WxConsts.mpapiurl+"/cgi-bin/message/mass/sendall";
    String responseContent = post(wxmpconfig,url, message.toJson());
    return WxMpMassSendResult.fromJson(responseContent);
  }


  /**
   * <pre>
   * 按openId列表群发消息
   * 如果发送图文消息，必须先使用 {@link #massNewsUpload(org.springrain.weixin.sdk.mp.bean.WxMpMassNews)} 获得media_id，然后再发送
   * 如果发送视频消息，必须先使用 {@link #massVideoUpload(org.springrain.weixin.sdk.mp.bean.WxMpMassVideo)} 获得media_id，然后再发送
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN
   * </pre>
   */
  @Override
  public WxMpMassSendResult massOpenIdsMessageSend(IWxMpConfig wxmpconfig,WxMpMassOpenIdsMessage message) throws WxErrorException {
    String url = WxConsts.mpapiurl+"/cgi-bin/message/mass/send";
    String responseContent = post(wxmpconfig,url, message.toJson());
    return WxMpMassSendResult.fromJson(responseContent);
  }

  /**
   * <pre>
   * 群发消息预览接口
   * 开发者可通过该接口发送消息给指定用户，在手机端查看消息的样式和排版。为了满足第三方平台开发者的需求，在保留对openID预览能力的同时，增加了对指定微信号发送预览的能力，但该能力每日调用次数有限制（100次），请勿滥用。
   * 接口调用请求说明
   *  http请求方式: POST
   *  https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN
   * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN
   * </pre>
   *
   * @return wxMpMassSendResult
   */
  @Override
  public WxMpMassSendResult massMessagePreview(IWxMpConfig wxmpconfig,WxMpMassPreviewMessage wxMpMassPreviewMessage) throws Exception {
    String url = WxConsts.mpapiurl+"/cgi-bin/message/mass/preview";
    String responseContent = post(wxmpconfig,url, wxMpMassPreviewMessage.toJson());
    return WxMpMassSendResult.fromJson(responseContent);
  }

  /**
   * <pre>
   * 长链接转短链接接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=长链接转短链接接口
   * </pre>
   *
   */
  @Override
  public String shortUrl(IWxMpConfig wxmpconfig,String long_url) throws WxErrorException {
    String url = WxConsts.mpapiurl+"/cgi-bin/shorturl";
    JsonObject o = new JsonObject();
    o.addProperty("action", "long2short");
    o.addProperty("long_url", long_url);
    String responseContent = post(wxmpconfig,url, o.toString());
    JsonElement tmpJsonElement = JSON_PARSER.parse(responseContent);
    return tmpJsonElement.getAsJsonObject().get("short_url").getAsString();
  }

  /**
   * <pre>
   * 语义查询接口
   * 详情请见：http://mp.weixin.qq.com/wiki/index.php?title=语义理解
   * </pre>
   */
  @Override
  public WxMpSemanticQueryResult semanticQuery(IWxMpConfig wxmpconfig,WxMpSemanticQuery semanticQuery) throws WxErrorException {
    String url = WxConsts.mpapiurl+"/semantic/semproxy/search";
    String responseContent = post(wxmpconfig,url, semanticQuery.toJson());
    return WxMpSemanticQueryResult.fromJson(responseContent);
  }
  /**
   * <pre>
   * 构造第三方使用网站应用授权登录的url
   * 详情请见: <a href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN">网站应用微信登录开发指南</a>
   * URL格式为：https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
   * </pre>
   *
   * @param redirectURI 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
   * @param scope 应用授权作用域，拥有多个作用域用逗号（,）分隔，网页应用目前仅填写snsapi_login即可
   * @param state 非必填，用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
   * @return url
   */
  @Override
  public String buildQrConnectUrl(IWxMpConfig wxmpconfig,String redirectURI, String scope,
      String state) {
    StringBuilder url = new StringBuilder();
    url.append(WxConsts.mpopenurl+"/connect/qrconnect?");
    url.append("appid=").append(wxmpconfig.getAppId());
    url.append("&redirect_uri=").append(URIUtil.encodeURIComponent(redirectURI));
    url.append("&response_type=code");
    url.append("&scope=").append(scope);
    if (state != null) {
      url.append("&state=").append(state);
    }

    url.append("#wechat_redirect");
    return url.toString();
  }


  /**
   * <pre>
   * 构造oauth2授权的url连接
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
   * </pre>
   *
   * @param redirectURI 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
   * @return url
   */
  @Override
  public String oauth2buildAuthorizationUrl(IWxMpConfig wxmpconfig,String redirectURI, String scope, String state) {
    StringBuilder url = new StringBuilder();
    url.append(WxConsts.mpopenurl+"/connect/oauth2/authorize?");
    url.append("appid=").append(wxmpconfig.getAppId());
    url.append("&redirect_uri=").append(URIUtil.encodeURIComponent(redirectURI));
    url.append("&response_type=code");
    url.append("&scope=").append(scope);
    if (state != null) {
      url.append("&state=").append(state);
    }
    url.append("#wechat_redirect");
    return url.toString();
  }

  
  private WxMpOAuth2AccessToken getOAuth2AccessToken(IWxMpConfig wxmpconfig,StringBuilder url) throws WxErrorException {
    try {
      RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
      String responseText = executor.execute(wxmpconfig, url.toString(), null);
      return WxMpOAuth2AccessToken.fromJson(responseText);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * <pre>
   * 用code换取oauth2的access token
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
   * </pre>
   */
  @Override
  public WxMpOAuth2AccessToken oauth2getAccessToken(IWxMpConfig wxmpconfig,String code) throws WxErrorException {
    StringBuilder url = new StringBuilder();
    url.append(WxConsts.mpapiurl+"/sns/oauth2/access_token?");
    url.append("appid=").append(wxmpconfig.getAppId());
    url.append("&secret=").append(wxmpconfig.getSecret());
    url.append("&code=").append(code);
    url.append("&grant_type=authorization_code");

    return getOAuth2AccessToken(wxmpconfig,url);
  }

  /**
   * <pre>
   * 刷新oauth2的access token
   * </pre>
   */
  @Override
  public WxMpOAuth2AccessToken oauth2refreshAccessToken(IWxMpConfig wxmpconfig,String refreshToken) throws WxErrorException {
    StringBuilder url = new StringBuilder();
    url.append(WxConsts.mpapiurl+"/sns/oauth2/refresh_token?");
    url.append("appid=").append(wxmpconfig.getAppId());
    url.append("&grant_type=refresh_token");
    url.append("&refresh_token=").append(refreshToken);

    return getOAuth2AccessToken(wxmpconfig,url);
  }

  /**
   * <pre>
   * 用oauth2获取用户信息, 当前面引导授权时的scope是snsapi_userinfo的时候才可以
   * </pre>
   *
   * @param lang              zh_CN, zh_TW, en
   */
  @Override
  public WxMpUser oauth2getUserInfo(IWxMpConfig wxmpconfig,WxMpOAuth2AccessToken oAuth2AccessToken, String lang) throws WxErrorException {
    StringBuilder url = new StringBuilder();
    url.append(WxConsts.mpapiurl+"/sns/userinfo?");
    url.append("access_token=").append(oAuth2AccessToken.getAccessToken());
    url.append("&openid=").append(oAuth2AccessToken.getOpenId());
    if (lang == null) {
      url.append("&lang=zh_CN");
    } else {
      url.append("&lang=").append(lang);
    }

    try {
      RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
      String responseText = executor.execute(wxmpconfig, url.toString(), null);
      return WxMpUser.fromJson(responseText);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * <pre>
   * 验证oauth2的access token是否有效
   * </pre>
   *
   */
  @Override
  public boolean oauth2validateAccessToken(IWxMpConfig wxmpconfig,WxMpOAuth2AccessToken oAuth2AccessToken) {
    StringBuilder url = new StringBuilder();
    url.append(WxConsts.mpapiurl+"/sns/auth?");
    url.append("access_token=").append(oAuth2AccessToken.getAccessToken());
    url.append("&openid=").append(oAuth2AccessToken.getOpenId());

    try {
      RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
      executor.execute(wxmpconfig, url.toString(), null);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (WxErrorException e) {
      logger.error(e.getMessage(),e);
      return false;
    }
    return true;
  }

  /**
   * <pre>
   * 获取微信服务器IP地址
   * http://mp.weixin.qq.com/wiki/0/2ad4b6bfd29f30f71d39616c2a0fcedc.html
   * </pre>
   */
  @Override
  public List<String> getCallbackIP(IWxMpConfig wxmpconfig) throws WxErrorException {
    String url = WxConsts.mpapiurl+"/cgi-bin/getcallbackip";
    String responseContent = get(wxmpconfig,url, null);
    JsonElement tmpJsonElement = JSON_PARSER.parse(responseContent);
    JsonArray ipList = tmpJsonElement.getAsJsonObject().get("ip_list").getAsJsonArray();
    List<String> ipArray = new ArrayList<>();
    for (int i = 0; i < ipList.size(); i++) {
    	ipArray.add(ipList.get(i).getAsString());
    }
    return ipArray;
  }


  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求
   */
  @Override
  public String get(IWxMpConfig wxmpconfig,String url, String queryParam) throws WxErrorException {
    return execute(wxmpconfig,new SimpleGetRequestExecutor(), url, queryParam);
  }


  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求
   */
  @Override
  public String post(IWxMpConfig wxmpconfig,String url, String postData) throws WxErrorException {
    return execute(wxmpconfig,new SimplePostRequestExecutor(), url, postData);
  }

  /**
   * 向微信端发送请求，在这里执行的策略是当发生access_token过期时才去刷新，然后重新执行请求，而不是全局定时请求
   */
  /**
   * <pre>
   * Service没有实现某个API的时候，可以用这个，
   * 比{@link #get}和{@link #post}方法更灵活，可以自己构造RequestExecutor用来处理不同的参数和不同的返回类型。
   * 可以参考，{@link org.springrain.weixin.sdk.common.util.http.MediaUploadRequestExecutor}的实现方法
   * </pre>
   */
  @Override
  public <T, E> T execute(IWxMpConfig wxmpconfig,RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
	int retrySleepMillis = 1000;
	int maxRetryTimes = 5;
    int retryTimes = 0;
    
    do {
      try {
        T result = executeInternal(wxmpconfig,executor, uri, data);
        logger.debug("\n[URL]:  {}\n[PARAMS]: {}\n[RESPONSE]: {}",uri, data, result);
        return result;
      } catch (WxErrorException e) {
    	logger.error(e.getMessage(),e);
        WxError error = e.getError();
        // -1 系统繁忙, 1000ms后重试
        if (error.getErrorCode() == -1) {
          int sleepMillis = retrySleepMillis * (1 << retryTimes);
          try {
            logger.debug("微信系统繁忙，{}ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
            Thread.sleep(sleepMillis);
          } catch (InterruptedException e1) {
        	logger.error(e.getMessage(), e);
        	Thread.currentThread().interrupt();
            //throw new RuntimeException(e1);
          }
        } else {
          throw e;
        }
      }
    } while (++retryTimes < maxRetryTimes);

    throw new RuntimeException("微信服务端异常，超出重试次数");
  }

  protected synchronized <T, E> T executeInternal(IWxMpConfig wxmpconfig,RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
    if (uri.indexOf("access_token=") != -1) {
      throw new IllegalArgumentException("uri参数中不允许有access_token: " + uri);
    }
    String accessToken = getAccessToken(wxmpconfig,false);

    String uriWithAccessToken = uri;
    uriWithAccessToken += uri.indexOf('?') == -1 ? "?access_token=" + accessToken : "&access_token=" + accessToken;

    try {
      return executor.execute(wxmpconfig, uriWithAccessToken, data);
    } catch (WxErrorException e) {
      logger.error(e.getMessage(),e);
      WxError error = e.getError();
      /*
       * 发生以下情况时尝试刷新access_token
       * 40001 获取access_token时AppSecret错误，或者access_token无效
       * 42001 access_token超时
       */
      if (error.getErrorCode() == 42001 || error.getErrorCode() == 40001) {
        // 强制设置wxMpConfigStorage它的access token过期了，这样在下一次请求里就会刷新access token
        //wxmpconfig.expireAccessToken();
        wxMpConfigService.expireAccessToken(wxmpconfig);
        if(wxmpconfig.autoRefreshToken()){
          return execute(wxmpconfig,executor, uri, data);
        }
      }

      if (error.getErrorCode() != 0) {
        logger.error("\n[URL]:  {}\n[PARAMS]: {}\n[RESPONSE]: {}", uri, data,
            error);
        throw new WxErrorException(error);
      }
      return null;
    } catch (IOException e) {
      logger.error("\n[URL]:  {}\n[PARAMS]: {}\n[EXCEPTION]: {}", uri, data, e.getMessage());
      throw new RuntimeException(e);
    }
  }

 
}
