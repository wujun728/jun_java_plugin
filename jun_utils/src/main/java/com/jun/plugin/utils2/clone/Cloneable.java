package com.jun.plugin.utils2.clone;

/**
 * 克隆支持接口
 * @author Looly
 *
 * @param <T>
 */
public interface Cloneable<T> extends java.lang.Cloneable{
	T clone();
}
