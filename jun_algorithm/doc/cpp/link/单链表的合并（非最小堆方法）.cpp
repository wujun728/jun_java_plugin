#include <iostream>
#include <vector>
using namespace std;

/*
合并K个已经排序好的链表
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
可以类似于合并几个已经排序好的数组这种思想，比如借助最小堆，每次将堆顶元素
插入新的链表中，但是也可以使用数组来进行标记，每次从数组中挑选最小的节点
直到所有链表的节点都被选中
*/

List* Merge_k(vector<List*>& vec)
{
	int i;
	//使用数组进行标记
	vector<int> flag(vec.size(),1); //标记数组flag初始化值为1
	List* head = NULL;
	List* cur = NULL;
	List* tmp = NULL; //记录当前临时最小值
	int pos;//当前临时最小值在第几个链表中

	//开始处理K个链表
	while(1)
	{
		tmp = NULL;
		//开始遍历标记数组，找到合适的节点
		for(i =0;i<vec.size();i++)
		{
			if(vec[i] == NULL)
				flag[i] = 0;
			if(flag[i])
			{
				if(tmp ==NULL)
				{
					tmp = vec[i];
					pos =i;
				}
				if(tmp && tmp->value > vec[i]->value)
				{
					tmp = vec[i];
					pos =i;
				}
			}
		}
		//开始特殊处理头节点
		if(head == NULL)
		{
			head = tmp;
			cur = head;
		}
		else
		{
			cur->next = tmp;
			cur= cur->next;
		}

		//处理被选中的链表，该链表中要删除刚才的元素
		vec[pos] = vec[pos]->next;
		if(vec[pos] == NULL)
			flag[pos] =0;

		//判断是否所有的节点都已经被选中
		for(i=0;i<flag.size();i++)
		{
			if(flag[i])
				break;
		}
		if(i>=flag.size())
			break;
	}
	return head;

}


int main()
{
	int array1[]={1,4,7,8,13,19};
	int array2[]={5,8,9,10,12,15,17,22,23};
	int array3[]={3,6,11,16,17,18,21,24};
	int array4[]={2,14,20,25};
	vector<List*> vec(4);
	int i;

	Init_List(vec[0],array1,sizeof(array1)/sizeof(int));
	Init_List(vec[1],array2,sizeof(array2)/sizeof(int));
	Init_List(vec[2],array3,sizeof(array3)/sizeof(int));
	Init_List(vec[3],array4,sizeof(array4)/sizeof(int));

	List* head = Merge_k(vec);
	print_list(head);
	return 0;
}
