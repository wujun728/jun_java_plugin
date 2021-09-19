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

public class Demo03 {

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
        }
        
        System.out.println("===============");
        Elements hrefElements=doc.select("a[href]"); // ����href���Ե�aԪ��
        for(Element e:hrefElements){
        	System.out.println(e.toString());
        }
        
        System.out.println("===============");
        Elements imgElements=doc.select("img[src$=.png]"); // ������չ��Ϊ.png��ͼƬDOM�ڵ�
        for(Element e:imgElements){
        	System.out.println(e.toString());
        }
        
        Element element=doc.getElementsByTag("title").first(); // ��ȡtag��title������DOMԪ��
        String title=element.text(); // ����Ԫ�ص��ı�
        System.out.println("��ҳ�����ǣ�"+title);
        
	}
}
