#include <iostream>
using namespace std;

//插入排序 （思想：在一个已经排序好的序列中，为下一个元素找合适的插入位置）
void InsertSort(int* array,int n)
{
	int i,j;
	int temp;

	//从第二个元素开始进行插入排序 为其在前面排序好的序列中找合适的位置
	for(i=1;i<n;i++)
	{
		//为当前元素选择合适的位置
		temp = array[i];
		for(j=i-1;j>=0;j--)
			if(array[j] > temp)
				array[j+1] = array[j];
			else
				break;

		//找到合适的位置后填充当前值
		array[j+1] = temp;
	}

	for(i=0;i<n;i++)
		cout<<array[i]<<endl;
}

int main()
{
	int array[]={7,5,2,9,1,3};
	InsertSort(array,6);
	return 0;
}
