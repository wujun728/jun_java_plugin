package net.jueb.util4j.beta.tools.sbuffer.node;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang.math.RandomUtils;

/**
 * 向右分层(适合index逐渐增大的情况) 2个比特位=4
 * @author juebanlin
 */
public class NodeMap_first<K,V> implements RouteMap<K, V>{
	
	static final int BIT_NUMS=32;//总bit位数量
	/**
	 * 2进制的11
	 */
	static final int MASK_11=0x3;//2bit*16
	static final int MASK_1111=0xF;//4bit*8
	static final int MASK_1111_1111=0xFF;//8*4
	/**
	 * 截取掩码
	 */
	static final int mask=MASK_11;//CONFIG this
	/**
	 * 掩码占位数
	 */
	static final int bitNum;
	/**
	 * 分层(多少个掩码组合)
	 */
	static final int layout;
	/**
	 * 占位掩码的二进制容量
	 */
	static final int nodeSize;
	
	static{
		int tmp=mask;
		int num=0;
		while(tmp!=0)
		{
			tmp=tmp>>1;
			num++;
		}
		bitNum=num;
		nodeSize=2<<bitNum-1;
		layout=BIT_NUMS/bitNum;
		System.out.println("层bit位数:"+bitNum+",层容量:"+nodeSize+",层高:"+layout);
	}
	@SuppressWarnings("unchecked")
	private NodeMap_first<K,V>[] sub=new NodeMap_first[4];
	private V fruit;//果实
	
	protected V getFruit() {
		return fruit;
	}

	protected void setFruit(V fruit) {
		this.fruit = fruit;
	}

	/**
	 * 取整数某二进制位的值
	 * @param number
	 * @param pos 0开始
	 * @return
	 */
	private int getPosValue(int number,int pos,int mask)
	{
		int p=bitNum*pos;
		return (number & (mask<<p))>>>p;
	}

	private V _getByNumber(int number,int pos)
	{
		if(pos<0)
		{//超出范围
			return null;
		}
		if(pos==0)
		{
			return this.fruit;
		}
		pos--;
		int p=getPosValue(number,pos,mask);
		NodeMap_first<K,V> node=sub[p];
		if(node==null)
		{
			return null;
		}
		return node._getByNumber(number, pos);
	}
	
	private void _setByNumber(int number,int pos,V value)
	{
		if(pos<0)
		{//超出范围
			return ;
		}
		if(pos==0)
		{
			this.fruit=value;
			return ;
		}
		pos--;
		int p=getPosValue(number,pos,mask);
		NodeMap_first<K,V> node=sub[p];
		if(node==null)
		{
			node=new NodeMap_first<K,V>();
			sub[p]=node;
		}
		node._setByNumber(number, pos,value);
	}
	
	protected V getByNumber(int number)
	{
		return _getByNumber(number,layout);
	}

	protected void setByNumber(int number,V value)
	{
		_setByNumber(number,layout,value);
	}
	
	static final int hash(Object key) {
	    int h;
	    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	@Override
	public V put(K key, V value) {
		setByNumber(hash(key), value);
		return value;
	}


	@Override
	public V get(K key) {
		return getByNumber(hash(key));
	}

	public static class Test{
		
		public void test()
		{
			NodeMap_first<Integer,Byte> nmap=new NodeMap_first<>();
			int key=131072;
			byte v=123;
			nmap.put(key, v);
			Byte v2=nmap.get(key);
			System.out.println(v2);
		}
		
		public void testMap(byte[] data)
		{
			//map读写测试
			Map<Integer,Byte> map=new HashMap<>();
			long t=System.currentTimeMillis();
			for(int i=0;i<data.length;i++)
			{
				map.put(i,data[i]);
			}
			long m1=System.currentTimeMillis()-t;
			t=System.currentTimeMillis();
			for(int i=0;i<data.length;i++)
			{
				map.get(i);
			}
			long m2=System.currentTimeMillis()-t;
			System.out.println("map写:"+m1+",map读:"+m2);
		}
		
		public void testNMap(byte[] data)
		{
			//nmap读写测试
			NodeMap_first<Integer,Byte> nmap=new NodeMap_first<>();
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
		byte[] data=new byte[1024*1024*10];
		for(int i=0;i<data.length;i++)
		{
			data[i]=(byte) RandomUtils.nextInt(255);
		}
		System.out.println(data.length);
		System.out.println(Integer.toHexString(data.length));
		Scanner sc=new Scanner(System.in);
//		sc.nextLine();
//		new Test().testMap(data);
		sc.nextLine();
		new Test().testNMap(data);
		sc.nextLine();
	}
}
