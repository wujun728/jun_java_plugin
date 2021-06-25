package jun_webmagic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dbutils.NovelDAO;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Pic14Webmagic2  implements PageProcessor {
//    private static HashMap<String, List<String>> map = new HashMap<String, List<String>>();
    private static final String START_URL = "^http://yj1.b96dure93e9.rocks/pw/thread.php\\Sfid\\S14\\Spage\\S\\d{1,4}$";
    private static final String START_URL2 = "http://yj1.b96dure93e9.rocks/pw/thread.php?fid=14&page=1";//
    private static final String DETAIL = "http://yj1.b96dure93e9.rocks/pw/html_data/\\d{1,2}/\\d{1,4}/\\d{1,7}.html";//http://yj1.b96dure93e9.rocks/pw/html_data/17/1907/4163869.html
    private static final String DETAIL2 = "http://yj1.b96dure93e9.rocks/pw/read.php\\Stid\\S\\d{1,7}\\Sfpage\\S\\d{1,4}";//http://yj1.b96dure93e9.rocks/pw/read.php?tid=3976925&fpage=100
    private static final String DETAIL3 = "http://yj1.b96dure93e9.rocks/pw/read.php\\Stid\\S\\d{1,7}\\Spage\\Se\\Sfpage\\S\\d{1,4}\\S+";//http://yj1.b96dure93e9.rocks/pw/read.php?tid=3976880&page=e&fpage=100#a
    private static final String NEXT_PAGE = "^http://yj1.b96dure93e9.rocks/pw/thread.php\\Sfid\\S14\\Spage\\S\\d{1,4}$";
//    private static final String START_URL = "^https://www.imooc.com/course/list$";
//    private static final String START_URL2 = "https://www.imooc.com/course/list";
//    private static final String DETAIL = "https://www.imooc.com/learn/\\d{1,8}";
//    private static final String NEXT_PAGE = "^https://www.imooc.com/course/list\\?page=\\d*$";
    private static int count = 0;
//    private static Spider spider = Spider.create(new MukeProcessor());
//    private String keyTitles = "titles";
//    private String keyUrls = "keyUrls";
    private Site site = Site.me()
            .setDomain("www.baidu.com")
            .setSleepTime(1131)
//            .setCharset("GBK")
            .setCharset("utf-8")
            .setRetrySleepTime(2)
            .setTimeOut(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");


    @Override
    public void process(Page page) {
    	if (page.getUrl().regex(START_URL).match()) {
    		System.err.println("page url is --> "+page.getUrl().toString());
    	}
//        System.err.println("page.getHtml()="+page.getHtml());
        if (page.getUrl().regex(START_URL).match() || page.getUrl().regex(NEXT_PAGE).match()) {
            List<String> list = page.getHtml().xpath("//*[@id=\"main\"]/div[8]").links().all();
            String next = page.getHtml().xpath("//*[@id=\"main\"]/div[10]/span[2]/div[1]/a[2]").links().toString();
            String next3 = page.getHtml().xpath("//*[@id=\"main\"]/div[10]/span[2]/div[1]/a[3]").links().toString();
            String next4 = page.getHtml().xpath("//*[@id=\"main\"]/div[10]/span[2]/div[1]/a[4]").links().toString();
            String next5 = page.getHtml().xpath("//*[@id=\"main\"]/div[10]/span[2]/div[1]/a[5]").links().toString();
            String next6 = page.getHtml().xpath("//*[@id=\"main\"]/div[10]/span[2]/div[1]/a[6]").links().toString();
//            List<String> list = page.getHtml().xpath("*[@id=\"main\"]/div[2]/div[2]/div[1]/div").links().all();
//            String next = page.getHtml().xpath("*[@id=\"main\"]/div[2]/div[2]/div[2]/a[8]").links().toString();
            list.add(next);
            list.add(next3);
            list.add(next4);
            list.add(next5);
            list.add(next6);
            page.addTargetRequests(list);
            System.err.println(" new  add url list.size() ============="+list.size());
        } else if (page.getUrl().regex(DETAIL).match() 
        		|| page.getUrl().regex(DETAIL2).match()
        		|| page.getUrl().regex(DETAIL3).match()
        		) {
//            List<String> list = page.getHtml().xpath("/html/body/table[6]/tbody/tr/td[1]/table/tbody/tr/td/table[2]/tbody/tr/td/div/a").links().all();
//        	List<String> titles = page.getHtml().xpath("/html/body/table[6]/tbody/tr/td[1]/table/tbody/tr/td/table[2]/tbody/tr/td/div/a/text()").all();
//        	System.err.println("title ============="+titles);
        	System.err.println("todo url is --> "+page.getUrl().toString());
        	String title = page.getHtml().xpath("//*[@id=\"subject_tpc\"]").get();
        	title = getHtmlText("novel_title", title);
        	System.err.println("title ============="+title);
        	
        	String uptime = page.getHtml().xpath("//*[@id=\"td_tpc\"]/div[1]/span[2]").get();
        	uptime = getHtmlText("novel_time", uptime);
        	System.err.println("uptime ============="+uptime);
        	
        	String content = page.getHtml().xpath("//*[@id=\"read_tpc\"]").get();
        	System.err.println("content ============="+content.length());
        	List<String>  imglist = getImgList("img_list", content);
        	UrlFileDownloadUtil.downloadPictures(imglist);
        	
			/*
			 * Object params[] = {title,""+content.length(), content, uptime, new
			 * Date(),page.getUrl().toString()}; try { NovelDAO n = new NovelDAO();
			 * n.add(params); } catch (SQLException e) { e.printStackTrace(); }
			 */
        	
        } else {
        	System.err.println("err --------------------------------------------------------"+page.getUrl());
        }
        count++;
    }

    @Override
    public Site getSite() {
        return site;
    }
    
    public String getHtmlText(String tid,String html) {
    	StringBuilder _content = new StringBuilder("");
    	if(tid.equalsIgnoreCase("novel_title")) {
    		List<String> titles = new ArrayList<String>();
			List<String> urls = new ArrayList<String>();
//			String html = "<span id=\"subject_tpc\">淫荡秘书晓日</span>";
			Document doc = Jsoup.parse(html);
			Elements elements = doc.select("span");
			for (Element element : elements) {
				String title = element.text();
				titles.add(title);
//				urls.add(element.select("a").attr("href"));
//				System.out.println(title+" ---> "+element.select("a").attr("href"));
			}
			for (String title : titles) {
				System.out.println(title);
				_content.append(title);
			}
    	}else if(tid.equalsIgnoreCase("novel_time")) {
    		List<String> titles = new ArrayList<String>();
			List<String> urls = new ArrayList<String>();
//			String html = "<span id=\"subject_tpc\">淫荡秘书晓日</span>";
			Document doc = Jsoup.parse(html);
			Elements elements = doc.select("span");
			for (Element element : elements) {
				String title = element.text();
				titles.add(title);
//				urls.add(element.select("a").attr("href"));
//				System.out.println(title+" ---> "+element.select("a").attr("href"));
			}
			for (String title : titles) {
				System.out.println(title);
				_content.append(title);
			}
    	} else if(tid.equalsIgnoreCase("novel_time")) {
    		List<String> titles = new ArrayList<String>();
    		List<String> urls = new ArrayList<String>();
    		Document doc = Jsoup.parse(html);
    		Elements elements = doc.select("img");
    		for (Element element : elements) {
    			;
    			titles.add((element.select("img").attr("src")));
    		}
    		for (String title : titles) {
    			System.out.println(title);
    		}
    	}
		return _content.toString();
    }
    
    public List<String> getImgList(String tid,String html) {
    	StringBuilder _content = new StringBuilder("");
    	List<String> titles = new ArrayList<String>();
    	List<String> urls = new ArrayList<String>();
    	if(tid.equalsIgnoreCase("img_list")) {
    		Document doc = Jsoup.parse(html);
    		Elements elements = doc.select("img");
    		for (Element element : elements) {
    			;
    			urls.add((element.select("img").attr("src")));
    		}
    		for (String title : urls) {
    			System.out.println(title);
    		}
    	}
    	return urls;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.err.println("spider  is  start  now !!!");
        Spider.create(new Pic14Webmagic2()).thread(10).addUrl(START_URL2).run();
        long end = System.currentTimeMillis();
        System.err.println("spider  is  end  now !!!");
        System.err.println("the  under   is   result");
        System.err.println("###########################################");
		/*
		 * Set<Map.Entry<String, List<String>>> entries = map.entrySet(); for
		 * (Map.Entry<String, List<String>> entry : entries) { String key =
		 * entry.getKey(); List<String> values = entry.getValue(); values.forEach(s -> {
		 * System.err.println("the ke is {},the value is {}"+ key+s); });
		 * System.err.println("----------------------------------"); }
		 */
        System.err.println("###########################################");
        System.err.println("the  count  is  {}"+ count);
        System.err.println("cost  time  is :"+(end-start)/1000+"s");


    }


}
