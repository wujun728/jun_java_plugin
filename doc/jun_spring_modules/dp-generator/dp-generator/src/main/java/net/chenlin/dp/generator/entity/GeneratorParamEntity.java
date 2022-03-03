package net.chenlin.dp.generator.entity;

/**
 * 代码生成器请求参数
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月29日 上午11:29:00
 */
public class GeneratorParamEntity {

	private String[] tables;
	
	/**
	 * 系统模块，用户管理 shiro
	 */
	private String module;
	
	/**
	 * 功能编码，用户管理 user
	 */
	private String functionCode;
	
	/**
	 * 后台请求地址，用户管理 sys/user
	 */
	private String requestMapping;
	
	/**
	 * 页面路径，用户管理 base/user
	 */
	private String viewPath;
	
	/**
	 * 生成类型，1：生成包结构，2：只生成源代码
	 */
	private String type;

	public GeneratorParamEntity() {
		super();
	}

	public String[] getTables() {
		return tables;
	}

	public void setTables(String[] tables) {
		this.tables = tables;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getRequestMapping() {
		return requestMapping;
	}

	public void setRequestMapping(String requestMapping) {
		this.requestMapping = requestMapping;
	}

	public String getViewPath() {
		return viewPath;
	}

	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
