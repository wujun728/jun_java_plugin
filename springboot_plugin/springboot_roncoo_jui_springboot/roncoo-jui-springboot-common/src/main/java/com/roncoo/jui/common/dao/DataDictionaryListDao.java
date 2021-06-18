/*
 * Copyright 2015-2016 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roncoo.jui.common.dao;

import com.roncoo.jui.common.entity.RcDataDictionaryList;
import com.roncoo.jui.common.util.jui.Page;

/**
 * 
 * @author Wujun
 */
public interface DataDictionaryListDao {
	/**
	 * @param currentPage
	 * @param numPerPage
	 * @param fieldCode
	 * @param rcDataDictionaryList
	 * @return
	 */
	Page<RcDataDictionaryList> listForPage(int currentPage, int numPerPage, String fieldCode, RcDataDictionaryList rcDataDictionaryList);

	/**
	 * 添加
	 * 
	 * @param rcDataDictionaryList
	 * @return
	 */
	int insert(RcDataDictionaryList rcDataDictionaryList);

	/**
	 * 
	 * 功能：根据id删除
	 * 
	 * 
	 * @param id
	 * @return int
	 */
	int deleteById(Long id);

	/**
	 * 根据FieldCode删除
	 * 
	 * @param fieldCode
	 * @return
	 */
	int deleteByFieldCode(String fieldCode);

	/**
	 * 
	 * 功能：根据id查询
	 * 
	 * @param id
	 * @return RcDataDictionaryList
	 */
	RcDataDictionaryList selectById(Long id);

	/**
	 * 根据id更新
	 * 
	 * @param rcDataDictionaryList
	 * @return
	 */
	int updateById(RcDataDictionaryList rcDataDictionaryList);

}
