package book.arrayset;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 对Map中的元素进行排序
 */
public class SortMap {

	public static void main(String[] args) {
		Map myMap = new HashMap();
		myMap.put(new Integer(5), "aaa");
		myMap.put(new Integer(8), "bbb");
		myMap.put(new Integer(4), "ccc");
		myMap.put(new Integer(7), "ddd");
		myMap.put(new Integer(3), "eee");
		System.out.println("初始化后的myMap: ");
		TestMap.output(myMap);
		
		//借助TreeMap的排序功能将myMap排序
		Map treeMap = new TreeMap(myMap);
		System.out.println("排序后的myMap: ");
		TestMap.output(treeMap);
		//按照自定义的比较器排序
		TreeMap newTreeMap = new TreeMap(new MyIntComparator());
		newTreeMap.putAll(myMap);//将一个Map的所有数据放在本Map里
		System.out.println("使用自定义比较器排序后的myMap: ");
		TestMap.output(newTreeMap);
	}
}
