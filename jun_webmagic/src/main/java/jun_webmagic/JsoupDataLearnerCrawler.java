package jun_webmagic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*******
 * created by DuFei at 2017.08.25 21:00 web crawler example
 ******/
public class JsoupDataLearnerCrawler {

	public static void main(String[] args) {
		List<String> titles = new ArrayList<String>();
		List<String> urls = new ArrayList<String>();
		String html = "";
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("img");
		for (Element element : elements) {
			;
//			String title = element.text();
			titles.add((element.select("img").attr("src")));
//			titles.add(title);
//			urls.add(element.select("a").attr("href"));
//			System.out.println(title+" ---> "+element.select("a").attr("href"));
		}
		for (String title : titles) {
			System.out.println(title);
		}
//		for (String url : urls) {
//			System.out.println(url);
//		}
	}
	
	public static void subject_tpc(String[] args) {
		List<String> titles = new ArrayList<String>();
		List<String> urls = new ArrayList<String>();
		String html = "<span id=\"subject_tpc\">淫荡秘书晓日</span>";
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("span");
		for (Element element : elements) {
			String title = element.text();
			titles.add(title);
//			urls.add(element.select("a").attr("href"));
//			System.out.println(title+" ---> "+element.select("a").attr("href"));
		}
		for (String title : titles) {
			System.out.println(title);
		}
//		for (String url : urls) {
//			System.out.println(url);
//		}
	}

	public void testJsoup() throws Exception {
		List<String> titles = new ArrayList<String>();
		List<String> urls = new ArrayList<String>();
		// 假设我们获取的HTML的字符内容如下
		String html = "<html><div id=\"blog_list\"><div class=\"blog_title\"><a href=\"url1\">第一篇博客</a></div><div class=\"blog_title\"><a href=\"url2\">第二篇博客</a></div><div class=\"blog_title\"><a href=\"url3\">第三篇博客</a></div></div></html>";
		// 第一步，将字符内容解析成一个Document类
		Document doc = Jsoup.parse(html);
		// 第二步，根据我们需要得到的标签，选择提取相应标签的内容
		Elements elements = doc.select("div[id=blog_list]").select("div[class=blog_title]");
		for (Element element : elements) {
			String title = element.text();
			titles.add(title);
			urls.add(element.select("a").attr("href"));
		}
		// 输出测试
		for (String title : titles) {
			System.out.println(title);
		}
		for (String url : urls) {
			System.out.println(url);
		}
	}

	private void test2() {

		String url = "http://www.datalearner.com/blog_list";
		String rawHTML = null;
		try {
			rawHTML = getHTMLContent(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
//将当前页面转换成Jsoup的Document对象
		Document doc = Jsoup.parse(rawHTML);
//获取所有的博客列表集合
		Elements blogList = doc.select("div[class=card]");
//针对每个博客内容进行解析，并输出
		for (Element element : blogList) {
			String title = element.select("h4[class=card-title]").text();
			String introduction = element.select("p[class=card-text]").text();
			String author = element.select("span[class=fa fa-user]").text();
			System.out.println("Title:\t" + title);
			System.out.println("introduction:\t" + introduction);
			System.out.println("Author:\t" + author);
			System.out.println("--------------------------");
		}
	}

//根据url地址获取对应页面的HTML内容，我们将上一节中的内容打包成了一个方法，方便调用
	private static String getHTMLContent(String url) throws IOException {
//建立一个新的请求客户端
		CloseableHttpClient httpClient = HttpClients.createDefault();
//使用HttpGet方式请求网址
		HttpGet httpGet = new HttpGet(url);
//获取网址的返回结果
		CloseableHttpResponse response = httpClient.execute(httpGet);
//获取返回结果中的实体
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
//关闭HttpEntity流
		EntityUtils.consume(entity);
		return content;
	}

}