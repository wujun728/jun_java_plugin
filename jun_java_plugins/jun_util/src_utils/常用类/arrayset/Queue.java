package book.arrayset;

import java.util.LinkedList;

/**
 * 实现队列功能
 * 数据元素只能从队尾进入，从队首取出。
 * 在队列中，数据元素可以任意增减，但数据元素的次序不会改变。
 * 每当有数据元素从队列中被取出，后面的数据元素依次向前移动一位。
 * 所以，任何时候从队列中读到的都是队首的数据。
 */
public class Queue {
	
	private LinkedList data = new LinkedList();
	
	public Queue(){
	}
	/**
	 * 向队列添加一个元素，只能加入到队尾
	 * @param obj
	 */
	public void add(Object obj){
		this.data.addLast(obj);
	}
	/**
	 * 查看队首元素，数据还保留在队列中
	 * @return
	 */
	public Object peek(){
		if (data.isEmpty()){
			System.out.println("队列中没有元素！");
			return null;
		}
		return data.getFirst();
	}
	/**
	 * 删除队首元素
	 * @return
	 */
	public boolean remove(){
		if (data.isEmpty()){
			System.out.println("队列中没有元素！");
			return false;
		}
		data.removeFirst();
		return true;
	}
	/**
	 * 弹出元素，即获取队首元素并将其从队列中删除
	 * @return
	 */
	public Object pop(){
		if (data.isEmpty()){
			System.out.println("队列中没有元素！");
			return null;
		}
		return data.removeFirst();
	}
	/**
	 * 	在队列中查找元素，返回第一次出现的位置
	 * @param obj
	 * @return  
	 */
	public int indexOf(Object obj){
		return data.indexOf(obj);
	}
	/**
	 * 在队列中查找元素，返回最后一次出现的位置
	 * @param obj
	 * @return
	 */
	public int lastIndexOf(Object obj){
		return data.lastIndexOf(obj);
	}
	/**
	 * 判断队列是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return data.isEmpty();
	}
	/**
	 * 清除队列中所有元素
	 */
	public void clear(){
		data.clear();
	}

	public static void main(String[] args) {
		Queue myQueue = new Queue();
		//向队列插入元素
		myQueue.add("aaa");
		myQueue.add("bbb");
		myQueue.add("ccc");
		myQueue.add("bbb");
		myQueue.add("ccc");
		myQueue.add("bbb");
		//获取第一个元素
		System.out.println("队首的数据是: " + myQueue.peek());
		//删除元素
		myQueue.remove();
		//获取第一个元素并删除
		System.out.println("从队列弹出数据: " + myQueue.pop());
		//查找bbb第一次出现的位置
		System.out.println("bbb第一次在队列中出现的位置" + myQueue.indexOf("bbb"));
		//查找bb最后一次出现的位置
		System.out.println("bbb最后一次在队列中出现的位置" + myQueue.lastIndexOf("bbb"));
		//清除队列中的所有元素
		myQueue.clear();
		//判断队列中的元素个数是否为0
		System.out.println("队列中的元素个数为0？ " + myQueue.isEmpty());
		
		/**
		 * 因为队列经常需要对队首和队尾的数据进行操作，在队首插入和删除元素，
		 * 几乎不需要操作队列中间的数据。
		 * 因此，不适合使用ArrayList和Vector，因为它们使用数组实现的，元素的插入和删除都会引起数组的复制，开销较大。
		 * 所以这里选择LinkedList存放队列的数据，它用链表结构存储数据，很适合元素的插入和删除。
		 */
	}
}
