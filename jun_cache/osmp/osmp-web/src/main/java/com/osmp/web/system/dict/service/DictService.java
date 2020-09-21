package com.osmp.web.system.dict.service;

import java.util.List;
import java.util.Map;

import com.osmp.web.system.dict.entity.Dict;
import com.osmp.web.system.dict.entity.DictItem;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年11月14日 下午2:25:23
 */
public interface DictService {

	public List<Dict> getDictByPage(Pagination<Dict> p);

	public Dict getDict(Dict dict);

	public void insert(Dict dict);

	public void update(Dict dict);

	public void deleteDict(Dict dict);

	/**
	 * 初始化数据字典到内存
	 * 
	 * @return
	 */
	public Map<String, List<DictItem>> getDictMap();

	/**
	 * 刷内存
	 */
	public void refresh();

}
