package org.springrain.weixin.sdk.xcx.api.impl;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.frame.util.HttpClientUtils;
import org.springrain.weixin.sdk.common.api.IWxXcxConfig;
import org.springrain.weixin.sdk.common.api.IWxXcxConfigService;
import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.bean.WxAccessToken;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.http.RequestExecutor;
import org.springrain.weixin.sdk.common.util.http.SimpleGetRequestExecutor;
import org.springrain.weixin.sdk.common.util.http.SimplePostRequestExecutor;
import org.springrain.weixin.sdk.common.util.json.WxGsonBuilder;
import org.springrain.weixin.sdk.xcx.api.IWxXcxService;
import org.springrain.weixin.sdk.xcx.bean.result.WxMpOAuth2SessionKey;

/**
 * 
 * @author caomei
 *
 */
public class WxXcxServiceImpl implements IWxXcxService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// 生产环境应该是spring注入
	private IWxXcxConfigService wxXcxConfigService;

	public WxXcxServiceImpl() {

	}

	public WxXcxServiceImpl(IWxXcxConfigService wxXcxConfigService) {
		this.wxXcxConfigService = wxXcxConfigService;
	}

	@Override
	public WxMpOAuth2SessionKey oauth2getSessionKey(IWxXcxConfig wxxcxconfig, String code) throws WxErrorException {

		StringBuilder url = new StringBuilder();
		url.append(WxConsts.mpapiurl + "/sns/jscode2session?");
		url.append("appid=").append(wxxcxconfig.getAppId());
		url.append("&secret=").append(wxxcxconfig.getSecret());
		url.append("&js_code=").append(code);
		url.append("&grant_type=authorization_code");

		return getOAuth2SessionKey(wxxcxconfig, url);

	}

	private WxMpOAuth2SessionKey getOAuth2SessionKey(IWxXcxConfig wxxcxconfig, StringBuilder url)
			throws WxErrorException {
		try {
			RequestExecutor<String, String> executor = new SimpleGetRequestExecutor();
			String responseText = executor.execute(wxxcxconfig, url.toString(), null);
			return WxMpOAuth2SessionKey.fromJson(responseText);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getAccessToken(IWxXcxConfig wxxcxconfig) throws WxErrorException {
		return getAccessToken(wxxcxconfig, false);
	}

	@Override
	public String getAccessToken(IWxXcxConfig wxxcxconfig, boolean forceRefresh) throws WxErrorException {

		if (forceRefresh) {
			wxXcxConfigService.expireAccessToken(wxxcxconfig);
		}

		if (!wxxcxconfig.isAccessTokenExpired()) {
			return wxxcxconfig.getAccessToken();
		}

		WxAccessToken accessToken = wxXcxConfigService.getCustomAPIAccessToken(wxxcxconfig);
		if (accessToken == null) {
			String url = WxConsts.mpapiurl + "/cgi-bin/token?grant_type=client_credential" + "&appid="
					+ wxxcxconfig.getAppId() + "&secret=" + wxxcxconfig.getSecret();
			HttpGet httpGet = new HttpGet(url);
			if (wxxcxconfig.getHttpProxyHost() != null) {
				RequestConfig config = RequestConfig.custom()
						.setProxy(new HttpHost(wxxcxconfig.getHttpProxyHost(), wxxcxconfig.getHttpProxyPort())).build();
				httpGet.setConfig(config);
			}

			String resultContent = HttpClientUtils.sendHttpGet(httpGet);
			WxError error = WxError.fromJson(resultContent);
			if (error.getErrorCode() != 0) {
				throw new WxErrorException(error);
			}
			// accessToken = WxAccessToken.fromJson(resultContent);
			accessToken = WxGsonBuilder.create().fromJson(resultContent, WxAccessToken.class);
		}

		if (accessToken == null) {
			return null;
		}

		wxxcxconfig.setAccessToken(accessToken.getAccessToken());
		wxxcxconfig.setAccessTokenExpiresTime(Long.valueOf(accessToken.getExpiresIn()));
		wxXcxConfigService.updateAccessToken(wxxcxconfig);

		return wxxcxconfig.getAccessToken();
	}
	

	  /**
	   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求
	   */
	  @Override
	  public String get(IWxXcxConfig wxxcxconfig,String url, String queryParam) throws WxErrorException {
	    return execute(wxxcxconfig,new SimpleGetRequestExecutor(), url, queryParam);
	  }


	  /**
	   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求
	   */
	  @Override
	  public String post(IWxXcxConfig wxxcxconfig,String url, String postData) throws WxErrorException {
	    return execute(wxxcxconfig,new SimplePostRequestExecutor(), url, postData);
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
	  public <T, E> T execute(IWxXcxConfig wxxcxconfig,RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
		int retrySleepMillis = 1000;
		int maxRetryTimes = 5;
	    int retryTimes = 0;
	    
	    do {
	      try {
	        T result = executeInternal(wxxcxconfig,executor, uri, data);
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
	            throw new RuntimeException(e1);
	          }
	        } else {
	          throw e;
	        }
	      }
	    } while (++retryTimes < maxRetryTimes);

	    throw new RuntimeException("微信服务端异常，超出重试次数");
	  }

	  protected synchronized <T, E> T executeInternal(IWxXcxConfig wxxcxconfig,RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException {
	    if (uri.indexOf("access_token=") != -1) {
	      throw new IllegalArgumentException("uri参数中不允许有access_token: " + uri);
	    }
	    String accessToken = getAccessToken(wxxcxconfig,false);

	    String uriWithAccessToken = uri;
	    uriWithAccessToken += uri.indexOf('?') == -1 ? "?access_token=" + accessToken : "&access_token=" + accessToken;

	    try {
	      return executor.execute(wxxcxconfig, uriWithAccessToken, data);
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
	        //wxxcxconfig.expireAccessToken();
	        wxXcxConfigService.expireAccessToken(wxxcxconfig);
	        if(wxxcxconfig.autoRefreshToken()){
	          return execute(wxxcxconfig,executor, uri, data);
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
