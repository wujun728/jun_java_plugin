/*
输入一个字符串，输出该字符串中字符的所有组合
*/

#include <iostream>
#include <vector>
using namespace std;
/*
思路：在长度为n的字符串中求m个字符的组合
从头扫描字符串得第一个字符，针对第一个字符，有两种选择
把这个字符放到组合中去，接下来我们需要在剩下的n-1个字符中选取m-1个字符
不把这个字符放到组合中去，需要在剩下的n-1个字符中选取m个字符
*/
void Combination(char* string, int number, vector<char>& result)
{
    if(number == 0)
    {
        vector<char>::iterator iter = result.begin();
        for(; iter < result.end(); ++ iter)
            cout<<(*iter);
        cout<<endl;
        return;
    }
    if(*string == '\0')
        return;
    result.push_back(*string);
    Combination(string + 1, number - 1, result);
    result.pop_back();
    Combination(string + 1, number, result);
}
void Combination(char* string)
{
    if(string == NULL)
        return;
    int length = strlen(string);
    vector<char> result;
    for(int i = 1; i <= length; ++ i)
    {
        Combination(string, i, result);
    }
}
int main()
{
	char str[] ="abc";
	Combination(str);
	return 0;
}
