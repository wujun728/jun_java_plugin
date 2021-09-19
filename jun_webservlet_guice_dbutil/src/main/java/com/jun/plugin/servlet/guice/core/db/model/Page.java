package com.jun.plugin.servlet.guice.core.db.model;

import java.util.List;
import java.io.Serializable;  
 
/**
 * 对分页的基本数据进行一个简单的封装
 */
public class Page<T> implements Serializable{
    /**
     * 当前页返回数据列表
     */
    private List<T> results;

    private Long totalCount;

    public static <T> Page<T> build(List<T> results, Long totalCount) {
        return new Page<T>().setResults(results).setTotalCount(totalCount);
    }

    public List<T> getResults() {
        return results;
    }

    public Page<T> setResults(List<T> results) {
        this.results = results;
        return this;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public Page<T> setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }
 
}