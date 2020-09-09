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

//求单链表中节点的个数
int GetListLength(ListNode* phead)
{
	if(phead == NULL)
		return 0;
	int length = 0;
	ListNode* current = phead;
	while(current != NULL)
	{
		length++;
		current = current->next;
	}
	return length;
}

//递归的方法求解链表的长度
int Len_list(ListNode* list)
{
	if(list == NULL)
		return 0;
	else
		return Len_list(list->next)+1;
}


//反转单链表
ListNode* ReverseList(ListNode* phead)
{
	//如果单链表为空或者只有一个节点，无需翻转，直接返回头节点
	if(phead == NULL | phead->next == NULL)
		return phead;
	ListNode* preverse = NULL;//翻转后的新链表头指针，初始化为NULL
	ListNode* current = phead;
	while(current != NULL)
	{
		ListNode* temp = current;
		current = current->next;
		temp->next = preverse;//将当前节点记录，插入新链表的最前端
		preverse = temp;
	}
	return preverse;
}
int main()
{
	int array[]={1,2,3,4,5,6,7,8,9,10};
	ListNode* list;
	Init_List(list,array,sizeof(array)/sizeof(int));
	print_list(list);
	list = ReverseList(list);
	print_list(list);

	return 0;
}
