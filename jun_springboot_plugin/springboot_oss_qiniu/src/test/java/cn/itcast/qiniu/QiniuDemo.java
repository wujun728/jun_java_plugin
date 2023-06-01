package cn.itcast.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.zhang.qiniuDemo.utils.QiNiuYunDeBugUpload;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class QiniuDemo {

    /**
     * 将图片上传到七牛云服务
     *      1.更新用户图片信息（用户id=key）
     *      2.访问图片
     *          存储空间分配的：http://q8rcl2kke.bkt.clouddn.com/自定义的图片名称
     *          上传的文件名
     *          更新图片之后：访问的时候，再请求连接添加上时间戳,因为七牛云用到了缓冲技术
     *              eg:http://q8rcl2kke.bkt.clouddn.com/自定义的图片名称?t=2685
     *
     */
    @Test
    public void testUpload01() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "Rc_feElMSmVM3iAVXgj9L3RZkYomB0Xe11D6QTMN";
        String secretKey = "ixRQ5YAqKn0eiPbTDflbnZ9MTdlRxgxhqbDqhOTv";
        String bucket = "ihrm-zhang";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\Administrator\\Desktop\\照片\\G44A6528.JPG";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "1314";

        Auth auth = Auth.create(accessKey, secretKey);
        //这里加入第二个参数key的话,就支持覆盖上传   如果不加key参数,就不支持覆盖上传,如果key,图片不同的话就会报错
        //String upToken = auth.uploadToken(bucket,key);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }

    //断点传本地文件测试
    @Test
    public void testUpload02() {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        //...生成上传凭证，然后准备上传
        String accessKey = "COuoDRVa7JLsuurzIvQSI_pEDceHDw3yGfJEmvwv";
        String secretKey = "3RWpTjB5Jxg3QosUFr4mxbHXJ5JR2m6AHQqYsSlr";
        String bucket = "ihrm-bucket";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\ThinkPad\\Desktop\\ihrm\\day9\\资源\\资源\\test.xlsx";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "testExcel";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        //断点续传：
        String localTempDir = Paths.get(System.getProperty("java.io.tmpdir"), bucket).toString();
        System.out.println(localTempDir);
        try {
            //设置断点续传文件进度保存目录
            FileRecorder fileRecorder = new FileRecorder(localTempDir);
            UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
            try {
                Response response = uploadManager.put(localFilePath, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //断点传本地文件工具类测试
    @Test
    public void testUpload03(){
        String localFile =
                "D:\\BaiduNetdiskDownload\\JAVA\\softstudy\\26-传统行业SaaS解决方案\\09-图片上传及Jasper\\01-文件上传与PDF报表入门\\资源\\资源\\test.xlsx";
        String path = new QiNiuYunDeBugUpload().uploadLocalFile("aaa", localFile);
        System.out.println(path);
    }
}
