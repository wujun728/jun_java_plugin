#include <iostream>
#include <vector>
#include <algorithm>
#include <numeric>
using namespace std;
/*
在一个数组中，找到合适的四个数，使其和为0
*/
void helper(vector<int>& vec,int index,int num,vector<int>& target)
{
    if(index >= vec.size())
        return ;
    target.push_back(vec[index]);
    num--;
    if(num ==0)
    {
        int i=0;
        int sum=0;
        sum =accumulate(target.begin(),target.end(),sum);
        if(sum == 0)
        {
            for(i=0;i<target.size();i++)
                cout<<target[i]<<" ";
            cout<<endl;
        }

    }
    helper(vec,index+1,num,target);
    target.pop_back();
    num++;
    helper(vec,index+1,num,target);
}

void FourSum(vector<int>& vec)
{
    if(vec.size()==0)
        return ;
    vector<int> target;
    helper(vec,0,4,target);
}


int main()
{
	int array[]={1,0,-1,0,-2,2};
	vector<int> vec(array,array+sizeof(array)/sizeof(int));
	FourSum(vec);
	return 0;
}
