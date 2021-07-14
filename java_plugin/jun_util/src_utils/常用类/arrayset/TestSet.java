package book.arrayset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 演示各种Set的使用
 * 存入Set的每个元素必须是唯一的，因为Set不保存重复元素。
 */
public class TestSet {

	/**
	 * 初始化Set的元素
	 * @param set
	 */
	public static void init(Set set){
		if (set != null){
			set.add("aaa");
			set.add("ccc");
			set.add("bbb");
			set.add("eee");
			set.add("ddd");
		}
	}
	/**
	 * 输出set的元素
	 * @param set
	 */
	public static void output(Set set){
		if (set != null){
			//使用迭代器遍历Set，也只有这一种方法
			Iterator it = set.iterator();
			while (it.hasNext()){
				System.out.print(it.next() + " ");
			}
		}
		System.out.println();
	}
	/**
	 * 使用HashSet
	 */
	public static void testHashSet(){
		Set mySet = new HashSet();
		init(mySet);
		System.out.println("使用HashSet: ");
		output(mySet);
	}
	/**
	 * 使用TreeSet
	 */
	public static void testTreeSet(){
		Set mySet = new TreeSet();
		init(mySet);
		System.out.println("使用TreeSet: ");
		output(mySet);
	}
	/**
	 * 使用LinkedHashSet
	 */
	public static void testLinkedHashSet(){
		Set mySet = new LinkedHashSet();
		init(mySet);
		System.out.println("使用LinkedHashSet: ");
		output(mySet);
	}
	public static void main(String[] args) {
		TestSet.testHashSet();
		TestSet.testTreeSet();
		TestSet.testLinkedHashSet();
		
		Set mySet = new HashSet();
		init(mySet);
		//Set不允许元素重复
		mySet.add("aaa");
		mySet.add("bbb");
		System.out.println("为mySet加入aaa, bbb元素后: ");
		output(mySet);
		//删除元素
		mySet.remove("aaa");
		System.out.println("mySet删除aaa元素后: ");
		output(mySet);
		//增加另外一个集合中的所有元素
		List list = new ArrayList();
		list.add("aaa");
		list.add("aaa");
		list.add("fff");
		mySet.addAll(list);
		System.out.println("mySet添加另外一个集合的所有元素后: ");
		output(mySet);
		//删除除了另外一个集合包含的以外的所有元素
		mySet.retainAll(list);
		System.out.println("mySet删除除了另外一个集合包含的以外的所有元素后: ");
		output(mySet);
		//删除另外一个集合包含的所有元素
		mySet.removeAll(list);
		System.out.println("mySet删除另外一个集合包含的所有元素后: ");
		output(mySet);
		//获取Set中元素的个数
		System.out.println("mySet中当前元素的个数: " + mySet.size());
		//判断Set中元素个数是否为0
		System.out.println("mySet中当前元素为0？  " + mySet.isEmpty());
		
		/**
		 * （1）Set不允许重复元素，因此加入Set的Object必须定义equals()方法以确保对象的唯一性。
		 * （2）HashSet采用散列函数对元素进行排序，是专门为快速查询而设计的。存入HashSet的对象必须定义hashCode()。
		 * （3）TreeSet采用红黑树的数据结构进行排序元素，能保证元素的次序，使用它可以从Set中提取有序的序列。
		 * 需要注意的是，生成自己的类时，Set需要维护元素的存储顺序，因此要实现Comparable接口并定义compareTo()方法。
		 * （4）LinkedHashSet内部使用散列以加快查询速度，同时使用链表维护元素的插入的次序，在使用迭代器遍历Set时，结果会按元素插入的次序显示。
	 */
	}
}
