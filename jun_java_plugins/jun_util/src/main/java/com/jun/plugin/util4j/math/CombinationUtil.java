package com.jun.plugin.util4j.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 排列组合
 * @author jaci
 */
public class CombinationUtil {
	public static class ComputeStatus{
		private boolean stop;//停止循环
		private boolean skip;//跳过下层循环
		public boolean isStop() {
			return stop;
		}
		public void setStop(boolean stop) {
			this.stop = stop;
		}
		public boolean isSkip() {
			return skip;
		}
		public void setSkip(boolean skip) {
			this.skip = skip;
		}
	}
	
	@FunctionalInterface
	public static interface ForEachController<T>{
		
		/**
		 * 输出事件
		 * @param output 输出的数据排列
		 * @param outPutIndex 输出的终点位置
		 */
		public ComputeStatus onOutEvent(T[] output,int outPutIndex);
	}
	
	@FunctionalInterface
	public static interface ForEachIndexController{
		
		/**
		 * 输出事件
		 * @param output 输出的数据index的排列
		 * @param outPutIndex index数组的终点位置
		 */
		public ComputeStatus onOutEvent(int[] output,int outPutIndex);
	}
	
	/**
	 * 遍历任意数组元素排列组合情况
	 * @param input
	 * @param inputSkip
	 * @param output
	 * @param outPutIndex
	 * @param controller
	 */
	public static <T> void forEach(T[] input,boolean[] inputSkip,T[] output ,int outPutIndex,ForEachController<T> controller)
	{
		for(int i=0;i<input.length;i++)
		{
			if(inputSkip[i])
			{
				continue;
			}
			output[outPutIndex]=input[i];//输出当前位锁定值
			ComputeStatus status=controller.onOutEvent(output, outPutIndex);
			if(status.isStop())
			{
				return;
			}
			if(status.isSkip())
			{//跳过下层循环
				continue;
			}
			outPutIndex++;
			if(outPutIndex>=input.length)
			{//如果是最后一个则返回
				break;
			}
			//解锁下一个
			inputSkip[i]=true;//锁定当前位占用索引,其它位不可使用该索引
			forEach(input, inputSkip, output, outPutIndex,controller);
			outPutIndex--;//回到当前
			inputSkip[i]=false;//释放锁定
		}
	}
	
	/**
	 * 遍历任意数组元素索引排列组合情况
	 * @param input 输入参数
	 * @param inputSkip  需要跳过的输入参数
	 * @param output 输出排列的input的index组合
	 * @param outPutIndex 输出output的位置
	 * @param controller
	 */
	public static <T> void forEachIndex(T[] input,boolean[] inputSkip,int[] output,int outPutIndex,ForEachIndexController controller)
	{
		for(int i=0;i<input.length;i++)
		{
			if(inputSkip[i])
			{
				continue;
			}
			output[outPutIndex]=i;//输出当前位锁定值
			ComputeStatus status=controller.onOutEvent(output, outPutIndex);
			if(status.isStop())
			{
				return;
			}
			if(status.isSkip())
			{//跳过下层循环
				continue;
			}
			outPutIndex++;
			if(outPutIndex>=input.length)
			{//如果是最后一个则返回
				break;
			}
			//解锁下一个
			inputSkip[i]=true;//锁定当前位占用索引,其它位不可使用该索引
			forEachIndex(input, inputSkip, output, outPutIndex,controller);
			outPutIndex--;//回到当前
			inputSkip[i]=false;//释放锁定
		}
	}
	
	/**
	 * 遍历int数组元素排列组合情况
	 * @param input
	 * @param inputSkip
	 * @param output
	 * @param outPutIndex
	 * @param controller
	 */
	public static void forEachIndex(int[] input,boolean[] inputSkip,int[] output ,int outPutIndex,ForEachIndexController controller)
	{
		for(int i=0;i<input.length;i++)
		{
			if(inputSkip[i])
			{
				continue;
			}
			output[outPutIndex]=i;//输出当前位锁定值
			ComputeStatus status=controller.onOutEvent(output, outPutIndex);
			if(status.isStop())
			{
				return;
			}
			if(status.isSkip())
			{//跳过下层循环
				continue;
			}
			outPutIndex++;
			if(outPutIndex>=input.length)
			{//如果是最后一个则返回
				break;
			}
			//解锁下一个
			inputSkip[i]=true;//锁定当前位占用索引,其它位不可使用该索引
			forEachIndex(input, inputSkip, output, outPutIndex,controller);
			outPutIndex--;//回到当前
			inputSkip[i]=false;//释放锁定
		}
	}
	
