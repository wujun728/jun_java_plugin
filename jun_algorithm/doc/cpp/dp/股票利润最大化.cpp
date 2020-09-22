
/*
股票买卖最大利润

这里维护两个变量，一个是当前到达第i天可以最多进行j次交易，
最好的利润是多少（global[i][j]）
另一个是当前到达第i天，最多可以进行j次交易，并且最后一次交易在当天卖出
那么最好的利润是多少（local[i][j]）
递推公式
global[i][j] = max(local[i][j],glboal[i-1][j]),
也就是取当前局部最好和过往全局最好的其中之一
对于局部最好
local[i][j] = max(global[i-1][j-1]+maxdiff(diff,0),local[i-1][j]+diff);
*/

/*
在进行两次交易的利润最大化
*/
int maxProfit(vector<int>& prices)
{
	if(prices.size() <= 0)
		return 0;
	int global[3];
	int local[3];
	for(int i=0;i<prices.size()-1;i++)
	{
		int diff = prices[i+1]-prices[i];
		for(int j=2;j>=1;j--)
		{
			local[j] = max(global[j-1]+(diff>0?diff:0),local[j]+diff);
			global[j] = max(local[j],global[j]);
		}
	}
	global[2];
}


/*
多次交易之后
*/
int helper(vector<int>& prices,int k)
{
	int len = prices.size();
	if(len == 0)
		return 0;
	int local[10][10];
	int global[10][10];//临时申请的空间
	for(int i=1;i<len;i++)
	{
		int diff = prices[i]-prices[i-1];
		for(j=1;j<=k;j++)
		{
			local[i][j] = max(global[i-1][j-1]+max(diff,0),local[i-1][j]+diff);
			glocal[i][j] = max(local[i][j],global[i-1][j]);
		}
	}
}

int maxProfit(vector<int>& prices)
{
	return helper(prices,2);
}
