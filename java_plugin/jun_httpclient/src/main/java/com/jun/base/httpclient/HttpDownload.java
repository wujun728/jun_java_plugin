package com.jun.base.httpclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpDownload {

	public static final int cache = 10 * 1024;
	public static final boolean isWindows;
	public static final String splash;
	public static final String root;
	static {
		if (System.getProperty("os.name") != null && System.getProperty("os.name").toLowerCase().contains("windows")) {
			isWindows = true;
			splash = "\\";
			root = "D:";
		} else {
			isWindows = false;
			splash = "/";
			root = "/search";
		}
	}

	public static String download(String url) {
		return download(url, null);
	}

	public static String download(String url, String filepath) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = client.execute(httpget);

			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			if (filepath == null)
				filepath = getFilePath(response);
			File file = new File(filepath);
			file.getParentFile().mkdirs();
			FileOutputStream fileout = new FileOutputStream(file);
			byte[] buffer = new byte[cache];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}
			is.close();
			fileout.flush();
			fileout.close();
			System.err.println("file.length()=" + file.length());
			if (file.length() < 255) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getFilePath(HttpResponse response) {
		String filepath = root + splash;
		String filename = getFileName(response);

		if (filename != null) {
			filepath += filename;
		} else {
			filepath += getRandomFileName();
		}
		return filepath;
	}

	public static String getFileName(HttpResponse response) {
		Header contentHeader = response.getFirstHeader("Content-Disposition");
		String filename = null;
		if (contentHeader != null) {
			HeaderElement[] values = contentHeader.getElements();
			if (values.length == 1) {
				NameValuePair param = values[0].getParameterByName("filename");
				if (param != null) {
					try {
						// filename = new String(param.getValue().toString().getBytes(), "utf-8");
						// filename=URLDecoder.decode(param.getValue(),"utf-8");
						filename = param.getValue();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return filename;
	}

	public static String getRandomFileName() {
		return String.valueOf(System.currentTimeMillis());
	}

	public static void outHeaders(HttpResponse response) {
		Header[] headers = response.getAllHeaders();
		for (int i = 0; i < headers.length; i++) {
			System.out.println(headers[i]);
		}
	}

	static String url;
	static String filepath;

	public static void main(String[] args) {
//      String url = "http://bbs.btwuji.com/job.php?action=download&pid=tpc&tid=320678&aid=216617";  
//      String filepath = "D:\\test\\a.torrent";  
		/*
		 * String url=
		 * "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
		 * String filepath = "D:\\test\\a.jpg"; HttpDownload.download(url, filepath);
		 */
		for (Long i = 30702404568L; i < Long.MAX_VALUE; i++) {
			url = "http://m1.xoimg.co/upload/image/20170307/" + i + ".jpg";
			url = "http://m1.xoimg.co/upload/image/20170307/" + i + ".jpg";
//        	http://xoimg.co/upload/image/20170307/30702399642.jpg
			filepath = "E:\\test\\20170307\\" + i + ".jpg";
			System.err.println(url);
//        	new Thread(new HttpDownload()).start();
			HttpDownload.download(url, filepath);
		}
	}

//	@Override
//	public void run() {
//		HttpDownload.download(url, filepath); 
//	}  
}