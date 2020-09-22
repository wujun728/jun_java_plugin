#include <iostream>
#include <vector>
#include <string>
using namespace std;

/*
最大子串乘积，由于可能出现负数。也是DP问题，也是局部最优和全局最优问题。
这里需要记录最小值，假设有两个数组，分别记录包括当前元素在内的子串所能构成
的最大和最小值，然后根据这个再更新全局最大，至于当前最大，可能是之前最大
乘以当前元素，也可能是前一个元素最小乘以当前元素，也可能是当前元素 
*/

int maxProduct(vector<int>& vec)
{
    if(vec.size()==0)
     return 0;
    vector<int> maxcur(vec.size(),0);
    vector<int> mincur(vec.size(),0);
    maxcur[0]=vec[0];
    mincur[0]=vec[0];
    int maxproduct = vec[0];
    int i,temp;
    for(i=1;i<vec.size();i++)
    {
       maxcur[i] = max(vec[i],max(maxcur[i-1]*vec[i],mincur[i-1]*vec[i]));
       mincur[i] = min(vec[i],min(mincur[i-1]*vec[i],maxcur[i-1]*vec[i]));
       
       maxproduct = max(maxcur[i],maxproduct);                      
                           
    }
    return maxproduct;
}
 

int main()
{
	int array[] ={2,3,-2,4};
	vector<int> vec(array,array+sizeof(array)/sizeof(int));
	cout<<maxProduct(vec)<<endl;
	return 0;
} 
