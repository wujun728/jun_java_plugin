package org.springrain.weixin.sdk.mp.api.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.frame.util.HttpClientUtils;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.WxConsts;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.BeanUtils;
import org.springrain.weixin.sdk.common.util.xml.XStreamInitializer;
import org.springrain.weixin.sdk.mp.api.IWxMpPayService;
import org.springrain.weixin.sdk.mp.api.IWxMpService;
import org.springrain.weixin.sdk.mp.bean.pay.WxPayJsSDKCallback;
import org.springrain.weixin.sdk.mp.bean.pay.request.WxEntPayQueryRequest;
import org.springrain.weixin.sdk.mp.bean.pay.request.WxEntPayRequest;
import org.springrain.weixin.sdk.mp.bean.pay.request.WxPayOrderCloseRequest;
import org.springrain.weixin.sdk.mp.bean.pay.request.WxPayOrderQueryRequest;
import org.springrain.weixin.sdk.mp.bean.pay.request.WxPayRedpackQueryRequest;
import org.springrain.weixin.sdk.mp.bean.pay.request.WxPayRefundQueryRequest;
import org.springrain.weixin.sdk.mp.bean.pay.request.WxPayRefundRequest;
import org.springrain.weixin.sdk.mp.bean.pay.request.WxPaySendRedpackRequest;
import org.springrain.weixin.sdk.mp.bean.pay.request.WxPayUnifiedOrderRequest;
import org.springrain.weixin.sdk.mp.bean.pay.result.WxEntPayQueryResult;
import org.springrain.weixin.sdk.mp.bean.pay.result.WxEntPayResult;
import org.springrain.weixin.sdk.mp.bean.pay.result.WxPayBaseResult;
import org.springrain.weixin.sdk.mp.bean.pay.result.WxPayOrderCloseResult;
import org.springrain.weixin.sdk.mp.bean.pay.result.WxPayOrderQueryResult;
import org.springrain.weixin.sdk.mp.bean.pay.result.WxPayRedpackQueryResult;
import org.springrain.weixin.sdk.mp.bean.pay.result.WxPayRefundQueryResult;
import org.springrain.weixin.sdk.mp.bean.pay.result.WxPayRefundResult;
import org.springrain.weixin.sdk.mp.bean.pay.result.WxPaySendRedpackResult;
import org.springrain.weixin.sdk.mp.bean.pay.result.WxPayUnifiedOrderResult;

import com.thoughtworks.xstream.XStream;

/**
 * Created by springrain on 2017/1/8.
 *
 * @author springrain (http://git.oschina.net/chunanyong/springrain)
 */

public class WxMpPayServiceImpl implements IWxMpPayService {

  private static final String PAY_BASE_URL = WxConsts.mppaybaseurl;
  private static final String[] TRADE_TYPES = new String[]{"JSAPI", "NATIVE", "APP"};
  private static final String[] REFUND_ACCOUNT = new String[]{"REFUND_SOURCE_RECHARGE_FUNDS",
    "REFUND_SOURCE_UNSETTLED_FUNDS"};
  private final Logger log = LoggerFactory.getLogger(getClass());
  
  private IWxMpService wxMpService;

  public WxMpPayServiceImpl() {
  }
  public WxMpPayServiceImpl(IWxMpService wxMpService) {
	  this.wxMpService=wxMpService;
  }
  

  @Override
  public WxPayRefundResult refund(IWxMpConfig wxmpconfig,WxPayRefundRequest request, File keyFile)
    throws WxErrorException {
    checkParameters(wxmpconfig,request);

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPayRefundRequest.class);
    xstream.processAnnotations(WxPayRefundResult.class);

    request.setAppid(wxmpconfig.getAppId());
    String partnerId = wxmpconfig.getPartnerId();
    request.setMchId(partnerId);
    request.setNonceStr(String.valueOf(System.currentTimeMillis()));
    request.setOpUserId(partnerId);
    String sign = createSign(wxmpconfig,BeanUtils.xmlBean2Map(request), wxmpconfig.getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/secapi/pay/refund";
    String responseContent = executeRequestWithKeyFile(wxmpconfig,url, keyFile, xstream.toXML(request), partnerId);
    WxPayRefundResult result = (WxPayRefundResult) xstream.fromXML(responseContent);
    checkResult(wxmpconfig,result);
    return result;
  }

