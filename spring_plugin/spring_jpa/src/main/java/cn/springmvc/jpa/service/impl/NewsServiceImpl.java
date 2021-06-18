package cn.springmvc.jpa.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.jpa.entity.News;
import cn.springmvc.jpa.entity.User;
import cn.springmvc.jpa.repository.NewsRepository;
import cn.springmvc.jpa.service.NewsService;

/**
 * @author Wujun
 *
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public void addNews(News news, User user) {
        if (news != null) {
            news.setUserId(user.getId());
            news.setCreateTime(Calendar.getInstance().getTime());
            newsRepository.save(news);
        }
    }

    @Override
    public List<News> findNews() {
        return newsRepository.findNews();
    }

    @Override
    public List<News> findNewsByKeywords(String keywords) {
        return newsRepository.findNewsByKeywords(keywords);
    }

}
