package com.chentongwei.spider.analyzer;

import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 解析HTML文档方法
 */
public interface DocumentAnalyzer {

    /**
     * 根据html文档对象获取List<Map>
     *
     * @param document html文档对象
     * @return 结果
     */
    List<Map<String, Object>> forListMap(Document document);
}
