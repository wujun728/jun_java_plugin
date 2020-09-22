#include <iostream>
#include <stack>
#include <vector>
#include <deque>
#include <queue>
using namespace std;

/*
二叉树的基本操作
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
层次遍历二叉树 使用队列或者双端队列
*/

void FloorVisit(BinTree* root)
{
	if(root == NULL)
		return ;
	deque<BinTree*> dequ;
	dequ.push_back(root);
	BinTree* current = NULL;
	//开始遍历
	while(dequ.size())
	{
		current = dequ.front();
		dequ.pop_front();
		cout<<current->value<<endl;
		if(current->left != NULL)
			dequ.push_back(current->left);
		if(current->right != NULL)
			dequ.push_back(current->right);
	}
}

/*
二叉树中节点的个数
*/
int NumOfTree(BinTree* root)
{
	if(root == NULL)
		return 0;
	return (NumOfTree(root->right)+NumOfTree(root->left)) +1;
}

/*
二叉树的深度
*/

int Depth(BinTree* root)
{
	if(root == NULL)
		return 0;
	int left = Depth(root->left);
	int right = Depth(root->right);
	return (left > right ? left:right)+1;
}

/*
二叉树的宽度 （二叉树的宽度就是节点最多的那层中节点的个数）
*/
void Width(BinTree* root,int& width)
{
	if(root == NULL)
		return;
	deque< BinTree*> dequ;
	dequ.push_back(root);
	BinTree* current;
	width =0;
	int tempWidth= dequ.size();
	//开始查找宽度
	while(tempWidth)
	{
		if(tempWidth > width)
			width = tempWidth;
		while(tempWidth)
		{
			current = dequ.front();
			dequ.pop_front();
			if(current->left != NULL)
				dequ.push_back(current->left);
			if(current->right != NULL)
				dequ.push_back(current->right);
			tempWidth--;
		}
		tempWidth = dequ.size();
	}
}

/*
二叉树中跟到叶子节点的路径
*/
void Routh(BinTree* root,vector<BinTree*>& vec)
{
	if(root == NULL)
		return ;
	vec.push_back(root);
	if(root->left == NULL && root->right == NULL)
	{
		vector<BinTree*>::iterator itr = vec.begin();
		for(;itr!=vec.end();itr++)
		{
			cout<<(*itr)->value<<endl;
		}
		cout<<"-----"<<endl;
	}
	Routh(root->left,vec);
	Routh(root->right,vec);
	vec.pop_back();
}

/*
判断两棵二叉树是否结构相同
*/
bool JudeSame(BinTree* first,BinTree* second)
{
	if(first == NULL && second == NULL)
		return true;
	if((first == NULL && second!=NULL) || (first != NULL && second == NULL))
		return false;
	if(first->value != second->value)
		return false;
	return (JudeSame(first->left,second->left)&&(JudeSame(first->right,second->right)));

}

/*
二叉树的镜像转换
*/
void Reverse(BinTree* root)
{
	if(root == NULL)
		return;
	BinTree* temp = NULL;
	temp = root->right;
	root->right = root->left;
	root->left = temp;
	//递归调用函数，使得左右子树都进行转换
	Reverse(root->left);
	Reverse(root->right);
}

int main()
{
	int array[]={30,22,45,15,24,40,50,10};
	BinTree* root = NULL;
	for(int i=0;i<sizeof(array)/sizeof(int);i++)
		root = InsertNode(root,array[i]);
	//FloorVisit(root);
	vector<BinTree*> vec;
	Routh(root,vec);
	return 0;
}
