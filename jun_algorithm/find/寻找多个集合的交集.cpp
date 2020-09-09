#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

/*
查找两个无序数组的交集
*/

void FindElements(vector<int>& first,vector<int>& second)
{
	if(first.size() <=0 || second.size() <= 0)
		return;
	//先排序
	sort(first.begin(),first.end());
	sort(second.begin(),second.end());

	//查找元素个数少的数组在另一个数组中是否出现
	if(first.size() >second.size())
		first.swap(second);
	int i,key;
	for(i =0;i<first.size();i++)
	{
		key = first[i];
		int low = 0;
		int high = second.size()-1;
		//以first数组中的元素为基准 在second数组中进行二分查找
		while(low<= high)
		{
			int mid = low +(high-low)/2;
			//如果找到 说明是交集中的元素，那么打印出来，再判断下一个元素是否可以找到
			if(key == second[mid])
			{
				cout<<second[mid]<<endl;
				break;
			}
			else if(key > second[mid])
				low = mid+1;
			else
				high = mid-1;

		}

	}
}

int main()
{
	int array[]={5,4,3,8,9,7,0};
	int array1[]={11,8,34,3,4,8,9,2};
	vector<int> first(array,array+sizeof(array)/sizeof(int));
	vector<int> second(array1,array1+sizeof(array1)/sizeof(int));
	FindElements(first,second);
	return 0;
}
