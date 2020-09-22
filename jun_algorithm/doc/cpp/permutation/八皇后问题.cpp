#include <iostream>
#include <vector>
using namespace std;
/*
题目要求：
在8*8的国际象棋上摆放着八个皇后，使其不能互相攻击
也就是说，任意两个皇后不得处在同一行、同一列或者同一对角线上
请问有多少种方法
*/

/*
八皇后问题
*/
int g_number=0;
  void Print(int ColumnIndex[] , int length)
    {
        cout<<g_number<<endl;

        for(int i = 0 ; i < length; ++i)
            cout<<ColumnIndex[i]<<" ";
       cout<<endl;
    }

    bool Check(int ColumnIndex[] , int length)
    {
        int i,j;
        for(i = 0 ; i < length; ++i)
        {
            for(j = i + 1 ; j < length; ++j)
            {
                if( i - j == ColumnIndex[i] - ColumnIndex[j] || j - i == ColumnIndex[i] - ColumnIndex[j])   //在正、副对角线上
                    return false;
            }
        }
        return true;
    }
    void Permutation(int ColumnIndex[] , int length , int index)
    {
        if(index == length)
        {
            if( Check(ColumnIndex , length) )   //检测棋盘当前的状态是否合法
            {
                ++g_number;
                Print(ColumnIndex , length);
            }
        }
        else
        {
            for(int i = index ; i < length; ++i)   //全排列
            {
                swap(ColumnIndex[index] , ColumnIndex[i]);
                Permutation(ColumnIndex , length , index + 1);
                swap(ColumnIndex[index] , ColumnIndex[i]);
            }
        }
    }


 void EightQueen( )
    {
        const int queens = 8;
        int ColumnIndex[queens];
        for(int i = 0 ; i < queens ; ++i)
            ColumnIndex[i] = i;    //初始化
        Permutation(ColumnIndex , queens , 0);
    }
int main()
{
	EightQueen();
	return 0;
}
