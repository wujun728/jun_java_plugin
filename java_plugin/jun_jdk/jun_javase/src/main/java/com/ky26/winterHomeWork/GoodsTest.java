package com.ky26.winterHomeWork;

public class GoodsTest {
	public static void main(String[] args) {
		Goods g1=new Goods(1,"дллг",23,"жьгЛ");
		Goods g2=new Goods(2,"дллг",24,"жьгЛ");
		Goods g3=new Goods(3,"дллг",21,"жьгЛ");
		Goods g4=new Goods(4,"дллг",29,"жьгЛ");
		Goods g5=new Goods(5,"дллг",5,"жьгЛ");
		Goods g6=new Goods(6,"дллг",3,"жьгЛ");
		Goods g7=new Goods(7,"дллг",67,"жьгЛ");
		Goods g8=new Goods(8,"дллг",23,"жьгЛ");
		Goods g9=new Goods(9,"дллг",13,"жьгЛ");
		Goods g10=new Goods(10,"дллг",88,"жьгЛ");
		Goods[] arr= {g1,g2,g3,g4,g5,g6,g7,g8,g9,g10};
		Goods.printArr(Goods.sortGoods(arr));
		
	}
}
class Goods{
	private int id;
	private String name;
	private int price;
	private String place;
	Goods(){
		
	}
	Goods(int id,String name,int price,String place){
		this.id=id;
		this.name=name;
		this.price=price;
		this.place=place;
	}
	int getPrice() {
		return this.price;
	}
	int getId() {
		return this.id;
	}
	String getName() {
		return this.name;
	}
	String getPlace() {
		return this.place;
	}
	
	public static Goods[] sortGoods(Goods[] arr) {
		for(int i=0;i<arr.length-1;i++) {
			for(int j=i+1;j<arr.length;j++) {
				if(arr[i].getPrice()>arr[j].getPrice()) {
					Goods temp=arr[i];
					arr[i]=arr[j];
					arr[j]=temp;
				}
			}                       
		}
		return arr;
	}
	
	
	static void printArr(Goods[] array) {
		System.out.print("{");
		for(int i=0;i<array.length;i++){
			if(i==array.length-1){
				System.out.println(array[i].getId()+array[i].getName()+array[i].getPrice()+array[i].getPlace()+"}");
			}
			else{
				System.out.println(array[i].getId()+array[i].getName()+array[i].getPrice()+array[i].getPlace()+",");
			}
		}
	}
}
