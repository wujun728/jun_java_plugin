#include <iostream>
#include <vector>
#include <string>
using namespace std;

/*
在一个无重复元素的已经被旋转过的数组中查找某一个元素
如果该元素存在该旋转数组中，返回1
如果该元素不存在该旋转数组中，返回0
*/

bool RotateArray(vector<int>& vec,int key)
{
	int low =0,high = vec.size()-1;
	int mid;
	while(low<=high)
	{
		mid = low + (high-low)/2;
		if(vec[mid] == key)
			return 1;
		//根据两个子数组的性质来划分情况
		if(vec[mid]>vec[low]) //前半部分一定是升序
		{
			if(key >= vec[low]&& key<vec[mid]) //说明待查找的元素在升序子序列中
				high =mid-1;
			else//剩下的情况继续在旋转数组中查找
				low = mid+1;
		}
		else //前半部分不是升序，包含了经过旋转的部分
		{
			if(key > vec[mid]&& key<= vec[high])
				low= mid+1;
			else
				high = mid-1;
		}
	}
	return 0;
}

/*
在一个有重复元素的旋转数组中查找某一个元素
判断该元素是否存在
*/
bool RotateArray2(vector<int>& vec,int key)
{
	int low = 0,high=vec.size()-1;
	int mid;
	//同样使用二分查找的思想
	while(low <= high)
	{
		mid = low+(high-low)/2;
		if(vec[mid] == key)
			return 1;
		if(vec[mid] > vec[low])//前半部分是升序
		{
			if(key >= vec[low] && key < vec[mid])
				high = mid-1;
			else
				low = mid+1;
		}
		else if(vec[mid] < vec[low]) //前半部分是降序
		{
			if(key > vec[mid] && key <= vec[high])
				low = mid+1;
			else
				high = mid-1;
		}
		else
			low++;
	}
	return 0;
}

int main()
{
	int array[]={4,5,6,7,0,1,2};
	vector<int> vec(array,array+sizeof(array)/sizeof(int));
	cout<<RotateArray2(vec,9)<<endl;
}
