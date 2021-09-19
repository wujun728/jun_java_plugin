package com.jun.plugin.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheTest {

	public static void main(String[] args) {
		CacheManager manager=CacheManager.create("./src/main/resources/ehcache.xml");
		Cache c=manager.getCache("a"); 
		Element e=new Element("test","abcde");
		c.put(e); 
		
		Element e2=c.get("test");
		System.out.println(e2);
		System.out.println(e2.getObjectValue());
		
		c.flush();
		manager.shutdown();
		
	}
}
