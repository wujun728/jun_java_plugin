#include <iostream>
#include <vector>
#include <string>
#include <limits>

using namespace std;

/*
实现atoi函数

需要注意的地方：
1.数值越界
2.正负数
3.字符串结束标志
*/

int StringToInt(string& str)
{
	if(str.length() == 0)
		return 0;
	int i;
	int flag =1;
	int result =0;
	int digit;

	//清空开始的空格字符
	for(i=0;i<str.length();i++)
		if(str[i] !=' ')
			break;
	//查找正负号
	if(str[i] == '+')
		i++;
	if(str[i]== '-')
	{
		flag =0;
		i++;
	}

	//开始处理字符
	for(;i<str.length();i++)
	{
		if(str[i]<'0' || str[i] > '9')
			break;
		digit = str[i]-'0';
		//判断越界
		if(flag && (numeric_limits<int>::max() - result*10) <= digit)
			return numeric_limits<int>::max();
		else if(!flag && (numeric_limits<int>::min() + result*10) >= digit*-1)
			return numeric_limits<int>::min();
		result = result*10+digit;
	}
	return flag ==1? result:-result;
}

int main()
{
	string str("-4324324878787998797");
	cout<<StringToInt(str)<<endl;
	return 0;
}
