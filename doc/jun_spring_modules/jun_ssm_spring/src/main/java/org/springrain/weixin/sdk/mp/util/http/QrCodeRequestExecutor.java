package org.springrain.weixin.sdk.mp.util.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.springrain.frame.util.HttpClientUtils;
import org.springrain.weixin.sdk.common.api.IWxConfig;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.fs.FileUtils;
import org.springrain.weixin.sdk.common.util.http.InputStreamResponseHandler;
import org.springrain.weixin.sdk.common.util.http.RequestExecutor;
import org.springrain.weixin.sdk.common.util.http.Utf8ResponseHandler;
import org.springrain.weixin.sdk.mp.bean.result.WxMpQrCodeTicket;

/**
 * 获得QrCode图片 请求执行器
 * @author springrain
 *
 */
public class QrCodeRequestExecutor implements RequestExecutor<File, WxMpQrCodeTicket> {

  @Override
  public File execute(IWxConfig wxconfig, String uri, 
      WxMpQrCodeTicket ticket) throws WxErrorException, IOException {
    if (ticket != null) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?") 
          ? "ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8") 
          : "&ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8");
    }
    
    HttpGet httpGet = new HttpGet(uri);
    if (wxconfig.getHttpProxyHost()!=null) {
        RequestConfig config = RequestConfig.custom().setProxy(new HttpHost(wxconfig.getHttpProxyHost(), wxconfig.getHttpProxyPort())).build();
        httpGet.setConfig(config);
      }

    try (CloseableHttpResponse response = HttpClientUtils.getHttpClient().execute(httpGet);
        InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response);) {
      Header[] contentTypeHeader = response.getHeaders("Content-Type");
      if (contentTypeHeader != null && contentTypeHeader.length > 0) {
        // 出错
        if (ContentType.TEXT_PLAIN.getMimeType().equals(contentTypeHeader[0].getValue())) {
          String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
          throw new WxErrorException(WxError.fromJson(responseContent));
        }
      }
      return FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
    } finally {
      httpGet.releaseConnection();
    }

  }

}
