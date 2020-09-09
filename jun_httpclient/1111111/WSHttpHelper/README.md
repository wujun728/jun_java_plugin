轻量级Http请求框架。清晰明了的请求接口描述，灵活的扩展机制。
快捷接口：
支持同步调用;
支持异步调用，异步回调;
支持JSON自动转换为指定类型;
支持自定义输入参数验证;
支持自定义结果解析;
支持自定义头部;

高级操作：
支持快速扩展请求接口，通过Ann描述，请求路径，参数等一目了然;
支持参数验证规则，URL生成规则，请求参数组装规则，结果解析皆可动态替换。
自动生成接口描述

适用场景：
适用于JAVA服务端通过HttpClient请求外部服务，Android客户端访问服务器接口请求数据。
针对各开放平台提供的接口开发客户端访问POJO，通过配置清晰明了，操作简便。如：微信开放平台接口，百度开放平台接口，阿里开发平台接口等。
针对多系统之间通过HttpClient的数据交互功能，可针对相应访问接口快速开发访问POJO，统一风格，便于维护。如：访问Solr等。

 * 请求执行过程，执行过程如下：
 * 1.根据@WSRequest注解描述生成请求上下文。若没有@WSRequest注解则抛出异常。
 * 2.调用init(context)方法执行初始化，主要是向contex中添加数据，或者添加自定义处理器。
 * 3.添加默认处理器。请求前处理顺序：默认值初始化，验证参数，生成请求参数，生成URL。请求后处理：解析结果。
 * 3.1首选根据配置文件获取指定的默认处理器，若配置文件中没有指定，则使用org.ws.httphelper.request.handler.impl包中的默认处理器。
 * 4.执行请求前处理,按照顺序依次执行。
 * 4.1默认初始化处理：若注解描述中存在默认值，并且参数没有输入值，则为参数设置该默认值。
 * 4.2验证参数处理：根据注解描述，验证必须值，验证输入参数类型，根据正则验证。
 * 4.3生成请求参数处理：根据配置的参数或者动态添加的参数生成请求参数。自动识别普通参数，数组参数，文件参数。
 * 4.4生成URL处理：根据输入key的值自动匹配替换URL中{key}的值。
 * 5.执行请求。
 * 6.执行请求后处理，按照顺序依次执行。
 * 6.1解析结果处理：只解析JSON为指定的对象。
 * 7.清理缓存。不清理Cookie。要清除Cookie通过context.clearCookie()。


