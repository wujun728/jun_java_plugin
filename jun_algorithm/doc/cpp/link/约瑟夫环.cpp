#include <iostream>
#include <vector>
#include <list>
using namespace std;

// 约瑟夫环问题，使用STL中的List，
int Joseph(list<int>& ring,int k)
{
	list<int>::iterator itr = ring.begin(),temp;
	int m;
	//链表节点个数大于1就会一直删除下去
	while(ring.size()>1)
	{
		m =1;
		//判断当前迭代器是否需要重新置位
		if(itr == ring.end())
			itr = ring.begin();
		//查找合适的节点进行删除
		while(m <k)
		{
			//始终需要判断是否需要重新置位
			if(itr == ring.end())
				itr = ring.begin();
			itr++;
			m++;
			if(itr == ring.end())
				itr = ring.begin();
		}
		temp = itr;
		itr++;
		//删除被选中的节点
		ring.erase(temp);
	}
	//只剩下一个节点
	itr = ring.begin();
	return *itr;
}

int main()
{
	int i;
	list<int> ring;
	for(i=1;i<=9;i++)
		ring.insert(ring.begin(),i);
	cout<<Joseph(ring,5)<<endl;
	return 0;
}
