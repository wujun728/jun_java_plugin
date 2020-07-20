package klg.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Request;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection.Response;
/**
 * 
 * @author klguang
 *
 */
public class JsoupHttp {
	
	
	private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:\"|')?([^\\s,;\"']*)");
	
	public static String get(String url) throws  IOException{
		java.lang.System.setProperty("https.protocols", "SSLv3,TLSv1,TLSv1.1,TLSv1.2");
		Request request=Jsoup.connect(url).timeout(100 * 1000).request();
		return execute(request); 
	}
	
	/**
	 * 用反射重写了jsoup execute方法，可以重用jsoup的 request 配置
	 * @param request
	 * @return
	 * @throws IOException
	 */
	
	public static String execute(Connection.Request request) throws IOException{
		//如果标头是Content-Encoding: gzip，方法一 设置头header("Accept-Encoding", "identity")，不让服务器进行gzip压缩。
        Method method = null;
		try {
			method = Response.class.getDeclaredMethod("createConnection",Connection.Request.class);
	        method.setAccessible(true);  
	        HttpURLConnection connection=(HttpURLConnection) method.invoke(Response.class, request); 
	        if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
	        	MyPrinter.print(connection.getHeaderFields());
	        	return null;
	        }
	        String result=getReponseBody(connection);
	        connection.disconnect();
	        
	        return result;
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static String getReponseBody(HttpURLConnection connection) throws IOException{
		InputStream inputStream=null;
		
		 if("gzip".equals(connection.getHeaderField("Content-Encoding"))){
			 inputStream=new GZIPInputStream(connection.getInputStream());
		 }else{
			 inputStream=connection.getInputStream();
		 }
		 
		 String charset=getCharsetFromContentType(connection.getContentType());
		 return IOUtils.toString(inputStream,charset);
	}
	
	private static String getCharsetFromContentType(String contentType){
	      if (contentType == null) return null;
	        Matcher m = charsetPattern.matcher(contentType);
	        if (m.find()) {
	            String charset = m.group(1).trim();
	            charset = charset.replace("charset=", "");
	            return validateCharset(charset);
	        }
	        return null;
	}
	
    private static  String validateCharset(String cs) {
        if (cs == null || cs.length() == 0) return null;
        cs = cs.trim().replaceAll("[\"']", "");
        try {
            if (Charset.isSupported(cs)) return cs;
            cs = cs.toUpperCase(Locale.ENGLISH);
            if (Charset.isSupported(cs)) return cs;
        } catch (IllegalCharsetNameException e) {
            // if our this charset matching fails.... we just take the default
        }
        return null;
    }	
    
    
    public static void main(String[] args) throws IOException {
    	String reposApi = "https://api.github.com/orgs/aeternity/repos";
    	System.out.println(JsoupHttp.get(reposApi));
	}
}