简单使用实例：
```
package org.ws.httphelper;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.model.ResponseResult;
import org.ws.httphelper.request.handler.CallbackHandler;
import sun.security.provider.MD5;
import sun.security.rsa.RSASignature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 15-12-11.
 */
public class TestWSHttpHelper extends TestCase {
    protected static Log log = LogFactory.getLog(TestWSHttpHelper.class);

    /**
     * 测试获取HTML
     * @throws Exception
     */
    public void testDoGetHtml()throws Exception{
        String html=WSHttpHelper.doGetHtml("http://git.oschina.net/wolfsmoke/WSHttpHelper");
        System.out.print(html);
    }

    /**
     * 测试带有回调的HTML
     * @throws Exception
     */
    public void testDoGetHtmlCallBack()throws Exception{
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("wq", "WSHttpHelper");
        WSHttpHelper.doAsyncGetHtml("http://www.baidu.com/s", parameters, "UTF-8", new CallbackHandler() {
            @Override
            public ResponseResult execute(ResponseResult result) throws WSException {
                String html = result.getBody().toString();
                html += "\n在回调里面修改返回结果。";
                result.setBody(html);

                log.debug(html);
                return result;
            }
        });
        log.debug("====");
        Thread.sleep(3000);
        log.debug("====");
    }

    /**
     * 测试获取byte[],下载文件
     * @throws Exception
     */
    public void testDoGetByteArray()throws Exception{
        // 下载个文件
        String url="http://mirror.bit.edu.cn/apache//commons/io/binaries/commons-io-2.4-bin.zip";
        // 执行请求
        byte[] fileBytes=WSHttpHelper.doGetByteArray(url);
        String filePath=FileUtils.getTempDirectoryPath()+"commons-io-2.4-bin.zip";
        File file  = new File(filePath);
        FileUtils.writeByteArrayToFile(file, fileBytes);
        TestCase.assertEquals("a732ec8558d464e7c5d8136e5aa9d85c",getMd5ByFile(file));
    }
    /**
     * 测试带有回调的获取byte[],下载文件
     * @throws Exception
     */
    public void testDoGetByteArrayCallBack()throws Exception{
        // 下载个文件
        String url="http://mirror.bit.edu.cn/apache//commons/io/binaries/commons-io-2.4-bin.zip";
        // 执行请求
        WSHttpHelper.doAsyncGetByteArray(url, null, new CallbackHandler() {
            @Override
            public ResponseResult execute(ResponseResult result) throws WSException {
                //String saveFilePath = FileUtils.getTempDirectoryPath() + "commons-io-2.4-bin.zip";
                String saveFilePath ="C:/commons-io-2.4-bin.zip";
                File file = new File(saveFilePath);
                try {
                    FileUtils.writeByteArrayToFile(file, (byte[]) result.getBody());
                } catch (IOException e) {
                    throw new WSException(e);
                }
                // 打印下载路径
                log.debug(saveFilePath);
                try {
                    TestCase.assertEquals("a732ec8558d464e7c5d8136e5aa9d85c", TestWSHttpHelper.getMd5ByFile(file));
                } catch (FileNotFoundException e) {
                    throw new WSException(e.getMessage(),e);
                }
                return result;
            }
        });
        log.debug("====");
        // 当前线程等待10秒，异步回调可以打印出文件路径，若不等待，则打印不出路径
        Thread.sleep(10000);
        log.debug("====");
    }

    /**
     * 获取文件md5
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    /**
     * 测试获取JSON，解析为MAP
     * @throws Exception
     */
    public void testDoGetMap()throws Exception{
        String url="https://www.hao123.com/sugdata_s4.json?r=-805836";
        Map resultMap = WSHttpHelper.doGetMap(url);
        TestCase.assertEquals("http://top.baidu.com/",resultMap.get("baseUrl"));
        TestCase.assertEquals("Success",resultMap.get("errormsg"));
    }

    /**
     * 测试获取JSON，将JSON解析为指定类型
     * @throws Exception
     */
    public void testDoGetJson()throws Exception{
        String url="https://www.hao123.com/sugdata_s4.json?r=-805836";
        Map resultMap = WSHttpHelper.doGetJson(url, Map.class);
        TestCase.assertEquals("http://top.baidu.com/",resultMap.get("baseUrl"));
        TestCase.assertEquals("Success",resultMap.get("errormsg"));
    }

}

```

高级应用实例：
```
/***********************************/
/******定义接口对应请求类***********/
/***********************************/
package org.ws.baidu.api;

import org.ws.httphelper.annotation.Parameter;
import org.ws.httphelper.annotation.WSRequest;
import org.ws.httphelper.model.WSRequestContext;
import org.ws.httphelper.request.WSAnnotationHttpRequest;

/**

 * Created by Administrator on 15-12-11.

 * 按照以下接口介绍开发对应接口实例

 * http://developer.baidu.com/map/index.php?title=webapi/guide/webservice-placeapi

 */
@WSRequest(
        name = "Place API 提供区域检索POI服务与POI详情服务",
        url = "http://api.map.baidu.com/place/v2/search",
        method = WSRequest.MethodType.GET,
        responseType = WSRequest.ResponseType.JSON,
        charset = "UTF-8",
        parameters = {
                @Parameter(name="q",description = "检索关键字",required = true,example = "中关村、ATM、百度大厦"),
                @Parameter(name="region",description = "检索区域",required = true,example = "北京"),
                @Parameter(name="output",description = "输出格式",defaultValue = "json",example = "日式烧烤/铁板烧、朝外大街"),
                @Parameter(name="scope",description = "检索结果详细程度",required = true,defaultValue = "1",example = ""),
                @Parameter(name="filter",description = "检索过滤条件",example = ""),
                @Parameter(name="page_size",description = "范围记录数量",defaultValue = "10",example = ""),
                @Parameter(name="page_num",description = "范围记录数量",defaultValue = "0",example = ""),
                @Parameter(name="ak",description = "用户的访问密钥",required = true,example = ""),
                @Parameter(name="sn",description = "用户的权限签名",example = ""),
                @Parameter(name="timestamp",description = "设置sn后该值必填",example = "")
        }
)
public class PlaceAPI extends WSAnnotationHttpRequest {
    public void init(WSRequestContext context){
        context.addInputData("ak","E4805d16520de693a3fe707cdc962045");
    }
}
/***********************************/
/***********测试接口****************/
/***********************************/
package org.ws.baidu.api;

import junit.framework.TestCase;
import org.ws.httphelper.WSHttpHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 15-12-11.
 */
public class TestPlaceAPI extends TestCase {
    public void testRequest()throws Exception{
        PlaceAPI api = new PlaceAPI();
        api.addParameter("q","饭店");
        api.addParameter("region","北京");
        System.out.println(api.execute().getBody().toString());
        System.out.println(api.getContext().getUrl());
    }

    public void testDoGet()throws Exception{
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("uid","5a8fb739999a70a54207c130");
        param.put("ak","E4805d16520de693a3fe707cdc962045");
        param.put("output","json");
        param.put("scope","2");
        Object obj=WSHttpHelper.doGetHtml("http://api.map.baidu.com/place/v2/detail", param);
        System.out.print(obj.toString());
    }
}
```
通过xml配置测试。

