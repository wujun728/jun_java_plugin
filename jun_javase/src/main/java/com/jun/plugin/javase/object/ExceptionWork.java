package com.jun.plugin.javase.object;

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
			System.out.println("��ʼ�Ͽ�");
			System.exit(0);
		}catch(BlueScreen e){
			e.printStackTrace();
		}catch(Crashed e){
			e.printStackTrace();
		}finally{
			System.out.println("���Ի��ˣ�����ϰ");
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
			throw new Crashed("����������");
		}
		if(condition==1){
			throw new BlueScreen("����������");
		}
		if(condition>1){
			System.out.println("������������");
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