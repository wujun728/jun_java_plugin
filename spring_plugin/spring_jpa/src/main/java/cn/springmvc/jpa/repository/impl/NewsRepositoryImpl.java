package cn.springmvc.jpa.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.springmvc.jpa.entity.News;
import cn.springmvc.jpa.repository.BaseRepository;
import cn.springmvc.jpa.repository.NewsRepository;

/**
 * @author Wujun
 *
 */
@Repository
public class NewsRepositoryImpl implements NewsRepository {

    @Autowired
    protected BaseRepository baseRepository;

    @Override
    public News save(News news) {
        baseRepository.save(news);
        return news;
    }

    @Override
    public List<News> findNews() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select id id , address address , create_time createTime , description description , news_time newsTime , title title, user_id userId ");
        sql.append(" from t_news");
        return baseRepository.executeNativeQueryForList(sql.toString(), News.class, new HashMap<String, Object>());
    }

    @Override
    public List<News> findNewsByKeywords(String keywords) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select id id , address address , create_time createTime , description description , news_time newsTime , title title, user_id userId ");
        sql.append(" from t_news where title like :title");
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("title", "%" + keywords + "%");
        return baseRepository.executeNativeQueryForList(sql.toString(), News.class, parameter);
    }

}
