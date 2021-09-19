package com.jun.plugin.crawler.test;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.jun.plugin.crawler.XxlCrawler;
import com.jun.plugin.crawler.annotation.PageFieldSelect;
import com.jun.plugin.crawler.annotation.PageSelect;
import com.jun.plugin.crawler.parser.PageParser;

/**
 * 爬虫示例01：爬取页面数据并封装VO对象
 *
 * @author xuxueli 2017-10-09 19:48:48
 */
public class XxlCrawlerTest {

    @PageSelect(cssQuery = "#search-projects-ulist .project")
    public static class PageVo {

        @PageFieldSelect(cssQuery = ".repository")
        private String repository;

        @PageFieldSelect(cssQuery = ".description")
        private String description;

        public String getRepository() {
            return repository;
        }

        public void setRepository(String repository) {
            this.repository = repository;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "PageVo{" +
                    "repository='" + repository + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {

        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("https://gitee.com/xuxueli0323/projects?page=1")
                .setWhiteUrlRegexs("https://gitee\\.com/xuxueli0323/projects\\?page=\\d+")
                .setThreadCount(3)
                .setPageParser(new PageParser<PageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, PageVo pageVo) {
                        // 解析封装 PageVo 对象
                        String pageUrl = html.baseUri();
                        System.out.println(pageUrl + "：" + pageVo.toString());
                    }
                })
                .build();

        System.out.println("start");
        crawler.start(true);
        System.out.println("end");
    }

}
