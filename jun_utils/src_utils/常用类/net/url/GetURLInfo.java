package book.net.url;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * 本例演示通过URL能够得到的信息
 **/
public class GetURLInfo {
	/**
	 * 输出一个URL相关的信息，主要使用了URLConnection类
	 */
    public static void printInfo(URL url) throws IOException {
    	// URL基本信息
    	System.out.println("  File: " + url.getFile());
    	System.out.println("  Protocol: " + url.getProtocol());
    	System.out.println("  Host: " + url.getHost());
    	System.out.println("  Port: " + url.getPort());
    	System.out.println("  Path: " + url.getPath());
    	
    	// 获取URLConnection对象
        URLConnection c = url.openConnection();
        // 连接到URL，如果不能连接到URL，则会出现超时信息。
        c.connect();
	
        // 显示信息
        System.out.println("  Content Type: " + c.getContentType());
        System.out.println("  Content Encoding: " + c.getContentEncoding());
        System.out.println("  Content Length: " + c.getContentLength());
        System.out.println("  Date: " + new Date(c.getDate()));
        System.out.println("  Last Modified: " +new Date(c.getLastModified()));
        System.out.println("  Expiration: " + new Date(c.getExpiration()));
	
        // 如果是HTTP连接，则能提供更丰富的信息
        if (c instanceof HttpURLConnection) {
            HttpURLConnection h = (HttpURLConnection) c;
            System.out.println("  Request Method: " + h.getRequestMethod());
            System.out.println("  Response Message: " +h.getResponseMessage());
            System.out.println("  Response Code: " + h.getResponseCode());
        }
    }
    
	public static void main(String[] args) {
		try {
			String urlStr = "http://www.sina.com.cn:80/index.htm";
			URL url = new URL(urlStr);
			printInfo(url);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
