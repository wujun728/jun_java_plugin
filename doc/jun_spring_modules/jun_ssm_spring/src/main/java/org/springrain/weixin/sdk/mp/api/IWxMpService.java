package org.springrain.weixin.sdk.mp.api;

import java.util.List;

import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.bean.WxJsApiSignature;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.http.RequestExecutor;
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

/**
 * 微信API的Service
 */
public interface IWxMpService {

  /**
   * <pre>
   * 验证消息的确来自微信服务器
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421135319&token=&lang=zh_CN
   * </pre>
   */
  boolean checkSignature(IWxMpConfig wxmpconfig,String timestamp, String nonce, String signature);

  /**
   * 获取access_token, 不强制刷新access_token
 * @throws Exception 
   *
   * @see #getAccessToken(boolean)
   */
  String getAccessToken(IWxMpConfig wxmpconfig) throws WxErrorException;

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
 * @throws Exception 
   */
  String getAccessToken(IWxMpConfig wxmpconfig,boolean forceRefresh) throws WxErrorException, Exception;

  /**
   * 获得jsapi_ticket,不强制刷新jsapi_ticket
   *
   * @see #getJsApiTicket(boolean)
   */
  String getJsApiTicket(IWxMpConfig wxmpconfig) throws WxErrorException;

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
  String getJsApiTicket(IWxMpConfig wxmpconfig,boolean forceRefresh) throws WxErrorException;

  /**
   * <pre>
   * 创建调用jsapi时所需要的签名
   *
   * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN
   * </pre>
   */
  WxJsApiSignature createJsApiSignature(IWxMpConfig wxmpconfig,String url) throws WxErrorException;

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
  WxMpMassUploadResult massNewsUpload(IWxMpConfig wxmpconfig,WxMpMassNews news) throws WxErrorException;

  /**
   * <pre>
   * 上传群发用的视频，上传后才能群发视频消息
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN
   * </pre>
   *
   * @see #massGroupMessageSend(org.springrain.weixin.sdk.mp.bean.WxMpMassTagMessage)
   * @see #massOpenIdsMessageSend(org.springrain.weixin.sdk.mp.bean.WxMpMassOpenIdsMessage)
   */
  WxMpMassUploadResult massVideoUpload(IWxMpConfig wxmpconfig,WxMpMassVideo video) throws WxErrorException;

  /**
   * <pre>
   * 分组群发消息
   * 如果发送图文消息，必须先使用 {@link #massNewsUpload(org.springrain.weixin.sdk.mp.bean.WxMpMassNews)} 获得media_id，然后再发送
   * 如果发送视频消息，必须先使用 {@link #massVideoUpload(org.springrain.weixin.sdk.mp.bean.WxMpMassVideo)} 获得media_id，然后再发送
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN
   * </pre>
   */
  WxMpMassSendResult massGroupMessageSend(IWxMpConfig wxmpconfig,WxMpMassTagMessage message) throws WxErrorException;

  /**
   * <pre>
   * 按openId列表群发消息
   * 如果发送图文消息，必须先使用 {@link #massNewsUpload(org.springrain.weixin.sdk.mp.bean.WxMpMassNews)} 获得media_id，然后再发送
   * 如果发送视频消息，必须先使用 {@link #massVideoUpload(org.springrain.weixin.sdk.mp.bean.WxMpMassVideo)} 获得media_id，然后再发送
   * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN
   * </pre>
   */
  WxMpMassSendResult massOpenIdsMessageSend(IWxMpConfig wxmpconfig,WxMpMassOpenIdsMessage message) throws WxErrorException;

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
  WxMpMassSendResult massMessagePreview(IWxMpConfig wxmpconfig,WxMpMassPreviewMessage wxMpMassPreviewMessage) throws Exception;

  /**
   * <pre>
   * 长链接转短链接接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=长链接转短链接接口
   * </pre>
   *
   */
  String shortUrl(IWxMpConfig wxmpconfig,String long_url) throws WxErrorException;

  /**
   * <pre>
   * 语义查询接口
   * 详情请见：http://mp.weixin.qq.com/wiki/index.php?title=语义理解
   * </pre>
   */
  WxMpSemanticQueryResult semanticQuery(IWxMpConfig wxmpconfig,WxMpSemanticQuery semanticQuery) throws WxErrorException;

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
  String buildQrConnectUrl(IWxMpConfig wxmpconfig,String redirectURI, String scope, String state);

  /**
   * <pre>
   * 构造oauth2授权的url连接
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
   * </pre>
   *
   * @param redirectURI 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
   * @return url
   */
  String oauth2buildAuthorizationUrl(IWxMpConfig wxmpconfig,String redirectURI, String scope, String state);

  /**
   * <pre>
   * 用code换取oauth2的access token
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
   * </pre>
   */
  WxMpOAuth2AccessToken oauth2getAccessToken(IWxMpConfig wxmpconfig,String code) throws WxErrorException;

  /**
   * <pre>
   * 刷新oauth2的access token
   * </pre>
   */
  WxMpOAuth2AccessToken oauth2refreshAccessToken(IWxMpConfig wxmpconfig,String refreshToken) throws WxErrorException;

  /**
   * <pre>
   * 用oauth2获取用户信息, 当前面引导授权时的scope是snsapi_userinfo的时候才可以
   * </pre>
   *
   * @param lang              zh_CN, zh_TW, en
   */
  WxMpUser oauth2getUserInfo(IWxMpConfig wxmpconfig,WxMpOAuth2AccessToken oAuth2AccessToken, String lang) throws WxErrorException;

  /**
   * <pre>
   * 验证oauth2的access token是否有效
   * </pre>
   *
   */
  boolean oauth2validateAccessToken(IWxMpConfig wxmpconfig,WxMpOAuth2AccessToken oAuth2AccessToken);

  /**
   * <pre>
   * 获取微信服务器IP地址
   * http://mp.weixin.qq.com/wiki/0/2ad4b6bfd29f30f71d39616c2a0fcedc.html
   * </pre>
   */
  List<String> getCallbackIP(IWxMpConfig wxmpconfig) throws WxErrorException;

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求
   */
  String get(IWxMpConfig wxmpconfig,String url, String queryParam) throws WxErrorException;

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求
   */
  String post(IWxMpConfig wxmpconfig,String url, String postData) throws WxErrorException;

  /**
   * <pre>
   * Service没有实现某个API的时候，可以用这个，
   * 比{@link #get}和{@link #post}方法更灵活，可以自己构造RequestExecutor用来处理不同的参数和不同的返回类型。
   * 可以参考，{@link org.springrain.weixin.sdk.common.util.http.MediaUploadRequestExecutor}的实现方法
   * </pre>
   */
  <T, E> T execute(IWxMpConfig wxmpconfig,RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException;



  }
