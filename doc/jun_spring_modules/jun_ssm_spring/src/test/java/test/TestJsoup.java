package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TestJsoup {
	public static void main(String[] args) throws IOException {
		
		Connection connect = Jsoup.connect("https://gd.122.gov.cn/m/login?winzoom=1");
		Map<String, String> header = new HashMap<String, String>(); 
		header.put("User-Agent", "  Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0");  
        header.put("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
        header.put("Accept-Language", "zh-cn,zh;q=0.5");  
        header.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");  
        header.put("Connection", "keep-alive");  
        Connection data = connect.data(header);
        data.request().cookie("test","test");
        Document doc =data.get();
        Response response = data.response();
        
        
        //我多写一个......
       String sessionId= response.cookie("JSESSIONID-L");
       System.out.println(sessionId);
        
        
        Map<String, String> cookies = response.cookies();
        
        
        Set set = cookies.entrySet();
        Map.Entry[] entries = (Map.Entry[])set.toArray(new Map.Entry[set.size()]);
        for (int i=0;i<entries.length;i++){
            System.out.println("Key:"+entries[i].getKey().toString());
            System.out.println("Value:"+entries[i].getValue().toString());
        }
        
        
        
        //System.out.println(response.body());
        
	
	    //System.out.println(doc.toString());
		
		//根据ID 得到登录表单的内容,jsonp有很多种选择器,具体查看文档,可以认为是java版的jquery,
		Element elementById = doc.getElementById("gryhdlHeader");
		
		System.out.println(elementById.toString());
		
		
		
	}

}
