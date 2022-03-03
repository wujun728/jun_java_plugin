package com.jun.common.select;

import java.util.List;

import com.jun.common.GerneralService;

/**
 * 下拉框数据Service接口
 * @author Wujun
 *
 */
public interface ISelectService extends GerneralService{
	/**
	 * 下拉框数据查询
	 * @param selectBO
	 * @return
	 */
	public List<SelectBO> getSelectList(SelectBO selectBO) throws Exception;
}
