package com.getall.util.maths.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.getall.util.maths.Det;
import com.getall.util.maths.Matrix;

/**
 * 多元线性回归算法
 * MultiVariableLinearRegression
 * Version：1.0
 * Author：WSK
 * 2013-5-10  下午05:52:35
 */
public class MultiVariableLinearRegression
{
	// 线性回归形如:
	// a[0]*x[0]+a[1]*x[1]+...+a[n-1]*x[n-1]+a[n]=y
	private List<List<Double>> x;// 变量
	private List<Double> y;// 值
	private Double[] a;// 参数
	private int n;// n元
	private Double[] avgX;
	private Double avgY;
	private Matrix L;

	//测试
	/*
	public static void main(String[] arg)
	{
		MultiVariableLinearRegression mvlr=new MultiVariableLinearRegression(2);
		List<Double> x1=new ArrayList<Double>();
		x1.add(0.2303);
		x1.add(0.1695);
		x1.add(0.6002);
		List<Double> x2=new ArrayList<Double>();
		x2.add(0.2284);
		x2.add(0.1739);
		x2.add(0.5977);
		List<Double> x3=new ArrayList<Double>();
		x3.add(0.2195);
		x3.add(0.1763);
		x3.add(0.6042);
		List<Double> x4=new ArrayList<Double>();
		x4.add(0.2145);
		x4.add(0.1844);
		x4.add(0.6011);
		List<Double> x5=new ArrayList<Double>();
		x5.add(0.209);
		x5.add(0.1745);
		x5.add(0.6165);
		Double y1=1.417969391;
		Double y2=1.407891933;
		Double y3=1.393787219;
		Double y4=1.395758884;
		//Double y5=1.394938722;
		mvlr.addExample(x1, y1);
		mvlr.addExample(x2, y2);
		mvlr.addExample(x3, y3);
		mvlr.addExample(x4, y4);
		//mvlr.addExample(x5, y5);
		mvlr.regression();
		mvlr.printAVector();
		List<Double> x6=new ArrayList<Double>();
		x6.add(0.1938);
		x6.add(0.1823);
		x6.add(0.6239);
		Double y5=mvlr.forecast(x5);
		System.out.println("预测结果:"+y5);
		Double y6=mvlr.forecast(x6);
		System.out.println("预测结果:"+y6);
	}
	*/
	
	public MultiVariableLinearRegression(int n)
	{
		this.n=n;
		this.a = new Double[n + 1];
		x = new ArrayList<List<Double>>();
		y = new ArrayList<Double>();
	}

	public void addExample(List<Double> x, Double y)
	{
		this.x.add(x);
		this.y.add(y);
	}

	// 计算回归参数
	public Boolean regression()
	{
		if (x.size() < n + 1)
		{
			System.out.println("样本不足,无法计算回归参数");
			return false;
		}
		computeAvgX();
		computeAvgY();
		computeLMatrix();
		computeAVector();
		return true;
	}
	//打印预测变量
	public String getRegressionLine()
	{
		String aString="线性回归直线:y=";
		for(int i=0;i<n;++i)
		{
			if(i>0&&a[i]>=0)
			{
				aString+="+";
			}
			aString+=a[i]+"*x"+(i+1);
		}
		aString+=(a[n]<0?"":"+")+a[n]+";";
		System.out.println(aString);
		return aString;
	}
	// 根据变量预测值
	public Double forecast(List<Double> x)
	{
		Double sum = 0d;
		for (int i = 0; i < n; ++i)
		{
			sum += a[i] * x.get(i);
		}
		return sum + a[n];// a[n]是常数
	}

	private void computeAvgX()
	{
		avgX = new Double[n];
		for (int j = 0; j < n; ++j)
		{
			Double sum = 0d;
			for (int i = 0; i < x.size(); ++i)
			{
				sum += x.get(i).get(j);
			}
			avgX[j] = sum / x.size();
		}
	}

	private void computeAvgY()
	{
		Double sum = 0d;
		for (int i = 0; i < y.size(); ++i)
		{
			sum += y.get(i);
		}
		avgY=sum/y.size();
	}

	private void computeLMatrix()
	{
		L = new Matrix(n, n + 1);
		//计算Lx
		for (int i = 0; i < n; ++i)
		{
			for (int j = 0; j < n; ++j)
			{
				Double sum = 0d;
				for (int k = 0; k < x.size(); ++k)
				{
					sum += (x.get(k).get(i) - avgX[i]) * (x.get(k).get(j) - avgX[j]);
				}
				L.setValue(i, j, sum);
			}
		}
		//计算Lxy
		for (int i = 0; i < n; ++i)
		{
			Double sum = 0d;
			for (int k = 0; k < x.size(); ++k)
			{
				sum += (x.get(k).get(i) - avgX[i]) * (y.get(k) - avgY);
			}
			L.setValue(i, n, sum);
		}
	}
	private void computeAVector()
	{
		Det dn=new Det(n);
		for(int i=0;i<n;++i)
		{
			dn.setColValue(i, L.getColValue(i));
		}
		Double dnDecade=dn.getDecade();
		Double sum=0d;
		for(int i=0;i<n;++i)
		{
			Det d=new Det(n);
			for(int j=0;j<n;++j)
			{
				if(i!=j)
				{
					d.setColValue(j, L.getColValue(j));
				}
				else
				{
					d.setColValue(j, L.getColValue(n));
				}
			}
			Double dDecade=d.getDecade();
			a[i]=dDecade/dnDecade;
			sum+=a[i]*avgX[i];
		}
		a[n]=avgY-sum;
	}
}
