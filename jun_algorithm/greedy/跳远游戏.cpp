#include <iostream>
#include <vector>
using namespace std;

/*
贪心思想，时刻计算当前位置和当前位置能跳的最远长度，并始终和界限比较
若在任意位置出现最大跳步为0，那么就无法继续跳下去
在任意位置出现最大跳步+当前位置 >界限，那么说明可以跳出去 
*/

bool canJump(vector<int>& vec)
{
	if(vec.size() <=0)
		return true;
	int maxstep = vec[0];
	for(int i=1;i<vec.size();i++)
	{
		if(maxstep == 0)
			return false;
		maxstep--;
		if(maxstep < vec[i])
			maxstep = vec[i];
		if(maxstep+i >= vec.size()-1)
			return true;
	}
}

int main()
{
	int array[]={2,3,1,1,4};
	vector<int> vec(array,array+sizeof(array)/sizeof(int));
	cout<<canJump(vec)<<endl;
	return 0;
}
