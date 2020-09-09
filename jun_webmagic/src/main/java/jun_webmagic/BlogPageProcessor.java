package jun_webmagic;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
 
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 * Created by Administrator on 2017/6/11.
 */
public class BlogPageProcessor implements PageProcessor {
    //抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(10).setSleepTime(1000);
    //博文数量
    private static int num = 0;
    //数据库持久化对象，用于将博文信息存入数据库
//    private BlogDao blogDao = new BlogDaoImpl();
 
    public static void main(String[] args) throws Exception {
        long startTime, endTime;
        System.out.println("========天善最热博客小爬虫【启动】喽！=========");
        startTime = new Date().getTime();
        Spider.create(new BlogPageProcessor()).addUrl("https://blog.hellobi.com/hot/weekly?page=1").thread(5).run();
        endTime = new Date().getTime();
        System.out.println("========天善最热博客小爬虫【结束】喽！=========");
        System.out.println("一共爬到" + num + "篇博客！用时为：" + (endTime - startTime) / 1000 + "s");
    }
 
    @Override
    public void process(Page page) {
        //1. 如果是博文列表页面 【入口页面】，将所有博文的详细页面的url放入target集合中。
        // 并且添加下一页的url放入target集合中。
        if (page.getUrl().regex("https://blog\\.hellobi\\.com/hot/weekly\\?page=\\d+").match()) {
            //目标链接
            page.addTargetRequests(page.getHtml().xpath("//h2[@class='title']/a").links().all());
            //下一页博文列表页链接
            page.addTargetRequests(page.getHtml().xpath("//a[@rel='next']").links().all());
        }
        //2. 如果是博文详细页面
        else {
//            String content1 = page.getHtml().get();
            try {
                /*实例化BlogInfo，方便持久化存储。*/
//                BlogInfo blog = new BlogInfo();
                //博文标题
                String title = page.getHtml().xpath("//h1[@class='clearfix']/a/text()").get();
                //博文url
                String url = page.getHtml().xpath("//h1[@class='clearfix']/a/@href").get();
                //博文作者
                String author = page.getHtml().xpath("//section[@class='sidebar']/div/div/a[@class='aw-user-name']/text()").get();
                //作者博客地址
                String blogHomeUrl = page.getHtml().xpath("//section[@class='sidebar']/div/div/a[@class='aw-user-name']/@href").get();
                //博文内容，这里只获取带html标签的内容，后续可再进一步处理
                String content = page.getHtml().xpath("//div[@class='message-content editor-style']").get();
                //推荐数(点赞数)
                String recommendNum = page.getHtml().xpath("//a[@class='agree']/b/text()").get();
                //评论数
                String commentNum = page.getHtml().xpath("//div[@class='aw-mod']/div/h2/text()").get().split("个")[0].trim();
                //阅读数（浏览数）
                String readNum = page.getHtml().xpath("//div[@class='row']/div/div/div/div/span/text()").get().split(":")[1].trim();
                //发布时间，发布时间需要处理，这一步获取原始信息
                String time = page.getHtml().xpath("//time[@class='time']/text()").get().split(":")[1].trim();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();// 取当前日期。
                cal = Calendar.getInstance();
                String publishTime = null;
                Pattern p = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
                Matcher m = p.matcher(time);
                //如果time是“YYYY-mm-dd”这种格式的，则不需要处理
                if (m.matches()) {
                    publishTime = time;
                } else if (time.contains("天")) { //如果time包含“天”，如1天前，
                    int days = Integer.parseInt(time.split("天")[0].trim());//则获取对应的天数
                    cal.add(Calendar.DAY_OF_MONTH, -days);// 取当前日期的前days天.
                    publishTime = df.format(cal.getTime());  //并将时间转换为“YYYY-mm-dd”这个格式
                } else {//time是其他格式，如几分钟前，几小时前，都为当日日期
                    publishTime = df.format(cal.getTime());
                }
                //对象赋值
//                blog.setUrl(url);
//                blog.setTitle(title);
//                blog.setAuthor(author);
//                blog.setBlogHomeUrl(blogHomeUrl);
//                blog.setCommentNum(commentNum);
//                blog.setRecommendNum(recommendNum);
//                blog.setReadNum(readNum);
//                blog.setContent(content);
//                blog.setPublishTime(publishTime);
                num++;//博文数++
 
//                System.out.println("num:" + num + " " + blog.toString());//输出对象
//                blogDao.saveBlog(blog);//保存博文信息到数据库
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

