#include <stdio.h>
#include <stdlib.h>

// 一个已经排序好的字符串 将重复的字符串保留一个

void Remove(char* str)
{
	int index =0;
	int pos =1;
	for(;str[pos]!='\0';pos++)
	{
		if(str[pos] != str[index])
		{
			index++;
			str[index]= str[pos];
		}
	}
	str[index+1] ='\0';
}

// 一个已经排序好的字符串 将重复的字符保留两个
void Remove2(char* str)
{
	int index =0;
	int pos =1;
	int flag =0;
	for(;str[pos]!='\0';pos++)
	{
		//如果当前位置的字符和已经保存的字符的最后一个位置的字符不同
		if(str[pos] != str[index])
		{
			index++;
			str[index] = str[pos];
			flag=0;
		}
		//如果当前位置的字符和已经保存的字符的最后一个位置的字符相同
		else
		{
			if(flag == 0)
			{
				index++;
				str[index] = str[pos];
				flag =1;
			}
		}
	}
	str[index+1]='\0';
}

//重复的字符全部删除
void Remove3(char* str)
{
	int flag =0;
	int index =-1;
	int pos;
	//遍历所有字符
	for(pos=0;str[pos+1]!='\0';)
	{
		//如果当前字符和下一个字符相同
		if(str[pos] == str[pos+1])
		{
			flag =1;
			pos++;
		}
	    //如果当前字符和下一个字符不同
		else
		{
			if(flag == 1)
			{
				flag =0;
				pos++;
			}
			else
			{
				index++;
				str[index] = str[pos];
				pos++;
			}
		}
	}
	if(str[pos]!= str[pos-1])
		str[++index] = str[pos];
	str[index+1] = '\0';
}

int main()
{
	char str[]="AAABCCCDFGGIIIKKLMNNOOPQQQQ";
	printf("%s\n",str);
	Remove3(str);
	printf("%s\n",str);
	return 0;
}
