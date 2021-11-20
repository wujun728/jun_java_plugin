package com.jun.plugin.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jun.plugin.utils.BeanUtil;

/**
 * Map工具类<br>
 * 
 * @author Wesley<br>
 * 
 */
public class MapUtil {

	 
 

	/**
	 * 对象转Map
	 * 
	 * @param object
	 *            目标对象
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> toMap(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return BeanUtil.describe(object);
	}

	/**
	 * 转换为Collection<Map<K, V>>
	 * 
	 * @param collection
	 *            待转换对象集合
	 * @return 转换后的Collection<Map<K, V>>
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static <T> Collection<Map<String, String>> toMapList(Collection<T> collection) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
		if (collection != null && !collection.isEmpty()) {
			for (Object object : collection) {
				Map<String, String> map = toMap(object);
				retList.add(map);
			}
		}
		return retList;
	}

	/**
	 * 转换为Collection,同时为字段做驼峰转换<Map<K, V>>
	 * 
	 * @param collection
	 *            待转换对象集合
	 * @return 转换后的Collection<Map<K, V>>
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static <T> Collection<Map<String, String>> toMapListForFlat(Collection<T> collection) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
		if (collection != null && !collection.isEmpty()) {
			for (Object object : collection) {
				Map<String, String> map = toMapForFlat(object);
				retList.add(map);
			}
		}
		return retList;
	}

	/**
	 * 转换成Map并提供字段命名驼峰转平行
	 * 
	 * @param clazz
	 *            目标对象所在类
	 * @param object
	 *            目标对象
	 * @param map
	 *            待转换Map
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static Map<String, String> toMapForFlat(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String, String> map = toMap(object);
		return toUnderlineStringMap(map);
	}

	 

	/**
	 * 将Map的Keys转译成下划线格式的<br>
	 * (例:branchNo -> branch_no)<br>
	 * 
	 * @param map
	 *            待转换Map
	 * @return
	 */
	public static <V> Map<String, V> toUnderlineStringMap(Map<String, V> map) {
		Map<String, V> newMap = new HashMap<String, V>();
		for (String key : map.keySet()) {
			newMap.put(BeanUtil.toUnderlineString(key), map.get(key));
		}
		return newMap;
	}
	
	
	
	/**************************************************************************************************************/
	/**************************************************************************************************************/
	/**************************************************************************************************************/

	/** 
	 * @ClassName: MapUtil 
	 * @Description: 读入内存AppParamCode
	 * @author XXXX@163.com
	 * @date 2011-10-2 上午10:08:47 
	 *  
	 */
	/** 
	  * @Fields paramCodeMap : 存放应用系统字典的Map,nickName和value,name
	  */ 
	private static Map<String,Map<String,String>> paramCodeMap = new HashMap<String,Map<String,String>>();
	
	/** 
	  * <p>Title: 私有构造方法</p> 
	  * <p>Description:为了实现单例 </p>  
	  */ 
//	private MapUtil(){
//	}
	/** 
	  * @ClassName: MapUtilHolder 
	  * @Description:私有静态类，用来存放单例 
	  * @author XXXX@163.com 
	  * @date 2011-10-6 下午2:57:10 
	  *  
	  */
	private static class MapUtilHolder{
		static final MapUtil INSTANCE = new MapUtil();
	}
	
	/** 
	  * @Title: getInstance 
	  * @Description: 获得单例对象
	  * @param @return
	  * @return MapUtil
	  * @throws 
	  */
	public static MapUtil getInstance(){
		return MapUtilHolder.INSTANCE;
	}
	
	/** 
	  * @Title: getParamCodeMap 
	  * @Description:获得字典map 
	  * @param @return
	  * @return Map<String,Map<String,String>>
	  * @throws 
	  */
	public Map<String, Map<String, String>> getParamCodeMap() {
		return paramCodeMap;
	}

	/** 
	  * @Title: setDictionarysMap 
	  * @Description:设置字典map 
	  * @param @param dictionarysMap
	  * @return void
	  * @throws 
	  */
	public void setParamCodeMapMap(
			Map<String, Map<String, String>> paramCodeMap) {
		MapUtil.paramCodeMap = paramCodeMap;
	}

	
	/** 
	  * @Title: getMapByNickName 
	  * @Description: 根据nickName获得value与name的键值对 
	  * @param @param nickName
	  * @param @return
	  * @return Map<String,String>
	  * @throws 
	  */
	public Map<String,String> getMapByDomainName(String nickName){
		return paramCodeMap.get(nickName);
	}
	
