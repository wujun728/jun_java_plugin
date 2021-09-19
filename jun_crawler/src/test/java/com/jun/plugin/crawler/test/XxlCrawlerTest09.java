package com.jun.plugin.crawler.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.crawler.XxlCrawler;
import com.jun.plugin.crawler.parser.strategy.NonPageParser;

/**
 * 爬虫示例09：采集非Web页面，如JSON接口等，直接输出响应数据
 *
 * @author xuxueli 2018-10-17
 */
public class XxlCrawlerTest09 {
    private static Logger logger = LoggerFactory.getLogger(XxlCrawlerTest05.class);

    public static void main(String[] args) {

        // 构造爬虫
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("http://news.baidu.com/widget?id=LocalNews&ajax=json")
                .setPageParser(new NonPageParser() {
                    @Override
                    public void parse(String url, String pageSource) {
                        System.out.println(url + ": " + pageSource);
                    }
                })
                .build();

        // 启动
        crawler.start(true);

    }

}
