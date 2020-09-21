package com.osmp.web.system.properties.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.core.SystemFrameWork;
import com.osmp.web.system.properties.dao.PropertiesMapper;
import com.osmp.web.system.properties.entity.Properties;
import com.osmp.web.system.properties.service.PropertiesService;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年11月21日 下午3:21:54
 */
@Service
public class PropertiesServiceImpl implements PropertiesService {

	@Autowired
	private PropertiesMapper prMapper;

	@Override
	public Properties getProperties(Properties pro) {
		List<Properties> list = prMapper.getObject(pro);
		if (null != list && list.size() == 1) {
			return list.get(0);
		}
		return new Properties();
	}

	@Override
	public List<Properties> getProByPage(Pagination<Properties> dict) {
		return prMapper.selectByPage(dict, Properties.class, "");
	}

	@Override
	public void insert(Properties pro) {
		prMapper.insert(pro);
		SystemFrameWork.refreshPro();// 刷新内存
	}

	@Override
	public void update(Properties pro) {
		prMapper.update(pro);
		SystemFrameWork.refreshPro();// 刷新内存
	}

	@Override
	public void deletePro(Properties pro) {
		prMapper.delete(pro);
		SystemFrameWork.refreshPro();// 刷新内存
	}

	@Override
	public Map<String, String> getProMap() {
		List<Properties> list = prMapper.selectAll(Properties.class, "");
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		for (Properties p : list) {
			map.put(p.getProkey(), p.getProvalue());
		}
		return map;
	}

}
