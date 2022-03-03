package org.springrain.weixin.sdk.mp.util.http;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springrain.frame.util.HttpClientUtils;
import org.springrain.weixin.sdk.common.api.IWxConfig;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.http.RequestExecutor;
import org.springrain.weixin.sdk.mp.bean.material.WxMediaImgUploadResult;

/**
 * @author springrain
 */
public class MediaImgUploadRequestExecutor implements RequestExecutor<WxMediaImgUploadResult, File> {
  @Override
  public WxMediaImgUploadResult execute(IWxConfig wxconfig, String uri, File data) throws WxErrorException, IOException {
    if (data == null) {
      throw new WxErrorException(WxError.newBuilder().setErrorMsg("文件对象为空").build());
    }

    HttpPost httpPost = new HttpPost(uri);
    if (wxconfig.getHttpProxyHost()!=null) {
        RequestConfig config = RequestConfig.custom().setProxy(new HttpHost(wxconfig.getHttpProxyHost(), wxconfig.getHttpProxyPort())).build();
        httpPost.setConfig(config);
  }


    HttpEntity entity = MultipartEntityBuilder
      .create()
      .addBinaryBody("media", data)
      .setMode(HttpMultipartMode.RFC6532)
      .build();
    httpPost.setEntity(entity);
    httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());

    String responseContent = HttpClientUtils.sendHttpPost(httpPost);
      WxError error = WxError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      }

      return WxMediaImgUploadResult.fromJson(responseContent);
    }
}
