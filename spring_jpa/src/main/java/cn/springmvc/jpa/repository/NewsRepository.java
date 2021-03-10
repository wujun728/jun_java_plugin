package cn.springmvc.jpa.repository;

import java.util.List;

import cn.springmvc.jpa.entity.News;

/**
 * @author Wujun
 *
 */
public interface NewsRepository {

    public News save(News news);

    public List<News> findNews();

    public List<News> findNewsByKeywords(String keywords);
}
