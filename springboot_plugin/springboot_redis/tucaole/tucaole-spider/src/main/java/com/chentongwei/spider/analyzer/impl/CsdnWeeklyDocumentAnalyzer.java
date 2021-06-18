package com.chentongwei.spider.analyzer.impl;

import com.chentongwei.spider.analyzer.DocumentAnalyzer;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 解析CSDN每周知识干货html文档具体实现
 */
@Component
public class CsdnWeeklyDocumentAnalyzer implements DocumentAnalyzer {

    @Override
    public List<Map<String, Object>> forListMap(Document document) {
        List<Map<String, Object>> results = new ArrayList<>();
        if (ObjectUtils.isEmpty(document)) {
            return results;
        }
        document.body().getElementsByClass("pclist").get(0).children().forEach(ele -> {
            Map<String, Object> result = new HashMap<>();
            result.put("type", ele.getElementsByTag("span").get(0).getElementsByTag("a").get(0).attr("href"));
            result.put("img", ele.getElementsByTag("span").get(0).getElementsByTag("a").get(0).getElementsByTag("img").get(0).attr("src"));
            result.put("url", ele.getElementsByTag("span").get(1).getElementsByTag("a").get(0).attr("href"));
            result.put("name", ele.getElementsByTag("span").get(1).getElementsByTag("a").get(0).text());
            result.put("views", Integer.valueOf(ele.getElementsByTag("span").get(1).getElementsByTag("span").get(0).getElementsByTag("em").get(0).text().replaceAll("\\D+", "")));
            result.put("collections", Integer.valueOf(ele.getElementsByTag("span").get(1).getElementsByTag("span").get(1).getElementsByTag("em").get(0).text().replaceAll("\\D+", "")));
            results.add(result);
        });
        return results;
    }



    /*@ResponseBody
    @GetMapping("/get/stage/{stage}")
    public List<Article> forWeekly(@PathVariable Integer stage) throws Exception {
        List<Article> lists = ArticleSpider.forEntityList("http://lib.csdn.net/weekly/" + stage, csdnWeeklyDocumentAnalyzer, Article.class);
        return lists;
    }*/
}
