#include <iostream>
#include <vector>
#include <string>
using namespace std;
/*
求一个序列中元素之和等于固定值的特定组合
*/

void Combination_helper(vector<int>& vec,int begin,int target,int& cur,vector<int>& path)
{

	if(begin >=vec.size())
		return ;

	cur += vec[begin];
	path.push_back(vec[begin]);
	if(cur == target)
	{
		for(int i=0;i<path.size();i++)
			cout<<path[i]<<endl;
		cout<<"=========="<<endl;
	}
	Combination_helper(vec,begin+1,target,cur,path);
	path.pop_back();
	cur -= vec[begin];
	int j;
	for(j=begin+1;j<vec.size();)
	{
		if(vec[j] == vec[begin])
			j++;
		else
			break;
	}
	Combination_helper(vec,j,target,cur,path);
}

void Combination(vector<int>& vec,int target)
{
	vector<int> path;
	int cur = 0;
	if(vec.size() == 0)
		return ;
	Combination_helper(vec,0,target,cur,path);
}

int main()
{

	vector<int> path;
	int array[]={1,1,2,5,6,7,10};
	int cur =0;
	vector<int> vec(array,array+sizeof(array)/sizeof(int));
	Combination(vec,8);
	return 0;
}
