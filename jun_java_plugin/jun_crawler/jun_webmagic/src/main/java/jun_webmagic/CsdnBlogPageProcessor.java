package jun_webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rush on 2017/3/27.
 */

public class CsdnBlogPageProcessor implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
//            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
    private static String username; // 设置的Csdn用户名
    private static int size = 0; // 共抓取到的文章数量

    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        // 如果匹配成功，说明是文章页
        if(!page.getUrl().regex("http://blog\\.csdn\\.net/" + username + "/article/details/\\d+").match()){
            // 添加所有文章页
            page.addTargetRequests(page.getHtml().xpath("//div[@id='article_list']").links()
                    .regex("/" + username + "/article/details/\\d+")
                    .replace("/" + username + "/", "http://blog.csdn.net/" + username + "/")
                    .all());
            // 添加其他列表页
            page.addTargetRequests(page.getHtml().xpath("//div[@id='papelist']").links() // 限定其他列表页获取区域
                    .regex("/" + username + "/article/list/\\d+")
                    .replace("/" + username + "/", "http://blog.csdn.net/" + username + "/")// 巧用替换给把相对url转换成绝对url
                    .all());
        } else {
            ++size;

            page.putField("numbers", page.getUrl().regex("\\d+$").get());
            page.putField("authors", username);
            page.putField("titles", page.getHtml()
                            .xpath("//div[@class='article_title']//h1//span[@class='link_title']/a/text()").get());
            page.putField("dates", page.getHtml()
                            .xpath("//div[@class='article_r']//span[@class='link_postdate']/text()").get());
            page.putField("tags", listToString(page.getHtml()
                    .xpath("//div[@class='article_l']//span[@class='link_categories']/a/text()").all()));
            page.putField("categorys", listToString(page.getHtml()
                            .xpath("//div[@class='category_r']//label//span//text()").all()));
            page.putField("views", page.getHtml()
                            .xpath("//div[@class='article_r']//span[@class='link_view']").regex("\\d+").get());
            page.putField("comments", page.getHtml()
                            .xpath("//div[@class='article_r']//span[@class='link_comments']//text()").regex("\\d+").get());
            page.putField("copyright", page.getHtml().regex("bog_copyright").match() ? 1 : 0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            page.putField("dateCreated", sdf.format(new Date()));

            /* 如果想要存储到数据库中，打开此代码段，并将上面的代码段注释掉。
            CsdnBlog csdnBlog = new CsdnBlog(
                    // 文章随机编号
                    Integer.valueOf(page.getUrl()
                            .regex("\\d+$").get()),
                    // 文章作者
                    username,
                    // 文章标题
                    page.getHtml()
                            .xpath("//div[@class='article_title']//h1//span[@class='link_title']/a/text()").get(),
                    // 文章日期
                    page.getHtml()
                            .xpath("//div[@class='article_r']//span[@class='link_postdate']/text()").get(),
                    // 文章标签
                    listToString(page.getHtml()
                            .xpath("//div[@class='article_l']//span[@class='link_categories']/a/text()").all()),
                    // 文章类别
                    listToString(page.getHtml()
                            .xpath("//div[@class='category_r']//label//span//text()").all()),
                    // 阅读人数
                    Integer.valueOf(page.getHtml()
                            .xpath("//div[@class='article_r']//span[@class='link_view']").regex("\\d+").get()),
                    // 评论人数
                    Integer.valueOf(page.getHtml()
                            .xpath("//div[@class='article_r']//span[@class='link_comments']//text()").regex("\\d+").get()),
                    // 是否原创
                    page.getHtml().regex("bog_copyright").match() ? 1 : 0
            );
            new CsdnBlogDao().add(csdnBlog);
            System.out.println(csdnBlog);
            */
        }
    }

    public Site getSite() {
        return site;
    }

    private static String listToString(List<String> stringList){
        if(stringList == null){
            return null;
        }
        StringBuffer result = new StringBuffer();
        boolean flag = false;
        for(String s : stringList){
            if(flag){
                result.append(',');
            } else {
                flag = true;
            }
            result.append(s);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        long startTime, endTime;
        System.out.print("请输入要爬取的CSDN博主用户名：");
//        Scanner scanner = new Scanner(System.in);
        username = "zhetmdoubeizhanyong";
//        username = scanner.next();
        System.out.println("-----------启动爬虫程序-----------");
        startTime = System.currentTimeMillis();
        Spider.create(new CsdnBlogPageProcessor())
            .addUrl("http://blog.csdn.net/" + username)
            .addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
            //开启5个线程抓取
            .thread(5)
            //启动爬虫
            .run();
        endTime = System.currentTimeMillis();
        System.out.println("结束爬虫程序，共抓取 " + size + " 篇文章，耗时约 " + ((endTime - startTime) / 1000) + " 秒！");
    }
}