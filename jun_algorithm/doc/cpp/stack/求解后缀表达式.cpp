#include <iostream>
#include <vector>
#include <string>
#include <stack>
#include <algorithm>
using namespace std;

/*
表达式求解，借助栈这种数据结构，需要注意字符串和整数之间的转化
*/

//将字符串转化为整型
int strtoi(string& s)
{
    if(s.length() == 0)
      return 0;
    int i=0,flag=1,result=0;
    if(s[0]=='-')
    {
       flag =-1;
       i++;
    }
    if(s[0]=='+')
    {
       i++;
    }
    for(;i<s.length();i++)
    {
        result *= 10;
        result += (s[i]-'0');
    }
    return result*flag;
}

//将整型转化为字符串
string itostr(int num)
{
    int flag =1,count = num;
    if(num <0)
    {
        flag = 0;
        count = -count;
    }

    string result;
    while(count)
    {
      result += (count%10 +'0');
      count = count/10;
    }
    if(flag == 0)
     result += '-';
    reverse(result.begin(),result.end());
    return result;
}

/*
表达式求解主函数
*/
int evalRPN(vector<string> &tokens)
{
   stack<string> sk;
   int result =0,i,temp;
   string first,second;
   //开始处理字符串向量
   for(i=0;i<tokens.size();i++)
   {
   	   //如果当前的字符串为符号，需要从栈中取出操作数
       if(tokens[i]=="+"||tokens[i]=="-"||tokens[i]=="*"||tokens[i]=="/")
       {
          first = sk.top();
          sk.pop();
          second =sk.top();
          sk.pop();
          //开始计算
          if(tokens[i] == "+")
             temp = strtoi(first)+strtoi(second);
          else if(tokens[i]=="-")
             temp = strtoi(first)-strtoi(second);
          else if(tokens[i]=="*")
             temp = strtoi(first)*strtoi(second);
          else
             temp = strtoi(second)/strtoi(first);
          //将结果继续压入栈
		  first = itostr(temp);
          sk.push(first);
       }
       else // 其他情况都只需要将当前字符串压入栈内即可
        sk.push(tokens[i]);
   }
   first = sk.top();
   result = strtoi(first);
   return result;
}

int main()
{
    string array[]={"4", "13", "5", "/", "+"};
    vector<string> tokens(array,array+sizeof(array)/sizeof(array[0]));
    cout<<evalRPN(tokens)<<endl;
    return 0;
}


