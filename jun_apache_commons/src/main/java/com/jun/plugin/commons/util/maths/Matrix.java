package com.jun.plugin.commons.util.maths;

/**
 * 矩阵
 * Matrix
 * Version：1.0
 * Author：WSK
 * 2013-5-10  下午05:52:51
 */
public class Matrix
{
	protected Double[][] matrix;
	protected int m;//行
	protected int n;//列
	
	//创建m*n的矩阵
	public Matrix(int m,int n)
	{
		this.m=m;
		this.n=n;
		matrix=new Double[m][n];
		reset();
	}
	//重置矩阵为0矩阵
	public void reset()
	{
		for(int i=0;i<m;++i)
		{
			for(int j=0;j<n;++j)
			{
				matrix[i][j]=0d;
			}
		}
	}
	public Double getValue(int i,int j)
	{
		return matrix[i][j];
	}
	public void setValue(int i,int j,Double x)
	{
		matrix[i][j]=x;
	}
	public int getRowCount()
	{
		return m;
	}
	public int getColumnCount()
	{
		return n;
	}
	public void setRowValue(int row,Double[] rowValue)
	{
		for(int j=0;j<n;++j)
		{
			matrix[row][j]=rowValue[j];
		}
	}
	public void setColValue(int col,Double[] colValue)
	{
		for(int i=0;i<m;++i)
		{
			matrix[i][col]=colValue[i];
		}		
	}
	public Double[] getRowValue(int row)
	{
		return matrix[row];
	}
	public Double[] getColValue(int col)
	{
		Double[] colValue=new Double[m];
		for(int i=0;i<m;++i)
		{
			colValue[i]=matrix[i][col];
		}
		return colValue;
	}
}
