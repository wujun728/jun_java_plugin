#include <iostream>
#include <vector>
using namespace std;

/*
进行两次扫描，一次从左向右，一次从右向左
第一次扫描的时候维护对于每一个小孩左边所需要最少的糖果数量
存入数组对应元素中，第二次扫描的时候维护右边所需的最少糖果数量
并且比较将左边和右边大的糖果数量存入结果数组对应元素中
*/

int candy(vector<int> &ratings)
{
    vector<int> candy(ratings.size(),1);
    int sum,i;
    for(i=1;i<ratings.size();i++)
    {
       if(ratings[i] > ratings[i-1])
         candy[i] = candy[i-1]+1;
    }

    sum = candy[ratings.size()-1];
    for(i=ratings.size()-2;i>=0;i--)
    {
       int cur =1;
       if(ratings[i] > ratings[i+1])
         cur = candy[i+1]+1;
       sum += max(cur,candy[i]);
       candy[i] = cur;
    }
    return sum;
}


/*
更清晰的思路
*/
int candy2(vector<int> &ratings)
{
    vector<int> candy(ratings.size(),1);
    int sum,i;
    for(i=1;i<ratings.size();i++)
    {
       if(ratings[i] > ratings[i-1])
         candy[i] = candy[i-1]+1;
    }

    sum = candy[ratings.size()-1];
    for(i=ratings.size()-2;i>=0;i--)
    {
       int cur =1;
       if(ratings[i] > ratings[i+1] && candy[i] <= candy[i+1])
        	candy[i] = candy[i+1]+1;
       sum += candy[i];

    }

    return sum;
}


int main()
{
	int array[]={4,2,6,8,5};
	vector<int> vec(array,array+sizeof(array)/sizeof(int));
	cout<<candy(vec)<<endl;
	return 0;
}
