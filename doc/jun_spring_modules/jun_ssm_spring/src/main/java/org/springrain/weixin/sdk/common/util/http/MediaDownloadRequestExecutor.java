package org.springrain.weixin.sdk.common.util.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
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

/**
 * 下载媒体文件请求执行器，请求的参数是String, 返回的结果是File
 * 视频文件不支持下载
 * @author springrain
 */
public class MediaDownloadRequestExecutor implements RequestExecutor<File, String> {

  private File tmpDirFile;

  public MediaDownloadRequestExecutor() {
  }

  public MediaDownloadRequestExecutor(File tmpDirFile) {
    this.tmpDirFile = tmpDirFile;
  }

  @Override
  public File execute(IWxConfig wxconfig, String uri, String queryParam) throws WxErrorException, IOException {
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


    try (CloseableHttpResponse response = HttpClientUtils.getHttpClient().execute(httpGet);
        InputStream inputStream = InputStreamResponseHandler.INSTANCE
            .handleResponse(response)) {

      Header[] contentTypeHeader = response.getHeaders("Content-Type");
      if (contentTypeHeader != null && contentTypeHeader.length > 0) {
        if (contentTypeHeader[0].getValue().startsWith(ContentType.APPLICATION_JSON.getMimeType())) {
          // application/json; encoding=utf-8 下载媒体文件出错
          String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
          throw new WxErrorException(WxError.fromJson(responseContent));
        }
      }

      String fileName = getFileName(response);
      if (StringUtils.isBlank(fileName)) {
        return null;
      }

      String[] nameAndExt = fileName.split("\\.");
      return FileUtils.createTmpFile(inputStream, nameAndExt[0], nameAndExt[1], this.tmpDirFile);

    } finally {
      httpGet.releaseConnection();
    }

  }

  private String getFileName(CloseableHttpResponse response) throws WxErrorException {
    Header[] contentDispositionHeader = response.getHeaders("Content-disposition");
    if(contentDispositionHeader == null || contentDispositionHeader.length == 0){
      throw new WxErrorException(WxError.newBuilder().setErrorMsg("无法获取到文件名").build());
    }

    Pattern p = Pattern.compile(".*filename=\"(.*)\"");
    Matcher m = p.matcher(contentDispositionHeader[0].getValue());
    if(m.matches()){
      return m.group(1);
    }
    throw new WxErrorException(WxError.newBuilder().setErrorMsg("无法获取到文件名").build());
  }

}
