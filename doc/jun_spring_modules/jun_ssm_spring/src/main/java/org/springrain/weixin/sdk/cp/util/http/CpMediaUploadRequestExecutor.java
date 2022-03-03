package org.springrain.weixin.sdk.cp.util.http;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springrain.frame.util.HttpClientUtils;
import org.springrain.weixin.sdk.common.api.IWxConfig;
import org.springrain.weixin.sdk.common.bean.result.WxError;
import org.springrain.weixin.sdk.common.bean.result.WxMediaUploadResult;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.common.util.http.RequestExecutor;
import org.springrain.weixin.sdk.common.util.http.Utf8ResponseHandler;

/**
 * 上传媒体文件请求执行器，请求的参数是File, 返回的结果是String
 *
 * @author springrain
 */
public class CpMediaUploadRequestExecutor implements RequestExecutor<WxMediaUploadResult, File> {

  @Override
  public WxMediaUploadResult execute(IWxConfig wxconfig, String uri, File file) throws WxErrorException, IOException {
    HttpPost httpPost = new HttpPost(uri);
    if (wxconfig.getHttpProxyHost()!=null) {
        RequestConfig config = RequestConfig.custom().setProxy(new HttpHost(wxconfig.getHttpProxyHost(), wxconfig.getHttpProxyPort())).build();
        httpPost.setConfig(config);
      }
    
    
    //参考网址:http://blog.csdn.net/paulorwys/article/details/74010585
    
    MultipartEntityBuilder builder = MultipartEntityBuilder.create(); 
    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);  
    
  //@2017-06-28 在文件上传中，有些系统不支持指定字符集(企业微信)  
  //builder.setCharset(Charset.forName("UTF-8") );  
    
  //先添加文件部分(无需指定编码)  
  if (file != null && file.exists()) {  
      builder.addBinaryBody("media", file, ContentType.DEFAULT_BINARY, file.getName() );  
  }  
 
  
  /*
  //再添加表单部分(需指定编码，@2017-06-28 key和value都需要指定编码)  
  if (params != null && !params.isEmpty()) {  
        
      //@2017-06-28 在文件上传中，有些系统不支持指定字符集(企业微信)  
      builder.setCharset(Charset.forName(FsSpec.Charset_Default) );  
      ContentType contentType = ContentType.create("text/plain", "UTF-8");  
        
      for (Map.Entry<String, String> entry : params.entrySet()) {  
            
          builder.addTextBody(entry.getKey(), entry.getValue(), contentType);  
      }  
  }  
    */
  
    //企业号不需要设置
   // httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());
  
    httpPost.setEntity(builder.build() );  
    
    try (CloseableHttpResponse response = HttpClientUtils.getHttpClient().execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      WxError error = WxError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      }
      return WxMediaUploadResult.fromJson(responseContent);
    } finally {
      httpPost.releaseConnection();
    }
  }

}
