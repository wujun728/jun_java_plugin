package com.jun.plugin.demo;

public class TestObersver {
	public static void main(String[] args) {
		Person person = new Person();//声明被观察者
		System.err.println("pp:"+person);
		person.addPersonListener(new PersonListener() {
			public void running(PersonEvent pe) {
				System.err.println("你正在跑....."+pe.getSource());
				throw new RuntimeException("他跑了。。。");
			}
		});
		person.run();
	}
}
class Person{
	private PersonListener pl;
	public void addPersonListener(PersonListener pl){
		this.pl = pl;
	}
	public void run(){
		if(pl!=null){
			pl.running(new PersonEvent(this));
		}
		System.err.println("我正在跑步......");
	}
}
interface PersonListener{
	void running(PersonEvent pe);
}
class PersonEvent{
	private Object src;
	public PersonEvent(Object obj) {
		this.src=obj;
	}
	public Object getSource(){
		return src;
	}
}