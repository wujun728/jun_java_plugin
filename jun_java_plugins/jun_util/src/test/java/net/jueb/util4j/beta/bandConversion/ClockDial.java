package net.jueb.util4j.beta.bandConversion;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

/**
 * 带有若干个符号转盘的表盘，用于表示数值
 * @author Administrator
 */
public class ClockDial {
	
	/**
	 * 符号转盘
	 */
	private final List<Rotor> rotors;
	
	/**
	 *所有转盘使用的相同的符号集合
	 */
	private final List<Numeral> numerals;
	
	/**
	 * 表盘的进制数，即符合集合的大小
	 */
	private final int radix;
	/**
	 * 表盘表示的位数，即符合转盘的个数
	 */
	private final int digit;
	
	/**
	 * @param rotor 转盘
	 * @param digit 转盘个数
	 */
	public ClockDial(Rotor rotor,int digit) {
		this(rotor.getNumerals(), digit);
	}
	
	
	/**
	 * @param numerals 符号集合
	 * @param digit 表盘数值位数
	 */
	public ClockDial(List<Numeral> numerals,int digit) {
		if(digit<=0)
		{
			throw new RuntimeException("至少有一位转盘");
		}else if(numerals!=null && numerals.size()<=1)
		{
			throw new RuntimeException("至少是2进制");
		}else
		{
			this.numerals=numerals;
			this.radix=numerals.size();
			this.digit=digit;
			this.rotors=new Vector<Rotor>();
			for(int i=0;i<digit;i++)
			{
				this.rotors.add(new Rotor(numerals));
			}
		}
	}
	
	
	/**
	 * 给表盘所示数值+1
	 */
	public synchronized boolean add()
	{
		synchronized (rotors) {
			for(int i=0;i<rotors.size();i++)
			{
				Rotor r=rotors.get(i);
				boolean addNext=r.add();
				if(addNext)
				{
					if(i==rotors.size())
					{
						return true;
					}
					continue;
				}else
				{
					break;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 给表盘加上一点数值
	 * @param value 加上去的值，该值可以为正负数
	 * @return 返回可能溢出的数值
	 */
	public synchronized BigDecimal add(BigDecimal value)
	{
		BigDecimal result=getValue().add(value);
		return this.setValue(result);
	}
	
	public List<Rotor> getRotors() {
		return rotors;
	}


	public List<Numeral> getNumerals() {
		return numerals;
	}


	public int getRadix() {
		return radix;
	}


	public int getDigit() {
		return digit;
	}


	/**
	 * 获取表盘所示字符串形式
	 * @return
	 */
	public String getViewStrs()
	{
		StringBuffer sb=new StringBuffer();
		for(int i=rotors.size()-1;i>=0;i--)
		{
			sb.append("["+rotors.get(i).getCurrentViewStr()+"]");
		}
		return sb.toString();
	}
	
	/**
	 *获取表盘表示的十进制数值
	 * @return
	 */
	public synchronized BigDecimal getValue()
	{
		BigDecimal value=new BigDecimal(0);
		for(int i=0;i<rotors.size();i++)
		{
			int v=rotors.get(i).getCurrentIndex();
			int m=i;
			BigDecimal vb=new BigDecimal(v);
			value=value.add(vb.multiply(new BigDecimal(radix).pow(m)));
		}
		return value;
	}
	
	/**
	 * 设置表盘数值
	 * @param value
	 * @return 返回溢出的值
	 */
	public synchronized BigDecimal setValue(BigDecimal value)
	{
		BigDecimal in=value.abs();
		BigDecimal br=new BigDecimal(radix);
		for(int i=rotors.size()-1;i>=0;i--)
		{
			BigDecimal v=new BigDecimal(radix).pow(i);
			BigDecimal count=in.divide(v,3);
			Rotor rotor=rotors.get(i);
			if(count.compareTo(new BigDecimal(0))==0)
			{
				rotor.setCurrenIndex(0);
				in=in.remainder(v);
			}else if(count.compareTo(new BigDecimal(0))>0 && count.compareTo(br)<0)
			{
				rotor.setCurrenIndex(count.intValue());
				in=in.subtract(v.multiply(count));
			}else if(count.compareTo(br)>=0)
			{
				rotor.setCurrenIndex(rotor.getMaxIndex());
				in=in.remainder(v);
				in=in.add(v.multiply(count.subtract(br).add(new BigDecimal(1))));
			}
		}
		return in;
	}
}
