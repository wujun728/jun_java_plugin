package com.osmp.web.system.dict.service;

import java.util.List;

import com.osmp.web.system.dict.entity.DictItem;

/**
 * Description:字典表项服务接口
 * 
 * @author: wangkaiping
 * @date: 2014年11月24日 下午4:06:21
 */
public interface DictItemService {

	public List<DictItem> getAll(String where);

	public List<DictItem> getOtherTable(String tableName, String name, String key, String dictCode);

	public List<DictItem> getProperties(String filePath, String dictCode);

	/**
	 * @param dictItem
	 */
	public void insert(DictItem dictItem);

	/**
	 * @param dictItem
	 */
	public void update(DictItem dictItem);

	/**
	 * @param dict
	 */
	public void deleteDictIteme(DictItem dict);

}
