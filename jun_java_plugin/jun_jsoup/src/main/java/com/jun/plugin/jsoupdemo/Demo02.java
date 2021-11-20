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

public class Demo02 {

	public static void main(String[] args) throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault(); // ����httpclientʵ��
        HttpGet httpget = new HttpGet("http://www.cnblogs.com/"); // ����httpgetʵ��
         
        CloseableHttpResponse response = httpclient.execute(httpget); // ִ��get����
        HttpEntity entity=response.getEntity(); // ��ȡ����ʵ��
        String content=EntityUtils.toString(entity, "utf-8");
        response.close(); // �ر������ͷ�ϵͳ��Դ
        
        Document doc=Jsoup.parse(content); // ������ҳ �õ��ĵ�����
        Elements elements=doc.getElementsByTag("title"); // ��ȡtag��title������DOMԪ��
        Element element=elements.get(0); // ��ȡ��1��Ԫ��
        String title=element.text(); // ����Ԫ�ص��ı�
        System.out.println("��ҳ�����ǣ�"+title);
        
        Element element2=doc.getElementById("site_nav_top"); // ��ȡid=site_nav_top��DOMԪ��
        String navTop=element2.text(); // ����Ԫ�ص��ı�
        System.out.println("�ںţ�"+navTop);
        
        Elements itemElements=doc.getElementsByClass("post_item"); // ������ʽ��������ѯDOM
        System.out.println("=======���post_item==============");
        for(Element e:itemElements){
        	System.out.println(e.html());
        	System.out.println("-------------");
        }
        
        Elements widthElements=doc.getElementsByAttribute("width"); // ����������������ѯDOM
        System.out.println("=======���with��DOM==============");
        for(Element e:widthElements){
        	System.out.println(e.toString());
        	System.out.println("-------------");
        }
        
        Elements targetElements=doc.getElementsByAttributeValue("target", "_blank");
        System.out.println("=======���target-_blank��DOM==============");
        for(Element e:targetElements){
        	System.out.println(e.toString());
        	System.out.println("-------------");
        }
        
	}
}
