package jun_webmagic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class CnBetaSpider implements PageProcessor {

	// 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		// 文章页，匹配 https://voice.hupu.com/nba/七位数字.html https://voice\\.hupu\\.com/nba/[0-9]{7}\\.html
//		https://www\\.cnbeta\\.com/articles/tech/[0-9]{6}.htm
		if (page.getUrl().regex("https://www\\.cnbeta\\.com/articles/tech/[0-9]{6}\\.htm").match()) {
			page.putField("Title", page.getHtml().xpath("/html/body/div[1]/div/div[2]/div/div[1]/header/h1/text()").toString());
//			page.putField("Image", page.getHtml().xpath("/html/body/div[4]/div[1]/div[2]/div/div[1]/img/text()").toString());
//			page.putField("Content", page.getHtml().xpath("/html/body/div[4]/div[1]/div[2]/div/div[2]/p[1]/text()")
//					.all().toString()
//					+ page.getHtml().xpath("/html/body/div[4]/div[1]/div[2]/div/div[2]/p[2]/text()").all().toString()
//					+ page.getHtml().xpath("/html/body/div[4]/div[1]/div[2]/div/div[2]/p[3]/text()").all().toString());
//            page.putField("Title", page.getHtml().xpath("/html/body/div[4]/div[1]/div[1]/h1/text()").toString());
//            page.putField("Content", page.getHtml().xpath("/html/body/div[4]/div[1]/div[2]/div/div[2]/p/text()").all().toString());
		}
		// 列表页
		else {
			// 文章url
			page.addTargetRequests(
					page.getHtml().xpath("/html/body/div[3]/div[1]/div[2]/ul/li/div[1]/h4/a/@href").all());
			// 翻页url
//			https://www.cnbeta.com/home/more?&type=all&page=3&_csrf=CBlEkJcYFAlkttssQWHz4xY3zC6mGg4vjjPZRJgc8xBRIAD39k1AXhfMtkszUsCBQ3GgR8pQNhm_apEn0STLRA%3D%3D&_=1561742466563
			page.addTargetRequests(
					page.getHtml().xpath("/html/body/div[3]/div[1]/div[3]/a[@class='page-btn-prev']/@href").all());
		}
	}

	public static void main(String[] args) {
		Spider.create(new CnBetaSpider()).addUrl("https://www.cnbeta.com").addPipeline(new MysqlPipeline()).thread(3)
				.run();
	}
}

// 自定义实现Pipeline接口
class MysqlPipeline implements Pipeline {

	public MysqlPipeline() {
	}

	public void process(ResultItems resultitems, Task task) {
		Map<String, Object> mapResults = resultitems.getAll();
		Iterator<Entry<String, Object>> iter = mapResults.entrySet().iterator();
		Map.Entry<String, Object> entry;
		// 输出到控制台
		while (iter.hasNext()) {
			entry = iter.next();
			System.err.println(entry.getKey() + "：" + entry.getValue());
		}
		// 持久化
		News news = new News();
		if (!mapResults.get("Title").equals("")) {
			news.setTitle((String) mapResults.get("Title"));
			news.setImage((String) mapResults.get("Image"));
			news.setContent((String) mapResults.get("Content"));
		}
//        System.err.println(news.getTitle());
//        System.err.println(news.getContent());
//        try {
//            InputStream is = Resources.getResourceAsStream("conf.xml");
//            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
//            SqlSession session = sessionFactory.openSession();
//            session.insert("add", news);
//            session.commit();
//            session.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
	}
}