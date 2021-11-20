package spring_distributed_oss_qiniu;

import org.json.JSONException;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
 
public class QiniuUploadTest {
	
	public static final String ACCESS_KEY = "ts0n9OF16ekFkDkZTTlpmyPI-tP3HKQDyw_GR4o2";  //你的access_key
	public static final String SECRET_KEY = "c-OjjwV3ZgzCQwxc6W_bsTFKuDg8qeyqohyJU0RL";  //你的secret_key
	public static final String BUCKET_NAME = "wujun-qiniu";   //你的bucket_name
	
	public static void uploadFile(String filePath, String fileName) throws QiniuException {
		
		com.qiniu.storage.Configuration cfg = new Configuration();
	    UploadManager uploadManager = new UploadManager(cfg);
	    Auth auth = Auth.create(QiniuUploadTest.ACCESS_KEY,QiniuUploadTest.SECRET_KEY);
	    String token = auth.uploadToken(QiniuUploadTest.BUCKET_NAME);
	    Response r = uploadManager.put(filePath, fileName, token);
		 
		System.out.println(token);   //输出上传凭证
		System.out.println(r.isOK());    //输出上传到七牛云之后的文件名称
		System.out.println(r.toString());    //输出上传到七牛云之后的文件名称
		
	}
 
	public static void main(String[] args) {
		try {
			uploadFile("D:\\img0.jpg","img0.jpg");  //第一个参数是本地文件路径，第二个参数是上传到七牛云之后的文件名称，由你来设定。
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
 
 