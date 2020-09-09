#include <iostream>
#include <vector>

using namespace std;

/*
在一个二维数组中 如果某一个元素的值为0 将其所在的行和列所有元素都
置为0 尽可能小的空间复杂度
*/

void SetZero(vector<vector<int> >& matrix)
{
	bool firstLine = false;
	bool firstRow = false;

	//判断第一行是否有值为0 的元素
	for(int i=0;i<matrix[0].size();i++)
	{
		if(matrix[0][i] == 0)
		{
			firstLine = true;
			break;
		}
	}

	//判断第一列是否有0的元素
	for(int i=0;i<matrix.size();i++)
	{
		if(matrix[i][0] == 0)
		{
			firstRow = true;
			break;
		}
	}

	//先处理第一行和第一列
	for(int i=1;i<matrix.size();i++)
	{
		for(int j=1;j<matrix[0].size();j++)
		{
			if(matrix[i][j] == 0)
			{
				matrix[i][0] =0;
				matrix[0][j] =0;
			}
		}
	}

	//根据第一行和第一列的元素标记  更新其他元素
	for(int i=1;i<matrix.size();i++)
	{
		for(int j=1;j<matrix[0].size();j++)
		{
			if(matrix[i][0]==0 || matrix[0][j] ==0)
			{
				matrix[i][j] =0;
			}
		}
	}

	//更新第一行
	if(firstLine)
	{
		for(int i=0;i<matrix[0].size();i++)
			matrix[0][i] =0;
	}

	//更新第一列
	if(firstRow)
	{
		for(int i=0;i<matrix.size();i++)
			matrix[i][0] = 0;
	}
}
