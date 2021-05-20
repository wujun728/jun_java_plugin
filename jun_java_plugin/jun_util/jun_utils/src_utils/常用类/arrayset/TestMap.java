package book.arrayset;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 演示各个Map的实现类
 */
public class TestMap {
	
	/**
	 * 初始化一个Map
	 * @param map
	 */
	public static void init(Map map){
		if (map != null){
			String key = null;
			for (int i=5; i>0; i--){
				key = new Integer(i).toString() + ".0";
				map.put(key, key.toString());
				//Map中的键是不重复的，如果插入两个键值一样的记录，
				//那么后插入的记录会覆盖先插入的记录
				map.put(key, key.toString() + "0");			}
		}
	}
	/**
	 * 输出一个Map
	 * @param map
	 */
	public static void output(Map map){
		if (map != null){
			Object key = null;
			Object value = null;
			//使用迭代器遍历Map的键，根据键取值
			Iterator it = map.keySet().iterator();
			while (it.hasNext()){
				key = it.next();
				value = map.get(key);
				System.out.println("key: " + key + "; value: " + value );
			}
			//或者使用迭代器遍历Map的记录Map.Entry
			Map.Entry entry = null;
			it = map.entrySet().iterator();
			while (it.hasNext()){
				//一个Map.Entry代表一条记录
				entry = (Map.Entry)it.next();
				//通过entry可以获得记录的键和值
				//System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
			}
		}
	}
	/**
	 * 判断map是否包含某个键
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean containsKey(Map map, Object key){
		if (map != null){
			return map.containsKey(key);
		}
		return false;
	}
	/**
	 * 判断map是否包含某个值
	 * @param map
	 * @param value
	 * @return
	 */
	public static boolean containsValue(Map map, Object value){
		if (map != null){
			return map.containsValue(value);
		}
		return false;
	}
	/**
	 * 演示HashMap
	 */
	public static void testHashMap(){
		Map myMap = new HashMap();
		init(myMap);
		//HashMap的键可以为null
		myMap.put(null,"ddd");
		//HashMap的值可以为null
		myMap.put("aaa", null);
		output(myMap);
	}
	/**
	 * 演示Hashtable
	 */
	public static void testHashtable(){
		Map myMap = new Hashtable();
		init(myMap);
		//Hashtable的键不能为null
		//myMap.put(null,"ddd");
		//Hashtable的值不能为null
		//myMap.put("aaa", null);
		output(myMap);
	}
	/**
	 * 演示LinkedHashMap
	 */
	public static void testLinkedHashMap(){
		Map myMap = new LinkedHashMap();
		init(myMap);
		//LinkedHashMap的键可以为null
		myMap.put(null,"ddd");
		//LinkedHashMap的值可以为null
		myMap.put("aaa", null);
		output(myMap);
	}
	/**
	 * 演示TreeMap
	 */
	public static void testTreeMap(){
		Map myMap = new TreeMap();
		init(myMap);
		//TreeMap的键不能为null
		//myMap.put(null,"ddd");
		//TreeMap的值不能为null
		//myMap.put("aaa", null);
		output(myMap);
	}

	public static void main(String[] args) {
		System.out.println("采用HashMap");
		TestMap.testHashMap();
		System.out.println("采用Hashtable");
		TestMap.testHashtable();
		System.out.println("采用LinkedHashMap");
		TestMap.testLinkedHashMap();
		System.out.println("采用TreeMap");
		TestMap.testTreeMap();
		
		Map myMap = new HashMap();
		TestMap.init(myMap);
		System.out.println("新初始化一个Map: myMap");
		TestMap.output(myMap);
		//清空Map
		myMap.clear();
		System.out.println("将myMap clear后，myMap空了么?  " + myMap.isEmpty());
		TestMap.output(myMap);
		myMap.put("aaa", "aaaa");
		myMap.put("bbb", "bbbb");
		//判断Map是否包含某键或者某值
		System.out.println("myMap包含键aaa?  "+ TestMap.containsKey(myMap, "aaa"));
		System.out.println("myMap包含值aaaa?  "+ TestMap.containsValue(myMap, "aaaa"));
		//根据键删除Map中的记录
		myMap.remove("aaa");
		System.out.println("删除键aaa后，myMap包含键aaa?  "+ TestMap.containsKey(myMap, "aaa"));
		//获取Map的记录数
		System.out.println("myMap包含的记录数:  " + myMap.size());
	}
	/**
	 * Map用于存储键值对，不允许键重复，值可以重复。
	 * （1）HashMap是一个最常用的Map，它根据键的hashCode值存储数据，根据键可以直接获取它的值，具有很快的访问速度。
	 * HashMap最多只允许一条记录的键为null，允许多条记录的值为null。
	 * HashMap不支持线程的同步，即任一时刻可以有多个线程同时写HashMap，可能会导致数据的不一致。
	 * 如果需要同步，可以用Collections.synchronizedMap(HashMap map)方法使HashMap具有同步的能力。
	 * （2）Hashtable与HashMap类似，不同的是：它不允许记录的键或者值为空；
	 * 它支持线程的同步，即任一时刻只有一个线程能写Hashtable，然而，这也导致了Hashtable在写入时会比较慢。
	 * （3）LinkedHashMap保存了记录的插入顺序，在用Iteraor遍历LinkedHashMap时，先得到的记录肯定是先插入的。
	 * 在遍历的时候会比HashMap慢。
	 * （4）TreeMap能够把它保存的记录根据键排序，默认是按升序排序，也可以指定排序的比较器。当用Iteraor遍历TreeMap时，
	 * 得到的记录是排过序的。
	 */
}