  @Override
  public WxPayRefundQueryResult refundQuery(IWxMpConfig wxmpconfig,String transactionId, String outTradeNo, String outRefundNo, String refundId) throws WxErrorException {
    if ((StringUtils.isBlank(transactionId) && StringUtils.isBlank(outTradeNo) && StringUtils.isBlank(outRefundNo) && StringUtils.isBlank(refundId)) ||
      (StringUtils.isNotBlank(transactionId) && StringUtils.isNotBlank(outTradeNo) && StringUtils.isNotBlank(outRefundNo) && StringUtils.isNotBlank(refundId))) {
      throw new IllegalArgumentException("transaction_id ， out_trade_no，out_refund_no， refund_id 必须四选一");
    }

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPayRefundQueryRequest.class);
    xstream.processAnnotations(WxPayRefundQueryResult.class);

    WxPayRefundQueryRequest request = new WxPayRefundQueryRequest();
    request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
    request.setTransactionId(StringUtils.trimToNull(transactionId));
    request.setOutRefundNo(StringUtils.trimToNull(outRefundNo));
    request.setRefundId(StringUtils.trimToNull(refundId));

    request.setAppid(wxmpconfig.getAppId());
    request.setMchId(wxmpconfig.getPartnerId());
    request.setNonceStr(String.valueOf(System.currentTimeMillis()));

    String sign = createSign(wxmpconfig,BeanUtils.xmlBean2Map(request),
      wxmpconfig.getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/pay/refundquery";

    String responseContent = executeRequest(wxmpconfig,url, xstream.toXML(request));
    WxPayRefundQueryResult result = (WxPayRefundQueryResult) xstream.fromXML(responseContent);
    result.composeRefundRecords(responseContent);
    checkResult(wxmpconfig,result);
    return result;
  }

  private void checkResult(IWxMpConfig wxmpconfig,WxPayBaseResult result) throws WxErrorException {
    if (!"SUCCESS".equalsIgnoreCase(result.getReturnCode())
      || !"SUCCESS".equalsIgnoreCase(result.getResultCode())) {
      throw new WxErrorException(WxError.newBuilder().setErrorCode(-1)
        .setErrorMsg("返回代码: " + result.getReturnCode() + ", 返回信息: "
          + result.getReturnMsg() + ", 结果代码: " + result.getResultCode() + ", 错误代码: "
          + result.getErrCode() + ", 错误详情: " + result.getErrCodeDes())
        .build());
    }
  }

  private void checkParameters(IWxMpConfig wxmpconfig,WxPayRefundRequest request) throws WxErrorException {
    BeanUtils.checkRequiredFields(request);

    if (StringUtils.isNotBlank(request.getRefundAccount())) {
      if (!ArrayUtils.contains(REFUND_ACCOUNT, request.getRefundAccount())) {
        throw new IllegalArgumentException("refund_account目前必须为" + Arrays.toString(REFUND_ACCOUNT) + "其中之一");
      }
    }

    if (StringUtils.isBlank(request.getOutTradeNo()) && StringUtils.isBlank(request.getTransactionId())) {
      throw new IllegalArgumentException("transaction_id 和 out_trade_no 不能同时为空，必须提供一个");
    }
  }

  @Override
  public WxPayJsSDKCallback getJSSDKCallbackData(IWxMpConfig wxmpconfig,String xmlData) throws WxErrorException {
    try {
      XStream xstream = XStreamInitializer.getInstance();
      xstream.alias("xml", WxPayJsSDKCallback.class);
      return (WxPayJsSDKCallback) xstream.fromXML(xmlData);
    } catch (Exception e) {
    	log.error(e.getMessage(), e);
      throw new WxErrorException(WxError.newBuilder().setErrorMsg("发生异常" + e.getMessage()).build());
    }
  }

  @Override
  public boolean checkJSSDKCallbackDataSignature(IWxMpConfig wxmpconfig,Map<String, String> kvm,
                                                 String signature) {
    return signature.equals(createSign(wxmpconfig,kvm,wxmpconfig.getPartnerKey()));
  }

  @Override
  public WxPaySendRedpackResult sendRedpack(IWxMpConfig wxmpconfig,WxPaySendRedpackRequest request, File keyFile)
    throws WxErrorException {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPaySendRedpackRequest.class);
    xstream.processAnnotations(WxPaySendRedpackResult.class);

