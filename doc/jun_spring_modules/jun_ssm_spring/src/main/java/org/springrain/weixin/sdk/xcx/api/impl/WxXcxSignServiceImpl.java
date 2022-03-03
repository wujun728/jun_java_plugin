package org.springrain.weixin.sdk.xcx.api.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springrain.frame.util.HttpClientUtils;
import org.springrain.weixin.sdk.common.api.IWxXcxConfig;
import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.BeanUtils;
import org.springrain.weixin.sdk.common.util.xml.XStreamInitializer;
import org.springrain.weixin.sdk.xcx.api.IWxXcxSignService;
import org.springrain.weixin.sdk.xcx.bean.result.sign.request.WxSignRequest;
import org.springrain.weixin.sdk.xcx.bean.result.sign.request.WxSurrenderRequest;
import org.springrain.weixin.sdk.xcx.bean.result.sign.result.WxSignBaseResult;
import org.springrain.weixin.sdk.xcx.bean.result.sign.result.WxSignResult;
import org.springrain.weixin.sdk.xcx.bean.result.sign.result.WxSurrenderResult;

import com.thoughtworks.xstream.XStream;

@Service("wxXcxSignService")
public class WxXcxSignServiceImpl implements IWxXcxSignService {
	
	private static final String PAY_BASE_URL = WxConsts.mppaybaseurl;
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public WxSignResult getWxSignInfo(IWxXcxConfig wxxcxconfig,
			WxSignRequest request) throws WxErrorException {

		XStream xstream = XStreamInitializer.getInstance();
		xstream.processAnnotations(WxSignRequest.class);
		xstream.processAnnotations(WxSignResult.class);

		request.setAppid(wxxcxconfig.getAppId());
		request.setMchId(wxxcxconfig.getPartnerId());

		String sign = createSign(wxxcxconfig, BeanUtils.xmlBean2Map(request),
				wxxcxconfig.getPartnerKey());
		request.setSign(sign);

		String url = PAY_BASE_URL + "/papay/entrustweb";

		String responseContent = executeRequest(wxxcxconfig, url,
				xstream.toXML(request));

		WxSignResult result = (WxSignResult) xstream.fromXML(responseContent);
        
		//校验结果
		checkSignResult(wxxcxconfig, result);

		return result;
	}
	
