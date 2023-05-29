package net.jueb.util4j.beta.bandConversion;
import java.util.List;

/**
 * 符号转盘
 * @author Administrator
 *
 */
public class Rotor{
	
	/**
	 * 符号集合
	 */
	private final List<Numeral> numerals;
	
	private final int radix;
	
	/**
	 * 当前指向符号索引
	 */
	private volatile int currenIndex;
	private volatile int maxIndex;
	
	
	public Rotor(List<Numeral> numerals) {
		if(numerals.size()<=1)
		{
			throw new RuntimeException("基数转盘至少有2个符号");
		}else
		{
			this.numerals=numerals;
			this.maxIndex=this.numerals.size()-1;
			this.currenIndex=0;
			this.radix=this.numerals.size();
		}
	}	
	/**
	 * 获取当前转盘指向的符号索引
	 * @return
	 */
	public synchronized int getCurrentIndex()
	{
		return this.currenIndex;
	}
	
	public synchronized void setCurrenIndex(int index)
	{
		this.currenIndex=index;
	}
	
	public synchronized Numeral getCurrentNumeral()
	{
		return this.numerals.get(currenIndex);
	}
	
	public synchronized int getRadix() {
		return this.radix;
	}
	
	public synchronized int getMaxIndex() {
		return this.maxIndex;
	}

	public synchronized String getCurrentViewStr()
	{
		return this.numerals.get(currenIndex).getViewStr();
	}	

	public synchronized List<Numeral> getNumerals() {
		return numerals;
	}
	
	public synchronized boolean add()
	{
		if(this.currenIndex<maxIndex)
		{
			this.currenIndex++;
			return false;
		}else
		{
			this.currenIndex=0;
			return true;
		}
	}

	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<this.numerals.size();i++)
		{
			sb.append("["+this.numerals.get(i).getViewStr()+"]");
		}
		return sb.toString();
	}
}
