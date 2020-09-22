/*斐波那契函数*/
#include<stdio.h>
#define NIL -1
#define MAX 100

int lookup[MAX];

/*初始化记忆数组*/
void _initialize()
{
  int i;
  for (i = 0; i < MAX; i++)
    lookup[i] = NIL;
}

/*记忆化（自上而下）：
记忆化存储其实是对递归程序小的修改，
作为真正的DP程序的过渡。
我们初始化一个数组中查找所有初始值为-1。
每当我们需要解决一个子问题，
我们先来看看这个数组(查找表)是否有答案。
如果预先计算的值有,那么我们就返回该值，
否则，我们计算该值并把结果存在数组(查找表)中，
以便它可以在以后重复使用。*/
int fib1(int n)
{
   if(lookup[n] == NIL)
   {
    if ( n <= 1 )
      lookup[n] = n;
    else
      lookup[n] = fib1(n-1) + fib1(n-2);
   }

   return lookup[n];
}

int fib2(int n)
{
  int f[n+1];
  int i;
  f[0] = 0;   f[1] = 1;
  for (i = 2; i <= n; i++)
      f[i] = f[i-1] + f[i-2];

  return f[n];
}


int main ()
{
  int n = 9;
  _initialize();
  printf("Fibonacci number is %d\n", fib1(n));
  printf("Fibonacci number is %d ", fib2(n));
  getchar();
  return 0;
}
