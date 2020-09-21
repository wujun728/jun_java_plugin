package com.osmp.web.system.dict.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.system.dict.dao.DictItemMapper;
import com.osmp.web.system.dict.entity.DictItem;
import com.osmp.web.system.dict.service.DictItemService;
import com.osmp.web.utils.PropertiesUtils;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年11月24日 下午4:07:35
 */
@Service
public class DictItemServiceImpl implements DictItemService {

	@Autowired
	private DictItemMapper dictItemMapper;

	@Override
	public List<DictItem> getAll(String where) {
		return this.dictItemMapper.selectAll(DictItem.class, where);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictItem> getOtherTable(String tableName, String name, String key, String dictCode) {
		String sql = "select " + name + ", " + key + " from " + tableName;
		List<Map<String, Object>> tableMap = (List<Map<String, Object>>) dictItemMapper.selectList(sql);
		List<DictItem> list = new ArrayList<DictItem>();
		if (null != tableMap && tableMap.size() > 0) {
			for (Map<String, Object> map : tableMap) {
				DictItem item = new DictItem();
				item.setName(String.valueOf(map.get(name)));
				item.setCode(String.valueOf(map.get(key)));
				item.setParentCode(dictCode);
				list.add(item);
			}
		}
		return list;
	}

	@Override
	public List<DictItem> getProperties(String filePath, String dictCode) {
		Map<String, String> map = PropertiesUtils.getMap(filePath);
		List<DictItem> list = new ArrayList<DictItem>();
		for (Map.Entry<String, String> en : map.entrySet()) {
			DictItem dictItem = new DictItem();
			dictItem.setParentCode(dictCode);
			dictItem.setName(en.getKey());
			dictItem.setCode(en.getValue());
			list.add(dictItem);
		}
		return list;
	}

	@Override
	public void insert(DictItem dictItem) {
		this.dictItemMapper.insert(dictItem);
	}

	@Override
	public void update(DictItem dictItem) {
		this.dictItemMapper.update(dictItem);
	}

	@Override
	public void deleteDictIteme(DictItem dict) {
		this.dictItemMapper.delete(dict);
	}

}
