package com.chentongwei.util;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;

/**
 * 七牛工具类
 *
 * @author TongWei.Chen 2017-05-16 14:31:49
 */
@Component
public class QiniuUtil {
    private static final Logger LOG = LoggerFactory.getLogger(QiniuUtil.class);

    @Autowired
    private QiNiuConstant qiNiuConstant;

    private Configuration configuration = new Configuration(Zone.autoZone());
    private UploadManager uploadManager = new UploadManager(configuration);

    /**
     * 七牛云上传文件
     *
     * @param fileName 上传后的文件名
     * @param filePath 上传的文件名路径
     * @return
     * @throws IOException
     */
    public StringMap upload(String fileName, String filePath) throws IOException {
        try {
            Auth auth = Auth.create(qiNiuConstant.getAccessKey(), qiNiuConstant.getSecretKey());
            String upToken = auth.uploadToken(qiNiuConstant.getBucket());
            //调用put方法上传
            Response res = uploadManager.put(filePath, fileName, upToken);
            //打印返回的信息
            return res.jsonToMap();
        } catch (QiniuException e) {
            Response response = e.response;
            // 请求失败时打印的异常的信息
            LOG.error("七牛上传图片异常", response.toString(), e);
        }
        return null;
    }

    /**
     * 七牛云上传文件
     *
     * @param bytes : 文件的字节数组
     * @return
     * @throws QiniuException : 七牛异常
     */
    public JSONObject upload(byte[] bytes, String suffix) throws QiniuException {
        Auth auth = Auth.create(qiNiuConstant.getAccessKey(), qiNiuConstant.getSecretKey());
        String upToken = auth.uploadToken(qiNiuConstant.getBucket());
        Response response = uploadManager.put(bytes, UUID.randomUUID() + suffix, upToken);
        return JSON.parseObject(response.bodyString());
    }

    /**
     * 多文件压缩
     *
     * @param urls ：资源路径集合
     */
    public String mkzip(List<String> urls) {
        Auth auth = Auth.create(qiNiuConstant.getAccessKey(), qiNiuConstant.getSecretKey());
        OperationManager om = new OperationManager(auth, new Configuration(Zone.zone0()));
        StringBuffer fops = new StringBuffer();
        fops.append("mkzip/2");
        urls.forEach(url -> {
            fops.append("/url/");
            fops.append(UrlSafeBase64.encodeToString(url));
        });
        fops.append("|saveas/");
        String zipName = "52doutu-" + UUID.randomUUID().toString() + ".zip";
        String saveas = "doutu-other:" + zipName ;
        fops.append(UrlSafeBase64.encodeToString(saveas));
        
        try {
        	//mkzip
            om.pfop(qiNiuConstant.getBucket(), "java.png", fops.toString());
            return zipName;
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            LOG.error("多文件压缩失败, fops为 " + fops.toString(), e.response);
            try {
                // 响应的文本信息
                LOG.error("多文件压缩失败,响应的文本信息： ", e.response.bodyString());
            } catch (QiniuException e1) {
                // ignore
            }
        }
        return null;
    }

    /**
     * 删除七牛云上的文件
     *
     * @param key：filename
     */
    public void delete(String bucketName, String key) {
        Auth auth = Auth.create(qiNiuConstant.getAccessKey(), qiNiuConstant.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, new Configuration(Zone.zone0()));
        try {
            bucketManager.delete(bucketName, key);
        } catch (QiniuException e) {
            LOG.error("删除七牛云上的文件失败, 删除的key为" + key, e);
        }
    }
}