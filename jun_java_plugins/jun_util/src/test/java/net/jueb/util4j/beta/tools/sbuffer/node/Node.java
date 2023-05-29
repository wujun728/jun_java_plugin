package net.jueb.util4j.beta.tools.sbuffer.node;

/**
 * 向左分层 (0~31)
 * @author juebanlin
 */
public class Node {
	private Node[] sub=new Node[2];
	private Byte attach;//附件
	private static final int maxPos=31;//0-31的int二进制位
	
	private Byte _get(int index,int pos)
	{
		if(pos>maxPos)
		{//超出范围
			return null;
		}
		if(pos==maxPos)
		{
			return this.attach;
		}
		int p=getPosValue(index,pos);
		Node node=sub[p];
		if(node==null)
		{
			return null;
		}
		pos++;
		return node._get(index, pos);
	}
	
	public Byte get(int index)
	{
		return _get(index,0);
	}
	
	private void _set(int index,int pos,Byte value)
	{
		if(pos>maxPos)
		{//超出范围
			return ;
		}
		if(pos==maxPos)
		{
			this.attach=value;
			return ;
		}
		int p=getPosValue(index,pos);
		Node node=sub[p];
		if(node==null)
		{
			node=new Node();
			sub[p]=node;
		}
		pos++;
		node._set(index, pos,value);
	}
	
	public void set(int index,Byte value)
	{
		_set(index,0,value);
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
	public static int getPosValue(int number,int pos)
	{
		return (number & (1<<pos))>>pos;
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
			Node node=new Node();
			int key=1001;
		    Byte value=123;
		    node.set(key,value);
		    Byte value2=node.get(key);
		    System.out.println(value2);  	
		}
		public void test2()
		{
			Node node=new Node();
			int num=100000;
			long t=System.currentTimeMillis();
		    for(int i=0;i<num;i++)
		    {
		    	int key=i;
		    	Byte v=(byte) i;
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
		    System.out.println("t1:"+t1+",t2:"+t2+",error:"+error);
		}
	}
	
	public static void main(String[] args) {
	    new Test().test2();
	}
}
