package org.springrain.frame.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpClient 工具类
 * 
 * @author caomei
 *
 */
public class HttpClientUtils {

    private HttpClientUtils() {
        throw new IllegalAccessError("工具类不能实例化");
    }

    // private static BasicHttpClientConnectionManager connectionManager = null;
    private static PoolingHttpClientConnectionManager connectionManager = null;

    // private static HttpClientBuilder httpClientBuilder=null;

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
            .setConnectionRequestTimeout(3000).build();

    static {

        /*
         * SSLContext sslcontext = SSLContexts.createSystemDefault();
         * 
         * // Create a registry of custom connection socket factories for supported //
         * protocol schemes.
         * 
         * 
         * Registry<ConnectionSocketFactory> socketFactoryRegistry =
         * RegistryBuilder.<ConnectionSocketFactory>create() .register("http",
         * PlainConnectionSocketFactory.INSTANCE) .register("https", new
         * SSLConnectionSocketFactory(sslcontext)) .build();
         */

        // 使用基本的Httpclient链接器
        // connectionManager=new
        // BasicHttpClientConnectionManager(socketFactoryRegistry);

        // connectionManager = new
        // PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(1000);
        connectionManager.setDefaultMaxPerRoute(200);// 每个路由最大的请求数量

        // httpClientBuilder =
        // HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig);
        // HttpHost localhost = new HttpHost("http://www.baidu.com",80);
        // connectionManager.setMaxPerRoute(new HttpRoute(localhost), 200);

    }

    public static CloseableHttpClient getHttpClient() {
        return getHttpClientBuilder().build();
    }

    public static CloseableHttpClient getHttpClient(SSLContext sslContext) {
        return getHttpClientBuilder(sslContext).build();
    }

