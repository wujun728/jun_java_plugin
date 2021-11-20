package jun_webmagic;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 爬取慕课网全部免费视频信息（这里是主要的视频url 和标题信息）
 */
public class MukeProcessor implements PageProcessor {
    //保存信息
    private static HashMap<String, List<String>> map = new HashMap<String, List<String>>();
    private static final String START_URL = "^https://www.imooc.com/course/list$";
    private static final String START_URL2 = "https://www.imooc.com/course/list";
    private static final String DETAIL = "https://www.imooc.com/learn/\\d{1,8}";
    private static final String NEXT_PAGE = "^https://www.imooc.com/course/list\\?page=\\d*$";
    private static int count = 0;
    private static Spider spider = Spider.create(new MukeProcessor());
    private String keyTitles = "titles";
    private String keyUrls = "keyUrls";
    private Site site = Site.me()
            .setDomain("www.baidu.com")
            .setSleepTime(1131)
            .setCharset("utf-8")
            .setRetrySleepTime(2)
            .setTimeOut(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");


    @Override
    public void process(Page page) {
    	System.err.println();
        System.err.println("the  get  url  is  {}"+page.getUrl().toString());
        if (page.getUrl().regex(START_URL).match() || page.getUrl().regex(NEXT_PAGE).match()) {
            List<String> list = page.getHtml().xpath("*[@id=\"main\"]/div[2]/div[2]/div[1]/div").links().all();
            String next = page.getHtml().xpath("*[@id=\"main\"]/div[2]/div[2]/div[2]/a[8]").links().toString();
            list.add(next);
            page.addTargetRequests(list);
        } else if (page.getUrl().regex(DETAIL).match()) {
            List<String> list = page.getHtml().xpath("*[@id=\"main\"]/div[3]/div[1]/div[1]/div[2]/div/ul/li/a").links().all();
            List<String> titles = page.getHtml().xpath("*[@id=\"main\"]/div[3]/div[1]/div[1]/div[2]/div/ul/li/a/text()").all();
            map.put(keyTitles + count, titles);
            map.put(keyUrls + count, list);
        } else {
        }
        count++;
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.err.println("spider  is  start  now !!!");

        spider
                .thread(10).addUrl(START_URL2)
                .run();
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