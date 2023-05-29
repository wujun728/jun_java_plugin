package com.jun.plugin.jsoupdemo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Demo04 {

	public static void main(String[] args) throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault(); // ����httpclientʵ��
        HttpGet httpget = new HttpGet("http://www.cnblogs.com/"); // ����httpgetʵ��
         
        CloseableHttpResponse response = httpclient.execute(httpget); // ִ��get����
        HttpEntity entity=response.getEntity(); // ��ȡ����ʵ��
        String content=EntityUtils.toString(entity, "utf-8");
        response.close(); // �ر������ͷ�ϵͳ��Դ
        
        Document doc=Jsoup.parse(content); // ������ҳ �õ��ĵ�����
        
        Elements linkElements=doc.select("#post_list .post_item .post_item_body h3 a"); //ͨ��ѡ�����������в�������DOM
        for(Element e:linkElements){
        	System.out.println("���ͱ��⣺"+e.text());
        	System.out.println("���͵�ַ��"+e.attr("href"));
        	System.out.println("target��"+e.attr("target"));
        }
        
        Element linkElement=doc.select("#friend_link").first();
        System.out.println("���ı���"+linkElement.text());
        System.out.println("Html��"+linkElement.html());
	}
}
