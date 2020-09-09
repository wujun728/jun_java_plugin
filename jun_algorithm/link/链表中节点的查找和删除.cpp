#include <iostream>
using namespace std;

typedef struct list_node ListNode;

struct list_node
{
	struct list_node* next;
	int value;
};

/*
初始化List 将从1~n的数字插入到链表中
*/
void Init_List(ListNode*& head,int* array,int n)
{
	head = NULL;
	ListNode* tmp;
	ListNode* record;

	for(int i=1;i<=n;i++)
	{
		tmp = new ListNode;
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

void print_list(ListNode* list)
{
	ListNode* tmp = list;
	while(tmp != NULL)
	{
		cout<<tmp->value<<endl;
		tmp = tmp->next;
	}
}

//查找单链表中倒数第K个节点
ListNode* GetKthNode(ListNode* phead,int k)
{
	//这里K的计数是从1开始
	if(k == 0 || phead == NULL)
		return NULL;

	ListNode* pAhead = phead;
	ListNode* pBehind = phead;
	//前面的指针先走到正向第K个节点
	while(k>1 && pAhead != NULL)
	{
		pAhead = pAhead->next;
		k--;
	}
	//节点个数小于K，直接返回NULL
	if(k>1 || pAhead == NULL)
		return NULL;
	//前后两个指针一起向前走，直到前面的指针指向最后一个节点
	while(pAhead->next != NULL)
	{
		pBehind = pBehind->next;
		pAhead = pAhead->next;
	}
	return pBehind;//后面的指针所指向的节点就是倒数第K个节点
}

//从尾到头打印链表，使用递归的方法
void RPrintList(ListNode* phead)
{
	if(phead == NULL)
		return ;
	else
	{
		RPrintList(phead->next);
		cout<<phead->value<<endl;
	}
}

//判断单链表中是否有环
bool HasCircle(ListNode* phead)
{
	ListNode* pfast = phead;//快指针每次前进两步
	ListNode* pslow = phead;//慢指针每次前进一步
	while(pfast!=NULL&& pfast->next != NULL)
	{
		pfast = pfast->next->next;
		pslow = pslow->next;
		if(pslow == pfast)
			return true;
	}
	return false;
}

/*
给出一单链表头指针phead和一个待删除的节点指针，
在O(1)时间复杂度内删除此节点
*/
void Delete(ListNode* phead,ListNode* tobedelete)
 {
 	if(tobedelete == NULL || phead == NULL)
 		return;
 	ListNode* temp = phead;

 	//将下一个节点的数据复制到本节点，然后删除下一个节点
	if(tobedelete->next != NULL)
	{
		tobedelete->value = tobedelete->next->value;
		ListNode* temp = tobedelete->next;
		tobedelete->next = tobedelete->next->next;
		delete temp;
	}
	else //要删除的是最后一个节点
	{
		if(phead == tobedelete)//链表中只有一个节点的情况
		{
			phead = NULL;
			delete tobedelete;

		}
		else
		{
			ListNode* pnode = phead;
			while(pnode->next != tobedelete)//找到倒数第二个节点
				pnode =pnode->next;
			pnode->next = NULL;
			delete tobedelete;
		}
	}
 }

int main()
{
	int array[]={1,2,3,4,5,6,7,8,9,10};
	ListNode* list;
	Init_List(list,array,sizeof(array)/sizeof(int));
//	print_list(list);
	Delete(list,list->next->next->next);
	print_list(list);
	return 0;
}
