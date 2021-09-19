package com.jun.plugin.javase.object.test;


public class Test {
	public static void main(String[] args) {
		/*Person p1=new Person("����",21);
		p1.show();
		Worker w1=new Worker("����",20,9999);
		w1.show();
		Student s1=new Student("����",14,1406101010);
		s1.show();
		Coder c1=new Coder("Ǯ��",27,"java");
		c1.show();*/
//		Person p1=null;
		/*Person[] arr2=new Person[10];
		for(int i=0;i<10;i++){
			Person p1=new Person("����"+(i+1)+"��",21+i);
			arr2[i]=p1;
		}
		for(int i=0;i<arr2.length;i++){
			arr2[i].show();
		}*/
		
		/*Milk m1=new Milk("���ζ","�б�",10);
		m1.showName();
		m1.showProperty();
		Coffee c1=new Coffee("��ʽ","��",40);
		c1.showName();
		c1.showProperty();
		Tea t1=new Tea("�̲�","С��",20);
		t1.showName();
		t1.showProperty();*/
		
		/*Triangle t1=new Triangle(6,8,10);
		if(t1.isTriangle()) {
			t1.setColor("��ɫ");
			t1.getShap();
			System.out.println("�ܳ���:"+t1.getPerimeter());
		}
		else {
			System.out.println("���߹�����������");
		}
		System.out.println("==============");
		Intrtface2 i1=new Intrtface2();
		System.out.println("�׳���:"+i1.fact(5));
		Intrtface2 i2=new Intrtface2();
		System.out.println(i2.intPower(2,3));
		Intrtface2 i3=new Intrtface2();
		System.out.println("�������100��:"+i3.findFactor(99,2));*/
		
		
		/*Student s11=new Student("����",21);
		Student s22=new Student("����",22,"Ů","��Ϣ����");
		Student s33=new Student("����",20);
		s11.introduce(s11);
		s22.introduce(s22);
		s33.introduce(s33);
		
		
		Student s1=new Student("����",21);
		Student s2=new Student("����",22);
		Student s3=new Student("����",20);
		Student s4=new Student("����",19);
		Student s5=new Student("����",23);
		Student[] stuArr={s1,s2,s3,s4,s5};
		
		for(int i=0;i<stuArr.length;i++){
			for(int j=i+1;j<stuArr.length;j++){
				if(stuArr[i].getAge()>stuArr[j].getAge()){
					Student temp=stuArr[i];
					stuArr[i]=stuArr[j];
					stuArr[j]=temp;
				}
			}
			System.out.println(stuArr[i].toString());
		}*/
		
		/*int[] arr={1,3,2,5,2,7,1,8};
		System.out.println(getElement(arr,7));*/
		
		int[] arr2={1,2,3,4,5,6,1};
		System.out.println(getElement(arr2,2));
		
	}
	
	static int getElement(int[] arr,int index){
		if(arr==null){
			throw new NullPointerException("����Ϊ��");
		}
		if(index>=arr.length){
			throw new ArrayIndexOutOfBoundsException("�����±�Խ��"+index);
		}
		return arr[index];
	}
}
