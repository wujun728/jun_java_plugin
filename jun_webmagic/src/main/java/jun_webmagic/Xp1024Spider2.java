package jun_webmagic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Xp1024Spider2 implements PageProcessor {
	    private Site site = Site.me().setRetryTimes(10).setSleepTime(1000);
	    private static int num = 0;
	    public static void main(String[] args) throws Exception {
	        long startTime, endTime;
	        System.out.println("========爬虫【启动】=========");
	        startTime = new Date().getTime();
	        Spider.create(new Xp1024Spider2()).addUrl("http://yj1.b96dure93e9.rocks/pw/thread.php?fid=14").thread(5).run();
	        endTime = new Date().getTime();
	        System.out.println("========爬虫【结束】=========");
	        System.out.println("爬到" + num + "篇博客！用时为：" + (endTime - startTime) / 1000 + "s");
	    }
	 
	    @Override
	    public void process(Page page) {
	        //1. 如果是博文列表页面 【入口页面】，将所有博文的详细页面的url放入target集合中。
	        // 并且添加下一页的url放入target集合中。
	    	// https://blog\\.hellobi\\.com/hot/weekly\\?page=\\d+
	    	// http://xh2.1024xp2.com/pw/html_data/14/1907/4159013.html
	        if (page.getUrl().regex("http://yj1\\.b96dure93e9\\.rocks/pw/html_data/14/1907/[0-9]{7}\\.html").match()) {
	            //目标链接
	            page.addTargetRequests(page.getHtml().xpath("//*[@id=\"ajaxtable\"]/tbody[2]/a").links().all());
	            //下一页博文列表页链接
	            page.addTargetRequests(page.getHtml().xpath("//*[@id=\"main\"]/div[10]/span[2]/div[1]/a").links().all());
	            System.err.println("111page.getUrl()="+page.getUrl());
	        }
	        //2. 如果是博文详细页面
	        else {
//	            String content1 = page.getHtml().get();
	            try {
	                String title = page.getHtml().xpath("//*[@id=\"breadCrumb\"]/font/a[4]/text()").get();
	                String content = page.getHtml().xpath("//*[@id=\"read_tpc\"]/img").get();
	                System.err.println("title="+title+",content="+content);
	                num++;
	                System.out.println("num2222:" + num );//输出对象
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
	 
	    @Override
	    public Site getSite() {
	        return this.site;
	    }


}
