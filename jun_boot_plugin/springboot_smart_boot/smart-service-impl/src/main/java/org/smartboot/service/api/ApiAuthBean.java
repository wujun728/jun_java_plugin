package org.smartboot.service.api;

import java.util.HashMap;
import java.util.Map;

import org.smartboot.shared.ToString;

/**
 * API鉴权数据实体
 * 
 * @author Wujun
 * @versio n ApiAuthBean.java, v 0.1 2016年2月10日 下午2:20:43 Seer Exp.
 */
public class ApiAuthBean extends ToString {
	/** */
	private static final long serialVersionUID = 1080681227403532680L;
	/** 服务名称 */
	private String srvname;
	/** 执行事件 */
	private String actName;
	/** 版本号 */
	private String apiVersion;

	/** */
	private Map<String, Object> context;

	/**
	 * Api鉴权上下文对象键名
	 * 
	 * @author Wujun
	 * @version ApiAuthBean.java, v 0.1 2016年6月16日 下午2:24:58 Seer Exp.
	 */
	public interface ContextKey {
		/** 权限列表 */
		public String PERMISSION_LIST = "permissions";
	}

	/**
	 * Getter method for property <tt>srvname</tt>.
	 *
	 * @return property value of srvname
	 */
	public final String getSrvname() {
		return srvname;
	}

	/**
	 * Setter method for property <tt>srvname</tt>.
	 *
	 * @param srvname
	 *            value to be assigned to property srvname
	 */
	public final void setSrvname(String srvname) {
		this.srvname = srvname;
	}

	/**
	 * Getter method for property <tt>actName</tt>.
	 *
	 * @return property value of actName
	 */
	public final String getActName() {
		return actName;
	}

	/**
	 * Setter method for property <tt>actName</tt>.
	 *
	 * @param actName
	 *            value to be assigned to property actName
	 */
	public final void setActName(String actName) {
		this.actName = actName;
	}

	/**
	 * Getter method for property <tt>apiVersion</tt>.
	 *
	 * @return property value of apiVersion
	 */
	public final String getApiVersion() {
		return apiVersion;
	}

	/**
	 * Setter method for property <tt>apiVersion</tt>.
	 *
	 * @param apiVersion
	 *            value to be assigned to property apiVersion
	 */
	public final void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	/**
	 * Getter method for property <tt>context</tt>.
	 *
	 * @return property value of context
	 */
	public final Object getContext(String key) {
		if (context == null) {
			context = new HashMap<>();
		}
		return context.get(key);
	}

	/**
	 * Setter method for property <tt>context</tt>.
	 *
	 * @param context
	 *            value to be assigned to property context
	 */
	public final void setContext(String key, Object value) {
		if (context == null) {
			context = new HashMap<>();
		}
		context.put(key, value);
	}

}
