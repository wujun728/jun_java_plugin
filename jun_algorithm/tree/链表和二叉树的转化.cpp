#include <iostream>
#include <vector>
#include <string>
using namespace std;

/*
有序单链表转化为二叉搜索树
二叉树转化为双向链表
*/

using namespace std;
typedef struct list_node List;
struct list_node
{
    struct list_node* next;
    int value;
};


typedef struct Bin_tree BinTree;
struct Bin_tree
{
       int value;
       BinTree* right;
       BinTree* left;
};

void print_list(List* list)
{
    List* tmp=list;
    while(tmp != NULL)
    {
        cout<<tmp->value<<endl;
        tmp = tmp->next;
    }
}

/*
初始化List  将从1~n的数字插入到链表中
*/
void Init_List(List*& head,int* array,int n)
{
    head = NULL;
    List* tmp;
    List* record;

    for(int i=1;i<=n;i++)
    {
        tmp = new List;
        tmp->next = NULL;
        tmp->value = array[i-1];
        if(head == NULL)
        {
            head = tmp;
            record = head;
        }
        else
        {
            record->next = tmp;
            record = tmp;
        }
    }
}

int Len_list(List* list)
{
    if(list == NULL)
        return 0;
    else
        return Len_list(list->next)+1;
}

void Inorder(BinTree* root)
{
     if(root == NULL)
       return ;
     Inorder(root->left);
     cout<<root->value<<endl;
     Inorder(root->right);
}

/*
将一个有序的单链表转化为二叉搜索树
*/
List* FindMid(List*& list,List*& head,List*& tail)
{
	List* fast = head;
	List* slow = head;
	while(fast != NULL && fast->next != tail)
	{
		slow = slow->next;
		fast = fast->next;
		if(fast != NULL &&fast->next != tail)
			fast = fast->next;
	}
	return slow;
}

BinTree* helper(List*& root,List*& head,List*& tail)
{
	BinTree* node = NULL;
	if(head == tail || head == NULL)
		return node;
	List* mid = FindMid(root,head,tail);
	List* next = mid->next;
	node = new BinTree;
	node->value= mid->value;
	node->left = helper(root,head,mid);
	node->right = helper(root,next,tail);
	return node;
}

BinTree* ConvertBST(List*& list)
{
	BinTree* root = NULL;
	if(list == NULL)
		return root;
	List* head = list;
	List* tail = NULL;
	root = helper(list,head,tail);
	return root;
}

/*
将一个二叉树转化为双向链表
*/
BinTree* head =NULL;
void helper(BinTree* root,BinTree*& pre)
{
	if(root == NULL && root == NULL)
		return ;

	helper(root->left,pre);
	if(head == NULL)
		head = root;
	if(pre == NULL)
		pre = root;
	else
	{
		root->left = pre;
		pre->right = root;
		pre = root;
	}
	//cout<<root->value<<"  "<<endl;
	helper(root->right,pre);
}
BinTree* SearchTreeConverstToList(BinTree* root)
{
	BinTree* pre = NULL;//用来记录链表的最后一个指针
	helper(root,pre);
	return head;
}

void helper_second(BinTree* root,BinTree*& head,BinTree*& tail)
{
	if(root==NULL || (root->left == NULL && root->right == NULL))
	{
		head = root;
		tail = root;
		return;
	}
	BinTree* left_head = NULL;
	BinTree* left_tail = NULL;
	BinTree* right_head = NULL;
	BinTree* right_tail = NULL;

	helper_second(root->left,left_head,left_tail);
	helper_second(root->right,right_head,right_tail);

	if(left_head == NULL)
		head = root;
	else
	{
		head = left_head;
		left_tail->right = root;
		root->left = left_tail;
	}
	if(right_head == NULL)
		tail = root;
	else
	{
		tail = right_tail;
		root->right = right_head;
		right_head->left = root;
	}
}
/*
第二种方法
*/
BinTree* ConverstToList(BinTree* root)
{
	BinTree* head=NULL;
	BinTree* tail = NULL;
	helper_second(root,head,tail);
	return head;
}
int main()
{
	int array[]={1,2,3,4,5,6,7,8,9,10};
	List* list;
	Init_List(list,array,sizeof(array)/sizeof(int));
	BinTree* root = ConvertBST(list);
	print_list(list);
	Inorder(root);
	return 0;
}
