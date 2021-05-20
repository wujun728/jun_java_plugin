package org.ws.httphelper;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ws.httphelper.exception.WSException;
import org.ws.httphelper.http.WSHttpTaskExecutor;
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
