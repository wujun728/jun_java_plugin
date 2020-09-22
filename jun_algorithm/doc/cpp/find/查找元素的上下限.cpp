#include <iostream>
#include <vector>
using namespace std;

//在一个有序序列中，查找某一个元素在该序列中的下限索引
//找到第一个不大于该元素的下标索引
int lower_bound(int* array,int low,int high,int key)
{
	int mid;
	if(high < low)
		return -1;

	//利用二分查找的策略
	while(high >= low)
	{
		mid = low+((high-low)>>1);
		if(array[mid] < key)
			low = mid+1;
		else
			high = mid-1;
	}
	return low;
}
/*
 在一个有序序列中，查找某一个元素在该序列中的上限索引
 找到第一个大于该元素的下标索引
*/
int upper_bound(int* array,int low,int high,int key)
{
	int mid;
	if(high < low)
		return -1;
	while(high >= low)
	{
		mid = low+((high-low)>>1);
		if(array[mid]>key)
			high = mid-1;
		else
			low = mid+1;
	}
	return low;
}


/*
查找某一个元素在一个有序序列中的范围
包含该元素的上下限 该区间为两端闭区间
如果没有找到 就返回-1
*/
pair<int,int> Findvalue(vector<int>& vec,int key)
{
	pair<int,int> pos(-1,-1);
	int mid,begin = 0,end = vec.size();
 	//首先判断该元素是否存在
	while(begin <= end)
	{
		mid = begin + ((end-begin)>>1);
		if(vec[mid] == key)
		{
			pos.first = mid;
			pos.second = mid;
			break;
		}
		else if(vec[mid] > key)
			end = mid-1;
		else
			begin =mid+1;
	}

	if(vec[mid] != key)
		return pos;
	int low = mid-1;
	int high = mid+1;

	//找低地址
	while(begin <= low)
	{
		mid = begin +(low-begin)/2;
		if(vec[mid] < key)
			begin = mid+1;
		else
			low = mid-1;
	}
	pos.first = begin;

	//找高地址
	while(high <= end)
	{
		mid = high +(end-high)/2;
		if(vec[mid] > key)
			end = mid-1;
		else
			high = mid+1;
	}
	pos.second = high -1;
	return pos;
}

int main()
{
	int array[]={1,2,3,3,3,3,3,3,4,5,7};
	cout<<lower_bound(array,0,10,3)<<endl;
	cout<<upper_bound(array,0,10,3)<<endl;
	vector<int> vec(array,array+sizeof(array)/sizeof(int));
	pair<int,int> pos = Findvalue(vec,3);
	cout<<pos.first<<endl<<pos.second<<endl;
	return 0;
}
