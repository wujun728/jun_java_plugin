#include <iostream>
#include <string>
#include <stack>
using namespace std;

/*
最长合法括号匹配长度
*/

/*
使用一个bool数组来标记已经匹配过的字符
直到最长的连续标记的长度就是所求的结果，只需要两次遍历数组，时间复杂度为O(n)
*/
int LongestValidParentheses(string s) {
         bool *a = new bool[s.length()];
        //memset(a, false, s.length());
         stack<int> st;
         for (int i = 0; i < s.length(); ++i) {
              if (s[i] == '(') {
                  st.push(i);
             } else if (s[i] == ')' && !st.empty()) {
                 a[i] = true;
                 a[st.top()] = true;
                 st.pop();
             }
         }
         int max_len = 0, cur_len = 0;
         for (int i = 0; i < s.length(); ++i) {
             if (a[i])
			 	cur_len++;
             else
			 	cur_len = 0;
            max_len = max(max_len, cur_len);
         }
         return max_len;
}

/*
如果我们使用栈记录某一个符号在字符串中的位置，假如对于当前字符和栈顶位置的
字符可以匹配，那么可以根据栈是否为空来判断当前最长匹配子序列
然后和目标进行匹配
*/
int LongestValidParentheses2(string s) {
        stack<int> st;
        int pos;
        int maxlen = 0;
        for(int i = 0 ; i < s.size() ; i++)
        {
            if(s[i] == '(') st.push(i);
            else  // ')'
            {
				if(!st.empty() && s[st.top()]=='(')
				{

					st.pop();
					if(st.empty())
						pos = i+1;
					else
						pos = i-st.top();
					if(maxlen < pos)
						maxlen = pos;
				}
				else
					st.push(i);
            }

        }
        return maxlen;
}

int main()
{
	string str=")()())";
	cout<<LongestValidParentheses(str);
	//cout<<endl<<Longest_dp(str);
//	cout<<str<<endl;

	return 0;
}


