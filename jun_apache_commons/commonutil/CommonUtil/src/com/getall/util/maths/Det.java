package com.getall.util.maths;

/**
 * 行列式
 * Det
 * Version：1.0
 * Author：WSK
 * 2013-5-10  下午05:52:56
 */
public class Det extends Matrix
{
	//n阶行列式
	public Det(int n)
	{
		super(n, n);
	}
	//单位矩阵
	public static Det getIDet(int n)
	{
		Det det=new Det(n);
		for(int i=0;i<n;++i)
		{
			det.setValue(i, i, 1d);
		}
		return det;
	}
	//矩阵的秩
	public Double getDecade()
	{
	    Double product1=1d,sum1=0d;
        for(int i=0;i<n;++i)
        {
            int k=i;
            for(int j=0;j<n;++j)
            {
                product1*=matrix[j][(k++)%n];
            }
            sum1+=product1;
        }		

	    Double product2=1d,sum2=0d;
        for(int i=n-1;i>=0;i--)
        {
           int k=i;
           for(int j=0;j<n;j++)
           {
        	   product2*=matrix[j][(k--+n)%n];     
           }
           sum2+=product2;
        }
        return sum1-sum2;
  	}
	public int getRankCount()
	{
		return n;
	}
}
