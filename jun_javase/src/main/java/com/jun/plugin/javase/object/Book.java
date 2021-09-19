package com.jun.plugin.javase.object;

import java.util.Arrays;
import java.util.Scanner;
public class Book{
	public static void main(String[] args) {
		Book1 b1=new Book1(1,"java���ŵ���ͨ",23,"����","�廪��ѧ������","��8��",true,"���˺ͷ������ϵļ�¼����ˢ��������������Ұ����ɭ");
		Book1 b2=new Book1(2,"php���ŵ���ͨ",24.99,"����","�廪��ѧ������","��7��",false,"ʱ��ݻظ�ISO����Ŷ���ļ���is��");
		Book1 b3=new Book1(3,"c�������ŵ���ͨ",25.99,"����","�廪��ѧ������","��6��",true,"�ǣ�������ݷ��࿼�Եľ��뿼�Զ���");
		Book1 b4=new Book1(4,"c++���ŵ���ͨ",26.99,"����","�廪��ѧ������","��5��",false,"����IE���ֵ�ŷʮ����ʮ�������û����");
		Book1 b5=new Book1(5,"python���ŵ���ͨ",27.99,"����","�廪��ѧ������","��4��",true,"�������ͶƱ���ҿ���ż������˵");
		Book1 b6=new Book1(6,"html5���ŵ���ͨ",28,"�Ű�","�廪��ѧ������","��3��",true,"�������ܽ���������ż��������������˶��ٻ�");
		Book1 b7=new Book1(7,"css3���ŵ���ͨ",29.99,"�ž�","�廪��ѧ������","��2��",false,"��IE���ҾͶ��ÿ������Ѷ�ŶID����ŷ��");
		Book1 b8=new Book1(8,"javascript���ŵ���ͨ",23,"��ʮ","�廪��ѧ������","��1��",true,"¥�Ϸֿ���ʥ���ڷ���˵�ʵ�ʸ����˵�������");
		
		//System.out.println(b7.getAll());
		//System.out.println(Arrays.toString(bookArr));
		
		Book1[] bookArr={b1,b2,b3,b4,b5,b6,b7,b8};
		Scanner scan=new Scanner(System.in);
		System.out.println("������ͼ�����Ƽ�����");
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
			System.out.println("�޽��");
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
		return "{ͼ����=" + id + ", ͼ������=" + name +", ͼ��۸�=" + price + ", ����=" + author +", ������=" + publish +", �汾��=" + edition +", �Ƿ���=" + isSell +", ͼ����=" + intro +"}"+"\n";
	}
	
	public String getSell1(){
		if(this.sell){
			return "������";
		}
		else{
			return "�ǳ�����";
		}
	}
	public String toString() {
		String isSell=this.getSell1();
		return "{ͼ����=" + id + ", ͼ������=" + name +", ͼ��۸�=" + price + ", ����=" + author +", ������=" + publish +", �汾��=" + edition +", �Ƿ���=" + isSell +", ͼ����=" + intro +"}"+"\n";
	}	
}
