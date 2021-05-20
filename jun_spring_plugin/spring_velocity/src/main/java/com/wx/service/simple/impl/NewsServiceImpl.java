package com.wx.service.simple.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wx.common.base.model.Page;
import com.wx.common.utils.UUIDUtil;
import com.wx.entity.simple.News;
import com.wx.mapper.simple.NewsMapper;
import com.wx.service.simple.NewsService;

/**
 * @author Wujun
 *
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public void addNews(News news) {
        if (news != null) {
            news.setId(UUIDUtil.getRandom32PK());
            news.setCreateTime(Calendar.getInstance().getTime());
            newsMapper.insert(news);
        }
    }

    @Override
    public List<News> findNewsByKeywords(String keywords) {
        return newsMapper.findNewsByKeywords(keywords);
    }

    @Override
    public Page<News> findNewsByPage(Page<News> page, String keywords) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("keywords", keywords);
        page.setParamMap(param);
        List<News> news = newsMapper.findNewsByPage(page);
        page.setResultList(news);
        return page;
    }

}
