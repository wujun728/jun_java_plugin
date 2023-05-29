package net.jueb.util4j.beta.bandConversion;
import java.math.BigDecimal;
import java.util.Vector;

import net.jueb.util4j.beta.bandConversion.ClockDial;
import net.jueb.util4j.beta.bandConversion.Numeral;
import net.jueb.util4j.beta.bandConversion.Rotor;




public class Test {

	public static void main(String[] args) {
		Vector<Numeral> Numeral=new Vector<Numeral>();
		for(int i=0;i<60;i++)
		{
			Numeral rd=new Numeral(new byte[]{(byte)i},Character.toString((char) ('A'+i)));
			Numeral.add(rd);
		}
		Rotor rt=new Rotor(Numeral);
		System.out.println("符号集合:\n"+rt.toString());
		
		final ClockDial cd=new ClockDial(rt, 10);
		System.out.println("当前表盘读数:"+cd.getViewStrs());
		System.out.println("当前表盘10进制数值:"+cd.getValue().toString());
		
		long a1=921945165l;
		System.out.println("设置表盘数值为:"+a1);
		BigDecimal out=cd.setValue(new BigDecimal(a1));
		System.out.println("当前表盘读数:"+cd.getViewStrs());
		System.out.println("当前表盘10进制数值:"+cd.getValue().toString());
		System.out.println("溢出数值："+out.toString());
		
		long a2=1;
		System.out.println("表盘数值加:"+a2);
		out=cd.add(new BigDecimal(a2));
		System.out.println("当前表盘读数:"+cd.getViewStrs());
		System.out.println("当前表盘10进制数值:"+cd.getValue().toString());
		System.out.println("溢出数值："+out.toString());
		
		
	}
}
