package com.jun.plugin.javase.winterHomeWork.Exercise8;

public class RiverTest {
	public static void main(String[] args) {
		Changjiang c1=new Changjiang();
		c1.setWarning(100);
		c1.setWaterline(110);
		/*try {
			c1.flow();
		} catch (ChangjiangException e) {
			e.printStackTrace();
		}*/
		Huanghe h1=new Huanghe();
		h1.setWarning(100);
		h1.setWaterline(110);
		/*try {
			h1.flow();
		} catch (HuangheException e) {
			e.printStackTrace();
		}*/
		
		Admin a1=new Admin();
		a1.watch(h1);
		a1.watch(c1);
	}
}
abstract class River {
	static int warning;
	static int waterline;
	
	public static int getWarning() {
		return warning;
	}
	public static void setWarning(int warning) {
		River.warning = warning;
	}
	public static int getWaterline() {
		return waterline;
	}
	public static void setWaterline(int waterline) {
		River.waterline = waterline;
	}
	public abstract void flow() throws HuangheException,ChangjiangException;
}
class Changjiang extends River{
	public void flow() throws ChangjiangException{
		if(getWaterline()>getWarning()+9){
			throw new ChangjiangException("����������");
		}
		else {
			System.out.println("�����������·�ԶӰ�̿վ���Ω�����������");
		}
	}
}
class Huanghe extends River{
	public void flow() throws HuangheException{
		if(getWaterline()>getWarning()+5){
			throw new HuangheException("�ƺӾ�����");
		}
		else {
			System.out.println("�ƺ��������ƺ�֮ˮ������");
		}
	}
}
class Admin{
	void watch(River river) {
		try {
			river.flow();
			System.out.println("ˮλ����");
		}catch(ChangjiangException e) {
			e.printStackTrace();
			System.out.println("����������");
			drain();
		}catch(HuangheException e) {
			e.printStackTrace();
			System.out.println("�ƺӾ�����");
			drain();
		}
	}
	void drain() {
		System.out.println("���ˮ");
	}
}
class HuangheException extends Exception{
	HuangheException(String mes){
		super(mes);
	}
}
class ChangjiangException extends Exception{
	ChangjiangException(String mes){
		super(mes);
	}
}
