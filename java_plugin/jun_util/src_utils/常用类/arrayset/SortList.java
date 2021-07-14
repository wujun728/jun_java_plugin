package book.arrayset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 对List中的元素排序
 */
public class SortList {
	
	public static void output(List list){
		if (list == null){
			return;
		}
		for (int i=0; i<list.size(); i++){
			System.out.print(list.get(i).toString() + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		List list = new ArrayList();
		list.add(new Integer(5));
		list.add(new Integer(8));
		list.add(new Integer(1));
		list.add(new Integer(3));
		list.add(new Integer(2));
		list.add(new Double(3.1));
		System.out.println("list开始状态");
		SortList.output(list);
		//Collections.sort方法将用默认比较器排列list的元素
		Collections.sort(list);
		System.out.println("list被默认比较器排序后的状态");
		SortList.output(list);
		//下面将list的元素按降序排列
		Collections.sort(list, new MyIntComparator());
		System.out.println("list被自定义比较器排序后的状态");
		SortList.output(list);
		
		//因此，对于任意自定义类的对象，当保存在集合类容器中后，如果需要对它们进行排序，
		//需要自己提供适应于自定义类的比较器，自定义比较器必须实现Comparator接口。
		//然后采用Collections.sort(list, comparator);方法对容器进行排序。
	}
}

