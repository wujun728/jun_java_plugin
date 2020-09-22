#include <iostream>
#include <vector>
#include <string>
#include <stack>
using namespace std;

/*
判断一个括号字符串组成的序列是否合法
使用栈的思想 ,最后判断栈是否为空
*/
bool isValidParenthess(string& str)
{
	stack<char> st;
	int i;
	char tmp;
	//开始借助栈这个数据结构的特性来遍历整个字符串
	for(i=0;i<str.length();i++)
	{
		if(str[i]=='(' || str[i]=='[' || str[i]=='{')
			st.push(str[i]);
		else if(str[i]==']')
		{
			tmp = st.top();
			if(tmp != '[')
				return false;
			else
				st.pop();
		}
		else if(str[i] ==')')
		{
			tmp = st.top();
			if(tmp != '(')
				return false;
			else
				st.pop();
		}
		else if(str[i]=='}')
		{
			tmp = st.top();
			if(tmp != '{')
				return false;
			else
				st.pop();
		}
		else
			return false;

	}
	if(st.empty())
		return true;
	else
		return false;
}

/*
使用递归的方法将栈中的数据进行翻转
思路：如果栈为空，那么直接将元素放到栈的底部即可，如果栈中有元素，
那么取出栈中的元素，将原来的元素再次调用函数存放到栈底，然后将取出的元素压入栈
即可。
*/

//当某一个元素放入栈底部
void Push_Bottom(stack<int>& st,int value)
{
    int tmp;
    //如果栈为空，那么直接将当前元素压入栈
    if(st.size()==0)
        st.push(value);
    else  //如果栈中有元素，那么取出其中的元素，然后再将元素压入
    {
        tmp = st.top();
        st.pop();
        Push_Bottom(st,value);
        st.push(tmp);
    }
}

/*
翻转栈
*/
void Reverse_st(stack<int>& st)
{
    int tmp;
    if(st.size()<=1)
        return;
    else
    {
    	//取出栈顶元素
        tmp = st.top();
        st.pop();
        //递归调用，翻转剩余的元素
        Reverse_st(st);
        //将取出的元素放入栈底部
        Push_Bottom(st,tmp);
    }
}

int main()
{
	string str("{()[]{}}");
	cout<<isValidParenthess(str);
	stack<int> st;
        int i;
        int tmp;
        for(i=0;i<5;i++)
            st.push(i);

        Reverse_st(st);
    //  Push_Bottom(st,5);
        while(!st.empty())
        {
            tmp = st.top();
            cout<<tmp<<endl;
            st.pop();
        }
	return 0;
}
