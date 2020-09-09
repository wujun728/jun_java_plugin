package io.itit.itf.okhttp.callback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.itit.itf.okhttp.Response;
import io.itit.itf.okhttp.util.FileUtil;
import okhttp3.Call;

/**
 * 
 * @author icecooly
 *
 */
public abstract class DownloadFileCallback extends Callback{
	//
	public static Logger logger = LoggerFactory.getLogger(DownloadFileCallback.class);
	//
	private String fileAbsolutePath;
	//
	public DownloadFileCallback(){
	}
	//
	public DownloadFileCallback(String fileAbsolutePath){
		this.fileAbsolutePath=fileAbsolutePath;
	}
	//
	@Override
	public void onResponse(Call call, Response response, int id) {
		try {
			if(fileAbsolutePath!=null&&fileAbsolutePath.length()>0){
				File file=new File(fileAbsolutePath);
				FileUtil.saveContent(response.body().bytes(),file);
				onSuccess(call,file, id);
			}else{
				onSuccess(call,response.body().byteStream(),id);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}
	//
	public void onSuccess(Call call,File file, int id) {
		
	}
	//
	public void onSuccess(Call call,InputStream fileStream, int id) {
		
	}
}
