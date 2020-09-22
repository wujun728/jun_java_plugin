//已知一个单链表中存在环，求进入环中的第一个节点

ListNode* GetFirstNodeInCircle(ListNode* phead)
{
	if(phead == NULL || phead->next == NULL)
	{
		return NULL;
	}

	ListNode* pfast = phead;
	ListNode* pslow = phead;
	//先判断是否存在环
	while(pfast != NULL && pfast->next != NULL)
	{
		pslow = pslow->next;
		pfast = pfast->next->next;
		if(pslow == pfast)
			break;
	}
	if(pfast == NULL || pfast->next == NULL)
		return NULL;

	//如果存在环，快慢指针都以同样的速度前进，相遇即为第一个节点
	pfast = phead;
	while(pslow != pfast)
	{
		pslow= pslow->next;
		pfast = pfast->next;
	}
	return pslow;
}

//按照一定的要求删除链表中的节点
typedef boolk (*remove_fn)(ListNode* node);

ListNode* remove_if(ListNode* head,remove_fn rm)
{
	for(ListNode* prev= NULL,*curr = head;curr != NULL)
	{
		ListNode* next = curr->next;
		if(rm(curr))
		{
			if(prev)
				prev->next = next;
			else
				head= next;
			delete curr;
		}
		else
			prev= curr;
		curr = next;
	}
	return head;
}

// 升级版(使用二级指针删除单链表中的节点)（Linux内核代码）
void remove_if(ListNode** head,remove_if rm)
{
	for(ListNode** curr = head;*curr;)
	{
		ListNode* entry = *curr;
		if(rm(entry))
		{
			*curr = entry->next;
			delete entry;
		}
		else
			curr = &entry->next;
	}
}
