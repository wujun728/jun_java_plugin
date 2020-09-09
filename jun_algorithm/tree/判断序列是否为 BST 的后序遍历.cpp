#include <iostream>
#include <vector>
using namespace std;

/*
判断一个序列是否为二叉搜索树的后序遍历序列

对于后序遍历，一定要明白最后一个元素是根节点，左子树都比根元素大，
右子树都比根元素小,然后递归检测左右子树
*/

bool helper_verify(vector<int>& vec,int begin,int end)
{
	int i,j;
	if(end-begin <=1)
		return true;
	for(i=begin;i<end;i++)
		if(vec[i] > vec[end])
			break;
	for(j=i;j<end;j++)
		if(vec[j] < vec[end])
			break;
	if(j<end-1)
		return false;
	return helper_verify(vec,begin,i-1) && helper_verify(vec,i,end-1);
}

bool verifyBST(vector<int>& vec)
{
	if(vec.size() <=1)
		return true;
	return helper_verify(vec,0,vec.size()-1);
}



int main()
{
	int array[]={1,3,2,5,6,8,7,4};
	vector<int> vec(array,array+sizeof(array)/sizeof(int));
	cout<<verifyBST(vec);
	return 0;
}
