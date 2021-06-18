package com.ky26.object.test2;

public class ExceptionTest1 {
	public static void main(String[] args) {
		/*Son s1=new Son();
		s1.show();*/
		
		int num=show();
		System.out.println(num);
		
	}
	static int show(){
		int aa=0;
		try{
			System.out.println("show run ");
			if(1==1)
				throw new RuntimeException("AA");
			aa=10;
			return aa;
		}catch(RuntimeException e){
			System.out.println(e.toString()+"catch code");
			aa=20;
			return aa;	
		}finally{
			System.out.println("finally---"+aa);
			aa=30;
			System.out.println("finally+++"+aa);
//		    throw new RuntimeException("finally code");
//		    System.exit(0);
		}
	}
}

class A extends Exception{
	
}
class B extends RuntimeException{
	
}

class Father{
	void show() throws A{
		
	}
}

class Son extends Father{
	void show() throws RuntimeException{
		System.out.println(1);
	}
}