    public static HttpClientBuilder getHttpClientBuilder() {
        return HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig);
        // .setConnectionManagerShared(true);
    }

    public static HttpClientBuilder getHttpClientBuilder(SSLContext sslContext) {
        if (sslContext != null) {
            return getHttpClientBuilder().setSSLContext(sslContext);
        } else {
            return getHttpClientBuilder();
        }

    }

    /**
     * post 请求
     * 
     * @param httpUrl
     *            请求地址
     * @param sslContext
     *            ssl证书信息
     * @return
     */
    public static String sendHttpPost(String httpUrl, SSLContext sslContext) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        return sendHttpPost(httpPost, sslContext);
    }

    /**
     * 发送 post请求
     * 
     * @param httpUrl
     *            地址
     */
    public static String sendHttpPost(String httpUrl) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        return sendHttpPost(httpPost, null);
    }

    /**
     * 发送 post请求
     * 
     * @param httpUrl
     *            地址
     * @param params
     *            参数(格式:key1=value1&key2=value2)
     */
    public static String sendHttpPost(String httpUrl, String params) {
        return sendHttpPost(httpUrl, params, null);
    }

    /**
     * 发送 post请求
     * 
     * @param httpUrl
     *            地址
     * @param params
     *            参数(格式:key1=value1&key2=value2)
     * @param sslContext
     *            ssl证书信息
     */
    public static String sendHttpPost(String httpUrl, String params, SSLContext sslContext) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return sendHttpPost(httpPost, sslContext);
    }

    /**
     * 发送 post请求
     * 
     * @param httpUrl
     *            地址
     * @param maps
     *            参数
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps) {
        return sendHttpPost(httpUrl, maps, null);
    }

    /**
     * 发送 post请求
     * 
     * @param httpUrl
     *            地址
     * @param maps
     *            参数
     * @param sslContext
     *            ssl证书信息
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps, SSLContext sslContext) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Map.Entry<String, String> m : maps.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(m.getKey(), m.getValue()));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return sendHttpPost(httpPost, null);
    }

    /**
     * 发送 post请求（带文件）,默认 files 名称数组.
     * 
     * @param httpUrl
     *            地址
     * @param fileLists
     *            附件
     * @param maps
     *            参数
     */
    public static String sendHttpPost(String httpUrl, List<File> fileLists, Map<String, String> maps) {
        return sendHttpPost(httpUrl, fileLists, maps, null);
    }
    
    /**
     * 发送 post请求（带文件）
     * 
     * @param httpUrl
     *            地址
     * @param fileMap
     *           附件,名称和File对应
     * @param maps
     *            参数
     */
    public static String sendHttpPost(String httpUrl, Map<String,File> fileMap, Map<String, String> maps) {
        return sendHttpPost(httpUrl, fileMap, maps, null);
    }

    /**
     * 发送 post请求（带文件）,默认 files 名称数组.
     * 
     * @param httpUrl
     *            地址
     * @param fileLists
     *            附件
     * @param maps
     *            参数
     * @param sslContext
     *            ssl证书信息
     */
    public static String sendHttpPost(String httpUrl, List<File> fileLists, Map<String, String> maps,
            SSLContext sslContext) {

        Map<String,File> fileMap=new HashMap<>();
               
        if(CollectionUtils.isNotEmpty(fileLists)) {
            for (File file : fileLists) {
                fileMap.put("files", file);
            }
        }
       
        return sendHttpPost(httpUrl,fileMap,maps, sslContext);
    }
    
    /**
     * 发送 post请求（带文件）
     * 
     * @param httpUrl
     *            地址
     * @param fileMap
     *            附件,名称和File对应
     * @param maps
     *            参数
     * @param sslContext
     *            ssl证书信息
     */
    public static String sendHttpPost(String httpUrl, Map<String,File> fileMap, Map<String, String> maps,
            SSLContext sslContext) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        for (Map.Entry<String, String> m : maps.entrySet()) {
            meBuilder.addPart(m.getKey(), new StringBody(m.getValue(), ContentType.TEXT_PLAIN));
        }
        for (Map.Entry<String, File> m : fileMap.entrySet()) {
            FileBody fileBody = new FileBody(m.getValue());
            meBuilder.addPart(m.getKey(), fileBody);
        }
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return sendHttpPost(httpPost, sslContext);
    }
    
    

    /**
     * 发送Post请求
     * 
     * @param httpPost
     * @return
     */
    public static String sendHttpPost(HttpPost httpPost) {
        return sendHttpPost(httpPost, null);
    }

    /**
     * 发送Post请求
     * 
     * @param httpPost
     * @param sslContext
     *            ssl证书信息
     * @return
     */
    public static String sendHttpPost(HttpPost httpPost, SSLContext sslConext) {
        CloseableHttpClient httpClient = getHttpClient(sslConext);
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {

            try {
                // 关闭连接,释放资源
                if (entity != null) {
                    EntityUtils.consumeQuietly(entity); // 会自动释放连接
                }
                if (response != null) {
                    response.close();
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        }
        return responseContent;
    }

    /**
     * 发送 get请求
     * 
     * @param httpUrl
     */
    public static String sendHttpGet(String httpUrl) {
        return sendHttpGet(httpUrl, null);
    }

    /**
     * 发送 get请求
     * 
     * @param httpUrl
     * @param sslContext
     *            ssl证书信息
     */
    public static String sendHttpGet(String httpUrl, SSLContext sslConext) {
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttpGet(httpGet, sslConext);
    }

    /**
     * 发送Get请求
     * 
     * @param httpPost
     * @return
     */
    public static String sendHttpGet(HttpGet httpGet) {
        return sendHttpGet(httpGet, null);
    }

    /**
     * 发送Get请求
     * 
     * @param httpPost
     * @param sslContext
     *            ssl证书信息
     * @return
     */
    public static String sendHttpGet(HttpGet httpGet, SSLContext sslConext) {
        CloseableHttpClient httpClient = getHttpClient(sslConext);
        // System.out.println(httpClient);
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {

            try {
                // 关闭连接,释放资源
                if (entity != null) {
                    EntityUtils.consumeQuietly(entity); // 会自动释放连接
                }
                if (response != null) {
                    response.close();
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        }
        return responseContent;
    }

}
