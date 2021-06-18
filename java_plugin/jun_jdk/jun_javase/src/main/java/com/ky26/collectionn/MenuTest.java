package com.ky26.collectionn;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MenuTest {
	public static void main(String[] args) {
		Map map=new TreeMap();
		for(int i=0;i<20;i++){
			Menu mapp=new Menu(i, "Î÷ºìÊÁ³´¼¦µ°"+i, i+9.9);
			map.put(mapp.getId(), (mapp.getName()+"  ¼Û¸ñ£º"+mapp.getPrice()));
//			System.out.println(mapp.getId()+"-----"+mapp.getName()+"-----"+mapp.getPrice());
		}
		/*Iterator iter=map.keySet().iterator();
		while(iter.hasNext()){
			Object id=iter.next();
			Object name=map.get(id);
			System.out.println(id+"------"+name);
		}*/
		
		Person1 p1=new Person1(); 
		p1.order(map,7);
	}
}
class Menu{
	private int id;
	private String name;
	private double price;
	Menu(){
		
	}
	Menu( int id,String name,double price){
		this.id=id;
		this.name=name;
		this.price=price;
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
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}



