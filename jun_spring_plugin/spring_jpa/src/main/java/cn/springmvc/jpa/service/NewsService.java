package cn.springmvc.jpa.service;

import java.util.List;

import cn.springmvc.jpa.entity.News;
import cn.springmvc.jpa.entity.User;

/**
 * @author Vincent.wang
 *
 */
public interface NewsService {

    public void addNews(News news, User user);

    public List<News> findNews();

    public List<News> findNewsByKeywords(String keywords);

}