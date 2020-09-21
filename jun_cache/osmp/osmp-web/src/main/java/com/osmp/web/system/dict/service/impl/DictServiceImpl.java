package com.osmp.web.system.dict.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.core.SystemFrameWork;
import com.osmp.web.system.dict.dao.DictMapper;
import com.osmp.web.system.dict.entity.Dict;
import com.osmp.web.system.dict.entity.DictItem;
import com.osmp.web.system.dict.service.DictItemService;
import com.osmp.web.system.dict.service.DictService;
import com.osmp.web.utils.Pagination;

/**
 * Description:字典管理服务类
 * 
 * @author: wangkaiping
 * @date: 2014年11月14日 下午2:47:48
 */
@Service
public class DictServiceImpl implements DictService {

	@Autowired
	private DictMapper dictMapper;

	@Autowired
	private DictItemService dictItemService;

	@Override
	public List<Dict> getDictByPage(Pagination<Dict> p) {
		return dictMapper.selectByPage(p, Dict.class, "");
	}

	@Override
	public Dict getDict(Dict dict) {
		List<Dict> list = dictMapper.getObject(dict);
		if (null != list) {
			return list.get(0);
		}
		return new Dict();
	}

	@Override
	public void insert(Dict dict) {
		dictMapper.insert(dict);
	}

	@Override
	public void update(Dict dict) {
		if (dict.getType() == 1) {
			dict.setKeyFilde("");
			dict.setValueFilde("");
			dict.setTabName("");
			dict.setPropertiesFile("");
		} else if (dict.getType() == 2) {
			dict.setPropertiesFile("");
		} else if (dict.getType() == 3) {
			dict.setKeyFilde("");
			dict.setValueFilde("");
			dict.setTabName("");
		}
		dictMapper.update(dict);
	}

	@Override
	public void deleteDict(Dict dict) {
		dictMapper.delete(dict);
	}

	@Override
	public Map<String, List<DictItem>> getDictMap() {
		Map<String, List<DictItem>> map = new ConcurrentHashMap<String, List<DictItem>>();
		List<Dict> dictList = dictMapper.selectAll(Dict.class, "");
		for (Dict i : dictList) {
			if (Dict.DTYPE_DICTTABLE == i.getType()) {// 数据字典项表
				List<DictItem> list = null;
				try {
					list = this.dictItemService.getAll("parentCode='" + i.getCode() + "'");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (null != list && list.size() > 0) {
					map.put(i.getCode(), list);
				}
			} else if (Dict.DTYPE_OTHERTABLE == i.getType()) {// 其它第三方表
				List<DictItem> list = null;
				try {
					list = this.dictItemService.getOtherTable(i.getTabName(), i.getKeyFilde(), i.getValueFilde(),
							i.getCode());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (null != list && list.size() > 0) {
					map.put(i.getCode(), list);
				}
			} else if (Dict.DTYPE_PROPERTIES == i.getType()) {// 来源于 properties
				List<DictItem> list = null;
				try {
					list = this.dictItemService.getProperties(i.getPropertiesFile(), i.getCode());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (null != list && list.size() > 0) {
					map.put(i.getCode(), list);
				}
			}
		}
		return map;
	}

	@Override
	public void refresh() {
		SystemFrameWork.refreshDict();
	}
}
