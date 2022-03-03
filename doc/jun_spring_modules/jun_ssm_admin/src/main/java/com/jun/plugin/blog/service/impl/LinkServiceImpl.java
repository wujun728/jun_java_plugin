package com.jun.plugin.blog.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jun.plugin.blog.dao.LinkDao;
import com.jun.plugin.blog.entity.Link;
import com.jun.plugin.blog.service.LinkService;

/**
 * ��������Serviceʵ����
 * @author Wujun
 *
 */
@Service("linkService")
public class LinkServiceImpl implements LinkService{

	@Resource
	private LinkDao linkDao;
	
	public int add(Link link) {
		return linkDao.add(link);
	}

	public int update(Link link) {
		return linkDao.update(link);
	}

	public List<Link> list(Map<String, Object> map) {
		return linkDao.list(map);
	}

	public Long getTotal(Map<String, Object> map) {
		return linkDao.getTotal(map);
	}

	public Integer delete(Integer id) {
		return linkDao.delete(id);
	}

}
