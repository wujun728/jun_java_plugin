#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
实现字典树的创建、插入、查询
*/

#define MAX_CHILD 26

typedef struct Tree
{
	int count;//用来标记该节点是否可以形成一个单词，count！=0 说明可以
	struct Tree* child[MAX_CHILD];

}Node,*Trie_node;
/*
child是表示每层有多少种类的数，如果是小写字母，为26个
*/

/*
创建Trie节点
*/
Node* CreateTrie()
{
	Node* node = (Node*)malloc(sizeof(Node));
	memset(node,0,sizeof(Node));
	return node;
}

/*
Trie树插入节点
*/
void Insert_node(Trie_node root,char* str)
{
	if(root == NULL || *str == '\0')
		return ;
	Node* t = root;
	while(*str != '\0')
	{
		if(t->child[*str-'a'] == NULL)
		{
			Node* tmp = CreateTrie();
			t->child[*str-'a'] = tmp;
		}
		t = t->child[*str-'a'];
		str++;
	}
	t->count++;
}

/*
Trie中的查找
*/
void search_str(Trie_node root,char* str)
{
	if(NULL == root || *str == '\0')
	{
		printf("tire is empty\n");
		return;
	}
	Node* t = root;
	while(*str != '\0')
	{
		if(t->child[*str-'a'] != NULL)
		{
			t = t->child[*str-'a'];
			str++;
		}
		else
			break;
	}
	if(*str == '\0')
	{
		if(t->count == 0)
			printf("该字符串不存在\n");
		else
			printf("该字符串存在\n");
	}
	else
		printf("该字符串不存在\n");
}

/*
释放整个字典树的空间
*/
void del(Trie_node root)
{
	int i;
	for(i =0;i<MAX_CHILD;i++)
	{
		if(root->child[i] != NULL)
			del(root->child[i]);
	}
	free(root);
}


int main()
{
	int i,n;
	char str[20];
	printf("请输入要创建的trie树的大小：");
	scanf("%d",&n);
	Trie_node root = NULL;
	root =CreateTrie();
	if(root == NULL)
		printf("创建Trie树失败\n");
	for(i=0;i<n;i++)
	{
		scanf("%s",str);
		Insert_node(root,str);
	}
	printf("trie树已经建立\n");
	printf("请输入需要查询的字符串\n");
	while(scanf("%s",str)!= NULL)
	{
		search_str(root,str);
	}
	return 0;
}
