package jun_webmagic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dbutils.ContentDAO;
import dbutils.NovelDAO;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class BlogWebmagic implements PageProcessor {
	private static final String START_URL = "^http://blog.java1234.com/index.html[?]page=\\d{1,2}&$";
	private static final String START_URL2 = "http://blog.java1234.com/index.html?page=1&";//
	private static final String DETAIL = "http://blog.java1234.com/blog/articles/\\d{1,4}.html";// http://yj1.b96dure93e9.rocks/pw//html_data/17/1907/4163869.html
	private static final String NEXT_PAGE = "^http://blog.java1234.com/index.html?page=\\\\d{1,2}&$";
	private static int count = 0;
	private Site site = Site.me().setDomain("www.baidu.com").setSleepTime(1131)
//            .setCharset("GBK")
			.setCharset("utf-8").setRetrySleepTime(2).setTimeOut(3000).setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page) {
		if (page.getUrl().regex(START_URL).match()) {
			System.err.println("page url is --> " + page.getUrl().toString());
		}
		if (page.getUrl().regex(START_URL).match() || page.getUrl().regex(NEXT_PAGE).match()) {
			List<String> list = page.getHtml().xpath("/html/body/div[1]/div[3]/div[1]/div[1]/div[2]").links().all();
			String next = page.getHtml().xpath("/html/body/div[1]/div[3]/div[1]/div[2]/nav/ul/li[3]/a").links()
					.toString();
			String next3 = page.getHtml().xpath("/html/body/div[1]/div[3]/div[1]/div[2]/nav/ul/li[4]/a").links()
					.toString();
			String next4 = page.getHtml().xpath("/html/body/div[1]/div[3]/div[1]/div[2]/nav/ul/li[5]/a").links()
					.toString();
            String next9 = page.getHtml().xpath("/html/body/div[1]/div[3]/div[1]/div[2]/nav/ul/li[8]/a").links().toString();
//            String next6 = page.getHtml().xpath("//*[@id=\"main\"]/div[10]/span[2]/div[1]/a[6]").links().toString();
//            List<String> list = page.getHtml().xpath("*[@id=\"main\"]/div[2]/div[2]/div[1]/div").links().all();
//            String next = page.getHtml().xpath("*[@id=\"main\"]/div[2]/div[2]/div[2]/a[8]").links().toString();
			list.add(next);
			list.add(next3);
			list.add(next4);
			list.add(next9);
			page.addTargetRequests(list);
			System.err.println(" new  add url list.size() =============" + list.size());
		} else if (page.getUrl().regex(DETAIL).match()
//        		|| page.getUrl().regex(DETAIL2).match()
//        		|| page.getUrl().regex(DETAIL3).match()
		) {
//            List<String> list = page.getHtml().xpath("/html/body/table[6]/tbody/tr/td[1]/table/tbody/tr/td/table[2]/tbody/tr/td/div/a").links().all();
//        	List<String> titles = page.getHtml().xpath("/html/body/table[6]/tbody/tr/td[1]/table/tbody/tr/td/table[2]/tbody/tr/td/div/a/text()").all();
//        	System.err.println("title ============="+titles);
			System.err.println("todo url is --> " + page.getUrl().toString());
			String fromUrl = page.getUrl().toString();
			String title = page.getHtml().xpath("/html/body/div[1]/div[3]/div[1]/div[1]/div[2]/div[1]/h3/strong/text()").get();
			String info = page.getHtml().xpath("/html/body/div[1]/div[3]/div[1]/div[1]/div[2]/div[3]/text()").get();
			String keywords = page.getHtml().xpath("/html/body/div[1]/div[3]/div[1]/div[1]/div[2]/div[5]/text()").get();
			String contents1 = page.getHtml().xpath("/html/body/div[1]/div[3]/div[1]/div[1]/div[2]/div[4]/text()").get();
			String contents1html = page.getHtml().xpath("/html/body/div[1]/div[3]/div[1]/div[1]/div[2]/div[4]").get();
//        	title = getHtmlText("title", title);
			System.err.println("fromUrl =============" + page.getUrl().toString());
			System.err.println("title =============" + title);
//			System.err.println("info =============" + info);
//			System.err.println("keywords =============" + keywords);
//			System.err.println("contents1 =============" + contents1);
//			System.err.println("contents1html =============" + contents1html);
			
        	List<String>  imglist = getImgList("img_list", contents1html);
        	UrlFileDownloadUtil.downloadPictures(imglist);

			Object params[] = new Object[19];
			params[3]=title;
			params[13]=info;
			params[7]=new Date();
			params[8]=new Date();
			params[9]=new Date();
			params[10]=keywords;
			params[4]=contents1;
			params[12]=contents1html;
			params[18]=fromUrl;
			try {
				ContentDAO c = new ContentDAO();
				c.addContentData(params);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			System.err
					.println("err 正则URL匹配失败  --------------------------------------------------------" + page.getUrl());
		}
		count++;
	}

	@Override
	public Site getSite() {
		return site;
	}

	public String getHtmlText(String tid, String html) {
		StringBuilder _content = new StringBuilder("");
		if (tid.equalsIgnoreCase("novel_title")) {
			List<String> titles = new ArrayList<String>();
			List<String> urls = new ArrayList<String>();
			Document doc = Jsoup.parse(html);
			Elements elements = doc.select("span");
			for (Element element : elements) {
				String title = element.text();
				titles.add(title);
			}
			for (String title : titles) {
				System.out.println(title);
				_content.append(title);
			}
		} else if (tid.equalsIgnoreCase("novel_time")) {
			List<String> titles = new ArrayList<String>();
			List<String> urls = new ArrayList<String>();
			Document doc = Jsoup.parse(html);
			Elements elements = doc.select("span");
			for (Element element : elements) {
				String title = element.text();
				titles.add(title);
			}
			for (String title : titles) {
				System.out.println(title);
				_content.append(title);
			}
		}
		return _content.toString();
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.err.println("spider  is  start  now !!!");
		Spider.create(new BlogWebmagic()).thread(10).addUrl(START_URL2).run();
		long end = System.currentTimeMillis();
		System.err.println("spider  is  end  now !!!");
		System.err.println("the  under   is   result");
		System.err.println("###########################################");
		System.err.println("the  count  is  {}" + count);
		System.err.println("cost  time  is :" + (end - start) / 1000 + "s");

	}
	
    public List<String> getImgList(String host,String html) {
    	host = "http://blog.java1234.com";
    	List<String> urls = new ArrayList<String>();
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("img");
		for (Element element : elements) {
			String imgSrc = element.select("img").attr("src"); 
			if(!imgSrc.contains("http")) {
				urls.add(host+imgSrc);
			}
		}
		for (String img : urls) {
			System.err.println("images  =   "+img);
		}
    	return urls;
    }

}
