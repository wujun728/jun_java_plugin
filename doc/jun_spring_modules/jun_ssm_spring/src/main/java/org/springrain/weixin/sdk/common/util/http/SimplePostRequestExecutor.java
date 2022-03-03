package org.springrain.weixin.sdk.common.util.http;

import java.io.IOException;

import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.springrain.frame.util.HttpClientUtils;
import org.springrain.weixin.sdk.common.api.IWxConfig;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.exception.WxErrorException;

/**
 * 简单的POST请求执行器，请求的参数是String, 返回的结果也是String
 *
 * @author springrain
 */
public class SimplePostRequestExecutor implements RequestExecutor<String, String> {

  @Override
  public String execute(IWxConfig wxconfig, String uri, String postEntity) throws WxErrorException, IOException {
    HttpPost httpPost = new HttpPost(uri);
    if (wxconfig.getHttpProxyHost()!=null) {
        RequestConfig config = RequestConfig.custom().setProxy(new HttpHost(wxconfig.getHttpProxyHost(), wxconfig.getHttpProxyPort())).build();
        httpPost.setConfig(config);
      }

    if (postEntity != null) {
      StringEntity entity = new StringEntity(postEntity, Consts.UTF_8);
      httpPost.setEntity(entity);
    }
      String responseContent = HttpClientUtils.sendHttpPost(httpPost);
      
      if (responseContent.isEmpty()) {
        throw new WxErrorException(
            WxError.newBuilder().setErrorCode(9999).setErrorMsg("无响应内容")
                .build());
      }

      if (responseContent.startsWith("<xml>")) {
        //xml格式输出直接返回
        return responseContent;
      }

      WxError error = WxError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      }
      return responseContent;
   
  }

}