	/** 
	  * @Title: getNameByValue 
	  * @Description: 根据value获得name 
	  * @param @param value
	  * @param @return
	  * @return String
	  * @throws 
	  */
	public String getNameByValue(String value){
		Map<String,String> valueNameMap = new HashMap<String,String>();
		Set<String> keys = paramCodeMap.keySet();
		for(Iterator<String> iter=keys.iterator();iter.hasNext();){
			valueNameMap.putAll(paramCodeMap.get(iter.next()));
		}
		return valueNameMap.get(value);
	}
	/**
	 * @Description: 获取值 
	 * @param nickName
	 * @param key
	 * @return
	 */
	public String getNameByNickAndKey(String nickName,String key){
		Map<String,String> tmpMap = paramCodeMap.get(nickName);
		if(tmpMap!=null)
			return tmpMap.get(key);
		else
			return null;
	}	
	/**************************************************************************************************************/
	/**************************************************************************************************************/
	/**************************************************************************************************************/
	
	

/** 
  * @ClassName: MapUtil 
  * @Description: 全局参数工具类，用来同步数据库数据到内存
  * @author XXXX@163.com 
  * @date 2011-10-6 上午10:19:17 
  *  
  */
	/** 
	  * @Fields argumentsMap :存放全局参数的Map 
	  */ 
	private static Map<String,String> argumentsMap = new HashMap<String ,String>();

	/** 
	  * @ClassName: ArgumentsMemoryUtilsHolder 
	  * @Description: 私有静态类，用来存放单例
	  * @author XXXX@163.com 
	  * @date 2011-10-6 下午2:48:57 
	  *  
	  */
	private static class ArgumentsMemoryUtilsHolder{
		static final MapUtil INSTANCE = new MapUtil();
	}
	
	/** 
	  * @Title: getInstance 
	  * @Description: 单例方法 
	  * @param @return
	  * @return MapUtil
	  * @throws 
	  */
	public static MapUtil getInstance11(){
		return ArgumentsMemoryUtilsHolder.INSTANCE;
	}
	
	/** 
	  * <p>Title: 私有构造方法</p> 
	  * <p>Description: 为了实现单例</p>  
	  */ 
 
	/** 
	  * @Title: getValueByName 
	  * @Description:根据参数名称得到参数值
	  * @param @param name
	  * @param @return
	  * @return String
	  * @throws 
	  */
	public  String getValueByName(String name){
		return argumentsMap.get(name);
	}
	/**
	 * @Description:根据参数名称得到参数值,如果查不到，返回默认值
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public  String getValueByName(String name,String defaultValue){
		return argumentsMap.get(name)==null?defaultValue:argumentsMap.get(name);
	}	
	
	/** 
	  * @Title: getArgumentsMap 
	  * @Description:获得参数map 
	  * @param @return
	  * @return Map<String,String>
	  * @throws 
	  */
	public  Map<String, String> getArgumentsMap() {
		return argumentsMap;
	}
	
	/** 
	  * @Title: setArgumentsMap 
	  * @Description: 设置参数map
	  * @param @param argumentsMap
	  * @return void
	  * @throws 
	  */
	public  void setArgumentsMap(Map<String, String> argumentsMap) {
		MapUtil.argumentsMap = argumentsMap;
	}
	/**************************************************************************************************************/
	/**************************************************************************************************************/
	/**************************************************************************************************************/
	
	

/** 
  * @ClassName: MapUtil 
  * @Description: 全局参数工具类，用来同步数据库数据到内存
  * @author XXXX@163.com 
  * @date 2011-10-6 上午10:19:17 
  *  
  */
	/** 
	  * @Fields argumentsMap :存放全局参数的Map 
	  */ 
//	private static Map<String,String> argumentsMap = new HashMap<String ,String>();

	/** 
	  * @ClassName: ArgumentsMemoryUtilsHolder 
	  * @Description: 私有静态类，用来存放单例
	  * @author XXXX@163.com 
	  * @date 2011-10-6 下午2:48:57 
	  *  
	  */
//	private static class ArgumentsMemoryUtilsHolder{
//		static final MapUtil INSTANCE = new MapUtil();
//	}
	
	/** 
	  * @Title: getInstance 
	  * @Description: 单例方法 
	  * @param @return
	  * @return MapUtil
	  * @throws 
	  */
	public static MapUtil getInstance1(){
		return ArgumentsMemoryUtilsHolder.INSTANCE;
	}
	
