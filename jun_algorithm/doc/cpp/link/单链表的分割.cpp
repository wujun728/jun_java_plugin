#include <iostream>
#include <vector>
#include <string>
using namespace std;

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
思路：类似于快排，由于要求不改变原来的相对顺序，所以必须有节点的交换
要不然之间交换节点内部的值即可。
*/

/*
类似于快速排序的分割
*/

void PartitionList(List*& list,int key)
{
	if(list == NULL)
		return ;
	List* record,*cur,*pre,*tmp;
	record = NULL;
	cur = list;
	pre = NULL;
	//开始处理链表
	while(cur != NULL)
	{
		if(cur->value< key) //插入到pre之后，需要特殊处理头节点
		{
			tmp = cur->next;
			if(pre == NULL)
				pre = cur;
			if(record ==NULL)
			{
				record = list;
				list = cur;
				cur->next = record;
				record = cur;
				pre->next = tmp;
			}
			else
			{
				if(pre != record)
				{
					cur->next = record->next;
					record->next = cur;
					pre->next = tmp;
					record = cur;
				}
				else
				{
					record = pre= cur;
				}
			}

			cur = tmp;
		}
		else
		{
			pre= cur;
			cur = cur->next;
		}
	}
}

//第二种方法 使用伪指针
List* PartitionList2(List* list,int key)
{
	List* head = new List;
	head->next = list;
	List* temp = head;
	List* pre = head,*cur = list,*next;

	//开始处理
	while(cur != NULL)
	{
		next = cur->next;
		if(cur->value < key)
		{
			pre->next = next;
			cur->next = temp->next;
			temp->next = cur;
			temp = cur;
			cur = next;
		}
		else
		{
			pre = cur;
			cur = next;
		}
	}
	return head->next;
}

int main()
{
	int array[]={5,1,2,7,8,4,3,6,10,9};
	List* list;
	Init_List(list,array,sizeof(array)/sizeof(int));
	list = PartitionList2(list,5);
	print_list(list);
	return 0;
}
