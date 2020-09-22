#include <iostream>
#include <vector>
#include <string>
using namespace std;
/*
题目：一个字符串能包含另一个字符串中所有字幕的子串的最小长度
思路：使用两个指针，一个hash表，两个指针为了记录宽带，hash表为了记录是否存在
目标子串在两个指针之间出现了多少次，直到所有字符都出现在这段子串内，
移动前面的指针，直到某一个子串出现在子串中一次
那么这个空间的长度就是当前最短的，这样遍历直到把所有字符串遍历结束
*/

string MinLength(string& src,string& dest)
{
	int i=0,j=0;
	int flag =0;
	int len = src.size();
	int pos =0;
	vector<int> hash(26,-1);
	for(i=0;i<dest.length();i++)
		hash[dest[i]-'A'] =0;
	//开始处理
	for(i=0;i<src.size();i++)
	{
		if(hash[src[i]-'A'] >= 0)
		{
			hash[src[i]-'A']++;
			if(hash[src[i]-'A'] ==1)
				flag++;
		}
		if(flag == dest.length())
		{
			for(;j<i;j++)
			{
				if(hash[src[j]-'A'] ==1)
					break;
				else
					hash[src[j]-'A']--;
			}
			if(len>i-j+1)
			{
				len =i-j+1;
				pos = j;
			}

			hash[src[j]-'A'] =0;
			j++;
			flag--;
		}
	}
	cout<<string(src,pos,pos+len)<<" pos is "<<pos<<" len is "<<len<<endl;
	return string(src,pos,pos+len);
}


int main()
{
	string src("ADOBECODEBANCAAABC");
	string dest("ABC");
	cout<<MinLength(src,dest)<<endl;
	return 0;
}
