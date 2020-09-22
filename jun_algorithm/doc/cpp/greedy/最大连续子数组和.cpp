#include <iostream>
#include <vector>
#include <string>
using namespace std;

/*
最大连续子数组和 
*/
int MaxSubarray(vector<int>& vec)
{
	int sum = vec[0];
	int curmax= vec[0];
	int i;
	for(i=1;i<vec.size();i++)
	{
		curmax += vec[i];
		if(curmax < 0)
			curmax = 0;
		if(curmax > sum)
			sum = curmax;
	}
	if(sum<0)
	{
		for(i=0;i<vec.size();i++)
			if(sum < vec[i])
				sum = vec[i];
	}
	return sum;
} 

int main()
{
	int array[]={-1,1,-3,4,-1,2,1,-5,4};
	vector<int> vec(array,array+sizeof(array)/sizeof(int));
	cout<<MaxSubarray(vec)<<endl;
	return 0;
}
