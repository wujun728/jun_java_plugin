#include <iostream>
#include <vector>
using namespace std;

/*
共线最多的点的个数
思路：可以看所有两点能够构造的直线的斜率，根据斜率来判断共线的点的个数
这样斜率和该斜率上点的个数就构成了一个键值对，可以使用hash表来存储
*/

int maxPoints(vector<pair<int,int> >& points )
{
	if(points.size() == 0)
		return 0;
	int max =1;
	double ratio=0.0;
	//开始遍历点，构造键值对
	for(int i=0;i<points.size()-1;i++)
	{
		hash_map<double,int> map;
		int numofSame=0;
		int localMax=1;
		//从当前点和其之后的点构成的斜率
		for(int j=i+1;;j<points.size();j++)
		{
			if(points[j].first == points[j].first && points[j].second == points.second)
			{
				numofSame++;
				continue;
			}
			else if(points[j].first == points[i].first)
			{
				ratio = numeric_limits<double>::max();
			}
			else if(points[j].second == points[i].second)
			{
				ratio =0.0;
			}
			else
			{
				ratio = (double)(points[j].second-points[i].second)/(double)(points[j].first-points[i].first);

			}
			int num;
			if(map.find(ratio) != map.end())
				map[ratio]++;
			else
				map[ratio]=2;
		}
		//开始查找某一斜率下最多的点的个数
		hash_map<double,int>::iterator it = map.begin();
		fo(;it!=map.end();it++)
			localMax = max(localMax,it->second);
		localMax += numofSame;
		max = max(max,localMax);
	}
	return max;
}

