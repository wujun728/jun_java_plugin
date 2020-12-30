## Java常用的八种排序算法与代码实现

**目录：**

> # 1.直接插入排序
>
> # 2.希尔排序
>
> 3.简单选择排序
>
> # 4.堆排序
>
> # 5.冒泡排序
>
> # 6.快速排序
>
> # 7.归并排序
>
> # 8.基数排序



**1.直接插入排序**



经常碰到这样一类排序问题：把新的数据插入到已经排好的数据列中。

将第一个数和第二个数排序，然后构成一个有序序列

将第三个数插入进去，构成一个新的有序序列。

对第四个数、第五个数……直到最后一个数，重复第二步。



![图片](http://mmbiz.qpic.cn/mmbiz_png/MOwlO0INfQpKubibb9mtMxTlSzahzOFjAx2k7iabzcKcugHT0rykIhQmeszSGeIs5EIpq89o2GB0r8IV4SsSawUA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**如何写成代码：**

首先设定插入次数，即循环次数，for(int i=1;i<length;i++)，1个数的那次不用插入。

设定插入数和得到已经排好序列的最后一个数的位数。insertNum和j=i-1。

从最后一个数开始向前循环，如果插入数小于当前数，就将当前数向后移动一位。

将当前数放置到空着的位置，即j+1。



**代码实现如下：**



![图片](http://mmbiz.qpic.cn/mmbiz_png/MOwlO0INfQpKubibb9mtMxTlSzahzOFjAYQX9ib7TlctoaM5RibqTR6FSnQYXq7CYXR2PUBPCmzm7vqnAgCoznjXg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**2.希尔排序**



对于直接插入排序问题，数据量巨大时。

将数的个数设为n，取奇数k=n/2，将下标差值为k的书分为一组，构成有序序列。

再取k=k/2 ，将下标差值为k的书分为一组，构成有序序列。

重复第二步，直到k=1执行简单插入排序。



![图片](http://mmbiz.qpic.cn/mmbiz_png/MOwlO0INfQpKubibb9mtMxTlSzahzOFjApDyiaibDaqwaVR82jCpibuSicsHrbczyJIuKM7sHB38qAJASQ1PFVEJ7icg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**如何写成代码：**

首先确定分的组数。

然后对组中元素进行插入排序。

然后将length/2，重复1,2步，直到length=0为止。



**代码实现如下：**



![图片](http://mmbiz.qpic.cn/mmbiz_png/MOwlO0INfQpKubibb9mtMxTlSzahzOFjAHZ8tEjdCApxfzH7mUtXEpTgcicoBJ2oy09ia6jtz8PqCvvexTQTR3Abg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**3.简单选择排序**



常用于取序列中最大最小的几个数时。

(如果每次比较都交换，那么就是交换排序；如果每次比较完一个循环再交换，就是简单选择排序。)

遍历整个序列，将最小的数放在最前面。

遍历剩下的序列，将最小的数放在最前面。

重复第二步，直到只剩下一个数。

![图片](http://mmbiz.qpic.cn/mmbiz_png/MOwlO0INfQpKubibb9mtMxTlSzahzOFjAAxXCUxGNVHO0ehjY2ibPzzHezOgRkUz8kbSY5gTBO5r5fPVC31uFy3Q/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

**如何写成代码：**

首先确定循环次数，并且记住当前数字和当前位置。

将当前位置后面所有的数与当前数字进行对比，小数赋值给key，并记住小数的位置。

比对完成后，将最小的值与第一个数的值交换。

重复2、3步。



**代码实现如下：**



![img](data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==)



**4.堆排序**



对简单选择排序的优化。

将序列构建成大顶堆。

将根节点与最后一个节点交换，然后断开最后一个节点。

重复第一、二步，直到所有节点断开。



![图片](http://mmbiz.qpic.cn/mmbiz_png/MOwlO0INfQpKubibb9mtMxTlSzahzOFjAbqs24qOMGSYH6D6majYj7yibCTr36xWR9YEToJYBiauq9XJwEBu9j4MQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**代码实现如下：**



> 1. `public void heapSort(int[] a){`
> 2. `    System.out.println("开始排序");`
> 3. `    int arrayLength=a.length;`
> 4. `    //循环建堆  `
> 5. `    for(int i=0;i
> 6. `      //建堆  `
> 7. 
> 8. `      buildMaxHeap(a,arrayLength-1-i);`
> 9. `      //交换堆顶和最后一个元素  `
> 10. `      swap(a,0,arrayLength-1-i);`
> 11. `      System.out.println(Arrays.toString(a));`
> 12. `    }`
> 13. `  }`
> 14. `  private void swap(int[] data, int i, int j) {`
> 15. `    // TODO Auto-generated method stub  `
> 16. `    int tmp=data[i];`
> 17. `    data[i]=data[j];`
> 18. `    data[j]=tmp;`
> 19. `  }`
> 20. `  //对data数组从0到lastIndex建大顶堆  `
> 21. `  private void buildMaxHeap(int[] data, int lastIndex) {`
> 22. `    // TODO Auto-generated method stub  `
> 23. `    //从lastIndex处节点（最后一个节点）的父节点开始  `
> 24. `    for(int i=(lastIndex-1)/2;i>=0;i--){`
> 25. `      //k保存正在判断的节点  `
> 26. `      int k=i;`
> 27. `      //如果当前k节点的子节点存在  `
> 28. `      while(k*2+1<=lastIndex){`
> 29. `        //k节点的左子节点的索引  `
> 30. `        int biggerIndex=2*k+1;`
> 31. `        //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在  `
> 32. `        if(biggerIndex
> 33. `          //若果右子节点的值较大  `
> 34. `          if(data[biggerIndex]
> 35. `            //biggerIndex总是记录较大子节点的索引  `
> 36. `            biggerIndex++;`
> 37. `          }`
> 38. `        }`
> 39. `        //如果k节点的值小于其较大的子节点的值  `
> 40. `        if(data[k]
> 41. `          //交换他们  `
> 42. `          swap(data,k,biggerIndex);`
> 43. `          //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值  `
> 44. `          k=biggerIndex;`
> 45. `        }else{`
> 46. `          break;`
> 47. `        }`
> 48. `      }`
> 49. `    }`
> 50. `  }`
> 51. 
> 52. 



**5.冒泡排序**



一般不用。

将序列中所有元素两两比较，将最大的放在最后面。

将剩余序列中所有元素两两比较，将最大的放在最后面。

重复第二步，直到只剩下一个数。



![图片](http://mmbiz.qpic.cn/mmbiz_png/MOwlO0INfQpKubibb9mtMxTlSzahzOFjA0OicXlwPAKmZ7jXvj33ZXWuYMbuA7qibWAXzcaR4Miag3HRXHurNkmM8w/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

**如何写成代码：**

设置循环次数。

设置开始比较的位数，和结束的位数。

两两比较，将最小的放到前面去。

重复2、3步，直到循环次数完毕。



**代码实现如下：**



![图片](http://mmbiz.qpic.cn/mmbiz_png/MOwlO0INfQpKubibb9mtMxTlSzahzOFjAtXlRKb6NaEzLeJNPz5zuC38ACp55hdZVjehkoQdhmyDLk2zUibC63EQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**6.快速排序**



要求时间最快时。

选择第一个数为p，小于p的数放在左边，大于p的数放在右边。

递归的将p左边和右边的数都按照第一步进行，直到不能递归。



![图片](http://mmbiz.qpic.cn/mmbiz_png/MOwlO0INfQpKubibb9mtMxTlSzahzOFjAc2hHN5PqIBHh6ico8DDr9via2bZeq5axpeGOkdtSdN0Td7sSewboUrkQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**代码实现如下：**



![图片](http://mmbiz.qpic.cn/mmbiz_png/MOwlO0INfQpKubibb9mtMxTlSzahzOFjAqvXZUKte59KcLdgzTPtCX2kxtoGAbAvttI6nkeLTLkJwRLicLejGJIg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**7.归并排序**



速度仅次于快排，内存少的时候使用，可以进行并行计算的时候使用。

选择相邻两个数组成一个有序序列。

选择相邻的两个有序序列组成一个有序序列。

重复第二步，直到全部组成一个有序序列。



![图片](http://mmbiz.qpic.cn/mmbiz_png/MOwlO0INfQpKubibb9mtMxTlSzahzOFjAfhlknGEwgPhyQU9BPdH3WZKWk6IGH0q1kpmTK1xczhHlT6UkAKalicA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**代码实现如下：**

> 1. `public static void mergeSort(int[] numbers, int left, int right) { `
> 2. `  int t = 1;// 每组元素个数  `
> 3. `  int size = right - left + 1; `
> 4. `  while (t < size) { `
> 5. `    int s = t;// 本次循环每组元素个数  `
> 6. `    t = 2 * s; `
> 7. `    int i = left; `
> 8. `    while (i + (t - 1) < size) { `
> 9. `      merge(numbers, i, i + (s - 1), i + (t - 1)); `
> 10. `      i += t; `
> 11. `    } `
> 12. `    if (i + (s - 1) < right) `
> 13. `      merge(numbers, i, i + (s - 1), right); `
> 14. `  } `
> 15. `} `
> 16. `private static void merge(int[] data, int p, int q, int r) { `
> 17. `  int[] B = new int[data.length]; `
> 18. `  int s = p; `
> 19. `  int t = q + 1; `
> 20. `  int k = p; `
> 21. `  while (s <= q && t <= r) { `
> 22. `    if (data[s] <= data[t]) { `
> 23. `      B[k] = data[s]; `
> 24. `      s++; `
> 25. `    } else { `
> 26. `      B[k] = data[t]; `
> 27. `      t++; `
> 28. `    } `
> 29. `    k++; `
> 30. `  } `
> 31. `  if (s == q + 1) `
> 32. `    B[k++] = data[t++]; `
> 33. `  else `
> 34. `    B[k++] = data[s++]; `
> 35. `  for (int i = p; i <= r; i++) `
> 36. `    data[i] = B[i]; `
> 37. `}`





**8.基数排序**



用于大量数，很长的数进行排序时。

将所有的数的个位数取出，按照个位数进行排序，构成一个序列。

将新构成的所有的数的十位数取出，按照十位数进行排序，构成一个序列。



![图片](http://mmbiz.qpic.cn/mmbiz_png/MOwlO0INfQpKubibb9mtMxTlSzahzOFjAnF55npQc3WybgGTnCngps4Emm7sV55eniaibhVc9xA2Kxd4lN8pdzfog/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**代码实现如下：**



> 1. `public void sort(int[] array) {`
> 2. `    //首先确定排序的趟数;   `
> 3. `    int max = array[0];`
> 4. `    for (int i = 1; i < array.length; i++) {`
> 5. `      if (array[i] > max) {`
> 6. `        max = array[i];`
> 7. `      }`
> 8. `    }`
> 9. `    int time = 0;`
> 10. `    //判断位数;   `
> 11. `    while (max > 0) {`
> 12. `      max /= 10;`
> 13. `      time++;`
> 14. `    }`
> 15. `    //建立10个队列;   `
> 16. `    List queue = new ArrayList();`
> 17. `    for (int i = 0; i < 10; i++) {`
> 18. `      ArrayList queue1 = new ArrayList();`
> 19. `      queue.add(queue1);`
> 20. `    }`
> 21. `    //进行time次分配和收集;   `
> 22. `    for (int i = 0; i < time; i++) {`
> 23. `      //分配数组元素;   `
> 24. `      for (int j = 0; j < array.length; j++) {`
> 25. `        //得到数字的第time+1位数;  `
> 26. `        int x = array[j] % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);`
> 27. `        ArrayList queue2 = queue.get(x);`
> 28. `        queue2.add(array[j]);`
> 29. `        queue.set(x, queue2);`
> 30. `      }`
> 31. `      int count = 0;//元素计数器;   `
> 32. `      //收集队列元素;   `
> 33. `      for (int k = 0; k < 10; k++) {`
> 34. `        while (queue.get(k).size() > 0) {`
> 35. `          ArrayList queue3 = queue.get(k);`
> 36. `          array[count] = queue3.get(0);`
> 37. `          queue3.remove(0);`
> 38. `          count++;`
> 39. `        }`
> 40. `      }`
> 41. `    }`