	/**
	 * 微信公众号支付签名算法(详见:https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.
	 * php?chapter=4_3)
	 *
	 * @param packageParams
	 *            原始参数
	 * @param signKey
	 *            加密Key(即 商户Key)
	 * @return 签名字符串
	 */
	private String createSign(IWxXcxConfig wxxcxconfig,
			Map<String, String> packageParams, String signKey) {
		SortedMap<String, String> sortedMap = new TreeMap<>(packageParams);

		StringBuilder toSign = new StringBuilder();
		for (String key : sortedMap.keySet()) {
			String value = packageParams.get(key);
			if (null != value && !"".equals(value) && !"sign".equals(key)
					&& !"key".equals(key)) {
				toSign.append(key + "=" + value + "&");
			}
		}

		toSign.append("key=" + signKey);

		return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
	}
	  
	
	private String executeRequest(IWxXcxConfig wxxcxconfig, String url,
			String requestStr) throws WxErrorException {
		HttpPost httpPost = new HttpPost(url);
		if (wxxcxconfig.getHttpProxyHost() != null) {
			RequestConfig config = RequestConfig
					.custom()
					.setProxy(
							new HttpHost(wxxcxconfig.getHttpProxyHost(),
									wxxcxconfig.getHttpProxyPort())).build();
			httpPost.setConfig(config);
		}

		try {
			CloseableHttpClient httpclient = HttpClientUtils.getHttpClient();
			httpPost.setEntity(new StringEntity(new String(requestStr
					.getBytes("UTF-8"), "ISO-8859-1")));

			try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
				String result = EntityUtils.toString(response.getEntity(),
						Consts.UTF_8);
				log.debug("\n[URL]:  {}\n[PARAMS]: {}\n[RESPONSE]: {}", url,
						requestStr, result);
				return result;
			}
		} catch (IOException e) {
			log.error("\n[URL]:  {}\n[PARAMS]: {}\n[EXCEPTION]: {}", url,
					requestStr, e.getMessage());
			throw new WxErrorException(WxError.newBuilder().setErrorCode(-1)
					.setErrorMsg(e.getMessage()).build(), e);
		} finally {
			httpPost.releaseConnection();
		}
	}
	  
	
	
	private void checkSignResult(IWxXcxConfig wxxcxconfig,
			WxSignBaseResult result) throws WxErrorException {
		if (!"SUCCESS".equalsIgnoreCase(result.getReturnCode())
				|| !"SUCCESS".equalsIgnoreCase(result.getResultCode())) {
			throw new WxErrorException(WxError
					.newBuilder()
					.setErrorCode(-1)
					.setErrorMsg(
							"返回代码: " + result.getReturnCode() + ", 返回信息: "
									+ result.getReturnMsg() + ", 结果代码: "
									+ result.getResultCode() + ", 错误代码: "
									+ result.getErrCode() + ", 错误详情: "
									+ result.getErrCodeDes()).build());
		}
	}
	

	@Override
	public WxSurrenderResult getWxSurrenderInfo(IWxXcxConfig wxxcxconfig,
			WxSurrenderRequest request) throws WxErrorException {
		XStream xstream = XStreamInitializer.getInstance();
		xstream.processAnnotations(WxSurrenderRequest.class);
		xstream.processAnnotations(WxSurrenderResult.class);

		request.setAppid(wxxcxconfig.getAppId());
		request.setMchId(wxxcxconfig.getPartnerId());

		String sign = createSign(wxxcxconfig, BeanUtils.xmlBean2Map(request),
				wxxcxconfig.getPartnerKey());
		request.setSign(sign);

		String url = PAY_BASE_URL + "/papay/deletecontract";

		String responseContent = executeRequest(wxxcxconfig, url,
				xstream.toXML(request));

		WxSurrenderResult result = (WxSurrenderResult) xstream.fromXML(responseContent);
        
		//校验结果
		checkSignResult(wxxcxconfig, result);
		
		return result;

	}

	@Override
	public String getWxSignUrl(IWxXcxConfig wxxcxconfig, WxSignRequest request)
			throws WxErrorException {

		request.setAppid(wxxcxconfig.getAppId());
		request.setMchId(wxxcxconfig.getPartnerId());

		String sign = createSign(wxxcxconfig, BeanUtils.xmlBean2Map(request),
				wxxcxconfig.getPartnerKey());
		request.setSign(sign);
		
		String url = PAY_BASE_URL + "/papay/entrustweb";
		Map<String, String>  packageParams = BeanUtils.xmlBean2Map(request);
		SortedMap<String, String> sortedMap = new TreeMap<>(packageParams);
		StringBuilder toSign = new StringBuilder();
		
		toSign.append(url).append("?");
		
		for (String key : sortedMap.keySet()) {
			String value = packageParams.get(key);
			if (null != value && !"".equals(value)) {
				if("notify_url".equals(key)){
					try {
						value = java.net.URLEncoder.encode(value, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				toSign.append(key + "=" + value + "&");
			}
		}
		
		return toSign.toString();
	}

	@Override
	public Map<String, String> getSignParam(IWxXcxConfig wxxcxconfig,
			WxSignRequest request) throws WxErrorException {
		request.setAppid(wxxcxconfig.getAppId());
		request.setMchId(wxxcxconfig.getPartnerId());
		request.setPlanId(wxxcxconfig.getPlanId());
		request.setRequestSerial(wxxcxconfig.getRequestSerial());

		String sign = createSign(wxxcxconfig, BeanUtils.xmlBean2Map(request),
				wxxcxconfig.getPartnerKey());
		request.setSign(sign);
		
		String notifyURL = request.getNotifyURL();
		try {
			request.setNotifyURL(URLEncoder.encode(notifyURL, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		Map<String, String>  signParams = BeanUtils.xmlBean2Map(request);
		
		String url = PAY_BASE_URL + "/papay/entrustweb";
		signParams.put("url", url);
		
		return signParams;
	}
	
}
