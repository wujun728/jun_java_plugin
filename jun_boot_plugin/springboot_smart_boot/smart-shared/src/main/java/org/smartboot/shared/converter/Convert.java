package org.smartboot.shared.converter;

/**
 * 转换接口
 * 
 * @author Wujun
 * @version Convert.java, v 0.1 2015年12月3日 下午8:18:55 Seer Exp.
 */
public interface Convert<T, S> {

	public T convert(S s);
}
