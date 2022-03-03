package com.jun.common.basiccode;

import java.util.List;

/**
 * 基础数据Dao接口
 * @author Wujun
 *
 */
public interface IBasicCodeDao {
	/**
	 * 加载所有的基础数据列表
	 * @return
	 */
	public List<BasicCode> findAllBasicCode();
	/**
	 * 基础数据组合查询
	 * @param basicCode 查询参数对象，
	 *                  若传入对象为null则返回null
	 *                  若参数对象属性值全部为null,则查询所有
	 * @return
	 */
	public List<BasicCode> findBasicCodeList(BasicCode basicCode);
}
