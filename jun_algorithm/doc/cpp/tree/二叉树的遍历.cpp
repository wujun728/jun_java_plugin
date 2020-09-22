#include <iostream>
#include <stack>
#include <vector>
#include <deque>
using namespace std;

/*
二叉搜索树的创建
二叉树的递归遍历
二叉树的非递归遍历
*/
typedef struct Bin_Tree BinTree;
struct Bin_Tree
{
	int value;
	BinTree* right;
	BinTree* left;
};

/*
构造二叉搜索树
*/
BinTree* InsertNode(BinTree* root,int value)
{
	BinTree* newnode = new BinTree;
	newnode->value = value;
	newnode->right = NULL;
	newnode->left = NULL;
	//创建完一个新的节点，需要将此节点插入到合适的位置
	if(root == NULL)
	{
		root = newnode;
	}
	else
	{
		//找合适的位置
		BinTree* parent = root;
		while(parent != NULL)
		{
			if(parent->value < value)
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
		if(parent->value < value)
			parent->right = newnode;
		else
			parent->left = newnode;
	}
	return root;
}

/*
递归的方法前序遍历二叉树
*/
void Preorder(BinTree* root)
{
	if(root == NULL)
		return ;
	cout<<root->value<<endl;
	Preorder(root->left);
	Preorder(root->right);
}

/*
递归的方法中序遍历二叉树
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
递归的方法后序遍历二叉树
*/
void Postorder(BinTree* root)
{
	if(root == NULL)
		return ;
	Postorder(root->left);
	Postorder(root->right);
	cout<<root->value<<endl;
}

/*非递归的遍历二叉树*/

/*
非递归的前序遍历
*/
void Preorder_Nonrecursive(BinTree* root)
{
	if(root == NULL)
		return ;
	stack<BinTree*> s;
	s.push(root);
	while(!s.empty())
	{
		BinTree* temp = s.top();
		cout<<temp->value<<" ";
		s.pop();
		if(temp->right)
			s.push(temp->right);
		if(temp->left)
			s.push(temp->left);
	}
}


/*第二种方法*/

void Preorder_Nonrecursive1(BinTree* root)
{
	if(root == NULL)
		return ;
	stack<BinTree*> s;
	BinTree* cur = root;
	while(cur != NULL || !s.empty())
	{
		while(cur != NULL)
		{
			cout<<cur->value<<" ";
			s.push(cur);
			cur = cur->left;
		}
		if(!s.empty())
		{
			cur = s.top();
			s.pop();
			cur = cur->right;
		}
	}
}

/*非递归中序遍历*/
void Inorder_Nonre(BinTree* root)
{
	if(root == NULL)
		return ;
	BinTree* cur = root;
	stack<BinTree*> s;
	while(cur != NULL || !s.empty())
	{
		while(cur != NULL)
		{
			s.push(cur);
			cur = cur->left;
		}
		if(!s.empty())
		{
			cur = s.top();
			s.pop();
			cout<<cur->value<<" ";
			cur = cur->right;
		}
	}
}

/*
第二种方法
*/
void  Inorder_Nonre2(BinTree* root)
{
	if(root == NULL)
		return ;
	stack<BinTree*> st;
	BinTree* p = root;
	while(p!= NULL || !st.empty())
	{
		if(p != NULL)
		{
			st.push(p);
			p = p->left;
		}
		else
		{
			p=st.top();
			st.pop();
			cout<<p->value<<endl;
			p = p->right;
		}
	}
}

/*
非递归的后序遍历
*/
void Postorder_Nonre(BinTree* root)
{
	stack<BinTree*> s;
	BinTree* cur = root;
	BinTree* pre = NULL;//指向前一个被访问的节点
	while(cur != NULL || !s.empty())
	{
		while( cur != NULL) //一直向左走直到为空
		{
			s.push(cur);
			cur = cur->left;
		}
		cur = s.top();
		//当前节点的右孩子如果为空或者已经被访问，则访问当前节点
		if(cur->right == NULL || cur->right == pre)
		{
			cout<<cur->value<<" ";
			pre = cur;
			s.pop();
			cur = NULL;
		}
		else
			cur = cur->right;//否则访问右孩子
	}
}

/*
第二种方法
*/
void Postorder_Nonre2(BinTree* root)
{
	//使用双栈法
	stack<BinTree*> s1,s2;
	BinTree* cur;//指向当前要检查的节点
	s1.push(root);
	//栈为空时结束
	while(!s1.empty())
	{
		cur = s1.top();
		s1.pop();
		s2.push(cur);
		if(cur->left)
			s1.push(cur->left);
		if(cur->right)
			s1.push(cur->right);
	}
	while(!s2.empty())
	{
		cout<<s2.top()->value<<" ";
		s2.pop();
	}
}

int main()
{
	BinTree* root =NULL;
	int array[]={10,6,14,4,8,12,16};
	for(int i=0;i<sizeof(array)/sizeof(int);i++)
		root = InsertNode(root,array[i]);
	Inorder_Nonre2(root);
	return 0;
}
