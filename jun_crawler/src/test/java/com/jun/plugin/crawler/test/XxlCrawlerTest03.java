package com.jun.plugin.crawler.test;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.jun.plugin.crawler.XxlCrawler;
import com.jun.plugin.crawler.annotation.PageFieldSelect;
import com.jun.plugin.crawler.annotation.PageSelect;
import com.jun.plugin.crawler.conf.XxlCrawlerConf;
import com.jun.plugin.crawler.parser.PageParser;
import com.jun.plugin.crawler.util.FileUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 爬虫示例03：爬取页面，下载图片文件
 *
 * @author xuxueli 2017-10-09 19:48:48
 */
public class XxlCrawlerTest03 {

    @PageSelect(cssQuery = "body")
    public static class PageVo {

        @PageFieldSelect(cssQuery = "img", selectType = XxlCrawlerConf.SelectType.ATTR, selectVal = "abs:src")
        private List<String> images;

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        @Override
        public String toString() {
            return "PageVo{" +
                    "images=" + images +
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

                        // 文件信息
                        String filePath = "/Users/xuxueli/Downloads/tmp";

                        if (pageVo.getImages()!=null && pageVo.getImages().size() > 0) {
                            Set<String> imagesSet = new HashSet<>(pageVo.getImages());
                            for (String img: imagesSet) {

                                // 下载图片文件
                                String fileName = FileUtil.getFileNameByUrl(img, null);
                                boolean ret = FileUtil.downFile(img, XxlCrawlerConf.TIMEOUT_MILLIS_DEFAULT, filePath, fileName);
                                System.out.println("down images " + (ret?"success":"fail") + "：" + img);
                            }
                        }
                    }
                })
                .build();

        System.out.println("start");
        crawler.start(true);
        System.out.println("end");
    }

}
