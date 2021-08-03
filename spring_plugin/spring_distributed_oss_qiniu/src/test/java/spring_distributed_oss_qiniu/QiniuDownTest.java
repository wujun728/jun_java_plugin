package spring_distributed_oss_qiniu;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.qiniu.common.QiniuException;
import com.qiniu.util.Auth;
 
public class QiniuDownTest {
	
	//设置好账号的ACCESS_KEY和SECRET_KEY
	public static final String ACCESS_KEY = "ts0n9OF16ekFkDkZTTlpmyPI-tP3HKQDyw_GR4o2";  //你的access_key
	public static final String SECRET_KEY = "c-OjjwV3ZgzCQwxc6W_bsTFKuDg8qeyqohyJU0RL";  //你的secret_key
	public static final String BUCKET_NAME = "wujun-qiniu";   //你的bucket_name
	
    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //构造私有空间的需要生成的下载的链接
    String URL = "http://qx9slhu11.hn-bkt.clouddn.com/img0.jpg";

    public void download() {
        //调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
        String downloadRUL = auth.privateDownloadUrl(URL, 3600);
        System.out.println(downloadRUL);
    }
    
    public static void main(String args[]) {
    	new QiniuDownTest().download();
    }
     
}
 
 