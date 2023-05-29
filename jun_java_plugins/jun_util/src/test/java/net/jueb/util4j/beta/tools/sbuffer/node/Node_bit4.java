package net.jueb.util4j.beta.tools.sbuffer.node;

import java.util.Scanner;

import org.apache.commons.lang.math.RandomUtils;

/**
 * 向右分层(适合index逐渐增大的情况) 4个bit位=16
 * @author juebanlin
 */
public class Node_bit4 {
	private Node_bit4[] sub=new Node_bit4[16];
	private Byte attach;//附件
	private static final int maxPos=8;//8*8位
	
	private Byte _get(int index,int pos)
	{
		if(pos<0)
		{//超出范围
			return null;
		}
		if(pos==0)
		{
			return this.attach;
		}
		pos--;
		int p=getPosValue(index,pos);
		Node_bit4 node=sub[p];
		if(node==null)
		{
			return null;
		}
		return node._get(index, pos);
	}
	
	public Byte get(int index)
	{
		return _get(index,maxPos);
	}
	
	public static int num;
	
	private void _set(int index,int pos,Byte value)
	{
		if(pos<0)
		{//超出范围
			return ;
		}
		if(pos==0)
		{
			if(this.attach!=null)
			{
				System.out.println("");
			}
			this.attach=value;
			return ;
		}
		pos--;
		int p=getPosValue(index,pos);
		Node_bit4 node=sub[p];
		if(node==null)
		{
			node=new Node_bit4();
			sub[p]=node;
		}
		node._set(index, pos,value);
	}
	
	public void set(int index,Byte value)
	{
		_set(index,maxPos,value);
	}
	
	public void put(int index,Byte value)
	{
		set(index,value);
	}
	
	/**
	 * 取整数某二进制位的值
	 * @param number
	 * @param pos 0开始
	 * @return
	 */
	public static int getPosValue(int number,int layout)
	{
		int pos=4*layout;//0xF 是4长度bit位
		int posValue=(number & (0xF<<pos))>>>pos;
		return posValue;
	}
	
	
	public static class Test{
		
		public void test0()
		{
			String a = "10010101";  
		    int d = Integer.parseInt(a, 2); // 2进制  
		    System.out.println(d);  
		    for(int i=0;i<32;i++)
		    {
		    	System.out.println((d & (1<<i))>>i);
		    }
		}
		
		public void test1()
		{
			Node_bit4 node=new Node_bit4();
			int key=1001;
		    Byte value=123;
		    node.set(key,value);
		    Byte value2=node.get(key);
		    System.out.println(value2);  	
		}
		public void test2()
		{
			Node_bit4 node=new Node_bit4();
			int num=100000;
			long t=System.currentTimeMillis();
		    for(int i=0;i<num;i++)
		    {
		    	int key=i;
		    	Byte v=(byte) key;
		    	node.set(key,v);
		    }	
		    long t1=System.currentTimeMillis()-t;
		    t=System.currentTimeMillis();
		    int error=0;
		    for(int i=0;i<num;i++)
		    {
		    	int key=i;
		    	byte value=node.get(key);
		    	if(value!=(byte)key)
		    	{
		    		error++;
		    	}
		    }
		    long t2=System.currentTimeMillis()-t;
		    System.out.println("set:"+t1+",get:"+t2+",error="+error);
		}
		public void testNMap(byte[] data)
		{
			//nmap读写测试
			Node_bit4 nmap=new Node_bit4();
			long t=System.currentTimeMillis();
			for(int i=0;i<data.length;i++)
			{
				nmap.put(i,data[i]);
			}
			long n1=System.currentTimeMillis()-t;
			t=System.currentTimeMillis();
			int error=0;
			for(int i=0;i<data.length;i++)
			{
				byte value=nmap.get(i);
				if(value!=data[i])
				{
					error++;
				}
			}
			long n2=System.currentTimeMillis()-t;
			t=System.currentTimeMillis();
			System.out.println("nmap写:"+n1+",nmap读:"+n2+",error:"+error);
		}
	}
	
	public static void main(String[] args) {
		byte[] data=new byte[131075];
		for(int i=0;i<data.length;i++)
		{
			data[i]=(byte) RandomUtils.nextInt(125);
		}
		Scanner sc=new Scanner(System.in);
//		sc.nextLine();
//		new Test().testMap(data);
		sc.nextLine();
		new Test().testNMap(data);
		sc.nextLine();
	}
}
