package com.jun.plugin.util4j.hotSwap.mapClassFactory;

/**
 * 自定义映射实例工厂 
 * T不能做为父类加载
 * T尽量为接口类型,因为只有接口类型的类才没有逻辑,才可以不热加载,并且子类可选择实现
 */
public interface IMapClassFactory<C,K> {
	
	/**
	 * 创建一个脚本实例
	 * @param code
	 * @return
	 */
	public C buildInstance(K key);
	
	/**
	 * 创建一个脚本实例 
	 * @param code
	 * @param args 脚本构造参数
	 * @return
	 */
	public C buildInstance(K key,Object ...args);
	
	/**
	 * 重新加载脚本
	 */
	public void reload();
}
