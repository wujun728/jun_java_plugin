#include <stdio.h>
#include <stdlib.h>

//二分查找
/*
在一个有序序列中 查找某一个元素是否存在
如果存在，返回该元素在该序列中的索引位置
如果不存在，返回-1
*/
/*
如果high = n-1,while(high >= low) high = middle -1;
如果high = n while(high > low) high = middle;
*/
int binary_search(int* array,int n,int key)
{
	int low = 0;
	int high = n-1;
	int mid;
	if(high < low)
		return -1;
	while(high >= low)
	{
		mid = low+((high-low)>>1);
		if(array[mid] == key)
			return mid;
		else if(array[mid] > key)
			high = mid-1;
		else
			low = mid+1;
	}
	return -1;
}

int main()
{
	int array[]={1,2,3,4,5,6,7,8,9,10};
	printf("%d\n",binary_search(array,10,4));
	return 0;
}