    request.setWxAppid(wxmpconfig.getAppId());
    String mchId = wxmpconfig.getPartnerId();
    request.setMchId(mchId);
    request.setNonceStr(String.valueOf(System.currentTimeMillis()));

    String sign = createSign(wxmpconfig,BeanUtils.xmlBean2Map(request),
      wxmpconfig.getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/mmpaymkttransfers/sendredpack";
    if (request.getAmtType() != null) {
      //裂变红包
      url = PAY_BASE_URL + "/mmpaymkttransfers/sendgroupredpack";
    }

    String responseContent = executeRequestWithKeyFile(wxmpconfig,url, keyFile, xstream.toXML(request), mchId);
    WxPaySendRedpackResult result = (WxPaySendRedpackResult) xstream
      .fromXML(responseContent);
    checkResult(wxmpconfig,result);
    return result;
  }

  @Override
  public WxPayRedpackQueryResult queryRedpack(IWxMpConfig wxmpconfig,String mchBillNo, File keyFile) throws WxErrorException {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPayRedpackQueryRequest.class);
    xstream.processAnnotations(WxPayRedpackQueryResult.class);

    WxPayRedpackQueryRequest request = new WxPayRedpackQueryRequest();
    request.setMchBillNo(mchBillNo);
    request.setBillType("MCHT");

    request.setAppid(wxmpconfig.getAppId());
    String mchId = wxmpconfig.getPartnerId();
    request.setMchId(mchId);
    request.setNonceStr(String.valueOf(System.currentTimeMillis()));

    String sign = createSign(wxmpconfig,BeanUtils.xmlBean2Map(request),
      wxmpconfig.getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/mmpaymkttransfers/gethbinfo";
    String responseContent = executeRequestWithKeyFile(wxmpconfig,url, keyFile, xstream.toXML(request), mchId);
    WxPayRedpackQueryResult result = (WxPayRedpackQueryResult) xstream.fromXML(responseContent);
    checkResult(wxmpconfig,result);
    return result;
  }

