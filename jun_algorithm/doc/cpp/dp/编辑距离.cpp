#include <iostream>
#include <vector>
#include <string>
#include <limits>
using namespace std;

/*
典型的DP问题，这里需要使用S来匹配T， 利用动态规划思想
使用dp[i][j]表示S与T的前i个字符与前j个字符的匹配子串个数。
1）初始条件：T为空串时，S为任意字符串都能匹配一次，所以dp[i][0]=1 
S为空字符串，T不为空时，不能匹配，dp[0][j] =0
2)S[i] == T[j]，dp[i][j] = dp[i-1][j-1]+dp[i-1][j].表示当前字符可以保留
也可以抛弃
3）S[i] != T[j]，dp[i][j] = dp[i-1][j-1] 
*/

int Distinct_sub(string& src,string& dst)
{
	vector<vector<int> > dp(src.size());
	int i,j;
	for(i=0;i<src.size();i++)
		dp[i].assign(dst.size(),0);

	if(src[0] == dst[0])
		dp[0][0] =1;
	for(j=1;j<dp.size();j++)
		dp[j][0] = src[j]==dst[0]? dp[j-1][0]+1:dp[j-1][0];

	for(i=1;i<dp.size();i++)
	{
		for(j=1;j<dp[0].size();j++)
		{
			if(src[i] == dst[j])
				dp[i][j] = dp[i-1][j-1]+dp[i-1][j];
			else
				dp[i][j] = dp[i-1][j];
		}
	}
	return dp[src.size()-1][dst.size()-1];
}

/*
编辑距离问题 

使用便利dp[i][j]记录包括word1[i]在内的字符串和word2[j]在内的字符串的编辑
距离，如果word1[i+1] == word2[j+1] dp[i+1][j+1] = dp[i][j]，
否则dp[i+1][j+1] = dp[i][j]+1,
不过也有可能dp[i+1][j+1] = dp[i+1][j]+1或者dp[i][j+1]+1 
取三者最小 
*/

int Edit_distance(string& s1,string& s2)
{
	vector<vector<int> > dp(s1.size());
	int i,j;	
	for(i=0;i<dp.size();i++)
		dp[i].assign(s2.size(),numeric_limits<int>::max());
	if(s1[0]==s2[0])
		dp[0][0] =0;
	else
		dp[0][0] =1;
	for(i=1;i<dp[0].size();i++)
		if(s1[0] == s2[i])
			dp[0][i] = i;
		else
			dp[0][i] = dp[0][i-1]+1;
	for(i=1;i<dp.size();i++)
		if(s1[i] == s2[0])
			dp[i][0] = i;
		else
			dp[i][0] = dp[i-1][0]+1;
 
	for(i=1;i<dp.size();i++)
	{
		for(j=1;j<dp[0].size();j++)
			{
				if(s1[i] == s2[j])
					dp[i][j] = dp[i-1][j-1];
				else
					dp[i][j] = min(dp[i-1][j-1]+1,min(dp[i][j-1]+1,dp[i-1][j]+1));
 
			}
	}	
	return dp[s1.size()-1][s2.size()-1];
} 

int main()
{
	string src("rabbbit");
	string dst("rabbit");
	cout<<Distinct_sub(src,dst)<<endl;
	string s1("abcd");
	string s2("abc");
	cout<<Edit_distance(s1,s2)<<endl;
	
	return 0;
} 
