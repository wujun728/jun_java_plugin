package com.jun.plugin.util4j.cache.map.btree;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Stack;

/**
 * 优化节点非必要属性的内存占用
 * 分解层数越小,内存占用越低,速度越快
 * beta for NodeMap5
 * @author juebanlin
 */
public class BTree<V> implements BitTree<V>{
	
	private static final int BIT_NUMS=32;//总bit位数量
	private final MapConfig config;
	private final LayOutNode<V> root;
	private final int[] posCache;
	
	private int size;
	
	public BTree() {
		this(MaskEnum.MASK_1111_1111);
	}
	
	public BTree(MaskEnum mask) {
		int tmp=mask.getValue();
		int num=0;
		while(tmp!=0)
		{
			tmp=tmp>>>1;
			num++;
		}
		int maskLen=num;
		int nodeSize=2<<maskLen-1;//层节点数量
		int layout=BIT_NUMS/maskLen;//分解层级
		posCache=new int[layout];//层级数组
		for(int i=0;i<layout;i++)
		{
			posCache[i]=maskLen*i;
		}
		config=new MapConfig(mask.getValue(), maskLen, layout, nodeSize);
		root=new LayOutNode<V>();
		System.out.println(config);
	}

	/**
	 * 分段掩码
	 * @author juebanlin
	 */
	public static enum MaskEnum{
		MASK_1(0x1),
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

	interface Node<V>{
		V getData();
		
		void setData(V data);
		
		public Node<V>[] getSub();
		
		public void setSub(Node<V> sub[]);
		
		public int getNodeSize();
		
		public V _getByNumber(int number,int layout);
		
		public V _setByNumber(int number,int layout,V value);
	}
	
	abstract class AbstractNode<T extends V> implements Node<T>
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
	class LayOutNode<T extends V> extends AbstractNode<T>{
		
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
	class DataNode<T extends V> extends AbstractNode<T>{
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
		return root._getByNumber(number,config.layout);
	}

	protected V setByNumber(int number,V value)
	{
		return root._setByNumber(number,config.layout,value);
	}

	protected final LayOutNode<V> getRootNode() {
		return root;
	}

	protected final int[] getPosCache() {
		return posCache;
	}

	protected final MapConfig getConfig()
	{
		return config;
	}

	@Override
	public V write(int key, V value) {
		size++;
		return setByNumber(key, value);
	}

	@Override
	public V read(int key) {
		return getByNumber(key);
	}
	
	@SuppressWarnings("unchecked")
	public final void clear()
	{
		getRootNode().setSub(new Node[getConfig().getNodeSize()]);
		size=0;
	}
	
	@Override
	public void forEach(BitConsumer<V> consumer) {
		forEach(root, config.layout,0,consumer);
	}
	
	/**
	 * 循环搜索路径上存储的k-v
	 * @param currentNode
	 * @param layout
	 * @param number 逆向出来的数值key
	 */
	protected final void forEach(Node<V> currentNode,int layout,int number,BitConsumer<V> consumer)
	{
		if(layout==0)
		{
			consumer.accept(number, currentNode.getData());
			return ;
		}
		Node<V>[] sub=currentNode.getSub();
		for(int i=0;i<sub.length;i++)
		{
			Node<V> node=sub[i];
			if(node!=null)
			{
				layout--;
				int num=number+i&getConfig().mask<<layout;
				forEach(node, layout, num,consumer);
				layout++;
			}
		}
	}
	
	public Iterator<Entry<Integer,V>> iterator(){
		return new NodeIteratorBeta();
	}
	
	public int size()
	{
		return size;
	}
	
	class DataEntry implements Entry<Integer,V>{
		private final int key;
		private final V value;
		public DataEntry(int key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public Integer getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			return setByNumber(key, value);
		}
	}
	
	/**
	 * 迭代器
	 * @author juebanlin
	 */
	class NodeIteratorBeta implements Iterator<Entry<Integer,V>> {
		final Node<V>[] rootNodeVersion=getRootNode().getSub();
		Entry<Integer,V> next=null;
		int next_number;
		class StackContext {
			Node<V> currentNode;
			int layout;
			int number;
			public StackContext(Node<V> currentNode ,int layout, int number) {
				super();
				this.currentNode = currentNode;
				this.layout = layout;
				this.number = number;
			}
		}
		//用栈保存递归搜索上下文
		final Stack<StackContext> stack=new Stack<>();
		public NodeIteratorBeta() {
			stack.push(new StackContext(root, config.layout,0));
		}
		@Override
		public boolean hasNext() {
			if(getRootNode().getSub()==rootNodeVersion)
			{
				if(next==null)
				{
					if(!stack.isEmpty())
					{
						while(!stack.isEmpty())
						{
							StackContext ctx=stack.pop();
							if(ctx.layout==0)
							{//找到值
								accept(ctx.number,ctx.currentNode.getData());
								break;
							}
							Node<V>[] sub=ctx.currentNode.getSub();
							for(int i=0;i<sub.length;i++)
							{
								Node<V> node=sub[i];
								if(node!=null)
								{
									int layOut=ctx.layout-1;
									int num=ctx.number+i&getConfig().mask<<layOut;
									stack.push(new StackContext(node,layOut,num));
								}
							}
						}
					}
				}
			}
			return next!=null;
		}

		public void accept(int number,V value)
		{
			if(value!=null)
			{
				this.next=new DataEntry(number, value);
			}
		}
		
		@Override
		public Entry<Integer,V> next() {
			Entry<Integer,V> result=next;
			next=null;
			return result;
		}
		@Override
		public void remove() {
			Entry<Integer,V> result=next;
			if(result!=null)
			{
				setByNumber(result.getKey(),null);
			}
		}
	}
	
	public static void main(String[] args) {
		BTree<String> b=new BTree<>();
		for(int i=0;i<10;i++)
		{
			b.write(i,"i="+i);
		}
		Iterator<Entry<Integer,String>> it=b.iterator();
		while(it.hasNext())
		{
			Entry<Integer,String> e=it.next();
			System.out.println(e.getKey()+":"+e.getValue());
			e.setValue("null");
		}
		it=b.iterator();
		while(it.hasNext())
		{
			Entry<Integer,String> e=it.next();
			System.out.println(e.getKey()+":"+e.getValue());
		}
	}
}
