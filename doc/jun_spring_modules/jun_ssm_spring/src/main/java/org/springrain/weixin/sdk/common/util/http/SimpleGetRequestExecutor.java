package org.springrain.weixin.sdk.common.util.http;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.springrain.frame.util.HttpClientUtils;
import org.springrain.weixin.sdk.common.api.IWxConfig;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.exception.WxErrorException;

/**
 * 简单的GET请求执行器，请求的参数是String, 返回的结果也是String
 *
 * @author springrain
 */
public class SimpleGetRequestExecutor implements RequestExecutor<String, String> {

  @Override
  public String execute(IWxConfig wxconfig, String uri, String queryParam) throws WxErrorException, IOException {
    if (queryParam != null) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
    }
    HttpGet httpGet = new HttpGet(uri);
    if (wxconfig.getHttpProxyHost()!=null) {
      RequestConfig config = RequestConfig.custom().setProxy(new HttpHost(wxconfig.getHttpProxyHost(), wxconfig.getHttpProxyPort())).build();
      httpGet.setConfig(config);
    }
      String responseContent = HttpClientUtils.sendHttpGet(httpGet);
      WxError error = WxError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      }
      return responseContent;
  }

}
