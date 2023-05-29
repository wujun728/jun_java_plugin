package com.jun.plugin.collection;

/**
 *�Զ���ʵ��Map�Ĺ��ܣ�
 *�ݲ������� 
 *Map:��ż�ֵ�ԣ���ݼ�����Ҷ�Ӧ��ֵ����.�����ظ���
 *
 */
public class SxtMap001 {
	
	SxtEntry[]  arr  = new SxtEntry[990];
	int size;
	
	public void put(Object key,Object value){
		SxtEntry e = new SxtEntry(key,value);
		//�����ֵ�ظ��Ĵ���
		for(int i=0;i<size;i++){
			if(arr[i].key.equals(key)){
				arr[i].value=value;
				return ;
			}
		}
		
		arr[size++] = e;
	}
	
	public Object get(Object key){
		for(int i=0;i<size;i++){
			if(arr[i].key.equals(key)){
				return arr[i].value;
			}
		}
		return null;
	}
	
	public boolean containsKey(Object key){
		for(int i=0;i<size;i++){
			if(arr[i].key.equals(key)){
				return true;
			}
		}
		return false;
	}
	
	public boolean containsValue(Object value){
		for(int i=0;i<size;i++){
			if(arr[i].value.equals(value)){
				return true;
			}
		}
		return false;
	}
	
	
	
	public static void main(String[] args) {
		SxtMap001 m = new SxtMap001();
//		m.put("����", new Wife("����"));
//		m.put("����", new Wife("����"));
//		Wife w = (Wife) m.get("����");
//		System.out.println(w.name); 
	}

}

class  SxtEntry {
	Object key;
	Object value;
	
	public SxtEntry(Object key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}	
}