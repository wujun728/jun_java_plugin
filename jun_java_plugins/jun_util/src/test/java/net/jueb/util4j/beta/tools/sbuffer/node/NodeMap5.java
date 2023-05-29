package net.jueb.util4j.beta.tools.sbuffer.node;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.apache.commons.lang.math.RandomUtils;

/**
 * 优化节点非必要属性的内存占用
 * @author juebanlin
 */
public class NodeMap5<K,V> implements RouteMap<K, V>{
	
	private static final int BIT_NUMS=32;//总bit位数量
	private final MapConfig config;
	private final LayOutNode<V> node;
	final boolean byLeft;//从左边开始
	private final int[] posCache;
	
	public NodeMap5() {
		this(MaskEnum.MASK_1111, false);
	}
	
	public NodeMap5(MaskEnum mask,boolean byLeft) {
		this.byLeft=byLeft;
		int tmp=mask.getValue();
		int num=0;
		while(tmp!=0)
		{
			tmp=tmp>>>1;
			num++;
		}
		int maskLen=num;
		int nodeSize=2<<maskLen-1;
		int layout=BIT_NUMS/maskLen;
		posCache=new int[layout];
		for(int i=0;i<layout;i++)
		{
			posCache[i]=maskLen*i;
		}
		config=new MapConfig(mask.getValue(), maskLen, layout, nodeSize);
		node=new LayOutNode<V>();
		System.out.println(config);
	}

	public final MapConfig getConfig()
	{
		return config;
	}
	
	/**
	 * 分段掩码
	 * @author juebanlin
	 */
	public static enum MaskEnum{
		MASK_11(0x3),
		MASK_1111(0xF),
		MASK_1111_1111(0xFF),
		;
		private final int value;
		private MaskEnum(int value) {
			this.value=value;
		}
		public int getValue() {
			return value;
		}
	}

	class MapConfig{
		/**
		 * 截取掩码
		 */
		private final int mask;//CONFIG this
		/**
		 * 掩码占位数
		 */
		private final int maskLen;
		/**
		 * 分层(多少个掩码组合)
		 */
		private final int layout;
		/**
		 * 占位掩码的二进制容量
		 */
		private final int nodeSize;
		
		public MapConfig(int mask, int maskLen, int layout, int nodeSize) {
			super();
			this.mask = mask;
			this.maskLen = maskLen;
			this.layout = layout;
			this.nodeSize = nodeSize;
		}
	
		public int getMask() {
			return mask;
		}
	
		public int getMaskLen() {
			return maskLen;
		}
	
		public int getLayout() {
			return layout;
		}
	
		public int getNodeSize() {
			return nodeSize;
		}
	
		@Override
		public String toString() {
			return "MapConfig [bitNum=" + maskLen + ", layout=" + layout + ", nodeSize=" + nodeSize + "]";
		}
	}

	private interface Node<V>{
		V getData();
		
		void setData(V data);
		
		public Node<V>[] getSub();
		
		public void setSub(Node<V> sub[]);
		
		public int getNodeSize();
		
		public V _getByNumber(int number,int layout);
		
		public V _setByNumber(int number,int layout,V value);
	}
	
	private abstract class AbstractNode<T extends V> implements Node<T>
	{
		
		@Override
		public T _getByNumber(int number,int layout)
		{
			if(layout<0)
			{//超出范围
				return null;
			}
			if(layout==0)
			{
				return getData();
			}
			Node<T>[] sub=getSub();
			if(sub==null)
			{
				return null;
			}
			layout--;
			int p=getMaskValue(number,layout);
			Node<T> node=sub[p];
			if(node==null)
			{
				return null;
			}
			return node._getByNumber(number, layout);
		}
		
		@Override
		public T _setByNumber(int number,int layout,T value)
		{
			if(layout<0)
			{//超出范围
				return null;
			}
			if(layout==0)
			{
				T old=getData();
				setData(value);
				return old;
			}
			layout--;
			int p=getMaskValue(number,layout);
			Node<T>[] sub=getSub();
			Node<T> node=sub[p];
			if(node==null)
			{
				if(layout==0)
				{//layout=0的node具有data属性
					node=new DataNode<T>();
				}else
				{
					node=new LayOutNode<T>();
				}
				sub[p]=node;
			}
			return node._setByNumber(number, layout,value);
		}

		@Override
		public int getNodeSize() {
			return config.getNodeSize();
		}
		
		@Override
		public T getData() {
			return null;
		}

		@Override
		public void setData(T data) {
		}
		
		@Override
		public Node<T>[] getSub() {
			return null;
		}
		@Override
		public void setSub(Node<T>[] sub) {
			
		}
	}
	
	/**
	 * 层节点
	 * @author juebanlin
	 * @param <T>
	 */
	private class LayOutNode<T extends V> extends AbstractNode<T>{
		
		@SuppressWarnings("unchecked")
		private Node<T>[] sub=new Node[getNodeSize()];
		
		@Override
		public Node<T>[] getSub() {
			return sub;
		}

		@Override
		public void setSub(Node<T>[] sub) {
			this.sub=sub;
		}
	}

	/**
	 * 可存储数据的节点
	 * @author juebanlin
	 * @param <T>
	 */
	private class DataNode<T extends V> extends AbstractNode<T>{
		private T data;
		@Override
		public T getData() {
			return data;
		}
		@Override
		public void setData(T data) {
			this.data=data;
		}
	}

	/**
	 * 取整数某二进制位的值
	 * @param number
	 * @param pos 0开始
	 * @return
	 */
	protected int getMaskValue(int number,int layout)
	{
//		int pos=maskLen*layout;
//		int pos=posCache[layout];
//		int mask=config.mask;
		return (number & (config.mask<<posCache[layout]))>>>posCache[layout];
	}		

	protected V getByNumber(int number)
	{
		return node._getByNumber(number,config.layout);
	}

	protected V setByNumber(int number,V value)
	{
		return node._setByNumber(number,config.layout,value);
	}
	
	static final int hash(Object key) {
	    int h;
	    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	
	@Override
	public V put(K key, V value) {
		return setByNumber(hash(key), value);
	}

	@Override
	public V get(K key) {
		return getByNumber(hash(key));
	}

	public static class Test{
		
		Map<Integer,Byte> map=new HashMap<>();
		public void testMap(byte[] data)
		{
			//map读写测试
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
		
		Map<Integer,Byte> tmap=new TreeMap<>();
		public void testtMap(byte[] data)
		{
			//map读写测试
			long t=System.currentTimeMillis();
			for(int i=0;i<data.length;i++)
			{
				tmap.put(i,data[i]);
			}
			long m1=System.currentTimeMillis()-t;
			t=System.currentTimeMillis();
			for(int i=0;i<data.length;i++)
			{
				tmap.get(i);
			}
			long m2=System.currentTimeMillis()-t;
			System.out.println("map写:"+m1+",map读:"+m2);
		}
		
		NodeMap5<Integer,Byte> nmap=new NodeMap5<>();
		public void testNMap(byte[] data)
		{
			//nmap读写测试
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
		byte[] data=new byte[1024*1024];
		for(int i=0;i<data.length;i++)
		{
			data[i]=(byte) RandomUtils.nextInt(255);
		}
		Test t=new Test();
		Scanner sc=new Scanner(System.in);
		sc.nextLine();
//		t.testtMap(data);
//		sc.nextLine();
//		t.testMap(data);
//		sc.nextLine();
		t.testNMap(data);
		sc.nextLine();
	}
}
