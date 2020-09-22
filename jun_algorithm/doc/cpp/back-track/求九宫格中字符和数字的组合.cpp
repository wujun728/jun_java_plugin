#include <iostream>
#include <string>
#include <vector>
using namespace std;
/*
题目：在九宫格手机键盘上，每个数字都对应着几个字符
给出一个数字的字符串，找出所有对应的字符串的组合，每个
数字找一个字符进行对应 
*/
void helper(string& str,int begin,vector<string>& hash,vector<char>& vec)
{
	 int i;
	 if(begin > str.length())
	 	return;
	if(begin == str.length())
	{
		for(i=0;i<vec.size();i++)
			cout<<vec[i];
		cout<<endl;
		return;
	}
	for(i=0;i<hash[str[begin]-'0'].length();i++)
	{
		vec.push_back(hash[str[begin]-'0'][i]);
		helper(str,begin+1,hash,vec);
		vec.pop_back();
	}
}

void Combination(string& str,vector<string>& hash)
{
	if(str.length()==0)
		return ;
	vector<char> vec;
	helper(str,0,hash,vec);
}

void LetterCom(string& str)
{
	int i;
	vector<string> hash(10); //这里假设有10个数字 
	hash[0]=" ";
	hash[1]="-";
	hash[2]="abc";
	hash[3]="def";
	hash[4]="ghi";
	hash[5]="jkl";
	hash[6]="mno";
	hash[7]="pqrs";
	hash[8]="tuv";
	hash[9]="wxyz";
	
	Combination(str,hash);
	return ;
} 

int main() 
{
	string str("23");
	LetterCom(str);
	return 0;
}
