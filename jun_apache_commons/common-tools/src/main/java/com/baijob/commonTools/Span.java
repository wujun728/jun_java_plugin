package com.baijob.commonTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据区间对象 
 * 存放ID最小和最大值
 * 包含一个区间的完整性信息。
 * @author Luxiaolei
 *
 */
public class Span {
	
	/** ID */
	public long id;
	/** 此区间的名称 */
	public String name;
	
	/** 最小ID值 */
	public long min = Long.MAX_VALUE;
	/** 最大ID值 */
	public long max = 0;
	
	/** 区间内的ID数 */
	public int count;
	/*--------------------------构造方法 start-------------------------------*/
	public Span(){
	}
	
	/**
	 * @param id	区间ID
	 * @param name 区间名称
	 */
	public Span(long id, String name){
		this.id = id;
		this.name = name;
	}
	
	/**
	 * @param min 最小ID
	 * @param max 最大ID
	 */
	public Span(long min, long max){
		this.min = min;
		this.max = max;
	}
	
	/**
	 * @param name 段名称
	 * @param min 最小ID
	 * @param max 最大ID
	 */
	public Span(String name, long min, long max){
		this.name = name;
		this.min = min;
		this.max = max;
	}
	
	/**
	 * @param min 最小ID
	 * @param max 最大ID
	 * @param count 初始数目
	 */
	public Span(long min, long max, int count){
		this.min = min;
		this.max = max;
		this.count = count;
	}
	
	/**
	 * @param id ID
	 * @param min 最小ID
	 * @param max 最大ID
	 */
	public Span(long id, long min, long max) {
		this.id = id;
		this.min = min;
		this.max = max;
	}
	/*--------------------------构造方法 end-------------------------------*/
	
	/*--------------------------功能方法 start-------------------------------*/
	/**
	 * 获得两数之差
	 * @return 两数差
	 */
	public long getSub(){
		return max - min;
	}
	
	/**
	 * 切割此ID区间为更小的ID区间对象
	 * @param partCount 分成多少份
	 * @return 切割后的区间对象集合
	 */
	public List<Span> cut(int partCount){
		if(partCount == 0) return null;
		List<Span> list = new ArrayList<Span>();
		
		//数据不足以分开，则只返回一份
		if(partCount == 0 || getSub() == 0){
			list.add(this);
			return list;
		}
		//先分成partCount-1份完整的，将剩余部分给最后一份
		long part = getSub() / partCount;
		for(int i = 0; i < partCount; i++){
			long startId = part * i + this.min;
			//若为最后一批，将剩余的ID全部给之，否则给一个部分
			long endId = (i >= partCount-1) ? this.max : (startId + part -1);
			
			list.add(new Span(i,  startId, endId));
		}
		return list;
	}
	
	/**
	 * ID区间是否为空。
	 * 判断方式：max == 0 或 min == max
	 * @return 是否为空
	 */
	public boolean isEmpty(){
		if(max == 0 || min > max){
			return true;
		}
		return false;
	}
	
	/**
	 * 比较并替换掉对象中已经存在的ID<br/>
	 * ID计数 +1<br/>
	 * 所提供ID如果大于max或小于min时，替换掉相应的值
	 * @param number 被检查的数字
	 */
	public void compareToReplace(long number){
		if(number > max) max = number;
		if(number < min) min = number;
	}
	
	/**
	 * 是否在区间之内（闭区间）
	 * @param number 数字
	 * @return 是否在区间内
	 */
	public boolean isInBetween(long number){
		return (number >= min && number <= max);
	}
	
	/**
	 * 实例化一个空Span
	 * @return Span空对象
	 */
	public static Span newInstance(){
		return new Span();
	}
	/*--------------------------功能方法 end-------------------------------*/
}
