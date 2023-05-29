package com.jun.plugin.util4j.random.roundTable;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 圆桌概率随机工具
 * @author Administrator
 */
public class TableLottery<M>{
	
	/**
	 *	圆桌概率计算的参与块
	 * @author juebanlin
	 *
	 */
	public static interface TableBlock extends Comparable<TableBlock>
	{
		/**
		 * 块大小
		 * @return
		 */
		public int getTableBlockSize();
	}

	public static interface TableBlockator<T> extends Comparator<T>
	{
		/**
		 * 块大小
		 * @return
		 */
		public int getTableBlockSize(T obj);
	}

	public static class TableBlockAdapter<M> implements TableBlock
	{
		private M obj;//物品
		private int probability;//概率
		public TableBlockAdapter(M obj,int probability) {
			super();
			this.obj = obj;
			this.probability = probability;
		}
		
		public M getObj() {
			return obj;
		}

		public void setObj(M obj) {
			this.obj = obj;
		}

		public int getProbability() {
			return probability;
		}
		public void setProbability(int probability) {
			this.probability = probability;
		}
		
		@Override
		public int compareTo(TableBlock o) {
			return this.getTableBlockSize()-o.getTableBlockSize();
		}
		
		@Override
		public int getTableBlockSize() {
			return probability;
		}

		@Override
		public String toString() {
			return obj.toString();
		}
	}

	public static <M> M randomBlockAdapter(List<TableBlockAdapter<M>> tableBlocks,Random seed) 
	{
		TableBlockAdapter<M> m=randomBlock(tableBlocks, seed);
		if(m!=null)
		{
			return m.getObj();
		}
		return null;
	}
	
	public static <T> T randomBlock(Collection<T> tableBlocks,Random seed,TableBlockator<T> tableBlockator) {
	 	if (tableBlocks == null || tableBlocks.isEmpty()) {
			throw new UnsupportedOperationException("tableBlocks is empty");
    	}
		//占比值排序
	 	List<T> list=new ArrayList<T>(tableBlocks);
		Collections.sort(list,tableBlockator);
		T result=null;
    	// 计算总概率，这样可以保证不一定总概率是1
    	long sumProbability = 0l;
    	for (T tableBlock : list) 
    	{
    		sumProbability += tableBlockator.getTableBlockSize(tableBlock);
    	}
    	if(sumProbability==0)
    	{
    		return result;
    	}
    	double p=seed.nextDouble();//随机百分比,
    	p=p*sumProbability;//因为sumProbability不一定是100,所以需要换算为具体比例值
    	if(p==0)
    	{//当P为0,则桌子随机指针为整数1,即总概率的最小值
    		p=1;
    	}
    	double tempSumRate = 0l;
    	for (T tableBlock: list) 
    	{//叠加概率
    		tempSumRate +=tableBlockator.getTableBlockSize(tableBlock);//增加区块
    		if(tempSumRate>=p)
    		{// 根据区块值来获取抽取到的物品索引
    			result=tableBlock;
    			break;
    		}
    	}
    	return result;
	}
	
	public static <T extends TableBlock> T randomBlock(Collection<T> tableBlocks,Random seed) {
	 	if (tableBlocks == null || tableBlocks.isEmpty()) {
			throw new UnsupportedOperationException("tableBlocks is empty");
    	}
		//占比值排序
	 	List<T> list=new ArrayList<T>(tableBlocks);
		Collections.sort(list);
		T result=null;
    	// 计算总概率，这样可以保证不一定总概率是1
    	long sumProbability = 0l;
    	for (T tableBlock : list) 
    	{
    		sumProbability += tableBlock.getTableBlockSize();
    	}
    	if(sumProbability==0)
    	{
    		return result;
    	}
    	double p=seed.nextDouble();//随机百分比,
    	p=p*sumProbability;//因为sumProbability不一定是100,所以需要换算为具体比例值
    	if(p==0)
    	{//当P为0,则桌子随机指针为整数1,即总概率的最小值
    		p=1;
    	}
    	double tempSumRate = 0l;
    	for (T tableBlock: list) 
    	{//叠加概率
    		tempSumRate += tableBlock.getTableBlockSize();//增加区块
    		if(tempSumRate>=p)
    		{// 根据区块值来获取抽取到的物品索引
    			result=tableBlock;
    			break;
    		}
    	}
    	return result;
	}
	
	public static <T extends TableBlock> String test(Collection<T> tableBlocks,int testCount)
	{
		Random seed=new Random();
		String result="";
		int allCount=testCount;
		Map<T,Integer> map=new HashMap<T, Integer>();
		//统计元素出现次数
		for(int i=0;i<allCount;i++)
		{
			T item=randomBlock(tableBlocks, seed);
			if(map.containsKey(item))
			{
				int tmp=map.get(item);
				tmp++;
				map.put(item, tmp);
				
			}else
			{
				map.put(item, 1);
			}
		}
		//计算概率
		for(T key:map.keySet())
		{
			int count=map.get(key);
			DecimalFormat df = new DecimalFormat();  
		    df.setMaximumFractionDigits(2);// 设置精确2位小数   
		    df.setRoundingMode(RoundingMode.HALF_UP); //模式 例如四舍五入   
		    double p = (double)count/(double)allCount*100;//以100为计算概率200为实际总概率,则计算的概率会减半 
		    result="元素:"+key+"出现次数"+count+"/"+allCount+",出现概率:"+df.format(p)+"%";
		    System.out.println("元素:"+key+"出现次数"+count+"/"+allCount+",出现概率:"+df.format(p)+"%");
		}
		return result;
	}
	
    public static void main(String[] args) {
		List<TableBlockAdapter<String>> fruits=new ArrayList<TableBlockAdapter<String>>();
		//以总概率为200为例子
		TableBlockAdapter<String> fruit1=new TableBlockAdapter<String>("a", 50);
		TableBlockAdapter<String> fruit2=new TableBlockAdapter<String>("b", 150);
		TableBlockAdapter<String> fruit3=new TableBlockAdapter<String>("c", 200);
		TableBlockAdapter<String> fruit4=new TableBlockAdapter<String>("d", 250);
		TableBlockAdapter<String> fruit5=new TableBlockAdapter<String>("e",350);
		fruits.add(fruit1);
		fruits.add(fruit2);
		fruits.add(fruit3);
		fruits.add(fruit4);
		fruits.add(fruit5);
		int allCount=10000000;
		test(fruits, allCount);
	}
}
