package com.ky26.collectionn;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class LinkedListTest2 {
	public static void main(String[] args) {
		Map map=new HashMap();
		Ticket t7=new Ticket(7,"三毛流浪记7","张九","时间哈撒互粉萨洛克静静地看撒垃圾斗卡萨");
		Ticket t8=new Ticket(8,"三毛流浪记8","张十","看到沙发看电视两家开发商龙卷风实践活动看似简单");
		Ticket t1=new Ticket(1,"三毛流浪记1","张三","IE叫我和绝对死扶二师弟快捷方式离开了我弟说佛菩萨的");
		Ticket t2=new Ticket(2,"三毛流浪记2","张四","肯定是过来看电视剧化工你倒是");
		Ticket t3=new Ticket(3,"三毛流浪记3","张五","耳朵上来看健康绿色减肥了看电视机房的私房");
		Ticket t4=new Ticket(4,"三毛流浪记4","张六","偶尔就认可了那份快乐的释放你的快乐贷款纠纷");
		Ticket t5=new Ticket(5,"三毛流浪记5","张七","搜快乐圣诞节福利看电视剧浮动额屁哦配送贷款首付四氟垫");
		Ticket t6=new Ticket(6,"三毛流浪记6","张八","可我就日饿哦就放空的说法达康书记弗兰克的说法奇偶is的辽阔的积分");
		
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
		p1.buyTicket(map,"三毛流浪记4");
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
		System.out.println("你购买的票是"+name);
		map.remove(name);
		System.out.println("已经为你出票");
	}
	
}
