#include <iostream>
#include <string>
#include <vector>
using namespace std;

/*
对于几对括号，有多少种正确的组合方式
思路：使用递归的方法，只不过在递归的时候
时刻需要保证左边的括号比右边的括号多
只有在左括号比右括号多的情况下才有可能保证整个序列为合法的括号匹配
*/

void helper(vector<char>& str,int l,int r)
{
	if(l == 0 && r == 0)
	{
		for(int i=0;i<str.size();i++)
		{
			cout<<str[i];
		}
		cout<<endl;
	}
	if(l>0)
	{
		str.push_back('(');
		helper(str,l-1,r);
		str.pop_back();
	}
	if(r>0 && l<r)
	{
		str.push_back(')');
		helper(str,l,r-1);
		str.pop_back();
	}
}

void GenerateParenthess(int n)
{
	if(n<=0)
		return ;
	vector<char> tmp;
	helper(tmp,n,n);
}

int main()
{
	GenerateParenthess(3);
	return 0;
}
