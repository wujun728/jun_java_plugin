#include <stdio.h>
#include <stdlib.h>

// 排序字符串 使得字符串中大写字符在前 小写字符在后
void StrSort(char* str)
{
	int index =-1;
	int pos =0;
	char temp;
	for(;str[pos]!='\0';pos++)
	{
		if(str[pos] >= 'A' && str[pos]<='Z')
		{
			index++;
			temp = str[index];
			str[index] = str[pos];
			str[pos]= temp;
		}
	}
}

// 同样的要求 但是使得小写字符的相对顺序不改变
void StrSort2(char* str)
{
	int len =0;
	while(str[len]!='\0')
		len++;
	int index =len;
	int pos = len-1;
	char temp;

	for(;pos>=0;pos--)
	{
		if(str[pos] >= 'a' && str[pos] <= 'z')
		{
			index--;
			temp = str[index];
			str[index]=str[pos];
			str[pos] = temp;
		}
	}
}


int main()
{
	char str[] ="jdfIhdHHfdslfdSFDldfaDSKdIdgHWlkfDf";
	printf("%s\n",str);
	StrSort(str);
	printf("%s\n",str);

	char str[] ="jdfIhdHHfdslfdSFDldfaDSKdIdgHWlkfDf";
	StrSort2(str);
	printf("%s\n",str);
	return 0;
}
