package com.getall.util.test;

import com.getall.util.log.LogUtil;
import com.getall.util.maths.Matrix;
import com.getall.util.maths.algorithm.Viterbi;

public class TestViterbi
{

	/**
	 * TestMatrix.main()
	 * @param args
	 * @return void
	 * Author：WSK
	 * 2013-5-10 下午06:10:13
	 */
	public static void main(String[] args)
	{
		Matrix tran=new Matrix(3, 3);
		Double[] row0={0.4,0.3,0.3};
		Double[] row1={0.5,0.2,0.3};
		Double[] row2={0.6,0.2,0.2};
		tran.setRowValue(0, row0);
		tran.setRowValue(1, row1);
		tran.setRowValue(2, row2);
		Matrix prob=new Matrix(3, 3);
		Double[] prow0={0.1,0.4,0.5};
		Double[] prow1={0.6,0.3,0.1};
		Double[] prow2={0.4,0.4,0.2};
		prob.setRowValue(0, prow0);
		prob.setRowValue(1, prow1);
		prob.setRowValue(2, prow2);
		Object[] states={"Rainy","Sunny","Cloudy"};
		Object[] observes={"Walk","Shop","Clean"};
		
		Viterbi viterbi=new Viterbi(tran, prob, states, observes);
		int[] obs={0,1,1,2,2,1,2,0,2,2,2,2,0,0};
		double[] start={0.9,0.05,0.05};
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<obs.length;++i)
		{
			sb.append(i==0?"":"->").append(obs[i]);
		}
		LogUtil.getLogger().info("观测的结果为:"+sb.toString());
		LogUtil.getLogger().info("使用维特比算法...");
		LogUtil.getLogger().info("维特比算法"+(viterbi.viterbi(obs, start)?"执行成功":"执行失败"));
		LogUtil.getLogger().info("最可能的天气为:"+viterbi.getMaxPath());
		LogUtil.getLogger().info("概率:"+viterbi.getMaxProb());		
		LogUtil.getLogger().info("总概率:"+viterbi.getAllProb());		
	}
}
