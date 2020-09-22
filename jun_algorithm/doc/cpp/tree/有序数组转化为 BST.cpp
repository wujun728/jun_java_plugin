#include <iostream>
#include <vector>
using namespace std;
/*
将一个有序数组高效地插入到二叉搜索树中
*/
typedef struct Bin_tree BinTree;
struct Bin_tree
{
	int value;
	BinTree* right;
	BinTree* left;
};

/*将有序数组插入BST*/
void InsertFromArray(BinTree*& root,int* array,int start,int end)
{
	if(start >end)
		return ;
	//初始化一个节点
	root = new BinTree;
	root->left = NULL;
	root->right = NULL;
	//找到有序数组的中间点作为根节点
	int mid = start+(end-start)/2;
	root->value = array[mid];
	//然后递归调用创造左子树和右子树
	InsertFromArray(root->left,array,start,mid-1);
	//创建右子树
	InsertFromArray(root->right,array,mid+1,end);
}

/*
递归中序遍历
*/
void Inorder(BinTree* root)
{
	if(root == NULL)
		return;
	Inorder(root->left);
	cout<<root->value<<endl;
	Inorder(root->right);
}

int main()
{
	int array[]={1,2,3,4,5,6,7,8,9};
	BinTree* root = NULL;
	InsertFromArray(root,array,0,8);
	Inorder(root);
	return 0;
}
