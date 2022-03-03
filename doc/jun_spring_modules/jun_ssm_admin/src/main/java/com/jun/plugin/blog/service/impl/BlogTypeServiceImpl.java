package com.jun.plugin.blog.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jun.plugin.blog.dao.BlogTypeDao;
import com.jun.plugin.blog.entity.BlogType;
import com.jun.plugin.blog.service.BlogTypeService;

/**
 * ��������Serviceʵ����
 * @author Wujun
 *
 */
@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService{

	@Resource
	private BlogTypeDao blogTypeDao;
	
	public List<BlogType> countList() {
		return blogTypeDao.countList();
	}

	public List<BlogType> list(Map<String, Object> map) {
		return blogTypeDao.list(map);
	}

	public Long getTotal(Map<String, Object> map) {
		return blogTypeDao.getTotal(map);
	}

	public Integer add(BlogType blogType) {
		return blogTypeDao.add(blogType);
	}

	public Integer update(BlogType blogType) {
		return blogTypeDao.update(blogType);
	}

	public Integer delete(Integer id) {
		return blogTypeDao.delete(id);
	}

}
