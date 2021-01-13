package com.jun.plugin.collection;

/**
 * �Լ�ʵ��һ��ArrayList���������Ǹ�õ����ArrayList��ĵײ�ṹ��
 *
 */
public class SxtArrayList /*implements List*/ {
	
	private Object[] elementData;
	private int size;
	
	
	public int size(){
		return size;
	}
	
	public boolean isEmpty(){
		return size==0;
	}
	
	public SxtArrayList(){
		this(10);
	}
	
	public SxtArrayList(int initialCapacity){
		if(initialCapacity<0){
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		elementData = new Object[initialCapacity];
	}
	
	public void add(Object obj){
		//�������ݺ���ݵĿ���
		if(size==elementData.length){
			Object[] newArray = new Object[size*2+1];
			System.arraycopy(elementData, 0, newArray, 0, elementData.length);
//			for(int i=0;i<elementData.length;i++){
//				newArray[i] = elementData[i];
//			}
			elementData = newArray;
		}
		
		elementData[size++]=obj;
//		size++;
	}
	
	public Object get(int index){
		rangeCheck(index);
		
		return elementData[index];
	}
	
	public void remove(int index){
		rangeCheck(index);
		//ɾ��ָ��λ�õĶ���
		//a b d e
		int numMoved = size - index - 1;
		if (numMoved > 0){
			System.arraycopy(elementData, index+1, elementData, index,
					numMoved);
		}
		elementData[--size] = null; // Let gc do its work
	}

	public void remove(Object obj){
		for(int i=0;i<size;i++){
			if(get(i).equals(obj)){  //ע�⣺�ײ���õ�equals��������==.
				remove(i);
			}
		}
	}
	
	public Object set(int index,Object obj){
		rangeCheck(index);

		Object oldValue =  elementData[index];
		elementData[index] = obj;
		return oldValue;
	}
	
	public void add(int index,Object obj){
		rangeCheck(index);
		
		ensureCapacity();  //��������
		
		System.arraycopy(elementData, index, elementData, index + 1,
				 size - index);
		elementData[index] = obj;
		size++;
	}
	
	private void ensureCapacity(){
		//�������ݺ���ݵĿ���
				if(size==elementData.length){
					Object[] newArray = new Object[size*2+1];
					System.arraycopy(elementData, 0, newArray, 0, elementData.length);
//							for(int i=0;i<elementData.length;i++){
//								newArray[i] = elementData[i];
//							}
					elementData = newArray;
				}
	}
	
	
	private void rangeCheck(int index){
		if(index<0||index>=size){
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		SxtArrayList list = new SxtArrayList(3);
		list.add("333");
		list.add("444");
		list.add("5");
		list.add("344433");
		list.add("333");
		list.add("333");
		System.out.println(list.size()); 
//		System.out.println(list.get(6));
		list.remove("444");
		System.out.println(list.size());
	}

}
