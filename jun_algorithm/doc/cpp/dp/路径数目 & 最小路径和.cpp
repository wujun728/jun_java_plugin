#include <iostream>
#include <vector>
#include <limits>
using namespace std;

/*
思路：对于某一点dp[i][j]的路径数目，是该点正上方和正左方路径数目之和
dp[i][j] = dp[i][j-1] + dp[i-1][j]; 但是对于特殊地方需要特殊考虑 
*/

int Unique_path(int m,int n,int first,int second)
{
	vector<vector<int> > dp(m);
	int i,j;
	for(i=0;i<dp.size();i++)
		dp[i].assign(n,0);
	dp[0][0] =1;
	for(i=0;i<dp.size();i++)
	{
		for(j=0;j<dp[0].size();j++)
		{
			if(i!=0 || j!=0)
			{
				if(i == first && j == second)
					dp[i][j] =0;
				else
				{
					if(i == 0)
						dp[i][j] = dp[i][j-1];
					else if(j== 0)
						dp[i][j] = dp[i-1][j];
					else
						dp[i][j] = dp[i][j-1]+dp[i-1][j];
				}
			}
		}
	} 
	return dp[m-1][n-1];
} 

/*
第二个问题，从左上角到右下角，寻找代价最小的路径 
典型的动态规划问题，和上个问题类似
*/

int MinPathSum(vector<vector<int> >& vec)
{
	vector<vector<int> > dp(vec.size());
	int i,j;
	for(i=0;i<vec.size();i++)
		dp[i].assign(vec[i].size(),numeric_limits<int>::max());
	dp[0][0] = vec[0][0];
	for(i=1;i<vec.size();i++)
		dp[i][0] = vec[i][0]+dp[i-1][0];
	for(j=1;j<vec[0].size();j++)
		dp[0][j] = vec[0][j] + dp[0][j-1];
	
	int tmp;
	for(i=1;i<vec.size();i++)
	{
		for(j=1;j<vec[0].size();j++)
		{
			tmp = min(vec[i][j]+dp[i][j-1],vec[i][j]+dp[i-1][j]);
			dp[i][j] = min(dp[i][j],tmp);
		}		
 	}
	return dp[vec.size()-1][vec[0].size()-1];
} 


int main()
{
//	cout << Unique_path(3,7,2,3)<<endl;
	vector<vector<int> > vec(3);
	int i,j;
	int array[]={2,4,3,7};
	int array1[]={5,3,2,1};
	int array2[]={4,8,6,2};
	vec[0].assign(array,array+4);
	vec[1].assign(array1,array1+4);
	vec[2].assign(array2,array2+4);
	cout<<MinPathSum(vec)<<endl;
	return 0;
} 
