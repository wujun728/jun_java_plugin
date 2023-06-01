package com.zhang.qiniuDemo.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/14 13:11
 * <p>
 * 七牛云图片上传工具类(断点上传):即上传到一半程序突然停止运行,等下次再启动程序时就会接着上次的位置继续实现上传
 */
public class QiNiuYunDeBugUpload {

    /**
     * 七牛云网站中 自己账号的  accessKey
     */
    private static final String accessKey = "Rc_feElMSmVM3iAVXgj9L3RZkYomB0Xe11D6QTMN";

    /**
     * 七牛云网站中 自己账号的  secretKey
     */
    private static final String secretKey = "ixRQ5YAqKn0eiPbTDflbnZ9MTdlRxgxhqbDqhOTv";

    /**
     * 七牛云网站中 自己指定的 空间名
     */
    private static final String bucket = "huanhuanxinxin";

    /**
     * 外域连接前缀
     */
    private static final String prix = "http://qen8knqje.bkt.clouddn.com/";

    private UploadManager manager;

    public QiNiuYunDeBugUpload() {
        //初始化基本配置
        Configuration cfg = new Configuration(Region.region0());
        //断点续传：
        String localTempDir = Paths.get(System.getProperty("java.io.tmpdir"), bucket).toString();
        System.out.println("七牛云断点时上传的临时目录:" + localTempDir);
        //设置断点续传文件进度保存目录
        FileRecorder fileRecorder = null;
        try {
            fileRecorder = new FileRecorder(localTempDir);

            //创建上传管理器
            manager = new UploadManager(cfg, fileRecorder);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 上传的是由前端传过来的file.getBytes()
     *
     * @param imgName 七牛云中想要设置的文件名称
     * @param bytes   前端传过来的file.getBytes()
     * @return String 七牛云外域+文件名   使用这个字符串就可以在浏览器中直接访问图片
     */
    public String upload(String imgName, byte[] bytes) {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            //上传的是由前端传过来的file.getBytes()
            //如果要传本地文件,这里就可以将bytes换成localfile,localfile指的是本地文件的路径
            Response response = manager.put(bytes, imgName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //返回请求地址
            return prix + putRet.key + "?t=" + System.currentTimeMillis();
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

        return null;
    }


    public String uploadLocalFile(String imgName, String localfile) {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            //上传的是由前端传过来的file.getBytes()
            //如果要传本地文件,这里就可以将bytes换成localfile,localfile指的是本地文件的路径
            Response response = manager.put(localfile, imgName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //返回请求地址
            return prix + putRet.key + "?t=" + System.currentTimeMillis();
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

        return null;
    }
}
