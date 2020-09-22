#include <iostream>
#include <string>
#include <vector>
#include <stack>
using namespace std;
/*
一个表达式所表述的路径，进行最简化
*/
string SimplifyPath(string& str)
{
	string result;
	stack<char> st;
	if(str.length() == 0 || str[0] !='/')
		return result;
	int i;
	char tmp;
	st.push(str[0]);
	for(i=1;i<str.length();i++)
	{
		tmp = st.top();
		if(isalpha(str[i])) //当期字符是字母
			st.push(str[i]);
		if(str[i]=='/' && i!=str.length()-1)
		{
			if(tmp != '/' && tmp != '.') // 斜线
			{
				st.push(str[i]);
			}
			if(tmp == '.')
			{
				st.pop();
			}
		}

		if(str[i]=='.') //逗点
		{
			if(tmp == '.')
			{
				st.pop();
				st.pop();
				if(!st.empty())
					tmp = st.top();
				while(!st.empty() && tmp != '/')
				{
					st.pop();
					tmp = st.top();
				}
				if(st.empty())
					st.push('/');
			}
			else
				st.push(str[i]);
		}
	}
	//处理结果
	result.append(st.size(),'c');
	i=st.size()-1;
	while(!st.empty())
	{
		tmp = st.top();
		st.pop();
		result[i--] = tmp;
	}

	return result;
}

int main()
{
	string str("/a/./b/../../c/");
	//string str("/../");
	cout<<SimplifyPath(str);
	return 0;
}
