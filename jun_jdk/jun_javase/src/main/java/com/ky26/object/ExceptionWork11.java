package com.ky26.object;

public class ExceptionWork11 {
	public static void main(String[] args) {
		Teacher1 t1=new Teacher1("张三");
		try{
			t1.prelect();
		}catch(NoPlanException2 e){
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
}

class Teacher1{
	private String name;
	private Computer1 comp;
	Teacher1(String name){
		this.name=name;
		comp=new Computer1();
	}
	public void prelect()throws NoPlanException2{
		try{
			comp.run();
			System.out.println(name+"在上课");
		}catch(LanPingException2 e){
			System.out.println(e.toString());
			e.printStackTrace();
			comp.reset();
			prelect();
		}catch(MaoYanException2 e){
			e.printStackTrace();
			test();
			throw new NoPlanException2("课程无法继续");
		}
	}
	public void test(){
		System.out.println("上自习");
	}
}
class Computer1{
	private int state;
	Computer1(){
		
	}
	public void run() throws LanPingException2,MaoYanException2{
		state=(int)(Math.random()*3+1);
		if(state==1)
			throw new LanPingException2("蓝屏了");
		if(state==2)
			throw new MaoYanException2("冒烟了");
		System.out.println("正常运行");
	}
	public void reset(){
		state=0;
		System.out.println("电脑重启");
	}
}
class LanPingException2 extends Exception{
	LanPingException2(String msg){
		super(msg);
	}
}
class MaoYanException2 extends Exception{
	MaoYanException2(String msg){
		super(msg);
	}
}
class NoPlanException2 extends Exception{
	NoPlanException2(String msg){
		super(msg);
	}
}
