#include <iostream>
#include <string>
#include <vector>
using namespace std;

/*
思路：
这是一个典型的DP问题，假设定义一个数组，dp[i]为到第i个字符所能够组成的
所有编码方式的个数。那么对于dp[i+1]来说，肯定至少和dp[i]一样多，如果第i
个字符和i+1个字符可以合成一个字符，那么dp[i+1] += dp[i-1]. 
*/

int Decode_num(string& str)
{
	vector<int> vec(str.size(),1);
	if(str.size() <2)
		return 1;
	if(str[0]=='1'||(str[0]=='2'&& str[1]<='6'))
		vec[1] =2;
	int i;
	int tmp;
	for(i=2;i<str.size();i++)
	{
		if(str[i] >= '0' && str[i] <= '9')//判断是合法的字符 
			vec[i] = vec[i-1];
		else
			return 0; 
		tmp = str[i-1] -'0';
		tmp = tmp*10 + str[i]-'0';
		
		if(str[i-1]!='0' && tmp <=26)
			vec[i] += vec[i-2];
		else
			vec[i] = vec[i-1];
	}
	return vec[str.size()-1];
}

int main()
{
	string str("1231725");
	cout<<Decode_num(str)<<endl;
	return 0;
} 
