package com.getall.util.maths.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.getall.util.log.LogUtil;
import com.getall.util.maths.Matrix;

/**
 * 维特比算法
 * Viterbi
 * Version：1.0
 * Author：WSK
 * 2013-5-10  下午06:26:47
 */
public class Viterbi
{
	private Matrix translation;//状态转移矩阵
	private Matrix probability;//概率矩阵
	private Path maxPath;//最优路径
	private double maxProb;//最优路径概率
	private double allProb;//所有路径总概率
	private Object[] states;
	private Object[] observes;
	
	private class Triad
	{
		Double allPorb;//开始状态到所有路径概率之和
		Path path;//开始状态到当前的维特比路径
		Double prob;//开始状态到当前的维特比路径的概率
		Triad(Double allPorb,Path path,Double prob)
		{
			this.allPorb=allPorb;
			this.path=path;
			this.prob=prob;
		}
	}
	public class Path
	{
		List<Object> path=new ArrayList<Object>();
		public String toString()
		{
			StringBuilder sbBuilder=new StringBuilder();
			for(int i=0;i<path.size();++i)
			{
				sbBuilder.append(i==0?"":"->").append(path.get(i).toString());
			}
			return sbBuilder.toString();
		}
	}
	public Viterbi(Matrix tran,Matrix prob,Object[] states,Object[] observes)
	{
		this.translation=tran;
		this.probability=prob;
		this.states=states;
		this.observes=observes;
		this.maxPath=new Path();
	}
	public boolean viterbi(int[] obs,double[] start)
	{
		if(this.observes.length!=this.probability.getColumnCount())
		{
			LogUtil.getLogger().error("观测结果数和概率矩阵的列数不一致");
			return false;
		}
		if(this.states.length!=this.probability.getRowCount())
		{
			LogUtil.getLogger().error("状态数和概率矩阵的行数不一致");
			return false;
		}
		if(this.translation.getRowCount()!=this.translation.getColumnCount())
		{
			LogUtil.getLogger().error("状态转移矩阵的行列数不一致");
			return false;
		}
		if(this.states.length!=this.translation.getRowCount())
		{
			LogUtil.getLogger().error("状态数和状态转移矩阵的行数不一致");
			return false;
		}
		//初始化三元组
		Map<Object,Triad> triads=new HashMap<Object,Triad>();
		for(int i=0;i<states.length;++i)
		{
			Path path=new Path();
			path.path.add(states[i]);
			triads.put(states[i],new Triad(start[i],path,start[i]));
		}
		for(int i=0;i<obs.length;++i)//对观察结果遍历
		{
			Map<Object,Triad> triadsTemp=new HashMap<Object,Triad>();
			for(int j=0;j<states.length;++j)//对下一状态进行遍历
			{
				Object nextState=states[j];
				double total=0;
				Path argmax=new Path();
				double valmax=0;				
				for(int k=0;k<states.length;++k)//对当前状态遍历
				{
					Object curState=states[k];
					Triad tmp=triads.get(curState);
					double allProb=tmp.allPorb;
					Path path=tmp.path;
					double prob= tmp.prob;
					double p=probability.getValue(k, obs[i])*translation.getValue(k, j);
					allProb*=p;
					prob*=p;
					total+=allProb;//利用贝叶斯定理计算条件概率
					if(prob > valmax)
					{	//选择概率最大的作为维特比路径
						argmax.path.clear();
						argmax.path.addAll(path.path);
						argmax.path.add(nextState);
						valmax=prob;
					}
				}
				Triad triad=new Triad(total, argmax, valmax);
				triadsTemp.put(nextState, triad);
			}
			triads=triadsTemp;//交换元祖集
		}
		
		//获取结果
		this.allProb=0;
		for(int i=0;i<states.length;++i)
		{
			Triad tmp=triads.get(states[i]);
			double allProb=tmp.allPorb;
			Path path=tmp.path;
			double prob= tmp.prob;
		    this.allProb += allProb;
			if(prob > maxProb)
			{	//选择概率最大的作为维特比路径
				maxPath.path.clear();
				maxPath.path.addAll(path.path);
				maxProb=prob;
			}
		}
		return true;
	}
	public Matrix getTranslation()
	{
		return translation;
	}
	public void setTranslation(Matrix translation)
	{
		this.translation = translation;
	}
	public Matrix getProbability()
	{
		return probability;
	}
	public void setProbability(Matrix probability)
	{
		this.probability = probability;
	}
	public Path getMaxPath()
	{
		return maxPath;
	}
	public Object[] getStates()
	{
		return states;
	}
	public void setStates(Object[] states)
	{
		this.states = states;
	}
	public Object[] getObserves()
	{
		return observes;
	}
	public void setObserves(Object[] observes)
	{
		this.observes = observes;
	}
	public double getMaxProb()
	{
		return maxProb;
	}
	public double getAllProb()
	{
		return allProb;
	}

}
