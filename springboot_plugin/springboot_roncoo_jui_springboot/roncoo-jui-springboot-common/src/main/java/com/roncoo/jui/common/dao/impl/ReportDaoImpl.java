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

import com.roncoo.jui.common.dao.ReportDao;
import com.roncoo.jui.common.entity.RcReport;
import com.roncoo.jui.common.entity.RcReportExample;
import com.roncoo.jui.common.entity.RcReportExample.Criteria;
import com.roncoo.jui.common.mapper.RcReportMapper;
import com.roncoo.jui.common.util.SqlUtil;
import com.roncoo.jui.common.util.jui.Page;

/**
 * 
 * @author Wujun
 */
@Repository
public class ReportDaoImpl implements ReportDao {

	@Autowired
	private RcReportMapper mapper;

	@Override
	public Page<RcReport> listForPage(int currentPage, int numPerPage, String orderField, String orderDirection, RcReport rcReport) {

		RcReportExample example = new RcReportExample();
		Criteria c = example.createCriteria();

		// 邮箱查询
		if (StringUtils.hasText(rcReport.getUserEmail())) {
			c.andUserEmailLike(SqlUtil.like(rcReport.getUserEmail()));
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
		Page<RcReport> page = new Page<RcReport>(totalCount, SqlUtil.countTotalPage(totalCount, numPerPage), currentPage, numPerPage, mapper.selectByExample(example));
		page.setOrderField(orderField);
		page.setOrderDirection(orderDirection);
		return page;
	}

	@Override
	public Integer insert(RcReport rcReport) {
		rcReport.setStatusId("Y");
		rcReport.setCreateTime(new Date());
		rcReport.setUpdateTime(rcReport.getCreateTime());
		rcReport.setSort(100);
		return mapper.insertSelective(rcReport);
	}

}
