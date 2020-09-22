#include <stdio.h>
#include <stdlib.h>

//查找字符串中各个字符出现的次数
void FindTimes(char* str)
{
	int hash[256]={0};
	int index =0;
	while(str[index]!='\0')
	{
		hash[str[index]]++;
		index++;
	}

	for(index=0;index<256;index++)
		printf("%d ",hash[index]);
	printf("\r\n");
}
// 如何翻转一个句子，将整个句子以单词为单位进行翻转
/**
首先完成整个句子的翻转
然后完成内部单词的二次翻转
*/

//对字符串中固定位置的区域进行翻转
void Reverse(char* str,int begin,int end)
{
	char tmp;
	while(begin < end)
	{
		tmp = str[begin];
		str[begin] = str[end];
		str[end] = tmp;
		begin++;
		end--;
	}
}

/*
字符串内部单词的翻转 从头部开始 每确定一个单词就进行翻转
*/

void Reverse_second(char* str,int len)
{
	int begin,i,j;
	for(i=0;i<len;)
	{
		begin =i;
		for(j=i;j<len;j++)
		   if(str[j]==' ')
		      break;
		Reverse(str,begin,j-1);
		i = j+1;
	}
}
int main()
{
	char str[]="Hello world dunso";
//	FindTimes(str);
    Reverse(str,0,16);
    Reverse_second(str,17);
    printf("%s \r\n",str);
	return 0;
}
