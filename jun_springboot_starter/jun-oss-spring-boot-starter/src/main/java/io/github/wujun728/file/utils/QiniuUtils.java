package io.github.wujun728.file.utils;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import lombok.extern.slf4j.Slf4j;

import javax.security.auth.message.AuthException;


@Component
@Slf4j
public class QiniuUtils {

	public static final String ACCESS_KEY = "ts0n9OF16ekFkDkZTTlpmyPI-tP3HKQDyw_GR4o2"; // 你的access_key
	public static final String SECRET_KEY = "c-OjjwV3ZgzCQwxc6W_bsTFKuDg8qeyqohyJU0RL"; // 你的secret_key
	public static final String BUCKET_NAME = "qixing-files"; // 你的bucket_name
	public static final String domain = "http://qiniu.hbqxcpa.cn"; // 你的bucket_name
	public static final long TOKEN_TIME = 3600 * 24 * 365 * 5; // 你的bucket_name

	public static void uploadFile(String filePath, String fileName) throws QiniuException {
		com.qiniu.storage.Configuration cfg = new Configuration();
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(QiniuUtils.ACCESS_KEY, QiniuUtils.SECRET_KEY);
		String token = auth.uploadToken(QiniuUtils.BUCKET_NAME);
		Response r = uploadManager.put(filePath, fileName, token);
		if (r.isOK()) {
			log.info("上传成功!");
			log.info("上传文件路径：" + domain + r.url());
		} else {
			log.info("上传失败!");
		}
		System.out.println(token); // 输出上传凭证
		System.out.println(r.isOK()); // 输出上传到七牛云之后的文件名称
		System.out.println(r.toString()); // 输出上传到七牛云之后的文件名称
		System.out.println(r.url()); // 输出上传到七牛云之后的文件名称

	}

	public static void uploadFile(File file, String fileName) throws QiniuException {
		com.qiniu.storage.Configuration cfg = new Configuration();
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(QiniuUtils.ACCESS_KEY, QiniuUtils.SECRET_KEY);
		String token = auth.uploadToken(QiniuUtils.BUCKET_NAME);
		Response r = uploadManager.put(file, fileName, token);
		if (r.isOK()) {
			log.info("上传成功!");
			log.info("上传文件路径：" + domain + r.url());
		} else {
			log.info("上传失败!");
		}
		System.out.println(token); // 输出上传凭证
		System.out.println(r.isOK()); // 输出上传到七牛云之后的文件名称
		System.out.println(r.toString()); // 输出上传到七牛云之后的文件名称
		System.out.println(r.url()); // 输出上传到七牛云之后的文件名称

	}

	public static void uploadFile(InputStream ins, String fileName) throws QiniuException {
		com.qiniu.storage.Configuration cfg = new Configuration();
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(QiniuUtils.ACCESS_KEY, QiniuUtils.SECRET_KEY);
		String token = auth.uploadToken(QiniuUtils.BUCKET_NAME);
		Response r = uploadManager.put(chanageInputStream2byte(ins), fileName, token);
		if (r.isOK()) {
			log.info("上传成功!");
			log.info("上传文件路径：" + domain + r.url());
		} else {
			log.info("上传失败!");
		}
		System.out.println(token); // 输出上传凭证
		System.out.println(r.isOK()); // 输出上传到七牛云之后的文件名称
		System.out.println(r.toString()); // 输出上传到七牛云之后的文件名称
		System.out.println(r.url()); // 输出上传到七牛云之后的文件名称
	}

