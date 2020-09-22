#include <iostream>
#include <vector>
using namespace std;

/*
非递归的方法实现全排列
思路：使用STL中的思想，从后往前找一对相邻的数，在一对相邻的数中
第一个元素小于第二个元素，记做*i < *ii,从后往前找到第一个元素*j，
使得*j > *i,然后交换*i和*j,然后交换从*ii开始（包括*ii在内）到
数组末尾的序列
*/
void Rserve(vector<int>& vec,int begin,int end)
{
	while(begin <= end)
	{
		swap(vec[begin],vec[end]);
		begin++;
		end--;
	}
}

bool FindPair(vector<int>& vec,int& first,int& second)
{
	int last = vec.size()-1;
	for(;last>0;last--)
	{
		second = last;
		first = last-1;
		if(vec[first] < vec[second])
			return true;
	}
	return false;
}

bool next_premutation(vector<int>& vec)
{
	int first,second,index;
	if(FindPair(vec,first,second))
	{
		index = vec.size()-1;
		for(;index>=first;index--)
		{
			if(vec[index] > vec[first])
				break;
		}
		swap(vec[index],vec[first]);
		Rserve(vec,second,vec.size()-1);
		return true;
	}
	return false;
}

int main()
{
	vector<int> vec(3);
	int i;
	for(i=0;i<vec.size();i++)
		vec[i] = i+1;
	vec[1]	=1;
	do{
		int i;
		for(i=0;i<vec.size();i++)
			cout<<vec[i]<<" ";
		cout<<endl;
	}while(next_premutation(vec));
	return 0;
}