  /**
   * 微信公众号支付签名算法(详见:https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=4_3)
   *
   * @param packageParams 原始参数
   * @param signKey       加密Key(即 商户Key)
   * @return 签名字符串
   */
  private String createSign(IWxMpConfig wxmpconfig,Map<String, String> packageParams, String signKey) {
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

  @Override
  public WxPayOrderQueryResult queryOrder(IWxMpConfig wxmpconfig,String transactionId, String outTradeNo) throws WxErrorException {
    if ((StringUtils.isBlank(transactionId) && StringUtils.isBlank(outTradeNo)) ||
      (StringUtils.isNotBlank(transactionId) && StringUtils.isNotBlank(outTradeNo))) {
      throw new IllegalArgumentException("transaction_id 和 out_trade_no 不能同时存在或同时为空，必须二选一");
    }

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPayOrderQueryRequest.class);
    xstream.processAnnotations(WxPayOrderQueryResult.class);

    WxPayOrderQueryRequest request = new WxPayOrderQueryRequest();
    request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
    request.setTransactionId(StringUtils.trimToNull(transactionId));
    request.setAppid(wxmpconfig.getAppId());
    request.setMchId(wxmpconfig.getPartnerId());
    request.setNonceStr(String.valueOf(System.currentTimeMillis()));

    String sign = createSign(wxmpconfig,BeanUtils.xmlBean2Map(request),
      wxmpconfig.getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/pay/orderquery";

    String responseContent = executeRequest(wxmpconfig,url, xstream.toXML(request));
    WxPayOrderQueryResult result = (WxPayOrderQueryResult) xstream.fromXML(responseContent);
    result.composeCoupons(responseContent);
    checkResult(wxmpconfig,result);
    return result;
  }

  @Override
  public WxPayOrderCloseResult closeOrder(IWxMpConfig wxmpconfig,String outTradeNo) throws WxErrorException {
    if (StringUtils.isBlank(outTradeNo)) {
      throw new IllegalArgumentException("out_trade_no 不能为空");
    }

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPayOrderCloseRequest.class);
    xstream.processAnnotations(WxPayOrderCloseResult.class);

    WxPayOrderCloseRequest request = new WxPayOrderCloseRequest();
    request.setOutTradeNo(StringUtils.trimToNull(outTradeNo));
    request.setAppid(wxmpconfig.getAppId());
    request.setMchId(wxmpconfig.getPartnerId());
    request.setNonceStr(String.valueOf(System.currentTimeMillis()));

    String sign = createSign(wxmpconfig,BeanUtils.xmlBean2Map(request),
      wxmpconfig.getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/pay/closeorder";

    String responseContent = executeRequest(wxmpconfig,url, xstream.toXML(request));
    WxPayOrderCloseResult result = (WxPayOrderCloseResult) xstream.fromXML(responseContent);
    checkResult(wxmpconfig,result);

    return result;
  }

  @Override
  public WxPayUnifiedOrderResult unifiedOrder(IWxMpConfig wxmpconfig,WxPayUnifiedOrderRequest request)
    throws WxErrorException {
    checkParameters(wxmpconfig,request);

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxPayUnifiedOrderRequest.class);
    xstream.processAnnotations(WxPayUnifiedOrderResult.class);

    request.setAppid(wxmpconfig.getAppId());
    request.setMchId(wxmpconfig.getPartnerId());
    request.setNonceStr(String.valueOf(System.currentTimeMillis()));

    String sign = createSign(wxmpconfig,BeanUtils.xmlBean2Map(request),
      wxmpconfig.getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/pay/unifiedorder";

    String responseContent = executeRequest(wxmpconfig,url, xstream.toXML(request));
    WxPayUnifiedOrderResult result = (WxPayUnifiedOrderResult) xstream
      .fromXML(responseContent);
    checkResult(wxmpconfig,result);
    return result;
  }

  private void checkParameters(IWxMpConfig wxmpconfig,WxPayUnifiedOrderRequest request) throws WxErrorException {
    BeanUtils.checkRequiredFields(request);

    if (!ArrayUtils.contains(TRADE_TYPES, request.getTradeType())) {
      throw new IllegalArgumentException("trade_type目前必须为" + Arrays.toString(TRADE_TYPES) + "其中之一");
    }

    if ("JSAPI".equals(request.getTradeType()) && request.getOpenid() == null) {
      throw new IllegalArgumentException("当 trade_type是'JSAPI'时未指定openid");
    }

    if ("NATIVE".equals(request.getTradeType()) && request.getProductId() == null) {
      throw new IllegalArgumentException("当 trade_type是'NATIVE'时未指定product_id");
    }
  }

  @Override
  public Map<String, String> getPayInfo(IWxMpConfig wxmpconfig,WxPayUnifiedOrderRequest request) throws WxErrorException {
    WxPayUnifiedOrderResult unifiedOrderResult = unifiedOrder(wxmpconfig,request);
    String prepayId = unifiedOrderResult.getPrepayId();
    if (StringUtils.isBlank(prepayId)) {
      throw new RuntimeException(String.format("Failed to get prepay id due to error code '%s'(%s).",
        unifiedOrderResult.getErrCode(), unifiedOrderResult.getErrCodeDes()));
    }

    Map<String, String> payInfo = new HashMap<>();
    payInfo.put("appId", wxmpconfig.getAppId());
    // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
    payInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
    payInfo.put("nonceStr", String.valueOf(System.currentTimeMillis()));
    payInfo.put("package", "prepay_id=" + prepayId);
    payInfo.put("signType", "MD5");
    if ("NATIVE".equals(request.getTradeType())) {
      payInfo.put("codeUrl", unifiedOrderResult.getCodeURL());
    }

    String finalSign = createSign(wxmpconfig,payInfo, wxmpconfig.getPartnerKey());
    payInfo.put("paySign", finalSign);
    return payInfo;
  }

  @Override
  public WxEntPayResult entPay(IWxMpConfig wxmpconfig,WxEntPayRequest request, File keyFile) throws WxErrorException {
    BeanUtils.checkRequiredFields(request);

    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxEntPayRequest.class);
    xstream.processAnnotations(WxEntPayResult.class);

    request.setMchAppid(wxmpconfig.getAppId());
    request.setMchId(wxmpconfig.getPartnerId());
    request.setNonceStr(String.valueOf(System.currentTimeMillis()));

    String sign = createSign(wxmpconfig,BeanUtils.xmlBean2Map(request), wxmpconfig.getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/mmpaymkttransfers/promotion/transfers";

    String responseContent = executeRequestWithKeyFile(wxmpconfig,url, keyFile, xstream.toXML(request), request.getMchId());
    WxEntPayResult result = (WxEntPayResult) xstream.fromXML(responseContent);
    checkResult(wxmpconfig,result);
    return result;
  }

  @Override
  public WxEntPayQueryResult queryEntPay(IWxMpConfig wxmpconfig,String partnerTradeNo, File keyFile) throws WxErrorException {
    XStream xstream = XStreamInitializer.getInstance();
    xstream.processAnnotations(WxEntPayQueryRequest.class);
    xstream.processAnnotations(WxEntPayQueryResult.class);

    WxEntPayQueryRequest request = new WxEntPayQueryRequest();
    request.setAppid(wxmpconfig.getAppId());
    request.setMchId(wxmpconfig.getPartnerId());
    request.setNonceStr(String.valueOf(System.currentTimeMillis()));

    String sign = createSign(wxmpconfig,BeanUtils.xmlBean2Map(request), wxmpconfig.getPartnerKey());
    request.setSign(sign);

    String url = PAY_BASE_URL + "/mmpaymkttransfers/gettransferinfo";

    String responseContent = executeRequestWithKeyFile(wxmpconfig,url, keyFile, xstream.toXML(request), request.getMchId());
    WxEntPayQueryResult result = (WxEntPayQueryResult) xstream.fromXML(responseContent);
    checkResult(wxmpconfig,result);
    return result;
  }

  private String executeRequest(IWxMpConfig wxmpconfig,String url, String requestStr) throws WxErrorException {
	  HttpPost httpPost = new HttpPost(url);
	    if (wxmpconfig.getHttpProxyHost()!=null) {
	        RequestConfig config = RequestConfig.custom().setProxy(new HttpHost(wxmpconfig.getHttpProxyHost(), wxmpconfig.getHttpProxyPort())).build();
	        httpPost.setConfig(config);
	      }


    try  {
      CloseableHttpClient httpclient = HttpClientUtils.getHttpClient();
      httpPost.setEntity(new StringEntity(new String(requestStr.getBytes("UTF-8"), "ISO-8859-1")));

      try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
        String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
        log.debug("\n[URL]:  {}\n[PARAMS]: {}\n[RESPONSE]: {}", url, requestStr, result);
        return result;
      }
    } catch (IOException e) {
      log.error("\n[URL]:  {}\n[PARAMS]: {}\n[EXCEPTION]: {}", url, requestStr, e.getMessage());
      throw new WxErrorException(WxError.newBuilder().setErrorCode(-1).setErrorMsg(e.getMessage()).build(), e);
    } finally {
      httpPost.releaseConnection();
    }
  }