	public static void uploadFile(MultipartFile file, String fileName) throws IOException {
		com.qiniu.storage.Configuration cfg = new Configuration();
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(QiniuUtils.ACCESS_KEY, QiniuUtils.SECRET_KEY);
		String token = auth.uploadToken(QiniuUtils.BUCKET_NAME);
//		try {
			Response r = uploadManager.put(file.getBytes(), fileName, token);
			if (r.isOK()) {
				log.info("上传成功!");
				log.info("上传文件路径：" + domain + r.url());
			} else {
				log.info("上传失败!");
			}
			System.out.println(token); // 输出上传凭证
			System.out.println(r.isOK()); // 输出上传到七牛云之后的文件名称
			System.out.println(r.toString()); // 输出上传到七牛云之后的文件名称
			System.out.println(r.url()); // 输出上传到七牛云之后的文件名称
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public static String uploadFileV2(MultipartFile file, String fileName) throws IOException {
		com.qiniu.storage.Configuration cfg = new Configuration();
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(QiniuUtils.ACCESS_KEY, QiniuUtils.SECRET_KEY);
		String token = auth.uploadToken(QiniuUtils.BUCKET_NAME);
		Response r = uploadManager.put(file.getBytes(), fileName, token);
		if (r.isOK()) {
			log.info("上传成功!");
			log.info("上传文件路径：" + domain + r.url());
		} else {
			log.info("上传失败!");
		}
		System.out.println(token); // 输出上传凭证
		System.out.println(r.isOK()); // 输出上传到七牛云之后的文件名称
		System.out.println(r.toString()); // 输出上传到七牛云之后的文件名称
		System.out.println(r.url()); // 输出上传到七牛云之后的文件名称
		String URL = QiniuUtils.domain + "/" + fileName;
		String downloadRUL = auth.privateDownloadUrl(URL, TOKEN_TIME);
		log.info("下载文件路径：" + downloadRUL);
		return downloadRUL;
	}
	public static String uploadFileV2(File file, String fileName) throws IOException {
		com.qiniu.storage.Configuration cfg = new Configuration();
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(QiniuUtils.ACCESS_KEY, QiniuUtils.SECRET_KEY);
		String token = auth.uploadToken(QiniuUtils.BUCKET_NAME);
		FileInputStream input = new FileInputStream(file);
		Response r = uploadManager.put(IOUtils.toByteArray(input), fileName, token);
		if (r.isOK()) {
			log.info("上传成功!");
			log.info("上传文件路径：" + domain + r.url());
		} else {
			log.info("上传失败!");
		}
		System.out.println(token); // 输出上传凭证
		System.out.println(r.isOK()); // 输出上传到七牛云之后的文件名称
		System.out.println(r.toString()); // 输出上传到七牛云之后的文件名称
		System.out.println(r.url()); // 输出上传到七牛云之后的文件名称
		String URL = QiniuUtils.domain + "/" + fileName;
		String downloadRUL = auth.privateDownloadUrl(URL, TOKEN_TIME);
		log.info("下载文件路径：" + downloadRUL);
		return downloadRUL;
	}

	public static String downloadURL(String fileName) {
		// 密钥配置
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		// 构造私有空间的需要生成的下载的链接
		String URL = QiniuUtils.domain + "/" + fileName;
		log.info("下载原始文件路径：" + URL);
		// 调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
		String downloadRUL = auth.privateDownloadUrl(URL, TOKEN_TIME);
		log.info("下载文件路径：" + downloadRUL);
		System.out.println(downloadRUL);
		return downloadRUL;
	}

	public static byte[] chanageInputStream2byte(InputStream fis) {
		byte[] buffer = null;
		try {
			// FileInputStream fis = new FileInputStream(tradeFile);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	public static String FormetFileSize(Long fileLength) {
		String fileSizeString = "";
		DecimalFormat df = new DecimalFormat("#.00");
		if (fileLength != null) {
			if (fileLength < 1024) {
				fileSizeString = df.format((double) fileLength) + "B";
			} else if (fileLength < 1048576) {
				fileSizeString = df.format((double) fileLength / 1024) + "K";
			} else if (fileLength < 1073741824) {
				fileSizeString = df.format((double) fileLength / 1048576) + "M";
			} else {
				fileSizeString = df.format((double) fileLength / 1073741824) + "G";
			}
		}
		return fileSizeString;
	}

	public static String getFileNameByDate(String filename) {
		String filenameExtension = "";
		String filenamePre = "";
		if (filename.contains(".")) {
			filenameExtension = filename.substring(filename.indexOf(".") + 1, filename.length());
			filenamePre = filename.substring(0, filename.indexOf("."));
		} else {
			filenamePre = filename;
		}
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
		String filenameNew = filenamePre + "-" + time.format(new Date()) + "." + filenameExtension;
		log.info(filenameNew);
		return filenameNew;

	}
	
	public static String getFileNameByDate(String username , String filename) {
		String filenameExtension = "";
		String filenamePre = "";
		if (filename.contains(".")) {
			filenameExtension = filename.substring(filename.indexOf(".") + 1, filename.length());
			filenamePre = filename.substring(0, filename.indexOf("."));
		} else {
			filenamePre = filename;
		}
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
		String filenameNew =username+"/" + filenamePre + "-" + time.format(new Date()) +"-"+  username+ "." + filenameExtension;
		log.info(filenameNew);
		return filenameNew;
		
	}

	// 删除文件
	public static int deleteFileFromQiniu(String fileName) {
		// 构造一个带指定Zone对象的配置类
		@SuppressWarnings("deprecation")
		Configuration cfg = new Configuration(Zone.zone0());
		String key = fileName;
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		BucketManager bucketManager = new BucketManager(auth, cfg);
		try {
			Response delete = bucketManager.delete(BUCKET_NAME, key);
			return delete.statusCode;
		} catch (QiniuException ex) {
			// 如果遇到异常，说明删除失败
			ex.printStackTrace();
			System.err.println(ex.code());
			System.err.println(ex.response.toString());
		}
		return -1;
	}


	public static void main(String[] args) {
		try {
//			uploadFile("D:\\11111108599.zip", "11111108599.zip");
//			uploadFile(new File("D:\\quickstart-master.tar.gz"), "quickstart-master.tar.gz");
//			download("11111108599.zip");
//			getFileNameByDate("11111108599.tar.gz");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	// *****************************************************************************************************************
	// *****************************************************************************************************************
	// *****************************************************************************************************************

	private static Logger logger = LoggerFactory.getLogger(QiniuUtils.class);
	String bucketName = "bucketName";
//	private String bucketName = propertyUtil.getProperty("bucketName");
//	private String domain = propertyUtil.getProperty("domain");
//	private String ACCESS_KEY = propertyUtil.getProperty("ACCESS_KEY");
//	private String SECRET_KEY = propertyUtil.getProperty("SECRET_KEY");

	//通过文件路径上传文件
	public ExecuteResult<String> uploadFile(String localFile) throws AuthException, JSONException {
		File file = new File(localFile);
		/**
		 * 文件后缀名 文件扩展名
		 */
		String filenameExtension = localFile.substring(localFile.lastIndexOf("."), localFile.length());
		return uploadFile2(file, filenameExtension);
	}

	//通过File上传
	public ExecuteResult<String> uploadFile2(File file, String filename) throws AuthException, JSONException {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
//        String uptoken = getUpToken();
		// 可选的上传选项，具体说明请参见使用手册。
//        PutExtra extra = new PutExtra();
//        PutRet ret = IoApi.putFile(uptoken,filename , extra);
		com.qiniu.storage.Configuration cfg = new Configuration();
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(ACCESS_KEY,SECRET_KEY);
		String token = auth.uploadToken(bucketName);
		Response r;
		try {
			r = uploadManager.put(file, filename, token);
			System.out.println(token);   //输出上传凭证
			System.out.println(r.isOK());    //输出上传到七牛云之后的文件名称
			System.out.println(r.toString());    //输出上传到七牛云之后的文件名称
			if (r.isOK()) {
				executeResult.setSuccessMessage("上传成功!");
				executeResult.setResult(domain+r.url());
			} else {
				executeResult.addErrorMessage("上传失败");
			}
		} catch (QiniuException e) {
			e.printStackTrace();
		}


		return executeResult;
	}
	public String getFileNameByDateAndUUID(String filenameExtension) {
		SimpleDateFormat time = new SimpleDateFormat("yyyy/MM/dd");
		String filename = time.format(new Date()) + "/" + UUID.randomUUID() + filenameExtension;
		return filename;

	}

	/**
	 * 从 inputstream 中写入七牛
	 *
	 * @param content 要写入的内容
	 * @return
	 * @throws AuthException
	 * @throws JSONException
	 */
	public boolean uploadFile2(String content ,String fileName) throws AuthException, JSONException {
		// 读取的时候按的二进制，所以这里要同一
		ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
		// 可选的上传选项，具体说明请参见使用手册。
		com.qiniu.storage.Configuration cfg = new Configuration();
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(ACCESS_KEY,SECRET_KEY);
		String token = auth.uploadToken(bucketName);

		Response r;
		try {
			r = uploadManager.put(QiniuUtils.chanageInputStream2byte(inputStream), fileName, token);
//	    	Response r = uploadManager.put(inputStream, key, token);
			System.out.println(token);   //输出上传凭证
			System.out.println(r.isOK());    //输出上传到七牛云之后的文件名称
			System.out.println(r.toString());    //输出上传到七牛云之后的文件名称
			if (r.isOK()) {
				return true;
			} else {
				return false;
			}
		} catch (QiniuException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean uploadFile2(InputStream ins,String fileName) throws AuthException, JSONException {
		com.qiniu.storage.Configuration cfg = new Configuration();
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(ACCESS_KEY,SECRET_KEY);
		String token = auth.uploadToken(bucketName);
		Response r;
		try {
			r = uploadManager.put(QiniuUtils.chanageInputStream2byte(ins), fileName, token);
			//Response r = uploadManager.put(inputStream, key, token);
			System.out.println(token);   //输出上传凭证
			System.out.println(r.isOK());    //输出上传到七牛云之后的文件名称
			System.out.println(r.toString());    //输出上传到七牛云之后的文件名称
			if (r.isOK()) {
				return true;
			} else {
				return false;
			}
		} catch (QiniuException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static byte[] chanageInputStream2byte2(InputStream fis){
		byte[] buffer = null;
		try
		{
//            FileInputStream fis = new FileInputStream(tradeFile);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1)
			{
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		return buffer;
	}
	public static byte[] File2byte(File tradeFile){
		byte[] buffer = null;
		try
		{
			FileInputStream fis = new FileInputStream(tradeFile);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1)
			{
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		return buffer;
	}

	//获得下载地址
//    public String getDownloadFileUrl(String filename) throws Exception {
//        Mac mac = getMac();
//        String baseUrl = URLUtils.makeBaseUrl(domain, filename);
//        GetPolicy getPolicy = new GetPolicy();
//        String downloadUrl = getPolicy.makeRequest(baseUrl, mac);
//        return downloadUrl;
//    }
//
//    //删除文件
//    public void deleteFile(String filename) {
//        Mac mac = getMac();
//        RSClient client = new RSClient(mac);
//        client.delete(domain, filename);
//    }
//
//    //获取凭证
//    private String getUpToken() throws AuthException, JSONException {
//        Mac mac = getMac();
//        PutPolicy putPolicy = new PutPolicy(bucketName);
//        String uptoken = putPolicy.token(mac);
//        return uptoken;
//    }
//
//    private Mac getMac() {
//        Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
//        return mac;
//    }

}
