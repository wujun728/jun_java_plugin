package com.jun.plugin.jpa.service;

import java.util.List;

import com.jun.plugin.jpa.entity.News;
import com.jun.plugin.jpa.entity.User;

/**
 * @author Vincent.wang
 *
 */
public interface NewsService {

    public void addNews(News news, User user);

    public List<News> findNews();

    public List<News> findNewsByKeywords(String keywords);

}