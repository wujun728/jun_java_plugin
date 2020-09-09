#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;
/*
组合的另一种实现
*/
void helper(vector<int>& vec,int begin,int& num,vector<int>& subset)
{
	if(begin >= vec.size() || num<0 )
		return ;
	subset.push_back(vec[begin]);
	num--;
	if(num == 0)
	{
		int i;
		for(i=0;i<subset.size();i++)
			cout<<subset[i]<<" ";
		cout<<endl;
	}
	helper(vec,begin+1,num,subset);
	subset.pop_back();
	num++;
	helper(vec,begin+1,num,subset);
}

void Combination(vector<int>& vec,int k)
{
	if(vec.size()==0 || k <0)
		return ;
	vector<int> subset;
	sort(vec.begin(),vec.end());
	helper(vec,0,k,subset);
}

void Subsets(vector<int>& vec)
{
	int i;
	for(i=0;i<=vec.size();i++)
		Combination(vec,i);
}
/*
另一种方法
*/
void generate(vector<int> res, vector<int> &S, int i)
    {
        if(i == S.size())
        {
			for(int j=0;j<res.size();j++)
				cout<<res[j]<<" ";
			cout<<endl;
            //return;
        }
        else
        {
            generate(res, S, i+1);
            res.push_back(S[i]);
            generate(res, S, i+1);
        }
    }

void SubsetSecond(vector<int>& vec)
{
	if(vec.size()<=0)
		return;

	sort(vec.begin(),vec.end());
	vector<int> result;
	generate(result,vec,0);

}


int main()
{
	int array[]={2,1,3};
	vector<int> vec(array,array+sizeof(array)/sizeof(int));
	SubsetSecond(vec);
	return 0;
}
