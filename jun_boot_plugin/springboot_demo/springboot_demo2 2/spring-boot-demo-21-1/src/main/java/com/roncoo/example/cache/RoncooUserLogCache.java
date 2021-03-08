package com.roncoo.example.cache;

import com.roncoo.example.bean.RoncooUserLog;

/**
 * @author Wujun
 */
public interface RoncooUserLogCache {

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 */
	RoncooUserLog selectById(Integer id);

	/**
	 * 更新
	 * 
	 * @param roncooUserLog
	 * @return
	 */
	RoncooUserLog updateById(RoncooUserLog roncooUserLog);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	String deleteById(Integer id);
}
