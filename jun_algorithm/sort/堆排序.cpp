#include <stdio.h>
#include <stdlib.h>

//调整堆
void HeapAdjust(int* array,int s,int length)
{
	int temp = array[s];//待调整的元素
	int child = 2*s+1;//待调整节点的左孩子的位置

	//开始调整
	while(child < length)
	{
		//如果右孩子大于左孩子，找到比当前待调整节点大的孩子节点
		if(child+1 < length&& array[child] < array[child+1])
			child++;
		//如果较大的孩子大于待调整的节点
		if(array[s] < array[child])
		{
			array[s] = array[child];//那么较大的节点向上移
			s = child;//更新待调整节点的位置
			child = 2*s+1;//更新待调整节点的左孩子
		}

		else
			break;
		array[s] = temp;

	}
}

//堆排序
void HeapSort(int* array,int length)
{
	int i;
	//构造最大堆
	for(i=(length-1)/2;i>=0;i--)
	{
		HeapAdjust(array,i,length);
	}

	//从最后一个元素开始对序列进行调整
	for(i=length-1;i>0;i--)
	{
		//交换堆顶元素和堆中最后一个元素
		int tmp = array[i];
		array[i] = array[0];
		array[0] = tmp;
		//每次交换元素之后，就需要重新调整堆
		HeapAdjust(array,0,i);
	}
}

int main()
{
	int array[]={3,4,5,1,9,8,6,2,7,10};
	HeapSort(array,10);
	int i;
	for(i=0;i<10;i++)
		printf("%d\n",array[i]);
	return 0;
}
