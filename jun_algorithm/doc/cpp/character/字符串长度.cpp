#include <stdio.h>
#include <stdlib.h>


//1、递归的方式求字符串长度
int RecurLength(char* str)
{
	if(str[0] == '\0')
		return 0;
	else
		return RecurLength(str+1)+1;
}

//2、求字符串中最后一个单词的长度(已知字符串的长度)
int LastWordLen(char* str,int len)
{
	int lastlen = 0;
	int index = len-1;
	while(index>=0 && str[index--] != ' ')
		lastlen++;
	return lastlen;
}

//2、求字符串中最后一个单词的长度(未知字符串的长度)
int LastWordLen2(char* str)
{
	int lastlen = 0;
	int index =0;
	while(str[index] !='\0')
	{
		if(str[index]==' ')
		  lastlen =0;
		else
		  lastlen++;
		index++;
	}
	return lastlen;
}


//字符串内存的拷贝 实现memmove函数(无覆盖拷贝)
char* my_memmove(char* dst,char* src,int count)
{
	char* ret = dst;
	if(dst == NULL || src == NULL)
		return NULL;
	//如果dst和src区域没有重叠，那么从开始处逐一拷贝
	if(dst <= src || dst >= (src+count))
	{
		while(count--)
		{
			*dst = *src;
			dst++;
			src++;
		}
	}
	//如果dst和src区域有交叉，那么从尾部开始向起始位置拷贝，这样可以避免数据重叠
	else
	{
		dst = dst + count-1;
		src = src + count-1;
		while(count--)
		{
			*dst = *src;
			dst--;
			src--;
		}
	}
	return ret;
}
int main()
{
	char str[]="Hello dunso";
	printf("str len is %d\r\n",RecurLength(str));
	printf("lastword len is %d\r\n",LastWordLen(str,12));
	printf("lastword len is %d\r\n",LastWordLen2(str));
	return 0;
}
