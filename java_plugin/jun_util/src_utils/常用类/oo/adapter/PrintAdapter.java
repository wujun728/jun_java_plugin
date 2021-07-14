package book.oo.adapter;

import book.oo.factory.Factory;
import book.oo.sort.ISortNumber;

public class PrintAdapter extends Printer implements ISortNumber{

	private ISortNumber mySort;
	public PrintAdapter(ISortNumber sort){
		super();
		this.mySort = sort;
	}
	
	public int[] sortASC(int[] intArray) {
		if (this.mySort != null){
			return this.mySort.sortASC(intArray);
		} else {
			return null;
		}
	}
	
	public static void main(String[] args){
		int[] array = new int[]{7,9,4,6,2,-1,12};
		PrintAdapter adapter = new PrintAdapter(Factory.getOrderNumber(Factory.BUBBLE_SORT));
		adapter.printIntArray(adapter.sortASC(array));
	}
	
	/**
	 * Adapter模式，就是适配器模式，使两个原本没有关联的类结合一起使用。
平时我们会经常碰到这样的情况，有了两个现成的类，它们之间没有什么联系，但是我们现在既想用其中一个类的方法，
同时也想用另外一个类的方法。有一个解决方法是，修改它们各自的接口，但是这是我们最不愿意看到的。这个时候Adapter模式就会派上用场了。
	 * 
	 * 我们已经实现了一个数组排序的接口，也有了一个能够打印数组的类，假定不能修改原有的实现。
	 * 现在，需要实现一个类，既能够打印数组，又能对数组进行排序。此时就需要用Adapter模式来实现了。
	 * 
	 * 定义：Adapter：将一个类的接口转换成客户希望的另外一个接口。Adapter模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。
	 */
}
