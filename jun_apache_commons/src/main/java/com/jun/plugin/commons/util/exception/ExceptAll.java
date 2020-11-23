/**   
 * @Title: ExceptAll.java
 * @Package rjzjh.tech.common.Exception
 * @Description: TODO(用一句话描述该文件做什么)
 * @author Wujun
 * @date 2010-10-29 下午01:26:42
 * @version V1.0   
 */
package com.jun.plugin.commons.util.exception;

/**
 * @ClassName: ExceptAll
 * @Description: 自定义异常编码与其解释（仅项目用。）
 * @author Wujun
 * @date 2010-10-29 下午01:26:42
 * 
 */
public enum ExceptAll {
	default_Project("系统错误，请联系相关管理人员"),
	default_Param("要连接的数据不能传空"),
	
	Project_nofile("找不到文件"),
	
	param_noblank("参数不能为空"),
	
	file_readerror("读取文件错误"),
	
	net_clienterror("客户端连接错误"),
	net_streamclose("关闭流错误"),
	
	reflect_nomethod("反射中缺少方法");// freemark模板读取错误
	
	

	private String desc;

	private ExceptAll(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
