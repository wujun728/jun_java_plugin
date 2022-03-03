package com.jun.common.select;

import java.util.List;
/**
 * 下拉框数据Dao接口
 * @author Wujun
 *
 */
public interface ISelectDao {
	/**
	 * 下拉框数据查询
	 * @param selectBO
	 * @return
	 */
	public List<SelectBO> getSelectList(SelectBO selectBO) throws Exception;
}
