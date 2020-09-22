#include <iostream>
#include <vector>
using namespace std;

typedef struct list_node List;
struct list_node
{
	int value;
	struct list_node* next;
};

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
删除重复的元素
*/
void RemoveDuplicate(List*& head)
{
	if(head == NULL || head->next == NULL)
		return;
	List* slow = head;
	List* fast = head->next;
	List* tmp;
	while(fast != NULL)
	{
		if(slow->value == fast->value)
		{
			tmp = fast;
			fast = fast->next;
			slow->next = fast;
			delete tmp;
		}
		else
		{
			slow = slow->next;
			fast = fast->next;
		}
	}
}
//删除链表中重复的元素，只保留不重复的元素（伪指针的引入）
List* removeDuplicate(List* head)
{
	if(head == NULL || head->next == NULL)
		return head;
	List* temp,*pre,*cur;
	List* newhead = new List;
	newhead->next = head;
	temp = newhead;
	pre = head;
	cur = head->next;
	while(cur != NULL)
	{
		while(cur != NULL && cur->value == pre->value)
			cur = cur->next;
		if(pre->next == cur)
		{
			temp->next = pre;
			temp = pre;
			pre = cur;
		}
		else
		{
			pre = cur;
		}
		if(cur != NULL)
			cur = cur->next;
	}
	temp->next = cur;
	return newhead->next;
}
int main()
{
	int array[]={1,1,1,2,3,3,4,5,6,6,7,7};
	List* head;
	Init_List(head,array,sizeof(array)/sizeof(int));
	head = removeDuplicate(head);
	print_list(head);
	return 0;
}
