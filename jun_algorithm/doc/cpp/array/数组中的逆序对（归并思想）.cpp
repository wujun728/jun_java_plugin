#include <iostream>
#include <vector>
#include <string>

using namespace std;

unsigned int CountInversions(vector<unsigned int>& a);
unsigned int MergeSortCount(vector<unsigned int>& a,int left,int right,vector<unsigned int>& temp);
unsigned int MergeCount(vector<unsigned int>& a,int left,int mid,int right,vector<unsigned int>& temp);

int main()
{
	int array[]={4,2,10,3,5,7};
	vector<unsigned int> a(array,array+sizeof(array)/sizeof(int));
	unsigned int result = CountInversions(a);
	cout <<result<<endl;
	return 0;
}

unsigned int MergeCount(vector<unsigned int>& a,int left,int mid,int right,vector<unsigned int>& temp)
{
	int i = left;
	int j= mid+1;
	int k = left;
	int count = 0;
	//开始查找逆序对的个数
	while( i<= mid && j<=right)
	{
		if(a[i]<= a[j])
			temp[k++] = a[i++];
		else
		{
			temp[k++]=a[j++];
			count+= mid-i+1;
		}
	}

	while(i<=mid) temp[k++]= a[i++];
	while(j<= right) temp[k++] = a[j++];

	for(i=left;i<=right;i++)
		a[i]= temp[i];
	return count;
}

unsigned int MergeSortCount(vector<unsigned int>& a,int left,int right,vector<unsigned int>& temp)
{
	if(left >= right) return 0;

	int mid = (left+right)/2;
	unsigned int Inverleft = MergeSortCount(a,left,mid,temp);
	unsigned int Inverright = MergeSortCount(a,mid+1,right,temp);
	unsigned int InverSum = MergeCount(a,left,mid,right,temp);

	return Inverleft+Inverright+InverSum;
}

unsigned int CountInversions(vector<unsigned int>& a)
{
	int n= a.size();
	vector<unsigned int> temp(a.begin(),a.end());
	unsigned int ans = MergeSortCount(a,0,n-1,temp);
	return ans;
}
