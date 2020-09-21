package com.osmp.web.system.properties.service;

import java.util.List;
import java.util.Map;

import com.osmp.web.system.properties.entity.Properties;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年11月21日 下午3:21:34
 */
public interface PropertiesService {

	/**
	 * @param pro
	 * @return
	 */
	public Properties getProperties(Properties pro);

	/**
	 * @param dict
	 * @return
	 */
	public List<Properties> getProByPage(Pagination<Properties> dict);

	/**
	 * @param pro
	 */
	public void insert(Properties pro);

	/**
	 * @param pro
	 */
	public void update(Properties pro);

	/**
	 * @param pro
	 */
	public void deletePro(Properties pro);

	/**
	 * 初始化内存
	 * 
	 * @return
	 */
	public Map<String, String> getProMap();

}
