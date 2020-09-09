#include <iostream>
#include <stack>
#include <deque>
#include <vector>
#include <queue>
using namespace std;

/*
二叉搜索树的基本操作
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


/**
查找二叉搜索树的最大值
*/

BinTree* Max(BinTree* root)
{
	if(root == NULL)
		return NULL;
	BinTree* temp = root;
	while(temp->left != NULL)
	{
		temp = temp->left;
	}
	return temp;
}

/*
查找二叉搜索树的最小值
*/
BinTree* Min(BinTree* root)
{
	if(root == NULL)
		return NULL;
	BinTree* temp = root;
	while(temp->right != NULL)
	{
		temp = temp->right;
	}
	return temp;
}

/*
在二叉搜索树中查找元素
*/
BinTree* Search(BinTree* root,BinTree*& parent,int value)
{
	if(root == NULL )
		return NULL;
	BinTree* temp = root;
	parent = NULL;
	while(temp != NULL)
	{
		if(temp->value == value)
			break;
		else
		{
			parent = temp;
			if(temp->value > value)
				temp = temp->left;
			else
				temp = temp->right;
		}
	}
	if(temp == NULL)
	{
		parent = NULL;
		return NULL;
	}
	return temp;
}


/*
删除二叉搜索树种的某个节点
*/
void Delete(BinTree*& root,int value)
{
	BinTree* delnode = NULL;
	if(root == NULL)
		return ;
	BinTree* temp = root;
	BinTree* parent = NULL;
	//首先需要查找该元素是否存在
	while(temp != NULL)
	{
		if(temp->value == value)
			break;
		else
		{
			parent = temp;
			if(temp->value > value)
				temp = temp->left;
			else
				temp = temp->right;
		}
	}
	//如果没有找到被删除的节点，那么直接返回
	if(temp == NULL)
		return ;
	delnode = temp;
	/*
	删除的节点被找到，接下来进行删除的操作，分为三种情况，
	第一种情况是待删除节点没有孩子，那么直接删除
	第二种情况是待删除节点有一个孩子（有可能是左孩子也有可能是右孩子）
	第三种情况是待删除节点有两个孩子
	*/
	/*
	第一种情况，直接删除此节点
	*/
	if(delnode->right == NULL && delnode->left == NULL)
	{
		if(delnode == root)
		{
			root = NULL;
		}
		if(parent && parent->left == delnode)
		{
			parent->left = NULL;
		}
		if(parent && parent->right == delnode)
		{
			parent->right = NULL;
		}
		delete delnode;
	}
	//如果此节点有一个孩子
	if(delnode->right != NULL && delnode->left == NULL)
	{
		if(parent != NULL)
		{
			if(parent->left == delnode)
				parent->left = delnode->right;
			else if(parent->right == delnode)
				parent->right = delnode->right;
		}
		else
		{
			root = delnode->right;
		}
		delete delnode;
	}
	if(delnode->left != NULL && delnode->right == NULL)
	{
		if(parent != NULL)
		{
			if(parent->left == delnode)
				parent->left = delnode->left;
			else if(parent->right == delnode)
				parent->right = delnode->left;
		}
		else
		{
			root = delnode->left;
		}
		delete delnode;
	}
	//两个节点都不为空 找一个合适的值替代
	if(delnode->left != NULL && delnode->right != NULL)
	{
		temp = delnode->right;
		parent = delnode;
		while(temp->left != NULL)
		{
			parent = temp;
			temp = temp->left;
		}
		delnode->value = temp->value;
		parent->left = temp->right;
		delete temp;
	}
}

int main()
{
	int array[]={30,22,45,15,24,40,50,10};
	BinTree* root = NULL;
	for(int i=0;i<sizeof(array)/sizeof(int);i++)
		root = InsertNode(root,array[i]);
	Delete(root,30);
	return 0;
}