  private String executeRequestWithKeyFile(IWxMpConfig wxmpconfig,String url, File keyFile, String requestStr, String mchId) throws WxErrorException {
    try (FileInputStream inputStream = new FileInputStream(keyFile)) {
      KeyStore keyStore = KeyStore.getInstance("PKCS12");
      keyStore.load(inputStream, mchId.toCharArray());

      SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
        new DefaultHostnameVerifier());

      HttpPost httpPost = new HttpPost(url);
	    if (wxmpconfig.getHttpProxyHost()!=null) {
	        RequestConfig config = RequestConfig.custom().setProxy(new HttpHost(wxmpconfig.getHttpProxyHost(), wxmpconfig.getHttpProxyPort())).build();
	        httpPost.setConfig(config);
	      }
	    CloseableHttpClient httpclient = HttpClientUtils.getHttpClientBuilder().setSSLSocketFactory(sslsf).build();
      try  {
        httpPost.setEntity(new StringEntity(new String(requestStr.getBytes("UTF-8"), "ISO-8859-1")));
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
          String result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
          log.debug("\n[URL]:  {}\n[PARAMS]: {}\n[RESPONSE]: {}", url, requestStr, result);
          return result;
        }
      } finally {
        httpPost.releaseConnection();
      }
    } catch (Exception e) {
      log.error("\n[URL]:  {}\n[PARAMS]: {}\n[EXCEPTION]: {}", url, requestStr, e.getMessage());
      throw new WxErrorException(WxError.newBuilder().setErrorCode(-1).setErrorMsg(e.getMessage()).build(), e);
    }
  }

}
