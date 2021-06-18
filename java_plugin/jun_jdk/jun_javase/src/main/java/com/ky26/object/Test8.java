package com.ky26.object;

public class Test8 {
	
	public void test(Bird11 bird){
		System.out.println(bird.getName()+"ÄÜ·É"+bird.fly()+"Ã×");
	}
	
	
	public static void main(String[] args) {
		Test8 t=new Test8();
		t.test(new Bird11(){
			public int fly(){
				return 1000;
			}
			public String getName(){
				return "ÂéÈ¸";
			}
		});
		/*Demo33 d=new Demo33();
		System.out.println(d.method());*/
		
		/*new Demo44(){
			public void show(){
				System.out.println("33");
			}
			public void show1(){
				System.out.println("44");
			}
			public void show3(){
				System.out.println("55");
			}
		}.show3();*/
		
		
	}
}

abstract class Bird11{
	String name;
	void setName(String name){
		this.name=name;
	}
	String getName(){
		return name;
	}
	abstract int fly();
}

interface Demo44{
	void show();
	void show1();
}
class Demo33{
	Demo44 method(){
		return new Demo44(){
			public void show(){
				System.out.println("11");
			}
			public void show1(){
				System.out.println("22");
			}
		};
	}
}


