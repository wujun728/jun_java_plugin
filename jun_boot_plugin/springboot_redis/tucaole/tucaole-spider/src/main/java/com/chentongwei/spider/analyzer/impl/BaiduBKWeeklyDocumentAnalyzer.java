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
 * @Description: 百度百科分析器
 */
@Component
public class BaiduBKWeeklyDocumentAnalyzer implements DocumentAnalyzer {

    @Override
    public List<Map<String, Object>> forListMap(Document document) {
        List<Map<String, Object>> results = new ArrayList<>();
        if (ObjectUtils.isEmpty(document)) {
            return results;
        }
        document.body().getElementsByClass("title-text").get(0).children().forEach(ele -> {
            Map<String, Object> result = new HashMap<>();
            result.put("collections",
                    Integer.valueOf(ele.getElementsByTag("span").get(1).getElementsByTag("span").get(1).getElementsByTag("em").get(0).text().replaceAll("\\D+", "")));
            results.add(result);
        });
        return results;
    }

}