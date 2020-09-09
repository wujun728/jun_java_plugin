#include <iostream>
#include <vector>
using namespace std;
/*
全排列问题
以字符串abc为例
首先固定a，求后面两个字符bc的全排列，结束之后。
把第一个字符a和后面的b交换，得到bac，借助固定第一个字符b，求后面两个字符ac
的排列，然后把c放到第一位置....
*/

int count =0;//记录排列个数
void Permutation(char* str,int begin)
{
	if(str[begin] == '\0')
	{
		cout<<str<<endl;
		count++;
	}
	for(int i= begin;str[i] != '\0';i++)
	{
		swap(str[i],str[begin]);//交换当前位置和第一个位置
		Permutation(str,begin+1);//求除第一位置之外的字符串的排列
		swap(str[i],str[begin]);//回归原始状态
	}
}

/*
去除重复的全排列的实现
如果一个数和后面的数相同，那么这两个数就不交换
也就是说，去除重复的全排列就是从第一个元素起每个元素分别与他后面非
重复出现的元素交换
*/
bool isValid(vector<int>& vec,int begin,int index)
{
	int i;
	for(i = begin;i<index;i++)
	{
		if(vec[i] == vec[index])
			return false;

	}
	return true;
}

void Permutation(vector<int>& vec,int begin,int end)
{
	int i=0;
	if(begin >= end)
	{
		for(i=0;i<vec.size();i++)
			cout<<vec[i]<<" ";
		cout<<endl;
		return ;
	}
	for(i = begin;i<= end;i++)
	{
		if(isValid(vec,begin,i))
		{
			swap(vec[begin],vec[i]);
			Permutation(vec,begin+1,end);
			swap(vec[begin],vec[i]);
		}
	}
}

int main()
{

	vector<int> vec(3);
	int i;
	for(i=0;i<vec.size();i++)
		vec[i] = i+1;
	vec[1]=1;
	int pos =0;
	Permutation(vec,pos,2);
	char str[]="abcd";
//	Permutation(str,0);
//	cout<<count<<endl;
	return 0;
}
