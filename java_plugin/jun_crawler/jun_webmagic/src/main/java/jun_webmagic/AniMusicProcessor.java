package jun_webmagic;


import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 
 * @author ReverieNight@Foxmail.com
 *
 */
public class AniMusicProcessor implements PageProcessor{
	
	private Site site = Site.me().setSleepTime(1000).setRetryTimes(3);
	
	//列表页的正则表达式
	public static final String URL_LIST = "http://xh2\\.1024xp2\\.com/pw/thread\\.php?fid=14";
    public static final String URL_POST = "http://xh2\\.1024xp2\\.com/pw/html_data/14/1907/[0-9]{7}\\.html";
	
	@Override
	public Site getSite() {
		return site;
	}
	
	@Override
	public void process(Page page) {
		//列表页
		 System.err.println(page.getUrl());
		 Selectable se = page.getUrl();
		 se.toString();
		 System.err.println(se.toString());
		 List<String> requests = page.getHtml().links().regex(URL_LIST).all();
		 
		 System.err.println(page.getHtml().links().toString());
	     page.addTargetRequests(requests);
        if (page.getUrl().toString().startsWith("http://xh2.1024xp2.com/pw/thread.php?fid=14") ) {
//        	if (page.getUrl().regex(URL_LIST).match()) {
//        	System.err.println(page.getHtml());
        	List<String> l_post = page.getHtml().xpath("//*[@id=\"ajaxtable\"]/tbody[2]").links().regex(URL_POST).all(); //目标详情
        	List<String> l_url = page.getHtml().links().regex(URL_LIST).all();	//所有的列表
            page.addTargetRequests(l_post);
            page.addTargetRequests(l_url);
        //详情页
        } else {
//        	String title = page.getHtml().xpath("//div[@class='location']").regex("\\[[\\S|\\s]+\\<").toString();	//匹配标题
//            page.putField("title", title.substring(0, title.length() - 1).trim());
//            page.putField("torrent", page.getHtml().xpath("//p[@class='original download']").links().toString().trim());	//匹配种子
        	String title = page.getHtml().xpath("//*[@id=\"breadCrumb\"]/font/a[4]/text()").get();
            String content = page.getHtml().xpath("//*[@id=\"read_tpc\"]/img[1]/").get();
            System.err.println("title="+title+",content="+content);
            System.err.println();
        }	
	}
	
	public static void main(String[] args) {
		Spider.create(new AniMusicProcessor())
			.addUrl("http://xh2.1024xp2.com/pw/thread.php?fid=14")	//开始地址	
			.addPipeline(new ConsolePipeline())	//打印到控制台
//			.addPipeline(new FilePipeline("D:\\webmagic\\AniMusic"))	//保存到文件夹
			.thread(5)	//开启5线程
			.run();
	}
	
}
