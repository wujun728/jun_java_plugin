package com.ketayao.learn.test.httpclient;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.Header;
import org.apache.http.impl.client.DefaultHttpClient;

public class __10086 {

	public void test() throws HttpException, IOException{
		String url = "http://12582.10086.cn/user/login/verifycode.aspx";
		HttpClient httpClient = new DefaultHttpClient();
		DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
		
		HttpGet getMethod = new HttpGet("http://12582.10086.cn/user/login/verifycode.aspx");
		getMethod.setHeader("Host", "12582.10086.cn");
		getMethod.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.2; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
		getMethod.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		getMethod.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		getMethod.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		getMethod.setHeader("Connection", "keep-alive");
//		getMethod.setHeader("Cookie", "Hm_lvt_6e06bb5a029d6c5473951d1079638828=1328777184942; Hm_lvt_e64244e1e591d0337e17a12b714c0996=1328777186856; WT_FPC=id=183.16.35.230-1530895312.30204303:lv=1328174810886:ss=1328174810886; ASP.NET_SessionId=gj25p555exiqjd45kdcqoq55; BIGipServernxt-wz=369797312.20480.0000; Hm_lpvt_6e06bb5a029d6c5473951d1079638828=1328777184942; Hm_lpvt_e64244e1e591d0337e17a12b714c0996=1328777186856; .12582portals=4CF97704261E34DBE12913CBA18211005E960282A771D94FF3709BAFC99610A7397BE8293ADB2E876A0A7380AC4A158016419745F27511D6E79F82A408D009519D6DDFE18A578E5CFC5F48025C75B33B6EBD43953A7DB05AEBAAB856E0AA29112818B4910350AADACD2012F5DE56297B1F6622ED7F4959D31E19C474E48F7773D7966437");
		getMethod.setHeader("Cache-Control", "max-age=0");
		int code = httpClient.execute(getMethod);
        Header header = getMethod.getFirstHeader("Set-cookie"); 
        System.out.println(header.getValue());
        String headerCookie = header.getValue();
        String SessionId = headerCookie.substring(headerCookie.indexOf("NET_SessionId=") + "NET_SessionId=".length(), headerCookie.indexOf(";"));
        System.out.println(SessionId);
        String BIGipServernxt = headerCookie.substring(headerCookie.indexOf("BIGipServernxt-wz=") + "BIGipServernxt-wz=".length(), headerCookie.length());
        BIGipServernxt = BIGipServernxt.substring(0, BIGipServernxt.indexOf(";"));
        StringBuffer sb = new StringBuffer();
        sb.append("Hm_lvt_6e06bb5a029d6c5473951d1079638828=1328778307106; ");
        sb.append("Hm_lvt_e64244e1e591d0337e17a12b714c0996=1328778308090; ");
        sb.append("WT_FPC=id=183.16.35.230-1530895312.30204303:lv=1328174810886:ss=1328174810886; ");
        sb.append("ASP.NET_SessionId=").append(SessionId).append("; ");
        sb.append("BIGipServernxt-wz=").append(BIGipServernxt).append("; ");
        sb.append("Hm_lpvt_6e06bb5a029d6c5473951d1079638828=1328778307106; ");
        sb.append("Hm_lpvt_e64244e1e591d0337e17a12b714c0996=1328778308090");
        System.out.println(sb.toString());
        String ValidCode = savegif(getMethod);
        getMethod.releaseConnection();
        // 第二次链接
        httpClient.getHostConfiguration().setHost("12582.10086.cn", 80, "http");
        PostMethod method = getPostMethod(ValidCode);
		method.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.2; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
		method.setHeader("Host", "12582.10086.cn");
		method.setHeader("Accept", "application/json, text/javascript, */*");
		method.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
//		method.setHeader("Accept-Encoding", "gzip, deflate");
		method.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		method.setHeader("Connection", "keep-alive");
		method.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		method.setHeader("X-Requested-With", "XMLHttpRequest");
		method.setHeader("Referer", "http://12582.10086.cn/user/login/");
		method.setHeader("Cookie", sb.toString()); 
		httpClient.executeMethod(method);
		System.out.println(method.getStatusCode());
		System.out.println(method.getResponseBodyAsString());
		// 第三次链接
		Header header1 = method.getResponseHeader("Set-cookie"); 
		System.out.println("dd=" + header1.getValue());
		sb.append("; " + header1.getValue());
		Cookie[] cookies = httpClient.getState().getCookies();
		method.releaseConnection();
		String my = "http://12582.10086.cn/my";
		System.out.println(sb.toString());
		getMethod = new GetMethod(my);
		getMethod.setHeader("Host", "12582.10086.cn");
		getMethod.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.2; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
		getMethod.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		getMethod.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		getMethod.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		getMethod.setHeader("Connection", "keep-alive");
		getMethod.setHeader("Referer", "http://12582.10086.cn/user/login");
		getMethod.setHeader("Cookie", sb.toString());
		code = httpClient.executeMethod(getMethod);
		System.out.println(getMethod.getStatusCode());
		System.out.println(getMethod.getResponseBodyAsString());
		File storeFile = new File("c:/2008sohu.html");  
        FileOutputStream output = new FileOutputStream(storeFile);  
        //得到网络资源的字节数组,并写入文件  
        output.write(getMethod.getResponseBody());  
        output.close(); 
	}
	
	public String savegif(GetMethod getMethod) throws IOException {
		File storeFile = new File("c:/2008sohu.gif");  
        FileOutputStream output = new FileOutputStream(storeFile);  
        //得到网络资源的字节数组,并写入文件  
        output.write(getMethod.getResponseBody());  
        output.close();  
        InputStreamReader is = new InputStreamReader(System.in); 
		BufferedReader br = new BufferedReader(is);
		String ValidCode = "";
		try {
			ValidCode = br.readLine();
			br.close();
			is.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ValidCode;
	}
	
	private PostMethod getPostMethod(String ValidCode) {
		PostMethod post = new PostMethod("/ajax/postlogin");
		NameValuePair[] simcard = {
				new NameValuePair("email", "XXX"),
				new NameValuePair("password", "XXX"),
				new NameValuePair("ValidCode", ValidCode),
				new NameValuePair("rme", "0"),
				};
		post.setRequestBody(simcard);
		return post;
	}
	
	public static void main(String args[]) {
		__10086 _10086 = new __10086();
		try {
			_10086.test();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
