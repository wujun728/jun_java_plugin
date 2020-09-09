#include <iostream>
#include <vector>
using namespace std;

/*
一个数组中只有一个数组出现了一次 其他都是出现了两次
找到这个出现一次的数字
*/

//将整个数组中的整数进行异或 最后得到的结果就是所要找的数
int SingleNumber(vector<int>& vec)
{
	if(vec.size()<=0)
		return 0;
	int i;
	int  value = vec[0];
	for(i=1;i<vec.size();i++)
		value ^= vec[i];
	return value;
}

/*
如果一个数组中有两个数出现了一次 其他都是出现了两次 这两个数分别是什么
*/

void SingleNumberII(vector<int>& a,int& pN1,int& pN2)
{
	int i,j,temp;

	//计算这两个数的异或结果
	temp =0;
	for(i=0;i<a.size();i++)
		temp ^= a[i];

	//找到异或结果中第一个为1的位
	for(j=0;j<sizeof(int)*8;j++)
		if(((temp >> j)&1) ==1)
			break;

	//第j位为1，说明这两个数字在第j位上是不同的  根据这个来进行分组
	pN1 =0;
	pN2 =0;
	for(i=0;i<a.size();i++)
		if(((a[i] >> j )&1) ==0)
			pN1 ^= a[i];
		else
			pN2 ^= a[i];

}

/*
一个数组中只有一个数出现了一次 其他都是出现了三次 找出这个数
*/

//同样根据位来判断

int SingleNumberIII(vector<int>& vec)
{
	int i,j;
	int low=0,high=vec.size()-1;
	int bit =1;
	int flag =0;

	//通过某一位的值将数组分为两部分，其中一部分包含了待找的数 另一部分不包含
	while(low <= high)
	{
		if(low == high)
			return vec[low];
		i = low-1;
		for(j = low;j<= high;j++)
		{
			if((vec[j]&bit) == 0)
			{
				i++;
				swap(vec[i],vec[j]);
			}
		}
		if(i >= low)
		{
			if((i-low+1)%3 == 0)
				low = i+1;
			else
				high =i;
		}
		bit = bit<<1;
	}
	return 0;
}
int main()
{
	int array[]={12,23,12,45,56,45,23,78,78,78,12,23,45};
	vector<int> vec(array,array+sizeof(array)/sizeof(int));
	int fir,sec;
	cout<<SingleNumberIII(vec);
//	cout<<fir<<endl<<sec<<endl;
	return 0;
}
