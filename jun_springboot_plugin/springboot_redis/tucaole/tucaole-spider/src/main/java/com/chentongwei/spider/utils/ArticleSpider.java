package com.chentongwei.spider.utils;

import com.chentongwei.spider.analyzer.DocumentAnalyzer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 文章抓取工具类
 */
public class ArticleSpider {
    private static final Logger LOG = LogManager.getLogger(ArticleSpider.class);

    public static <T> List<T> forEntityList(String url, DocumentAnalyzer docAnalyzer, Class<T> type) throws Exception {
        LOG.info("开始抓取文章：" + url);

        List<T> results = new ArrayList<>();
        docAnalyzer.forListMap(Jsoup.connect(url).timeout(50000).get()).forEach(map->{
            try {
                results.add(CollectionUtil.mapToBean(map, type));
            } catch (Exception e) {
                LOG.error("抓取文章失败：" + url);
            }
        });
        return results;
    }

}