	/** 
	  * <p>Title: 私有构造方法</p> 
	  * <p>Description: 为了实现单例</p>  
	  */ 
	/** 
	  * @Title: getValueByName 
	  * @Description:根据参数名称得到参数值
	  * @param @param name
	  * @param @return
	  * @return String
	  * @throws 
	  */
	public  String getValueByName1(String name){
		return argumentsMap.get(name);
	}
	/**
	 * @Description:根据参数名称得到参数值,如果查不到，返回默认值
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public  String getValueByName1(String name,String defaultValue){
		return argumentsMap.get(name)==null?defaultValue:argumentsMap.get(name);
	}	
	
	/** 
	  * @Title: getArgumentsMap 
	  * @Description:获得参数map 
	  * @param @return
	  * @return Map<String,String>
	  * @throws 
	  */
	public  Map<String, String> getArgumentsMap1() {
		return argumentsMap;
	}
	
	/** 
	  * @Title: setArgumentsMap 
	  * @Description: 设置参数map
	  * @param @param argumentsMap
	  * @return void
	  * @throws 
	  */
	public  void setArgumentsMap1(Map<String, String> argumentsMap) {
		MapUtil.argumentsMap = argumentsMap;
	}
	/**************************************************************************************************************/
	/**************************************************************************************************************/
	/**************************************************************************************************************/
	

	
	/** 
	  * @Fields dictionarysMap : 存放系统字典的Map,nickName和value,name
	  */ 
	private static Map<String,Map<String,String>> dictionarysMap = new HashMap<String,Map<String,String>>();
	
	/** 
	  * <p>Title: 私有构造方法</p> 
	  * <p>Description:为了实现单例 </p>  
	  */ 
	private MapUtil(){
	}
	/** 
	  * @ClassName: MapUtilHolder 
	  * @Description:私有静态类，用来存放单例 
	  * @author XXXX@163.com 
	  * @date 2011-10-6 下午2:57:10 
	  *  
	  */
	private static class MapUtilHolder1{
		static final MapUtil INSTANCE = new MapUtil();
	}
	
	/** 
	  * @Title: getInstance 
	  * @Description: 获得单例对象
	  * @param @return
	  * @return MapUtil
	  * @throws 
	  */
	public static MapUtil getInstance111(){
		return MapUtilHolder.INSTANCE;
	}
	
	/** 
	  * @Title: getDictionarysMap 
	  * @Description:获得字典map 
	  * @param @return
	  * @return Map<String,Map<String,String>>
	  * @throws 
	  */
	public Map<String, Map<String, String>> getDictionarysMap() {
		return dictionarysMap;
	}

	/** 
	  * @Title: setDictionarysMap 
	  * @Description:设置字典map 
	  * @param @param dictionarysMap
	  * @return void
	  * @throws 
	  */
	public void setDictionarysMap(
			Map<String, Map<String, String>> dictionarysMap) {
		MapUtil.dictionarysMap = dictionarysMap;
	}

	
	/** 
	  * @Title: getMapByNickName 
	  * @Description: 根据nickName获得value与name的键值对 
	  * @param @param nickName
	  * @param @return
	  * @return Map<String,String>
	  * @throws 
	  */
	public Map<String,String> getMapByNickName(String nickName){
		return dictionarysMap.get(nickName);
	}
	/**
	 * @Description: 刷新键值对 
	 * @param nickName
	 * @param name
	 * @param value
	 * @return
	 */
	public void refreshDic(String nickName,String name,String value){
		dictionarysMap.get(nickName).put(value, name);
	}
	/**
	 * @Description: 获取值 
	 * @param nickName
	 * @param key
	 * @return
	 */
	/*public String getNameByNickAndKey(String nickName,String key){
		Map<String,String> tmpMap = dictionarysMap.get(nickName);
		if(tmpMap!=null)
			return tmpMap.get(key);
		else
			return null;
	}	
	*//** 
	  * @Title: getNameByValue 
	  * @Description: 根据value获得name 
	  * @param @param value
	  * @param @return
	  * @return String
	  * @throws 
	  *//*
	public String getNameByValue(String value){
		Map<String,String> valueNameMap = new HashMap<String,String>();
		Set<String> keys = dictionarysMap.keySet();
		for(Iterator<String> iter=keys.iterator();iter.hasNext();){
			valueNameMap.putAll(dictionarysMap.get(iter.next()));
		}
		return valueNameMap.get(value);
	}*/
	
	
	/**************************************************************************************************************/
	/**************************************************************************************************************/
	/**************************************************************************************************************/
	

}
