package book.j2se5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 范型编程
 */
public class GeneralProgram {

	/**
	 * 使用问号？通配符，？代表任何类型，所以它的参数可以是任何类型的Collection。
	 */
	public static void printCollection(Collection<?> collect){
		if (collect == null){
			return;
		}
		for(Object obj : collect){
			System.out.print(obj + "    ");
		}
		System.out.println();
	}
	/**
	 * 使用有限制的通配符“? extends”，可以接受任何Parent及其子类的Collection
	 * @param collect
	 */
	public static void printNames(Collection<? extends Parent> collect){
		if (collect == null){
			return;
		}
		for(Parent parent : collect){
			System.out.print(parent.name + "    ");
		}
		System.out.println();
	}
	/**
	 * 范型方法，将一个任意类型的数组，添加到列表中。
	 * @param <T> 代表一个任意的类
	 * @param datas	数组对象
	 * @return
	 */
	public static <T> List<T> arrayToList(T[] datas){
		if (datas == null){
			return null;
		}
		List<T> list_T = new ArrayList<T>();
		for (T x : datas){
			list_T.add(x);
		}
		return list_T;
	}
	/**
	 * 范型方法，在一组Parent对象中查找名字为name的Parent对象
	 * @param <T>	可以是Parent对象或者子类对象
	 * @param parents	Parent对象组
	 * @param name	目标name
	 * @return	匹配的Parent对象
	 */
	public static <T extends Parent> T findParent(T[] parents, String name) {
		if (parents == null) {
			return null;
		}
		T parent = null;
		// 依次遍历Parent对象组
		for (T x : parents) {
			// 如果Parent对象的name与参数name匹配，则退出遍历
			if (x.name.equals(name)) {
				parent = x;
				break;
			}
		}
		// 返回
		return parent;
	}
	
	public static void main(String[] args) {
		/** * 指定具体的类型 ** */
		// 声明一个用于装字符串的列表，该列表只能装字符串类型的对象
		List<String> list_S = new ArrayList<String>();
		list_S.add("first");
		list_S.add("second");
		list_S.add("third");
		// 不能装整数对象
		Integer iObj = 10;
		// list_S.add(iObj);// error!!!
		// 在从列表中取值时，不用作强制类型转换。
		String firstS = list_S.get(0);
		String thirdS = list_S.get(2);
		System.out.println("firstS: " + firstS + "; thirdS: " + thirdS);

		/** **范型和继承** */
		// String 继承 Object
		String s = "sss";
		Object o = s;
		// 但List<String>不继承List<Object>
		// List<Object> list_O = list_S;// error!!!

		/** * 通配符 *** */
		// 调用具有“？”通配符的方法
		List<Integer> list_I = new ArrayList<Integer>();
		list_I.add(5555);
		list_I.add(6666);
		list_I.add(7777);
		// 该方法既可以打印整型列表，也可以打印字符串列表
		printCollection(list_I);
		printCollection(list_S);

		// 调用具有“? extends” 通配符的方法
		// 只接受父类以及子类类型的列表
		List<Parent> list_Parent = new ArrayList<Parent>();
		list_Parent.add(new Parent("parentOne"));
		list_Parent.add(new Parent("parentTow"));
		list_Parent.add(new Parent("parentThree"));
		List<Child> list_Child = new ArrayList<Child>();
		list_Child.add(new Child("ChildOne", 20));
		list_Child.add(new Child("ChildTow", 22));
		list_Child.add(new Child("ChildThree", 21));
		printNames(list_Parent);
		printNames(list_Child);
		// 不能接受其他类型的参数
		// printNames(list_S);// error!!!

		/** * 范型方法 *** */
		// arrayToList方法将任意类型的对象数组变成相应的列表
		Integer[] iObjs = { 55, 66, 77, 88, 99 };
		// 转换整型数组
		List<Integer> result_I = arrayToList(iObjs);
		printCollection(result_I);
		String[] ss = { "temp", "temptemp", "hehe", "he", "hehehe" };
		// 转换字符串数组
		List<String> result_S = arrayToList(ss);
		printCollection(result_S);

		// findParent方法在一组Parent对象中根据name查找Parent
		Parent[] parents = { new Parent("abc"), new Parent("bcd"),
				new Parent("def") };
		Parent parent = findParent(parents, "bcd");
		System.out.println("找到的bcd：" + parent);
		Child[] children = { new Child("abc", 22), new Child("bcd", 23),
				new Child("def", 22) };
		Child child = findParent(children, "bcd");
		System.out.println("找到的bcd：" + child);
		// 但是不能在字符串数组中进行查找
		// String sss = findParent(ss, "temp");// error!!!
	}
}
// 父类
class Parent{
	public String name;
	public Parent(String name){
		this.name = name;
	}
	public String toString(){
		return "name = " + this.name;
	}
}
// 子类
class Child extends Parent{
	public int age;
	public Child(String name, int age){
		super(name);
		this.age = age;
	}
	public String toString(){
		return super.toString() + ";  age = " + age;
	}
}