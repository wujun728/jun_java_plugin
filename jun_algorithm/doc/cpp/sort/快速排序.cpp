#include <stdio.h>
#include <stdlib.h>

//将一个数组以某一个基准点划分为两个子数组
int Partition(int* array,int left,int right)
{
	int key = array[right];//以最后一个元素为基准点
	int i = left-1;
	int temp;

	//开始以基准点为标准分割序列
	for(int j = left;j<right;j++)
	{
		if(array[j] < key)
		{
			i++;

			temp = array[j];
			array[j] = array[i];
			array[i] = temp;
		}
	}
	//将基准点放置到合适的位置上
	temp = array[i+1];
	array[i+1] = array[right];
	array[right] = temp;
	return i+1;
}

//快速排序
void quicksort(int* array,int left,int right)
{
	if(left < right)
	{
		int k = Partition(array,left,right);
		quicksort(array,left,k-1);
		quicksort(array,k+1,right);
	}
}

//第二种分割的方法
void swap(int* a,int* b)
{
	int temp = *a;
	*a  = *b;
	*b = temp;
}
int partition2(int* array,int low,int high)
{
	int key = array[low];

	while(low < high)
	{
		//从后面找到一个合适的值和前面的交换
		while(low<high && array[high] >= key)
			high--;
		swap(&array[low],&array[high]);

		//从前面找到一个合适的值和后面的交换
		while(low< high && array[low] <= key)
			++low;
		swap(&array[low],&array[high]);
	}

	return low;
}

//第二种分割方法下的快速排序
void quicksort2(int* array,int left,int right)
{
	int i,j,key;
	if(left < right)
	{
		i = left;
		j = right;
		key = array[i];//以最左边的元素作为划分的基准点
		do
		{
			while(array[j] > key && i<j)
				j--;//从右向左找第一个小于基准值的位置j
			if(i<j) //找到了位置j
			{
				array[i] = array[j];
				i++;
			} //将第J个元素置于左端并重置i

			while(array[i] < key && i<j)
				i++;//从左向右找第一个大于标准的位置i
			if(i<j)
			{
				array[j] = array[i];
				j--;
			} //将第I个元素置于右端并重置j


		} while(i!= j);

		array[i] = key;// 分割完成
		quicksort2(array,left,i-1);
		quicksort2(array,i+1,right);
	}
}

int main()
{
	int array[]={9,4,5,2,1,3,7,8,6};
	quicksort2(array,0,8);
	int i;
	for(i=0;i<9;i++)
		printf("%d\n",array[i]);
	return 0;
}
