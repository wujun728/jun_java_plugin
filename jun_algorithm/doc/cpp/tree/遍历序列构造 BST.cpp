#include <iostream>
#include <vector>
using namespace std;

/*
由一个二叉搜索树的前序序列和中序序列构造树
*/
typedef struct tree_node Tree;
struct tree_node
{
	Tree* left;
	Tree* right;
	int value;
};

Tree* helper(vector<int>& pre,int pre_begin,int pre_end,vector<int>& inorder,int in_begin,int in_end)
{
	Tree* root = NULL;
	int mid;
	int i;
	if(pre_end - pre_begin <0)
		return NULL;
	else
	{
		for(i = in_begin;i<=in_end;i++)
			if(inorder[i] == pre[pre_begin])
				break;
		if(i > in_end)
			return NULL;

		//创建节点
		root = new Tree;
		root->value = pre[pre_begin];
		root->left = helper(pre,pre_begin+1,pre_begin+1+i-in_begin,inorder,in_begin,i-1);
		root->right = helper(pre,pre_begin+1+i-1-in_begin+1,pre_end,inorder,i+1,in_end);
		return root;
	}
}

Tree* createBinTree(vector<int>& pre,vector<int>& inorder)
{
	if(pre.size() == 0 || inorder.size()== 0)
		return NULL;
	return helper(pre,0,pre.size()-1,inorder,0,inorder.size()-1);
}


/*
由中序和后序序列遍历构造二叉树
*/
Tree* helpersecond(vector<int>& inorder,int in_begin,int in_end,vector<int>& post,int post_begin,int post_end)
{
	Tree* root = NULL;
	int mid;
	int i;
	if(in_begin > in_end)
	{
		return NULL;
	}
	else
	{
		for(i= in_begin;i<=in_end;i++)
			if(inorder[i] == post[post_end])
				break;
		if(i > in_end)
			return NULL;
		root = new Tree;
		root->value = post[post_end];
		root->left = helpersecond(inorder,in_begin,i-1,post,post_begin,post_begin+i-1-in_begin);
		root->right = helpersecond(inorder,i+1,in_end,post,post_begin+i-in_begin,post_end-1);

	}
	return root;
}

Tree* createBinTree2(vector<int>& inorder,vector<int>& post)
{
	if(inorder.size() == 0|| post.size()==0)
		return NULL;
	return helpersecond(inorder,0,inorder.size()-1,post,0,post.size()-1);
}

/*前序遍历*/
void print_pre(Tree* tree)
{
	if(tree != NULL)
	{
		cout<<tree->value<<" "<<endl;
		print_pre(tree->left);
		print_pre(tree->right);
	}
}

/*中序遍历*/
void print_inorder(Tree* tree)
{
	if(tree != NULL)
	{
		print_inorder(tree->left);
		cout<<tree->value<<endl;
		print_inorder(tree->right);
	}
}

void print_post(Tree* root)
{
	if(root != NULL)
	{
		print_post(root->left);
		print_post(root->right);
		cout<<root->value<<endl;
	}

}


int main()
{
	int array1[]={4,2,1,3,7,6,5,8};
	vector<int> preorder(array1,array1+sizeof(array1)/sizeof(int));

	int array[]={1,2,3,4,5,6,7,8};
	vector<int> inorder(array,array+sizeof(array)/sizeof(int));

	int array2[]={1,3,2,5,6,8,7,4};
	vector<int> postorder(array2,array2+sizeof(array2)/sizeof(int));

	Tree* root = createBinTree(preorder,inorder);
	print_inorder(root);
	cout<<"==========="<<endl;

	Tree* second = createBinTree2(inorder,postorder);
	print_inorder(second);

	return 0;
}
