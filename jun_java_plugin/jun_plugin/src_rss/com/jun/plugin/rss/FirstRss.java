package com.jun.plugin.rss;

import java.io.FileWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

public class FirstRss {

	public static void main(String[] args) throws Exception {
		DateFormat dateParser = new SimpleDateFormat("yyyyMMddhhmmss");   
    
    SyndFeed feed = new SyndFeedImpl();   
    feed.setFeedType("rss_2.0");   
    feed.setTitle("Sample Feed (created with Rome)");   
    feed.setLink("http://rome.dev.java.net");   
    feed.setDescription("This feed has been created using Rome (Java syndication utilities");   
       
    List<SyndEntry> entries = new ArrayList<SyndEntry>();   
    SyndEntry entry;   
    SyndContent description;   
       
    //item 内容   
    //重复add产生多个item   
    entry = new SyndEntryImpl();   
    entry.setTitle("第一个条目的标题");   
    entry.setLink("http://wiki.java.net/bin/view/Javawsxml/Rome01");   
    entry.setPublishedDate(new Date());   
    description = new SyndContentImpl();   
    description.setType("text/plain");   
    description.setValue("描述内容,并提供以检测中文是否能够支持.");   
    entry.setDescription(description);   
    entries.add(entry);   
       
    feed.setEntries(entries);   
       
    String fileName = "c:\\feed"+dateParser.format(new Date())+".xml";   
    Writer writer = new FileWriter(fileName);   
    SyndFeedOutput output = new SyndFeedOutput();   
    output.output(feed,writer);
    writer.close();   

    System.out.println("本RSS写入到文件-> ["+fileName+"]");
	}

}
