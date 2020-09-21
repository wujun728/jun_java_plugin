package com.osmp.web.system.dict.dao;

import java.util.List;

import com.osmp.web.core.mybatis.BaseMapper;
import com.osmp.web.system.dict.entity.DictItem;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年11月24日 下午4:06:36
 */
public interface DictItemMapper extends BaseMapper<DictItem> {

	/**
	 * 通用查询其它表
	 * 
	 * @param sql
	 * @return
	 */
	public List<?> selectList(String sql);

}
