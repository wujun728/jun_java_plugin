package org.springrain.weixin.sdk.xcx.api;

import org.springrain.weixin.sdk.common.api.IWxXcxConfig;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.http.RequestExecutor;
import org.springrain.weixin.sdk.xcx.bean.result.WxMpOAuth2SessionKey;

/**
 * 用户管理相关操作接口
 *
 * @author springrain
 */
public interface IWxXcxService {

	  /**
	   * 获取access_token, 不强制刷新access_token
	 * @throws Exception 
	   *
	   * @see #getAccessToken(boolean)
	   */
	  String getAccessToken(IWxXcxConfig wxxcxconfig) throws WxErrorException;

	  /**
	   * <pre>
	   * 获取access_token，本方法线程安全
	   * 且在多线程同时刷新时只刷新一次，避免超出2000次/日的调用次数上限
	   *
	   * 另：本service的所有方法都会在access_token过期是调用此方法
	   *
	   * 程序员在非必要情况下尽量不要主动调用此方法
	   *
	   * 详情请见: https://mp.weixin.qq.com/debug/wxadoc/dev/api/notice.html
	   * </pre>
	   *
	   * @param forceRefresh 强制刷新
	 * @throws Exception 
	   */
	  String getAccessToken(IWxXcxConfig wxxcxconfig,boolean forceRefresh) throws WxErrorException, Exception;
	
	
	/**
	 * <pre>
	  * 用code换取oauth2的sessionKey
	  * 详情请见: https://mp.weixin.qq.com/debug/wxadoc/dev/api/api-login.html#wxloginobject
	 * </pre>
	 * @param wxxcxconfig
	 * @param code
	 * @throws WxErrorException
	 */
	WxMpOAuth2SessionKey oauth2getSessionKey(IWxXcxConfig wxxcxconfig, String code) throws WxErrorException;
	
	/**
	   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求
	   */
	  String get(IWxXcxConfig wxxcxconfig,String url, String queryParam) throws WxErrorException;

	  /**
	   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求
	   */
	  String post(IWxXcxConfig wxxcxconfig,String url, String postData) throws WxErrorException;

	  /**
	   * <pre>
	   * Service没有实现某个API的时候，可以用这个，
	   * 比{@link #get}和{@link #post}方法更灵活，可以自己构造RequestExecutor用来处理不同的参数和不同的返回类型。
	   * 可以参考，{@link org.springrain.weixin.sdk.common.util.http.MediaUploadRequestExecutor}的实现方法
	   * </pre>
	   */
	  <T, E> T execute(IWxXcxConfig wxxcxconfig,RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException;


}
