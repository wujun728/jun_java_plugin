package com.neo.redis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface ICacheManager {

	/**
	 * 根据缓存key获取值
	 * @param cacheKey
	 * @return
	 */
	public Object getCache(Serializable cacheKey);

	/**
	 * 设置缓存数据的key-value，并设置失效时间，单位为秒
	 * @param cacheKey
	 * @param objValue
	 * @param expiration
	 * @return
	 */
	public boolean putCache(Serializable cacheKey, Object objValue, int expiration);

	/**
	 * 清除缓存
	 * @param cacheKey
	 */
	public void removeCache(Serializable cacheKey);
	
	/**
	 * 向指定list集合中添加对象，在list尾部添加对象
	 * @param cacheKey
	 * @param obj
	 * @return
	 */
	public boolean putListCache(Serializable cacheKey, Object objValue);

	/**
	 * 向指定list集合中添加对象，并指定位置坐标
	 * @param cacheKey
	 * @param obj
	 * @param index
	 * @return
	 */
	public boolean putListCache(Serializable cacheKey, Object objValue, int index);
	
	/**
	 * 根据坐标，返回一段集合
	 * @param cacheKey
	 * @param start 起始坐标 头部为0
	 * @param end 结束坐标 尾部为-1
	 * @return
	 */
	public List<Object> getListCache(Serializable cacheKey, int start, int end);
	
	/**
	 * 返回结合
	 * @param cacheKey
	 * @return
	 */
	public List<Object> getListCache(Serializable cacheKey);
	
	/**
	 * 裁剪list集合
	 * @param cacheKey
	 * @param start 起始坐标
	 * @param end 结束坐标
	 * @return
	 */
	public boolean trimListCache(Serializable cacheKey, int start, int end);
	
	/**
	 * 添加map集合
	 * @param cacheKey
	 * @param map
	 * @return
	 */
	public boolean putMapCache(Serializable cacheKey, Map<Object, Object> map);
	
	/**
	 * 删除map中的键值
	 * @param cacheKey
	 * @param mapKey
	 * @return
	 */
	public boolean deleteMapCache(Serializable cacheKey, Serializable mapKey);
	
	
	/**
	 * 获取map中的值
	 * @param cacheKey
	 * @param mapKey
	 * @return
	 */
	public Object getMapValueCache(Serializable cacheKey, Serializable mapKey);
}
