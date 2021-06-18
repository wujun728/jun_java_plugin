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

import com.roncoo.jui.common.dao.DataDictionaryDao;
import com.roncoo.jui.common.entity.RcDataDictionary;
import com.roncoo.jui.common.entity.RcDataDictionaryExample;
import com.roncoo.jui.common.entity.RcDataDictionaryExample.Criteria;
import com.roncoo.jui.common.mapper.RcDataDictionaryMapper;
import com.roncoo.jui.common.util.SqlUtil;
import com.roncoo.jui.common.util.jui.Page;

/**
 * 
 * @author Wujun
 */
@Repository
public class DataDictionaryDaoImpl implements DataDictionaryDao {

	@Autowired
	private RcDataDictionaryMapper mapper;

	@Override
	public Page<RcDataDictionary> listForPage(int currentPage, int numPerPage, String orderField, String orderDirection, RcDataDictionary rcDataDictionary) {
		RcDataDictionaryExample example = new RcDataDictionaryExample();
		Criteria c = example.createCriteria();
		
		// 字段查询
		if (StringUtils.hasText(rcDataDictionary.getFieldName())) {
			c.andFieldNameLike(SqlUtil.like(rcDataDictionary.getFieldName()));
		}

		// 字段排序
		StringBuilder orderByClause = new StringBuilder();
		if (StringUtils.hasText(orderField)) {
			orderByClause.append(orderField).append(" ").append(orderDirection).append(", ");
		}
		example.setOrderByClause(orderByClause.append("update_time desc").toString());

		int totalCount = mapper.countByExample(example);
		numPerPage = SqlUtil.checkPageSize(numPerPage);
		currentPage = SqlUtil.checkPageCurrent(totalCount, numPerPage, currentPage);
		example.setLimitStart(SqlUtil.countOffset(currentPage, numPerPage));
		example.setPageSize(numPerPage);
		Page<RcDataDictionary> page = new Page<RcDataDictionary>(totalCount, SqlUtil.countTotalPage(totalCount, numPerPage), currentPage, numPerPage, mapper.selectByExample(example));
		page.setOrderField(orderField);
		page.setOrderDirection(orderDirection);
		return page;
	}
	
	@Override
	public int insert(RcDataDictionary rcDataDictionary) {
		rcDataDictionary.setStatusId("1");
		rcDataDictionary.setCreateTime(new Date());
		rcDataDictionary.setUpdateTime(rcDataDictionary.getCreateTime());
		return mapper.insertSelective(rcDataDictionary);
	}

	@Override
	public int deleteById(Long id) {
		return mapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public RcDataDictionary selectById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateById(RcDataDictionary rcDataDictionary) {
		rcDataDictionary.setUpdateTime(new Date());
		return mapper.updateByPrimaryKeySelective(rcDataDictionary);
	}

	
}
