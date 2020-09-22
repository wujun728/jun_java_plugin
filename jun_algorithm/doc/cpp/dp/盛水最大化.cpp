#include <iostream>
#include <vector>
#include <string>
#include <stack>
using namespace std;

/*
夹逼方法
从数组的两段走起，每次迭代时判断左边点和右边点指向的数字哪个大
如果左边点下，就意味着左移动右边点不可能使得结果变得更好
因为瓶颈在左边点，移动右边点只会变小，所以这个时候我们选择左边点右移
反之，选择右边点左移，在这个过程中一直维护最大的容积 
*/

int area(vector<int> &height, int i, int j)  
{  
    int h = height[i]<height[j]?height[i]:height[j];  
    return h*(j-i);  
}  
int maxArea(vector<int> &height) 
{  
    int max=0;  
    for(int i=0;i<height.size();i++)  
    {  
        for(int j=i+1;j<height.size();j++)  
        {  
            int a = area(height,i,j);  
            if(a>max)  
            	max=a;  
        }  
    }  
    return max;  
}  

/*
第二种方法 
*/
int maxarea(vector<int>& vec)
{
	int maxarea=0;
	int first,second;
	int i=0,j=vec.size()-1;
	while( i<j)
	{
		if(min(vec[i],vec[j])*(j-i) > maxarea)
		{
			maxarea = min(vec[i],vec[j])*(j-i);
		}
		if(vec[i] < vec[j])
			i++;
		else
			j--;
	}
	return maxarea;
}

/*

			|
			|						 __						
			|                       |  |
			|		  __            |  |__    __ 
			|		 |	|           |  |  |  |  |
		    |	__	 | 	|__	   __   |  |  |__|  |__
			|  |  |	 |	|  |  |  |  |  |  |  |  |  |
			|__|__|__|__|__|__|__|__|__|__|__|__|__|_____
            1  2  3  4  5  6  7  8  8  9  10 11 12  
上述给定的一个序列为[0,1,0,2,1,0,1,3,2,1,2,1]，每个元素代表柱子的高度
最后函数的返回值为6 

思路：可以在这个序列中找到最高的柱子位置，那么从两头开始找可以
盛水的多少，假如从头开始遍历，需要遍历到柱子最高的位置，遍历到当前位置
如果发现当前的柱子比之前记录的柱子高，那么更新
如果没有之前记录的柱子高，那么就可以计算当前柱子相对之前的高柱子的盛水量 
*/ 
int TrapRainWater(vector<int>& vec)
{
	int i,maxhigh;
	maxhigh = 0;
	int left=0,right = 0;
	int sum =0;
	for(i=0;i<vec.size();i++)
		if(vec[i] > vec[maxhigh])
			maxhigh = i;
	
	for(i=0;i<maxhigh;i++)
	{
		if(vec[i] < left)
			sum +=(left-vec[i]);
		else
			left = vec[i];
	}
	
	for(i=vec.size()-1;i>maxhigh;i--)
	{
		if(vec[i]<right)
			sum += (right-vec[i]);
		else
			right = vec[i];
	}
	return sum;
} 

int main()
{
//	int array[]={4,3,4,5,7,9,7,6,8,5,3,2};
//	vector<int> vec(array,array+sizeof(array)/sizeof(int));
//	cout<<maxArea(vec)<<endl;
	int array[]={0,1,0,2,1,0,1,3,2,1,2,1};
	vector<int> vec(array,array+sizeof(array)/sizeof(int));
	cout<<TrapRainWater(vec)<<endl;
	return 0;
}
