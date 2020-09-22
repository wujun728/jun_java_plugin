#include <iostream>
#include <vector>
#include <string>
using namespace std;
/*
题目：最长没有重复字符的子串
思路：假设所有的字符都是26个小写英文字符。以此建立hash表，
hash表内记录着字符出现的位置，如果发现当前字符在以前遍历过的字符串中已经存在
那么从上次出现这个字符的位置的下一个字符重新遍历
那么把hash表清空，继续从当前位置开始遍历
*/

int Longest_substring(string& str)
{
	vector<int> hash(26,-1);
	int i;
	int cur =0;
	int maxlen =0;
	int tmp;
	for(i=0;i<str.size();i++)
	{
		if(hash[str[i]-'a'] ==-1)
		{
			hash[str[i]-'a'] =i;
			++cur;
			maxlen = maxlen > cur?maxlen:cur;
		}
		else
		{
			tmp = hash[str[i]-'a'];//此时记录的位置
			i = tmp;
			cur =0;
			//memset(&hash[0],-1,sizeof(int)*hash.size());
		}
	}
	return maxlen;
}
/*
同样的思想 简洁的方法
*/
int lengthOfLongeststring(string s)
{
	vector<int> charIndex(256,-1);//这里扩展到了所有的字符
	int longest =0;
	int m=0;

	for(int i=0;i<s.size();i++)
	{
		m = max(charIndex[s[i]]+1,m);
		charIndex[s[i]] =i;
		longest = max(longest,i-m+1);
	}
	return longest;
}
int main()
{
	string str("abcabferafjlkcbb");
	cout<<lengthOfLongeststring(str)<<endl;
	return 0;
}
