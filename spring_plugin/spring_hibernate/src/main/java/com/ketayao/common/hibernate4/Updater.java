package com.ketayao.common.hibernate4;

import java.util.HashSet;
import java.util.Set;

/**
 * 更新对象类
 * 
 * 提供三种更新模式：MAX, MIN, MIDDLE
 * <ul>
 * <li>MIDDLE：默认模式。除了null外，都更新。exclude和include例外。</li>
 * <li>MAX：最大化更新模式。所有字段都更新（包括null）。exclude例外。</li>
 * <li>MIN：最小化更新模式。所有字段都不更新。include例外。</li>
 * </ul>
 * 
 * @author liufang
 * 
 */
public class Updater<T> {
	/**
	 * 构造器
	 * 
	 * @param bean
	 *            待更新对象
	 */
	public Updater(T bean) {
		this.bean = bean;
	}

	/**
	 * 构造器
	 * 
	 * @param bean
	 *            待更新对象
	 * @param mode
	 *            更新模式
	 * @return
	 */
	public Updater(T bean, UpdateMode mode) {
		this.bean = bean;
		this.mode = mode;
	}

	/**
	 * 设置更新模式
	 * 
	 * @param mode
	 * @return
	 */
	public Updater<T> setUpdateMode(UpdateMode mode) {
		this.mode = mode;
		return this;
	}

	/**
	 * 必须更新的字段
	 * 
	 * @param property
	 * @return
	 */
	public Updater<T> include(String property) {
		includeProperties.add(property);
		return this;
	}

	/**
	 * 不更新的字段
	 * 
	 * @param property
	 * @return
	 */
	public Updater<T> exclude(String property) {
		excludeProperties.add(property);
		return this;
	}

	/**
	 * 某一字段是否更新
	 * 
	 * @param name
	 *            字段名
	 * @param value
	 *            字段值。用于检查是否为NULL
	 * @return
	 */
	public boolean isUpdate(String name, Object value) {
		if (this.mode == UpdateMode.MAX) {
			return !excludeProperties.contains(name);
		} else if (this.mode == UpdateMode.MIN) {
			return includeProperties.contains(name);
		} else if (this.mode == UpdateMode.MIDDLE) {
			if (value != null) {
				return !excludeProperties.contains(name);
			} else {
				return includeProperties.contains(name);
			}
		} else {
			// never reach
		}
		return true;
	}

	private T bean;

	private Set<String> includeProperties = new HashSet<String>();

	private Set<String> excludeProperties = new HashSet<String>();

	private UpdateMode mode = UpdateMode.MIDDLE;

	// private static final Logger log = LoggerFactory.getLogger(Updater.class);

	public static enum UpdateMode {
		MAX, MIN, MIDDLE
	}

	public T getBean() {
		return bean;
	}

	public Set<String> getExcludeProperties() {
		return excludeProperties;
	}

	public Set<String> getIncludeProperties() {
		return includeProperties;
	}
}
