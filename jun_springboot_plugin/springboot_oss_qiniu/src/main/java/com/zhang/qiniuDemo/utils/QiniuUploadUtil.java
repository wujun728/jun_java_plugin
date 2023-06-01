package com.zhang.qiniuDemo.utils;


import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;



/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/14 11:45
 * 七牛云图片上传工具类(普通)
 */
public class QiniuUploadUtil {

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

    public QiniuUploadUtil() {
        //初始化基本配置
        Configuration cfg = new Configuration(Region.region0());
        //创建上传管理器
        manager = new UploadManager(cfg);
    }

    //文件名 = key
    //文件的byte数组
    public String upload(String imgName , byte [] bytes) {
        Auth auth = Auth.create(accessKey, secretKey);
        //构造覆盖上传token
        String upToken = auth.uploadToken(bucket,imgName);
        try {
            Response response = manager.put(bytes, imgName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //返回请求地址
            return prix+putRet.key+"?t="+System.currentTimeMillis();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

