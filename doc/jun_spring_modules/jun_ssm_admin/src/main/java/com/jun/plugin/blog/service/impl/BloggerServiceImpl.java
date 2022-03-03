package com.jun.plugin.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jun.plugin.blog.dao.BloggerDao;
import com.jun.plugin.blog.entity.Blogger;
import com.jun.plugin.blog.service.BloggerService;

/**
 * ����Serviceʵ����
 * @author Wujun
 *
 */
@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService{

	@Resource
	private BloggerDao bloggerDao;

	public Blogger find() {
		return bloggerDao.find();
	}

	public Blogger getByUserName(String userName) {
		return bloggerDao.getByUserName(userName);
	}

	public Integer update(Blogger blogger) {
		return bloggerDao.update(blogger);
	}
	
	
}
