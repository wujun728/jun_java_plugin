package io.itit.itf.okhttp.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.itit.itf.okhttp.FastHttpClient;
import io.itit.itf.okhttp.Response;
import io.itit.itf.okhttp.callback.Callback;
import io.itit.itf.okhttp.callback.DownloadFileCallback;
import io.itit.itf.okhttp.callback.StringCallback;
import io.itit.itf.okhttp.interceptor.DownloadFileInterceptor;
import io.itit.itf.okhttp.util.FileUtil;
import junit.framework.TestCase;
import okhttp3.Call;

/**
 * 
 * @author icecooly
 *
 */
public class HttpClientTestCase extends TestCase{
	//
	private static Logger logger=LoggerFactory.getLogger(HttpClientTestCase.class);
	//
	private static String url = "http://localhost:7002/p/api/test";
	//
	public void testGetSync() throws IOException{
		Response response = FastHttpClient.get().url("http://sz.bendibao.com/news/2016923/781534.htm").
				addParams("para1", "icecool").
				addParams("para2", "111111").
				build().
				execute();
		logger.info(response.string("gb2312"));//default is utf_8
	}
	//
	public void testPostSync() throws IOException{
		Response response = FastHttpClient.post().url(url).
				addParams("para1", "123456").
				addParams("para2", "测试").
				build().
				execute();
		logger.info(response.string());
	}
	//
	public void testGetAsync() throws InterruptedException{
		FastHttpClient.get().url(url).
		addParams("para1", "icecool").
		addParams("para2", "111111").
		build().
		executeAsync(new StringCallback() {
			@Override
			public void onFailure(Call call, Exception e, int id) {
				logger.error(e.getMessage(),e);
			}
			@Override
			public void onSuccess(Call call, String response, int id) {
				logger.info("response:{}",response);
			}
		});
		Thread.sleep(50000);
	}
	//
	public void testPostAsync() throws InterruptedException{
		FastHttpClient.post().url(url).
		addParams("para1", "icecool").
		addParams("para2", "测试中文").
		build().
		executeAsync(new Callback() {
				@Override
				public void onFailure(Call call, Exception e, int id) {
					logger.error(e.getMessage(),e);
				}
				@Override
				public void onResponse(Call call, Response response, int id) {
					try {
						logger.info(response.string());
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.exit(0);
				}
		});
		Thread.sleep(50000);
	}
	//
	public void testDownloadFile() throws InterruptedException{
		FastHttpClient.get().
		url("http://e.hiphotos.baidu.com/image/pic/item/faedab64034f78f0b31a05a671310a55b3191c55.jpg").
		build().addNetworkInterceptor(new DownloadFileInterceptor(){
			@Override
			public void updateProgress(long downloadLenth, long totalLength, boolean isFinish) {
				logger.info("updateProgress downloadLenth:"+downloadLenth+
						",totalLength:"+totalLength+",isFinish:"+isFinish);
			}
		}).
		executeAsync(new DownloadFileCallback("/tmp/tmp.jpg") {//save file to /tmp/tmp.jpg
				@Override
				public void onFailure(Call call, Exception e, int id) {
					logger.error(e.getMessage(),e);
				}
				@Override
				public void onSuccess(Call call, File file, int id) {
					logger.info("filePath:"+file.getAbsolutePath());
				}
				@Override
				public void onSuccess(Call call, InputStream fileStream, int id) {
					logger.info("onSuccessWithInputStream");
				}
		});
		Thread.sleep(50000);
	}
	//
	public void testUploadFile() throws UnsupportedEncodingException, IOException{
		byte[] imageContent=FileUtil.getBytes("/tmp/tmp.jpg");
		Response response = FastHttpClient.post().url(url).
				addFile("file1", "a.txt", "123").
				addFile("file2", "b.jpg", imageContent).
				build().connTimeOut(10000).
				execute();
		logger.info(response.body().string());
	}
	//
	public void testHttpsGet() throws IOException{
		Response response = FastHttpClient.get().url("https://kyfw.12306.cn/otn/").
				build().
				execute();
		logger.info(response.string());
	}
	//
	public void testHttpsPost() throws IOException{
		Response response = FastHttpClient.post().url("https://kyfw.12306.cn/otn/").
				build().
				execute();
		logger.info(response.string());
	}
	//
}
