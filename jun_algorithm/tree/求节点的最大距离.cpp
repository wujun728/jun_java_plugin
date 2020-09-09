#include <iostream>
#include <stack>
using namespace std;

/*
求二叉树中节点的最大距离
*/
typedef struct Bin_tree BinTree;
struct Bin_tree
{
       int value;
       BinTree* right;
       BinTree* left;
};
/*
  构造二叉查找树
*/
BinTree* InsertNode(BinTree* root,int data)
{
         BinTree* newnode = new BinTree;
         newnode->value = data;
         newnode->right = NULL;
         newnode->left = NULL;
         if(root == NULL)
         {
                 root = newnode;
                 }
         else
         {
             BinTree* parent = root;
        //     BinTree* cur = NULL;
             while(parent != NULL)
             {
                     if(parent->value < data)
                     {
                        if(parent->right == NULL)
                          break;
                        else
                          parent = parent->right;
                     }
                     else
                     {
                         if(parent->left == NULL)
                           break;
                         else
                           parent = parent->left;
                     }
             }
             if(parent->value < data)
              parent->right = newnode;
             else
              parent->left = newnode;
             }
         return root;
}
/*
  递归 前序遍历二叉树
*/
void Preorder(BinTree* root)
{
     if(root == NULL)
       return ;
     cout<<root->value <<endl;
     Preorder(root->left);
     Preorder(root->right);
}

/*
  递归 中序遍历二叉树
*/
void Inorder(BinTree* root)
{
     if(root == NULL)
       return ;
     Inorder(root->left);
     cout<<root->value<<endl;
     Inorder(root->right);
}

/*
二叉树中任意两个节点的最远距离，这里的距离就是边的个数
*/
/*
分情况讨论：
最远的两个节点一个在右子树，一个在左子树，那么就是左子树种最远节点
到当前节点的距离加上右子树种最远节点到当前节点的距离，这种情况最远
距离跨越了根节点。
第二种情况就是最远距离的两个节点都在右子树
第三种情况就是最远距离的两个节点都在左子树
*/

/*
distance是以root为根的树的深度
返回值就是以root为根的树，任意两点的最远距离
*/
int Distance(BinTree* root,int& depth)
{
	if(root == NULL)
	{
		depth = 0;
		return 0;
	}
	int left,right;
	int left_dis = Distance(root->left,left);
	int right_dis = Distance(root->right,right);
	depth = (left > right ? left: right)+1;

	return max(left_dis,max(right_dis,left+right));
}

/*
如果二叉树为空，深度都为0
如果不为空，最大距离要么是左子树的最大距离，要么是右子树
的最大距离，要么是左子树到根节点的最大距离+右子树节点中到根节点
最大距离
*/

int MaxDepth(BinTree* root)
{
	int depth =0;
	if(root != NULL)
	{
		int left_depth = MaxDepth(root->left);
		int right_depth = MaxDepth(root->right);
		depth = (left_depth > right_depth? left_depth: right_depth);
		depth++;
	}
	return depth;
}

int MaxDistance(BinTree* root)
{
	int maxdis = 0;
	if(root != NULL)
	{
		maxdis = MaxDepth(root->right) + MaxDepth(root->left);
		int left_dis = MaxDistance(root->left);
		int right_dis = MaxDistance(root->right);
		int temp = left_dis > right_dis? left_dis : right_dis;
		maxdis = temp > maxdis ? temp:maxdis;
	}
	return maxdis;
}


int GetMaxDistance(BinTree* root,int& maxLeft,int& maxRight)
{
	//maxLeft，左子树中的节点距离当前根节点的最远距离
	//maxRight 右子树中的节点距离当前根节点的最远距离
	if(root == NULL)
	{
		maxLeft  =0;
		maxRight = 0;
		return 0;
	}
	int maxLL,maxLR,maxRL,maxRR;
	int maxDisLeft,maxDisRight;
	if(root->left != NULL)
	{
		maxDisLeft = GetMaxDistance(root->left,maxLL,maxLR);
		maxLeft = max(maxLL,maxLR)+1;
	}
	else
	{
		maxDisLeft = 0;
		maxLeft = 0;
	}
	if(root->right != NULL)
	{
		maxDisRight = GetMaxDistance(root->right,maxRL,maxRR);
		maxRight = max(maxRL,maxRR)+1;
	}
	else
	{
		maxDisRight = 0;
		maxRight = 0;
	}
	return max(max(maxDisLeft,maxDisRight),maxLeft+maxRight);
}

int main()
{

	BinTree* root = NULL;
	int array[]={10,6,14,4,8,12,16,5};
	int i;
	for(i=0;i<sizeof(array)/sizeof(int);i++)
		root = InsertNode(root,array[i]);
	int dis=0;
	cout<<Distance(root,dis)<<endl;
	int right = 0;
	int left = 0;
	cout<<MaxDistance(root)<<endl;
	cout<<GetMaxDistance(root,left,right)<<endl;
	return 0;
}
