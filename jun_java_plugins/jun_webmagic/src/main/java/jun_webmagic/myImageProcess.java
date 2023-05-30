package jun_webmagic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.JMException;

import org.jsoup.helper.StringUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.processor.PageProcessor;

public class myImageProcess implements PageProcessor{
	//ҳURLʽ
    //.ƥеַ//.ʾֻƥһ//.?ͬ
	
    private static String REGEX_PAGE_URL = "http://www\\.win4000\\.com/zt/gaoqing_\\w+.html";
    //ȡҳ
    public static int PAGE_SIZE = 6;
    //
    public static int INDEX_PHOTO =1;
    
	public void process(Page page) {
		  List<String> SpidertURL = new ArrayList<String>();
		  
	        for (int i = 2; i < PAGE_SIZE; i++){//ӵĿurl
	        	SpidertURL.add("http://www.win4000.com/zt/gaoqing_" + i + ".html");
	        }
	        //url
	        page.addTargetRequests(SpidertURL);
	        //ͼƬбҳ
	        System.out.println(page.getUrl());
	        if (page.getUrl().regex(REGEX_PAGE_URL).match()) {
	            //ҳ
	            //page.getHtml().xpath("//a[@class=\"title\"]").links().all();
	            List<String> detailURL = page.getHtml().xpath("//ul[@class='clearfix']/li/a").links().all();
	            int x = 1;
	            for (String str:detailURL){//
	            	System.out.println(x+"----"+str);
	            	x++;
	            }
	            page.addTargetRequests(detailURL);
	        } else {//ҳ
	        		String detailUrl = page.getUrl().toString();
	        		System.out.println(detailUrl);
	                String picURL = page.getHtml().xpath("//div[@class='pic-meinv']/a").css("img", "src").toString();
	                System.out.println(picURL);
	                String currentIndex = page.getHtml().xpath("//div[@class='ptitle']/span/text()").toString();
	                String picname = page.getHtml().xpath("//div[@class='ptitle']/h1/text()").toString();
	                if(!"1".equals(currentIndex)){//ǵһҳͼƬƼҳ˳
	                	picname = picname+"_"+detailUrl;
//	                	picname = picname+"_"+StringUtil.getURLIndex(detailUrl);
	                }
	                String allPic = page.getHtml().xpath("//div[@class='ptitle']/em/text()").toString();
	                if(allPic!= null && picURL != null && "1".equals(currentIndex)){
	                	Integer pageindex = Integer.parseInt(allPic);
	                	List<String>otherPic = new ArrayList<String>();
	                	for(int i=2;i<=pageindex;i++){
	                		otherPic.add(detailUrl.replaceAll(".html", "_"+i+".html"));
	                	}
                		page.addTargetRequests(otherPic);
	                }
	                System.out.println(picname);
	                try {
	                    /**
	                     * String ͼƬַ
	                     * String ͼƬ
	                     * String ·
	                     */
	                	if(picURL !=null){
//	                		DownloadUtil.download( picURL, picname + ".jpg", "E:\\image3\\");
	                		System.out.println(""+(INDEX_PHOTO++)+""+",picname="+picname+",picURL="+picURL);
	                	}
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
		   }
		
	}

 public Site getSite() {
		return Site.me();
	}
	
	
	public static void main(String [] args) throws JMException{
		Date stdate = new Date();
		System.out.println("ʼʱ䣺"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(stdate));
		Spider picSpider = Spider.create(new myImageProcess()).addUrl("http://www.win4000.com/zt/gaoqing_1.html")
		.thread(5);
		SpiderMonitor.instance().register(picSpider);
		picSpider.start();
		Date edDate = new Date();
		System.out.println("ʱ䣺"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(edDate));
		System.out.println("ʱ"+(edDate.getTime()-stdate.getTime())/1000/60+"");
	}
}