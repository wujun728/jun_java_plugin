package cn.springmvc.mybatis.service.simple;

import java.util.List;

import cn.springmvc.mybatis.common.base.model.Page;
import cn.springmvc.mybatis.entity.auth.User;
import cn.springmvc.mybatis.entity.simple.News;

/**
 * @author Wujun
 *
 */
public interface NewsService {

    public void addNews(News news, User user);

    public List<News> findNewsByKeywords(String keywords);

    public Page<News> findNewsByPage(Page<News> page,String keywords);

}