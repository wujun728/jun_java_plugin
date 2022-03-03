package org.springrain.weixin.sdk.mp.util.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

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
import org.springrain.weixin.sdk.common.util.json.WxGsonBuilder;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterial;
import org.springrain.weixin.sdk.mp.bean.material.WxMpMaterialUploadResult;

public class MaterialUploadRequestExecutor implements RequestExecutor<WxMpMaterialUploadResult, WxMpMaterial> {

  @Override
  public WxMpMaterialUploadResult execute(IWxConfig wxconfig, String uri, WxMpMaterial material) throws WxErrorException, IOException {
	 HttpPost httpPost = new HttpPost(uri);
	 if (wxconfig.getHttpProxyHost()!=null) {
	        RequestConfig config = RequestConfig.custom().setProxy(new HttpHost(wxconfig.getHttpProxyHost(), wxconfig.getHttpProxyPort())).build();
	        httpPost.setConfig(config);
	  }

    if (material == null) {
      throw new WxErrorException(WxError.newBuilder().setErrorMsg("非法请求，material参数为空").build());
    }

    File file = material.getFile();
    if (file == null || !file.exists()) {
      throw new FileNotFoundException();
    }

    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
    multipartEntityBuilder
        .addBinaryBody("media", file)
        .setMode(HttpMultipartMode.RFC6532);
    Map<String, String> form = material.getForm();
    if (material.getForm() != null) {
      multipartEntityBuilder.addTextBody("description", WxGsonBuilder.create().toJson(form));
    }

    httpPost.setEntity(multipartEntityBuilder.build());
    httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());

     String responseContent = HttpClientUtils.sendHttpPost(httpPost);
      WxError error = WxError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      } else {
        return WxMpMaterialUploadResult.fromJson(responseContent);
      }
  }

}
