package com.jun.plugin.jpa.repository;

import java.util.List;

import com.jun.plugin.jpa.entity.News;

/**
 * @author Wujun
 *
 */
public interface NewsRepository {

    public News save(News news);

    public List<News> findNews();

    public List<News> findNewsByKeywords(String keywords);
}
