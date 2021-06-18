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
package com.roncoo.jui.common.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.roncoo.jui.common.dao.DataDictionaryListDao;
import com.roncoo.jui.common.entity.RcDataDictionaryList;
import com.roncoo.jui.common.entity.RcDataDictionaryListExample;
import com.roncoo.jui.common.entity.RcDataDictionaryListExample.Criteria;
import com.roncoo.jui.common.mapper.RcDataDictionaryListMapper;
import com.roncoo.jui.common.util.SqlUtil;
import com.roncoo.jui.common.util.jui.Page;

/**
 * 
 * @author Wujun
 */
@Repository
public class DataDictionaryListDaoImpl implements DataDictionaryListDao {

	@Autowired
	private RcDataDictionaryListMapper mapper;

	@Override
	public Page<RcDataDictionaryList> listForPage(int currentPage, int numPerPage, String fieldCode, RcDataDictionaryList rcDataDictionaryList) {
		RcDataDictionaryListExample example = new RcDataDictionaryListExample();
		Criteria c = example.createCriteria();
		c.andFieldCodeEqualTo(fieldCode);

		// 字段查询
		if (StringUtils.hasText(rcDataDictionaryList.getFieldKey())) {
			c.andFieldKeyEqualTo(rcDataDictionaryList.getFieldKey());
		}

		// 字段排序
		example.setOrderByClause("sort asc, update_time desc");

		int totalCount = mapper.countByExample(example);
		numPerPage = SqlUtil.checkPageSize(numPerPage);
		currentPage = SqlUtil.checkPageCurrent(totalCount, numPerPage, currentPage);
		example.setLimitStart(SqlUtil.countOffset(currentPage, numPerPage));
		example.setPageSize(numPerPage);
		return new Page<RcDataDictionaryList>(totalCount, SqlUtil.countTotalPage(totalCount, numPerPage), currentPage, numPerPage, mapper.selectByExample(example));
	}

	@Override
	public int insert(RcDataDictionaryList rcDataDictionaryList) {
		Date date = new Date();
		rcDataDictionaryList.setCreateTime(date);
		rcDataDictionaryList.setUpdateTime(date);
		return mapper.insertSelective(rcDataDictionaryList);
	}

	@Override
	public int deleteById(Long id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteByFieldCode(String fieldCode) {
		RcDataDictionaryListExample example = new RcDataDictionaryListExample();
		Criteria criteria = example.createCriteria();
		criteria.andFieldCodeEqualTo(fieldCode);
		return mapper.deleteByExample(example);
	}

	@Override
	public RcDataDictionaryList selectById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateById(RcDataDictionaryList rcDataDictionaryList) {
		rcDataDictionaryList.setUpdateTime(new Date());
		return mapper.updateByPrimaryKeySelective(rcDataDictionaryList);
	}

}
