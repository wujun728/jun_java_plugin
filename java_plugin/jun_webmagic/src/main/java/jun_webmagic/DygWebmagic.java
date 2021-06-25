package jun_webmagic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class DygWebmagic  implements PageProcessor {
    //保存信息
    private static HashMap<String, List<String>> map = new HashMap<String, List<String>>();
    private static final String START_URL = "^http://www.dygang.net/ys/index.htm$";
    private static final String START_URL2 = "http://www.dygang.net/ys/index.htm";
    private static final String DETAIL = "http://www.dygang.net/ys/\\d{1,8}/\\d{1,5}.htm";
    private static final String NEXT_PAGE = "^http://www.dygang.net/ys/index_\\d*.htm$";
//    private static final String START_URL = "^https://www.imooc.com/course/list$";
//    private static final String START_URL2 = "https://www.imooc.com/course/list";
//    private static final String DETAIL = "https://www.imooc.com/learn/\\d{1,8}";
//    private static final String NEXT_PAGE = "^https://www.imooc.com/course/list\\?page=\\d*$";
    private static int count = 0;
//    private static Spider spider = Spider.create(new MukeProcessor());
    private String keyTitles = "titles";
    private String keyUrls = "keyUrls";
    private Site site = Site.me()
            .setDomain("www.baidu.com")
            .setSleepTime(1131)
            .setCharset("GBK")
//            .setCharset("utf-8")
            .setRetrySleepTime(2)
            .setTimeOut(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");


    @Override
    public void process(Page page) {
        System.err.println("page  url  is  {}"+page.getUrl().toString());
//        System.err.println("page.getHtml()="+page.getHtml());
        
        if (page.getUrl().regex(START_URL).match() || page.getUrl().regex(NEXT_PAGE).match()) {
            List<String> list = page.getHtml().xpath("/html/body/table[6]/tbody/tr/td[1]/table/tbody/tr/td").links().all();
            String next = page.getHtml().xpath("/html/body/table[6]/tbody/tr/td[1]/table/tbody/tr/td/table[3]/tbody/tr/td/a/*").links().toString();
//            List<String> list = page.getHtml().xpath("*[@id=\"main\"]/div[2]/div[2]/div[1]/div").links().all();
//            String next = page.getHtml().xpath("*[@id=\"main\"]/div[2]/div[2]/div[2]/a[8]").links().toString();
            list.add(next);
            page.addTargetRequests(list);
            System.err.println(" add url list.size() ============="+list.size());
        } else if (page.getUrl().regex(DETAIL).match()) {
//            List<String> list = page.getHtml().xpath("/html/body/table[6]/tbody/tr/td[1]/table/tbody/tr/td/table[2]/tbody/tr/td/div/a").links().all();
        	List<String> titles = page.getHtml().xpath("/html/body/table[6]/tbody/tr/td[1]/table/tbody/tr/td/table[2]/tbody/tr/td/div/a/text()").all();
        	System.err.println("title ============="+titles);
        	
        	String filmName = page.getHtml().xpath("/html/body/table[6]/tbody/tr/td[1]/table/tbody/tr/td/table[3]/tbody/tr[1]/td[2]").get();
        	System.err.println("filmName ============="+filmName);
        	String filmTime = page.getHtml().xpath("/html/body/table[6]/tbody/tr/td[1]/table/tbody/tr/td/table[3]/tbody/tr[2]/td").get();
        	System.err.println("filmTime ============="+filmTime);
        	
        	String pic = page.getHtml().xpath("/html/body/table[6]/tbody/tr/td[1]/table/tbody/tr/td/table[3]/tbody/tr[1]/td[1]/table/tbody/tr/td/img").get();
        	System.err.println("pic  ============="+pic);
        	String picPath = pic.substring(pic.indexOf("src=\"") + 5, pic.indexOf("jpg") + 3);
        	System.err.println("picPath  ============="+picPath);
        	
        	String picBiger = page.getHtml().xpath("//*[@id=\"dede_content\"]/p[1]/img").get();
        	System.err.println("picBiger  ============="+picBiger);
        	String picBigerPath = picBiger.substring(picBiger.indexOf("src=\"") + 5, picBiger.indexOf("jpg") + 3);
        	System.err.println("picBigerPath  ============="+picBigerPath);
        	
        	List<String> dede_content_p2 = page.getHtml().xpath("//*[@id=\"dede_content\"]/p[2]").all();
        	System.err.println("dede_content_p2 ============="+dede_content_p2);
        	
        	List<String> dede_content_p3 = page.getHtml().xpath("//*[@id=\"dede_content\"]/p[3]").all();
        	System.err.println("dede_content_p3 ============="+dede_content_p3);
        	
        	List<String> dede_content_p4 = page.getHtml().xpath("//*[@id=\"dede_content\"]/p[4]").all();
        	System.err.println("dede_content_p4 ============="+dede_content_p4);
        	
        	
        	String pic_content_p3 = page.getHtml().xpath("//*[@id=\"dede_content\"]/p[3]/img").get();
        	String pic_content_p5 = page.getHtml().xpath("//*[@id=\"dede_content\"]/p[5]/img").get();
        	String pic_content_p6 = page.getHtml().xpath("//*[@id=\"dede_content\"]/p[6]/img").get();
        	String pic_content_p7 = page.getHtml().xpath("//*[@id=\"dede_content\"]/p[7]/img").get();
        	String pic_content_p4 = page.getHtml().xpath("//*[@id=\"dede_content\"]/p[4]/img").get();
        	String pic_content_p8 = page.getHtml().xpath("//*[@id=\"dede_content\"]/p[8]/img").get();
        	String pic_content_p9 = page.getHtml().xpath("//*[@id=\"dede_content\"]/p[9]/img").get();
        	if(pic_content_p5 == null) {
        		pic_content_p5 = pic_content_p3;
        		if(pic_content_p3 == null) {
        			pic_content_p5 = pic_content_p6;
        			if(pic_content_p6 == null) {
        				pic_content_p5 = pic_content_p7;
        				if(pic_content_p7 == null) {
        					pic_content_p5 = pic_content_p4;
        					if(pic_content_p4 == null) {
        						pic_content_p5 = pic_content_p8;
            					if(pic_content_p8 == null) {
            						pic_content_p5 = pic_content_p9;
                					if(pic_content_p9 == null) {
                						pic_content_p5 = "海报解析失败";
                					}
            					}
        					}
                    	}
                	}
            	}
        	}
        	System.err.println("pic_content_p5  ============="+pic_content_p5);
        	String pic_content_p5_path = pic_content_p5.substring(picBiger.indexOf("src=\"") + 5, pic_content_p5.indexOf("jpg") + 3);
        	System.err.println("pic_content_p5_path  ============="+pic_content_p5_path);
        	
        	
        	String downloadhtml = page.getHtml().xpath("//*[@id=\"dede_content\"]/table[1]").get();
//        	System.err.println("downloadlist ============="+downloadlist);
        	getDownloadLink(downloadhtml);
//            map.put(keyTitles + count, titles);
//            map.put(keyUrls + count, list);
//            List<String> list = page.getHtml().xpath("*[@id=\"main\"]/div[3]/div[1]/div[1]/div[2]/div/ul/li/a").links().all();
//            List<String> titles = page.getHtml().xpath("*[@id=\"main\"]/div[3]/div[1]/div[1]/div[2]/div/ul/li/a/text()").all();
//            map.put(keyTitles + count, titles);
//            map.put(keyUrls + count, list);
        } else {
        	System.err.println("err --------------------------------------------------------"+page.getUrl());
        }
        count++;
    }

    @Override
    public Site getSite() {
        return site;
    }
    public void getDownloadLink(String html) {
    	List<String> titles = new ArrayList<String>();
		List<String> urls = new ArrayList<String>();
    	Document doc = Jsoup.parse(html);
		Elements elements = doc.select("tr");
		for (Element element : elements) {
			String title = element.text();
			titles.add(title);
			urls.add(element.select("a").attr("href"));
			System.out.println(title+" ---> "+element.select("a").attr("href"));
		}
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.err.println("spider  is  start  now !!!");

        Spider.create(new DygWebmagic()).thread(5).addUrl(START_URL2).run();
        long end = System.currentTimeMillis();
        System.err.println("spider  is  end  now !!!");
        System.err.println("the  under   is   result");
        Set<Map.Entry<String, List<String>>> entries = map.entrySet();
        System.err.println("###########################################");
        for (Map.Entry<String, List<String>> entry : entries) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            values.forEach(s -> {
                System.err.println("the ke is {},the value is {}"+ key+s);
            });
            System.err.println("----------------------------------");
        }
        System.err.println("###########################################");
        System.err.println("the  count  is  {}"+ count);
        System.err.println("cost  time  is :"+(end-start)/1000+"s");


    }


}
