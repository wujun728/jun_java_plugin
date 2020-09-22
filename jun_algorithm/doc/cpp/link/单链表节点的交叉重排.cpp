#include <iostream>
#include <vector>
using namespace std;

/*
按照要求重新为单链表排序
*/

typedef struct list_node List;
struct list_node
{
	struct list_node* next;
	int value;
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

//求链表长度
int Len_list(List* list)
{
	if(list == NULL)
		return 0;
	else
		return Len_list(list->next)+1;
}

/*
链表的翻转
*/
void Reverse(List*& list)
{
	List* tmp = NULL;
	List* cur = list;
	List* next = list->next;
	while(next != NULL)
	{
		cur->next = tmp;
		tmp = cur;
		cur = next;
		next = next->next;
	}
	cur->next  = tmp;
	list = cur;
}

/*
重新排序链表，将一个链表拆分，然后重新组合
关键点在于链表个数是偶数还是奇数
*/

void Reorder_list(List*& list)
{
	List* first = list;
	List* second;
	List* tmp_first,*tmp_second;
	//需要根据链表中节点的个数来分割链表
	int len = Len_list(first);
	int i;
	if(len%2 == 0)
	{
		for(i=1;i<len/2;i++)
			first = first->next;
	}
	else
	{
		for(i=1;i<len/2+1;i++)
			first = first->next;
	}
	second = first->next;
	first->next = NULL;
	//将后面的链表进行翻转
	Reverse(second);

	//重新规划链表
	first = list;
	//开始进行合并，同时second链表的个数肯定不会比first链表的节点数多
	while(second != NULL)
	{
		tmp_first = first->next;
		tmp_second = second->next;
		first->next= second;
		second->next = tmp_first;
		second = tmp_second;
		first = tmp_first;
	} //能否使用伪指针将两个链表串联
}

int main()
{
	int array[]={1,2,3,4,5,6,7,8,9,10,11};
	List* head;
	Init_List(head,array,sizeof(array)/sizeof(int));

	Reorder_list(head);
	print_list(head);
	return 0;
}
