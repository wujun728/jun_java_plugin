package com.jun.plugin.javase.collectionn;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class LinkedListTest2 {
	public static void main(String[] args) {
		Map map=new HashMap();
		Ticket t7=new Ticket(7,"��ë���˼�7","�ž�","ʱ�������������˾����ؿ�������������");
		Ticket t8=new Ticket(8,"��ë���˼�8","��ʮ","����ɳ�����������ҿ����������ʵ������Ƽ�");
		Ticket t1=new Ticket(1,"��ë���˼�1","����","IE���Һ;���������ʦ�ܿ�ݷ�ʽ�뿪���ҵ�˵��������");
		Ticket t2=new Ticket(2,"��ë���˼�2","����","�϶��ǹ��������Ӿ绯���㵹��");
		Ticket t3=new Ticket(3,"��ë���˼�3","����","����������������ɫ�����˿����ӻ�����˽��");
		Ticket t4=new Ticket(4,"��ë���˼�4","����","ż�����Ͽ����Ƿݿ��ֵ��ͷ���Ŀ��ִ������");
		Ticket t5=new Ticket(5,"��ë���˼�5","����","�ѿ���ʥ���ڸ��������Ӿ縡����ƨŶ���ʹ����׸��ķ���");
		Ticket t6=new Ticket(6,"��ë���˼�6","�Ű�","���Ҿ��ն�Ŷ�ͷſյ�˵���￵��Ǹ����˵�˵����żis�������Ļ���");
		
		map.put(t1.getName(), t1.getIntroduce());
		map.put(t2.getName(), t2.getIntroduce());
		map.put(t3.getName(), t3.getIntroduce());
		map.put(t4.getName(), t4.getIntroduce());
		map.put(t5.getName(), t5.getIntroduce());
		map.put(t6.getName(), t6.getIntroduce());
		map.put(t7.getName(), t7.getIntroduce());
		map.put(t8.getName(), t8.getIntroduce());
		
		TreeMap treemap=new TreeMap();
		treemap.putAll(map);
		Iterator it=treemap.keySet().iterator();
		while(it.hasNext()){
			String str=(String)it.next();
			String intr=(String)treemap.get(str);
			System.out.println(str+"---"+intr);
		}
		
		Person p1=new Person();
		p1.buyTicket(map,"��ë���˼�4");
		System.out.println(map.keySet());
	}
}
class Person{
	public Person(){
		
	}
	public void buyTicket(Map map,String name){
		Ticket.sell(map, name);
	}
}
class Ticket{
	private int id;
	private String name;
	private String director;
	private String introduce;
	public Ticket(){
		
	}
	public Ticket(int id, String name, String director, String introduce) {
		super();
		this.id = id;
		this.name = name;
		this.director = director;
		this.introduce = introduce;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public static void sell(Map map,String name){
		System.out.println("�㹺���Ʊ��"+name);
		map.remove(name);
		System.out.println("�Ѿ�Ϊ���Ʊ");
	}
	
}
