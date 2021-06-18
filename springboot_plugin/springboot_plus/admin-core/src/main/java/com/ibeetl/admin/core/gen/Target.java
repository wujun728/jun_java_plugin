package com.ibeetl.admin.core.gen;

import org.beetl.core.GroupTemplate;

/**
 * 描述如何输出代码，有打印后台，页面输出，或者直接生成到项目里
 * @author Wujun
 *
 */
public interface Target {
	public void flush(AutoGen gen,String content);
	public GroupTemplate getGroupTemplate();
}
