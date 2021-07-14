package book.arrayset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数组与Collection之间的转换
 */
public class ArrayCollection {

	public static void main(String[] args) {
		
		List list = new ArrayList();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		//当List中的数据类型都一致时，可以将list转化成数组
		//转化成对象数组时，直接调用toArray方法
		Object[] objArray = list.toArray();
		System.out.println("从list转化成的对象数组的长度为: " + objArray.length);
		//在转化成其他类型的数组时，需要强制类型转换，并且，要使用带参数的toArray方法。
		//toArray方法的参数为一个对象数组，将list中的内容放入参数数组中
		//当参数数组的长度小于list的元素个数时，会自动扩充数组的长度以适应list的长度
		String[] strArray1 = (String[])list.toArray(new String[0]);
		System.out.println("从list转化成的字符串数组的长度为: " + strArray1.length);
		//分配一个长度与list的长度相等的字符串数组。
		String[] strArray2 = (String[])list.toArray(new String[list.size()]);
		System.out.println("从list转化成的字符串数组的长度为: " + strArray2.length);
		list.clear();//清空List
		
		//将数组转化成List
		//逐个添加到List
		for (int i=0; i<objArray.length; i++){
			list.add(objArray[i]);
		}
		System.out.println("从数组转化成的list的元素个数: " + objArray.length);
		list.clear();//清空List
		//直接使用Arrays类的asList方法
		list = Arrays.asList(objArray);
		System.out.println("从数组转化成的list的元素个数: " + objArray.length);
		
		Set set = new HashSet();
		set.add("aaa");
		set.add("bbb");
		//Set转化成数组
		objArray = set.toArray();
		strArray1 = (String[])set.toArray(new String[0]);
		strArray2 = (String[])set.toArray(new String[set.size()]);
		
		//数组转换成Set
		//将数组转换成List后，再用List构造Set
		set = new HashSet(Arrays.asList(objArray));
		//将Set清空，然后把数组转换成的List全部add
		set.clear();
		set.addAll(Arrays.asList(strArray1));
	}
}
