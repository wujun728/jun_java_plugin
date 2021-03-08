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

import com.roncoo.jui.common.entity.RcReport;
import com.roncoo.jui.common.util.jui.Page;

/**
 * 
 * @author Wujun
 */
public interface ReportDao {

	/**
	 * @param currentPage
	 * @param numPerPage
	 * @param orderField
	 * @param orderDirection
	 * @param rcReport
	 * @return
	 */
	Page<RcReport> listForPage(int currentPage, int numPerPage, String orderField, String orderDirection, RcReport rcReport);

	/**
	 * @param rcReport
	 * @return
	 */
	Integer insert(RcReport rcReport);
	

}