	/**
	 * 遍历short数组元素索引排列组合情况
	 * @param input
	 * @param inputSkip
	 * @param output
	 * @param outPutIndex
	 * @param controller
	 */
	public static void forEachIndex(short[] input,boolean[] inputSkip,int[] output ,int outPutIndex,ForEachIndexController controller)
	{
		for(int i=0;i<input.length;i++)
		{
			if(inputSkip[i])
			{
				continue;
			}
			output[outPutIndex]=i;//输出当前位锁定值
			ComputeStatus status=controller.onOutEvent(output, outPutIndex);
			if(status.isStop())
			{
				return;
			}
			if(status.isSkip())
			{//跳过下层循环
				continue;
			}
			outPutIndex++;
			if(outPutIndex>=input.length)
			{//如果是最后一个则返回
				break;
			}
			//解锁下一个
			inputSkip[i]=true;//锁定当前位占用索引,其它位不可使用该索引
			forEachIndex(input, inputSkip, output, outPutIndex,controller);
			outPutIndex--;//回到当前
			inputSkip[i]=false;//释放锁定
		}
	}
	
	/**
	 * 遍历byte数组元素索引排列组合情况
	 * @param input
	 * @param inputSkip
	 * @param output
	 * @param outPutIndex
	 * @param controller
	 */
	public static void forEachIndex(byte[] input,boolean[] inputSkip,int[] output ,int outPutIndex,ForEachIndexController controller)
	{
		for(int i=0;i<input.length;i++)
		{
			if(inputSkip[i])
			{
				continue;
			}
			output[outPutIndex]=i;//输出当前位锁定值
			ComputeStatus status=controller.onOutEvent(output, outPutIndex);
			if(status.isStop())
			{
				return;
			}
			if(status.isSkip())
			{//跳过下层循环
				continue;
			}
			outPutIndex++;
			if(outPutIndex>=input.length)
			{//如果是最后一个则返回
				break;
			}
			//解锁下一个
			inputSkip[i]=true;//锁定当前位占用索引,其它位不可使用该索引
			forEachIndex(input, inputSkip, output, outPutIndex,controller);
			outPutIndex--;//回到当前
			inputSkip[i]=false;//释放锁定
		}
	}
	
	public void test1(){
		/**
		 * 测试排列组合
		 * N个元素放在N个位置，有多少种放法
		 */
		final byte[] input={1,2,3};
		int[] output=new int[input.length];
		boolean[] inputSkip=new boolean[input.length];
		final List<Byte[]> result=new ArrayList<>();
		long t=System.nanoTime();
		final ComputeStatus status=new ComputeStatus();
		CombinationUtil.forEachIndex(input, inputSkip, output,0,(outPut,outPutIndex)->{
			if(outPutIndex+1>=output.length)
			{//如果是最后一个
				Byte[] succeed=new Byte[output.length];
				for(int i=0;i<succeed.length;i++)
				{
					succeed[i]=input[output[i]];
				}
				result.add(succeed);
				System.out.println(Arrays.toString(output));
			}
			return status;
		});
		t=System.nanoTime()-t;
		System.out.println("耗时："+t+"纳秒,"+TimeUnit.NANOSECONDS.toMillis(t)+"毫秒");
		for(Byte[] succeed:result)
		{
			System.out.println(Arrays.toString(succeed));
		}
		System.out.println("排列组合数量:"+result.size());
	
	}
	
	public void test2()
	{
		/**
		 * 测试排列组合
		 * N个元素放在N个位置，有多少种放法
		 */
		final byte[] input={1,2,3};
		int[] output=new int[input.length];
		boolean[] inputSkip=new boolean[input.length];
		final List<Byte[]> result=new ArrayList<>();
		long t=System.nanoTime();
		final ComputeStatus status=new ComputeStatus();
		CombinationUtil.forEachIndex(input, inputSkip, output,0,new ForEachIndexController() 
		{
			@Override
			public ComputeStatus onOutEvent(int[] output, int outPutIndex) {
				if(outPutIndex+1>=output.length)
				{//如果是最后一个
					Byte[] succeed=new Byte[output.length];
					for(int i=0;i<succeed.length;i++)
					{
						succeed[i]=input[output[i]];
					}
					result.add(succeed);
//					System.out.println(Arrays.toString(output));
				}
				return status;
			}
		});
		t=System.nanoTime()-t;
		System.out.println("耗时："+t+"纳秒,"+TimeUnit.NANOSECONDS.toMillis(t)+"毫秒");
		for(Byte[] succeed:result)
		{
			System.out.println(Arrays.toString(succeed));
		}
		System.out.println("排列组合数量:"+result.size());
	}

	public static void main(String[] args) {
		new CombinationUtil().test1();
		new CombinationUtil().test1();
		new CombinationUtil().test2();
	}
}