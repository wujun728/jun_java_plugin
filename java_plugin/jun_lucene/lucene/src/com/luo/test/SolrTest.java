package com.luo.test;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

public class SolrTest {
	private final static String solrServerUrl="http://localhost:8080/solr";
	private CommonsHttpSolrServer server=null;
	
	@Before
	public void init(){
		try {
			server=new CommonsHttpSolrServer(solrServerUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test0(){
		//1、创建solrServer对象CommonsHttpSolrServer 
		try {
			CommonsHttpSolrServer server=new CommonsHttpSolrServer(solrServerUrl);
			server.deleteByQuery("*:*");
			server.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void test1(){
		//1、创建solrServer对象CommonsHttpSolrServer 
		try {
			SolrInputDocument doc=new SolrInputDocument();
			doc.addField("id", "1");//id是唯一的主键，当相同的id添加后面的覆盖前面的
			doc.addField("title", "这是我第一个solrj程序,累");//最好在schema.xml将tile改为自己的field，如msg_title
			doc.addField("description", "我不爱学习爱睡觉");
			server.add(doc);
			server.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 基于java bean的添加
	 * Author:罗辉 ,Date:2013-7-31
	 */
	@Test
	public void test2(){
		//1、创建solrServer对象CommonsHttpSolrServer 
		try {
			List<Message> messages=new ArrayList<Message>();
			Message m1=new Message("2","基于java bean的添加", new String[]{"java bean内容","java bean添加的附件"});
			messages.add(m1);
			Message m2=new Message("3","基于java bean的数据添加", new String[]{"数据添加，没有附件"});
			messages.add(m2);
			server.addBeans(messages);
			server.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
