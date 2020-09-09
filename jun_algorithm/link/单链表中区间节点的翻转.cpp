#include <iostream>
#include <vector>
#include <string>
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
将链表中的第m个节点到第n个节点之间的元素进行翻转
*/
void ReverseList(List*& list,int m,int n)
{
	if(list == NULL ||list->next == NULL || n-m<1)
		return ;
	int num =1;
	List* pre,*next,*cur,*temp,*tmp;
	cur = list;
	pre = NULL;
	while(cur != NULL)
	{
		next = cur->next;
		if(num < m)
		{
			pre = cur;
			cur = next;
		}
		if(num == m)
		{
			tmp = cur;
			temp = cur;
			cur = next;
		}
		if(num >m && num <= n)
		{
			cur->next = temp;
			temp = cur;
			cur = next;
		}
		if(num == n)
		{
			if(m ==1)
				list = temp;
			else
				pre->next = temp;
			tmp->next = cur;
			break;
		}
		num++;
	}
}

int main()
{
	int array[]={5,1,2,7,8,4,3,6,10,9};
	List* list ;
	Init_List(list,array,sizeof(array)/sizeof(int));
	ReverseList(list,1,3);
	print_list(list);
	return 0;
}
