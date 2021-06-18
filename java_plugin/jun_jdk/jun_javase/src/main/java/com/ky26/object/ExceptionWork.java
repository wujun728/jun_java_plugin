package com.ky26.object;

public class ExceptionWork {
	public static void main(String[] args) {
		Computer c1=new Computer(1);
		Teacher t1=new Teacher();
		t1.lession(c1);
	}
}
class Teacher{
	void lession(Computer comp){
		try{
			comp.run();
			System.out.println("开始上课");
			System.exit(0);
		}catch(BlueScreen e){
			e.printStackTrace();
		}catch(Crashed e){
			e.printStackTrace();
		}finally{
			System.out.println("电脑坏了，上自习");
		}
	}
	
}
class Computer{
	private int condition=0;
	Computer(int condition){
		this.condition=condition;
	}
	
	void setCondition(int condition){
		this.condition=condition;
	}
	void run(){
		if(condition==0){
			throw new Crashed("电脑死机了");
		}
		if(condition==1){
			throw new BlueScreen("电脑蓝屏了");
		}
		if(condition>1){
			System.out.println("电脑正常运行");
		}
	}
}
class BlueScreen extends RuntimeException{
	BlueScreen(String msg){
		super(msg);
	}
}
class Crashed extends RuntimeException{
	Crashed(String msg){
		super(msg);
	}
}