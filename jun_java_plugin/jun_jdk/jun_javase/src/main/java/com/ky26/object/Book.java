package com.ky26.object;

import java.util.Arrays;
import java.util.Scanner;
public class Book{
	public static void main(String[] args) {
		Book1 b1=new Book1(1,"java入门到精通",23,"张三","清华大学出版社","第8版",true,"萨克和发动机上的记录卡萨刷卡建档立卡撒大家埃里克森");
		Book1 b2=new Book1(2,"php入门到精通",24.99,"张四","清华大学出版社","第7版",false,"时快捷回复ISO基地哦啊四季豆is嗷嗷");
		Book1 b3=new Book1(3,"c语言入门到精通",25.99,"张五","清华大学出版社","第6版",true,"是，里的数据分类考试的距离考试都是");
		Book1 b4=new Book1(4,"c++入门到精通",26.99,"张六","清华大学出版社","第5版",false,"速溶IE积分迪欧十几次十几次输出没看到");
		Book1 b5=new Book1(5,"python入门到精通",27.99,"张七","清华大学出版社","第4版",true,"网络贷款投票而我看见偶尔卡佛都说");
		Book1 b6=new Book1(6,"html5入门到精通",28,"张八","清华大学出版社","第3版",true,"辽阔的塑胶题库软件提偶恶如杰立即付款了多少积");
		Book1 b7=new Book1(7,"css3入门到精通",29.99,"张九","清华大学出版社","第2版",false,"我IE噢我就饿得看见他搜而哦ID送人欧赔");
		Book1 b8=new Book1(8,"javascript入门到精通",23,"张十","清华大学出版社","第1版",true,"楼上分开了圣诞节疯狂了的实际付款了的世界上");
		
		//System.out.println(b7.getAll());
		//System.out.println(Arrays.toString(bookArr));
		
		Book1[] bookArr={b1,b2,b3,b4,b5,b6,b7,b8};
		Scanner scan=new Scanner(System.in);
		System.out.println("请输入图书名称检索：");
		String keyName=scan.next();
		keySearch(bookArr,keyName);
	}
	
	static void keySearch(Book1[]bookArr,String key){
		boolean find=true;
		for(int i=0;i<bookArr.length;i++){
			if(bookArr[i].getName().contains(key)){
				System.out.println(bookArr[i]);
				find=false;
			}
			else{
				continue;
			}
		}
		if(find==true){
			System.out.println("无结果");
		}
	}
}

class Book1 {
	private int id;
	private String name;
	private double price;
	private String author;
	private String publish;
	private String edition;
	private boolean sell;
	private String intro;
	
	public Book1(){
		
	}
	public Book1(int id){
		this.id=id;
	}
	public Book1(String name){
		this.name=name;
	}
	public Book1(int id,String name){
		this.id=id;
		this.name=name;
	}
	public Book1(int id,String name,double price){
		this(id,name);
		this.price=price;
	}
	public Book1(int id,String name,double price,String author,String publish,String edition,boolean sell,String intro){
		this(id,name,price);
		this.author=author;
		this.publish=publish;
		this.edition=edition;
		this.sell=sell;
		this.intro=intro;
	}
	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setPrice(Double price){
		this.price=price;
	}
	public void setPrice(int price){
		this.price=price;
	}
	public Double getPrice(){
		return price;
	}
	public void setAuthor(String author){
		this.author=author;
	}
	public String getAuthor(){
		return author;
	}
	public void setPublish(String publish){
		this.publish=publish;
	}
	public String getPublish(){
		return publish;
	}
	public void setEdition(String edition){
		this.edition=edition;
	}
	public String getEdition(){
		return edition;
	}
	public void setSell(boolean sell){
		this.sell=sell;
	}
	public boolean getSell(){
		return sell;
	}
	public void setIntro(String intro){
		this.intro=intro;
	}
	public String getIntro(){
		return intro;
	}
	public void setAll(int id,String name,double price,String author,String publish,String edition,boolean sell,String intro){
		this.id=id;
		this.name=name;
		this.price=price;
		this.author=author;
		this.publish=publish;
		this.edition=edition;
		this.sell=sell;
		this.intro=intro;
	}
	public String getAll(){
		String isSell=this.getSell1();
		return "{图书编号=" + id + ", 图书名称=" + name +", 图书价格=" + price + ", 作者=" + author +", 出版社=" + publish +", 版本号=" + edition +", 是否畅销=" + isSell +", 图书简介=" + intro +"}"+"\n";
	}
	
	public String getSell1(){
		if(this.sell){
			return "畅销书";
		}
		else{
			return "非畅销书";
		}
	}
	public String toString() {
		String isSell=this.getSell1();
		return "{图书编号=" + id + ", 图书名称=" + name +", 图书价格=" + price + ", 作者=" + author +", 出版社=" + publish +", 版本号=" + edition +", 是否畅销=" + isSell +", 图书简介=" + intro +"}"+"\n";
	}	
}
