#include <iostream>
#include <stdlib.h>
#include <algorithm>
#include <vector>

using namespace std;

//查找一个数组中的第K大的元素

int Find_K(int a[],int low,int high,int k)
{
	if(k<=0)
		return -1;
	if(k>high-low+1)
		return -1;
	//随机选择一个支点
	int pivot = low+rand()%(high-low+1);
	swap(a[low],a[pivot]);
	int m = low;
	int count = 1;

	// 每遍历一次  把较大的放到数组的左边
	for(int i=low+1;i<=high;i++)
	{
		if(a[i] > a[low])
		{
			swap(a[++m],a[i]);
			count++; //比关键点大的数的个数为count+1
		}
	}
	swap(a[m],a[low]);// 将关键点放在左右两部分的分界点
	if(count > k)
		return Find_K(a,low,m-1,k);
	else if(count < k)
		return Find_K(a,m+1,high,k-count);
	else
		return m;
}

//查找最小的K个数
void FindMinTopK(vector<int>& vec,int k)
{
	vector<int> heap(vec.begin(),vec.begin()+k);
	make_heap(heap.begin(),heap.end());
	int i;

	//开始处理剩余的数据
	for(i =k;i<vec.size();i++)
	{
		if(vec[i]<heap[0])
		{
			pop_heap(heap.begin(),heap.end());
			heap.pop_back();
			heap.push_back(vec[i]);
			push_heap(heap.begin(),heap.end());
		}
	}

	for(i=0;i<heap.size();i++)
		cout<<heap[i]<<endl;
}
int main()
{
	int a[]={5,15,4,8,2,3,9,10};
	int r = Find_K(a,0,sizeof(a)/sizeof(int)-1,3);
//	cout<<(r==-1? r : a[r])<<endl;

	int array[]={5,3,6,7,4,2,1,9,8,10};
	vector<int> vec(array,array+sizeof(array)/sizeof(int));
	FindMinTopK(vec,4);
	return 0;
}
