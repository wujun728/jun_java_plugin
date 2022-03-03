package org.springrain.weixin.sdk.mp.util.http;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.frame.util.HttpClientUtils;
import org.springrain.weixin.sdk.common.api.IWxConfig;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.http.InputStreamResponseHandler;
import org.springrain.weixin.sdk.common.util.http.RequestExecutor;
import org.springrain.weixin.sdk.common.util.json.WxGsonBuilder;

public class MaterialVoiceAndImageDownloadRequestExecutor implements RequestExecutor<InputStream, String> {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  @SuppressWarnings("unused")
  private File tmpDirFile;

  public MaterialVoiceAndImageDownloadRequestExecutor() {
    super();
  }

  public MaterialVoiceAndImageDownloadRequestExecutor(File tmpDirFile) {
    super();
    this.tmpDirFile = tmpDirFile;
  }

  @Override
  public InputStream execute(IWxConfig wxconfig, String uri, String materialId) throws WxErrorException, IOException {
		 HttpPost httpPost = new HttpPost(uri);
		 if (wxconfig.getHttpProxyHost()!=null) {
		        RequestConfig config = RequestConfig.custom().setProxy(new HttpHost(wxconfig.getHttpProxyHost(), wxconfig.getHttpProxyPort())).build();
		        httpPost.setConfig(config);
		  }


    Map<String, String> params = new HashMap<>();
    params.put("media_id", materialId);
    httpPost.setEntity(new StringEntity(WxGsonBuilder.create().toJson(params)));
    try (CloseableHttpResponse response = HttpClientUtils.getHttpClient().execute(httpPost);
        InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response);){
      // 下载媒体文件出错
      byte[] responseContent = IOUtils.toByteArray(inputStream);
      String responseContentString = new String(responseContent, "UTF-8");
      if (responseContentString.length() < 100) {
        try {
          WxError wxError = WxGsonBuilder.create().fromJson(responseContentString, WxError.class);
          if (wxError.getErrorCode() != 0) {
            throw new WxErrorException(wxError);
          }
        } catch (com.google.gson.JsonSyntaxException ex) {
          logger.error(ex.getMessage(),ex);
          return new ByteArrayInputStream(responseContent);
        }
      }
      return new ByteArrayInputStream(responseContent);
    }finally {
      httpPost.releaseConnection();
    }
  }

}
