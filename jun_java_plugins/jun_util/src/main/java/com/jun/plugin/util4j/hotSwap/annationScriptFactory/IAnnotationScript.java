package com.jun.plugin.util4j.hotSwap.annationScriptFactory;

/**
 * 注意,脚本的实现类一定要保留无参构造器
 * @author juebanlin
 */
public interface IAnnotationScript extends Runnable{
	
	/**
	 * 脚本执行
	 */
	public void run();
}
