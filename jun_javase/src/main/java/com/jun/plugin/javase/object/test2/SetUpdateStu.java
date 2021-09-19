package com.jun.plugin.javase.object.test2;
import java.util.Iterator;
import java.util.TreeSet;
public class SetUpdateStu implements Comparable<Object> {
	String name;
	long id;
	public SetUpdateStu(String name,long id){
		this.id=id;
		this.name=name;
	}
	public int compareTo(Object o){
		SetUpdateStu upstu=(SetUpdateStu)o;
		int result=id>upstu.id?1:(id==upstu.id?0:-1);
		return result;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public long getId(){
		return id;
	}
	public void setId(long id){
		this.id=id;
	}
	
	public static void main(String[] args) {
		SetUpdateStu stu1=new SetUpdateStu("��ͬѧ",01011);
		SetUpdateStu stu2=new SetUpdateStu("��ͬѧ",01012);
		SetUpdateStu stu3=new SetUpdateStu("��ͬѧ",01013);
		SetUpdateStu stu4=new SetUpdateStu("��ͬѧ",01014);
		TreeSet<SetUpdateStu>tree=new TreeSet<SetUpdateStu>();
		tree.add(stu1);
		tree.add(stu2);
		tree.add(stu3);
		tree.add(stu4);
		Iterator<SetUpdateStu>it=tree.iterator();
		System.out.println("�����е�����Ԫ��");
		while(it.hasNext()){
			SetUpdateStu stu=(SetUpdateStu)it.next();
			System.out.println(stu.getId()+" "+stu.getName());
		}
		it=tree.headSet(stu2).iterator();
		System.out.println("��ȡǰ�沿�ֵļ���");
		while(it.hasNext()){
			SetUpdateStu stu=(SetUpdateStu)it.next();
			System.out.println(stu.getId()+" "+stu.getName());
		}
		it=tree.subSet(stu2,stu3).iterator();
		System.out.println("��ȡ�м䲿�ֵļ���");
		while(it.hasNext()){
			SetUpdateStu stu=(SetUpdateStu)it.next();
			System.out.println(stu.getId()+" "+stu.getName());
		}
		
		/*long a=01011;
		System.out.println((int)a);*/
		
	}
	
}