测试将http://image.baidu.com/中搜索到的图片下载下来。
在httphelper-config.xml中配置其他请求xml路径
```
<request-xml>
    <path value="/test-request/**"/>
</request-xml>
```
通过test/resources/test-request/request1.xml两个请求配置
downloadImage为搜索要下载的图片请求，saveImageRequest为下载图片请求。
在downloadImage请求的后处理里面解析下载图片然后调用saveImageRequest请求图片，再在saveImageRequest后处理中保存图片。
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE httphelper-config SYSTEM "httphelper-config.dtd">
<httphelper-config>
    <requests>
        <request name="downloadImage" url="http://image.baidu.com/search/index" charset="UTF-8" response-type="HTML">
            <parameters>
                <parameter name="tn" defaultValue="baiduimage"/>
                <parameter name="word" defaultValue="美女图片"/>
            </parameters>
            <handlers>
                <pro>
                    <handler clazz="org.ws.httphelper.request.handler.DownloadAllImageHandle"/>
                </pro>
            </handlers>
        </request>
        <request name="saveImageRequest" url="{url}" response-type="BYTE_ARRAY">
            <handlers>
                <pro>
                    <handler clazz="org.ws.httphelper.request.handler.SaveImageHandle"/>
                </pro>
            </handlers>
        </request>
    </requests>
</httphelper-config>
```
执行下载
```
package org.ws.httphelper.request;

import junit.framework.TestCase;
import org.ws.httphelper.http.WSHttpTaskExecutor;

/**
 * Created by Administrator on 16-1-2.
 */
public class TestWSHttpRequestFactory extends TestCase{
    public void testRequestPathXmlConfigRequest()throws Exception{
        WSHttpRequest request = WSHttpRequestFactory.getHttpRequest("downloadImage");
        // 若不传入word参数，默认使用配置的值“美女图片”
        //request.getContext().addInputData("word","宠物萌图");
        request.execute();
        // 等待所有线程执行完成
        WSHttpTaskExecutor.getInstance().waitForFinish(Thread.currentThread());
    }
}
```
搜索要下载图片的后处理。主要是解析html，获取下载图片url，执行下载请求。
```
public class DownloadAllImageHandle implements ResponseProHandler {
    @Override
    public ResponseResult handler(WSRequestContext context, ResponseResult result) throws WSException {
        String body = result.getBody(String.class);
        // 提取下载图片正则表达式


        String reg="\"objURL\":\"([^\"]*\\.jpg)\",";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(body);
        String savePath= "C:/testDownloadImage/";
        System.out.println(savePath);
        int i=0;
        while(matcher.find()){
            String imageUrl=matcher.group(1);
            WSHttpRequest request=WSHttpRequestFactory.getHttpRequest("saveImageRequest");

            request.getContext().addInputData("savePath",savePath+i+".jpg");
            request.getContext().addInputData("url",imageUrl);
            // 同步下载


            //request.execute();


            // 异步下载：多个下载请求同时进行


            request.asyncExecute();

            i++;
        }
        return result;
    }

    @Override
    public int level() {
        return WSHttpHelperConstant.PRO_HANDLER_USER;
    }
}
```
保持文件后处理，异步调用自动执行，根据传入保存路径，存储文件。
```
public class SaveImageHandle implements ResponseProHandler {

    @Override
    public ResponseResult handler(WSRequestContext context, ResponseResult result) throws WSException {
        String savePath = String.valueOf(context.getInputDataMap().get("savePath"));
        System.out.println("正在下载:" + savePath);

        if(context.getResponseType()== WSRequest.ResponseType.BYTE_ARRAY){
            File file = new File(savePath);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            byte[] body = (byte[])result.getBody();
            try {
                FileUtils.writeByteArrayToFile(file,body);
            } catch (IOException e) {
                new WSException(e.getMessage(),e);
            }
        }
        return result;
    }

    @Override
    public int level() {
        return WSHttpHelperConstant.PRO_HANDLER_USER;
    }
}
```