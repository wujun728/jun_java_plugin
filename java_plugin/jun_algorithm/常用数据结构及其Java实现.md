# [常用数据结构及其Java实现](https://www.cnblogs.com/javafirst0/p/10825309.html)



本文采用Java语言来进行描述，帮大家好好梳理一下[数据结构与算法](http://mp.weixin.qq.com/s?__biz=MzU5NTAzNjM0Mw==&mid=2247486068&idx=2&sn=38549b0917246312fe928249aac1d006&chksm=fe795bacc90ed2ba1d567ba5b0f630d3071d3a5a5fd22135fd349496f623993fee6a337c547e&scene=21#wechat_redirect)，在工作和面试中用的上。亦即总结常见的的数据结构，以及在Java中相应的实现方法，务求理论与实践一步总结到位。

常用数据结构

![img](https://upload-images.jianshu.io/upload_images/14266602-09a74965218b4a18?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 

数组

数组是相同数据类型的元素按一定顺序排列的集合，是一块连续的内存空间。数组的优点是：get和set操作时间上都是O(1)的；缺点是：add和remove操作时间上都是O(N)的。

Java中，Array就是数组，此外，[ArrayList使用了数组Array](http://mp.weixin.qq.com/s?__biz=MzU5NTAzNjM0Mw==&mid=2247484621&idx=1&sn=618ff09ab9c5f59d29d675a547832a21&chksm=fe795515c90edc03322d24b81570c7f99fde03d6e5aee3cb509f24827d70854183ebf9b1fa70&scene=21#wechat_redirect)作为其实现基础,它和一般的Array相比，最大的好处是，我们在添加元素时不必考虑越界，元素超出数组容量时，它会自动扩张保证容量。

Vector和ArrayList相比，主要差别就在于多了一个线程安全性，但是效率比较低下。如今java.util.concurrent包提供了许多线程安全的集合类（比如 LinkedBlockingQueue），所以不必再使用Vector了。

![img](https://upload-images.jianshu.io/upload_images/14266602-837220f250f8ee8c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


链表

链表是一种非连续、非顺序的结构，数据元素的逻辑顺序是通过链表中的指针链接次序实现的，链表由一系列结点组成。链表的优点是：add和remove操作时间上都是O(1)的；缺点是：get和set操作时间上都是O(N)的，而且需要额外的空间存储指向其他数据地址的项。

查找操作对于未排序的数组和链表时间上都是O(N)。

Java中，LinkedList 使用链表作为其基础实现。

![img](https://upload-images.jianshu.io/upload_images/14266602-aef20a7f89362704.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


//以上方法也适用于ArrayList

队列

队列是一种特殊的线性表，特殊之处在于它只允许在表的前端进行删除操作，而在表的后端进行插入操作，亦即所谓的先进先出（FIFO）。

Java中，LinkedList实现了Deque，可以做为双向队列（自然也可以用作单向队列）。另外PriorityQueue实现了带优先级的队列，亦即队列的每一个元素都有优先级，且元素按照优先级排序。

![img](https://upload-images.jianshu.io/upload_images/14266602-b6322ad21d31b962.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


栈

栈（stack）又名堆栈，它是一种运算受限的线性表。其限制是仅允许在表的一端进行插入和删除运算。这一端被称为栈顶，相对地，把另一端称为栈底。它体现了后进先出（LIFO）的特点。

Java中，Stack实现了这种特性，但是Stack也继承了Vector，所以具有线程安全线和效率低下两个特性，最新的JDK8中，推荐用Deque来实现栈，比如：

![img](https://upload-images.jianshu.io/upload_images/14266602-ce91ffec397b53c1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


集合

集合是指具有某种特定性质的具体的或抽象的对象汇总成的集体，这些对象称为该集合的元素，其主要特性是元素不可重复。

在[Java中，HashSet](http://mp.weixin.qq.com/s?__biz=MzU5NTAzNjM0Mw==&mid=2247485251&idx=4&sn=f3a7af867577f4b96cdde2330eacc47e&chksm=fe79569bc90edf8def15db47430884ff5c99d28b5f4f2251c483aa750ffce7b8023cc7e327f6&scene=21#wechat_redirect)体现了这种数据结构，而HashSet是在MashMap的基础上构建的。LinkedHashSet继承了HashSet，使用HashCode确定在集合中的位置，使用链表的方式确定位置，所以有顺序。TreeSet实现了SortedSet 接口，是排好序的集合（在TreeMap 基础之上构建），因此查找操作比普通的Hashset要快（log(N)）；插入操作要慢（log（N））,因为要维护有序。

![img](https://upload-images.jianshu.io/upload_images/14266602-d54b026c1c376b46.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


散列表

散列表也叫哈希表，是根据关键键值(Keyvalue)进行访问的数据结构，它通过把关键码值映射到表中一个位置来访问记录，以加快查找的速度，这个映射函数叫做散列函数。

Java中HashMap实现了散列表，而Hashtable比它多了一个线程安全性，但是由于使用了全局锁导致其性能较低，所以现在一般用[ConcurrentHashMap来实现](http://mp.weixin.qq.com/s?__biz=MzU5NTAzNjM0Mw==&mid=2247484560&idx=4&sn=1698890fc145404f058b4ce605e7dd06&chksm=fe795548c90edc5e5bf808ddac5139ff69bf1bcf551a414f45fd8d6b8fe8a3976688445e45e8&scene=21#wechat_redirect)线程安全的HashMap（类似的，以上的数据结构在最新的java.util.concurrent的包中几乎都有对应的高性能的线程安全的类）。TreeMap实现SortMap接口，能够把它保存的记录按照键排序。LinkedHashMap保留了元素插入的顺序。WeakHashMap是一种改进的HashMap，它对key实行“弱引用”，如果一个key不再被外部所引用，那么该key可以被GC回收，而不需要我们手动删除。

![img](https://upload-images.jianshu.io/upload_images/14266602-9fa91bf813dbfd55.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


树

树（tree）是包含n（n>0）个节点的有穷集合，其中：

每个元素称为节点（node）

有一个特定的节点被称为根节点或树根（root）

除根节点之外的其余数据元素被分为m（m≥0）个互不相交的结合T1，T2，……Tm-1，其中每一个集合Ti（1<=i<=m）本身也是一棵树，被称作原树的子树（subtree）

树这种数据结构在计算机世界中有广泛的应用，比如操作系统中用到了红黑树，数据库用到了B+树，编译器中的语法树，内存管理用到了堆（本质上也是树），信息论中的哈夫曼编码等等等等，在Java中TreeSet和TreeMap用到了树来排序（二分查找提高检索速度），不过一般都需要程序员自己去定义一个树的类，并实现相关性质，而没有现成的API。

下面用Java来实现各种常见的树。

二叉树

二叉树是一种基础而且重要的数据结构，其每个结点至多只有二棵子树，二叉树有左右子树之分，第i层至多有2^(i-1)个结点（i从1开始）；深度为k的二叉树至多有2^(k)-1)个结点，对任何一棵二叉树，如果其终端结点数为n0，度为2的结点数为n2，则n0=n2+1。

二叉树的性质：

在非空二叉树中，第i层的结点总数不超过2^(i-1), i>=1;

深度为h的二叉树最多有2^h-1个结点(h>=1)，最少有h个结点;

对于任意一棵二叉树，如果其叶结点数为N0，而度数为2的结点总数为N2，则N0=N2+1;

具有n个结点的完全二叉树的深度为log2(n+1);

有N个结点的完全二叉树各结点如果用顺序方式存储，则结点之间有如下关系： 若I为结点编号则 如果I>1，则其父结点的编号为I/2； 如果2I<=N，则其左儿子（即左子树的根结点）的编号为2I；若2I>N，则无左儿子； 如果2I+1<=N，则其右儿子的结点编号为2I+1；若2I+1>N，则无右儿子。

给定N个节点，能构成h(N)种不同的二叉树，其中h(N)为卡特兰数的第N项，h(n)=C(2*n, n)/(n+1)。

设有i个枝点，I为所有枝点的道路长度总和，J为叶的道路长度总和J=I+2i。

满二叉树、完全二叉树

满二叉树：除最后一层无任何子节点外，每一层上的所有结点都有两个子结点；

完全二叉树：若设二叉树的深度为h，除第 h 层外，其它各层 (1～(h-1)层) 的结点数都达到最大个数，第h层所有的结点都连续集中在最左边，这就是完全二叉树；

满二叉树是完全二叉树的一个特例。

二叉查找树

二叉查找树，又称为是二叉排序树（Binary Sort Tree）或二叉搜索树。二叉排序树或者是一棵空树，或者是具有下列性质的二叉树：

若左子树不空，则左子树上所有结点的值均小于它的根结点的值；

若右子树不空，则右子树上所有结点的值均大于或等于它的根结点的值；

左、右子树也分别为二叉排序树；

没有键值相等的节点。

二叉查找树的性质：对二叉查找树进行中序遍历，即可得到有序的数列。

二叉查找树的时间复杂度：它和二分查找一样，插入和查找的时间复杂度均为O(logn)，但是在最坏的情况下仍然会有O(n)的时间复杂度。原因在于插入和删除元素的时候，树没有保持平衡。我们追求的是在最坏的情况下仍然有较好的时间复杂度，这就是平衡二叉树设计的初衷。

二叉查找树可以这样表示：

![img](https://upload-images.jianshu.io/upload_images/14266602-62f5973bc6fddddf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


查找：

![img](https://upload-images.jianshu.io/upload_images/14266602-83f7c980a451abb9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


插入：

![img](https://upload-images.jianshu.io/upload_images/14266602-c3406845feaf114f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


删除：

![img](https://upload-images.jianshu.io/upload_images/14266602-526d2db70815aa40.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


平衡二叉树

平衡二叉树又被称为AVL树，具有以下性质：它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。它的出现就是解决二叉查找树不平衡导致查找效率退化为线性的问题，因为在删除和插入之时会维护树的平衡，使得查找时间保持在O(logn)，比二叉查找树更稳定。

ALLTree 的 Node 由 BST 的 Node 加上 private int height; 节点高度属性即可，这是为了便于判断树是否平衡。

维护树的平衡关键就在于旋转。对于一个平衡的节点，由于任意节点最多有两个儿子，因此高度不平衡时，此节点的两颗子树的高度差2，容易看出，这种不平衡出现在下面四种情况：

6节点的左子树3节点高度比右子树7节点大2，左子树3节点的左子树1节点高度大于右子树4节点，这种情况成为左左。

6节点的左子树2节点高度比右子树7节点大2，左子树2节点的左子树1节点高度小于右子树4节点，这种情况成为左右。

2节点的左子树1节点高度比右子树5节点小2，右子树5节点的左子树3节点高度大于右子树6节点，这种情况成为右左。

2节点的左子树1节点高度比右子树4节点小2，右子树4节点的左子树3节点高度小于右子树6节点，这种情况成为右右。

从图2中可以可以看出，1和4两种情况是对称的，这两种情况的旋转算法是一致的，只需要经过一次旋转就可以达到目标，我们称之为单旋转。2和3两种情况也是对称的，这两种情况的旋转算法也是一致的，需要进行两次旋转，我们称之为双旋转。

单旋转是针对于左左和右右这两种情况，这两种情况是对称的，只要解决了左左这种情况，右右就很好办了。图3是左左情况的解决方案，节点k2不满足平衡特性，因为它的左子树k1比右子树Z深2层，而且k1子树中，更深的一层的是k1的左子树X子树，所以属于左左情况。

为使树恢复平衡，我们把k1变成这棵树的根节点，因为k2大于k1，把k2置于k1的右子树上，而原本在k1右子树的Y大于k1，小于k2，就把Y置于k2的左子树上，这样既满足了二叉查找树的性质，又满足了平衡二叉树的性质。

这样的操作只需要一部分指针改变，结果我们得到另外一颗二叉查找树，它是一棵AVL树，因为X向上一移动了一层，Y还停留在原来的层面上，Z向下移动了一层。整棵树的新高度和之前没有在左子树上插入的高度相同，插入操作使得X高度长高了。因此，由于这颗子树高度没有变化，所以通往根节点的路径就不需要继续旋转了。

代码：

![img](https://upload-images.jianshu.io/upload_images/14266602-5c2ded3486ab6428.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


双旋转是针对于左右和右左这两种情况，单旋转不能使它达到一个平衡状态，要经过两次旋转。同样的，这样两种情况也是对称的，只要解决了左右这种情况，右左就很好办了。图4是左右情况的解决方案，节点k3不满足平衡特性，因为它的左子树k1比右子树Z深2层，而且k1子树中，更深的一层的是k1的右子树k2子树，所以属于左右情况。

为使树恢复平衡，我们需要进行两步，第一步，把k1作为根，进行一次右右旋转，旋转之后就变成了左左情况，所以第二步再进行一次左左旋转，最后得到了一棵以k2为根的平衡二叉树树。

代码：

![img](https://upload-images.jianshu.io/upload_images/14266602-3a7dd858d67513fe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


AVL查找操作与BST相同，AVL的删除与插入操作在BST基础之上需要检查是否平衡，如果不平衡就要使用旋转操作来维持平衡:

![img](https://upload-images.jianshu.io/upload_images/14266602-d2cdb39f24e51e72.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


堆

堆是一颗完全二叉树，在这棵树中，所有父节点都满足大于等于其子节点的堆叫大根堆，所有父节点都满足小于等于其子节点的堆叫小根堆。堆虽然是一颗树，但是通常存放在一个数组中，父节点和孩子节点的父子关系通过数组下标来确定。如下面的小根堆及[存储它的数组](http://mp.weixin.qq.com/s?__biz=MzU5NTAzNjM0Mw==&mid=2247486274&idx=1&sn=e03a9158d21561de7d9cfe0a11b30d0a&chksm=fe795a9ac90ed38c517875a11c84db84aa71b95e9cc1b2173526839666d62a65ca8488b5e811&scene=21#wechat_redirect)：

值：7,8,9,12,13,11

数组索引：0,1,2,3, 4, 5

通过一个节点在数组中的索引怎么计算出它的父节点及左右孩子节点的索引：

![img](https://upload-images.jianshu.io/upload_images/14266602-bcbe8249617f3e94.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


维护大根堆的性质：

![img](https://upload-images.jianshu.io/upload_images/14266602-06478b6b23f4ba98.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


构造堆：

![img](https://upload-images.jianshu.io/upload_images/14266602-c8070f130606648a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 


堆的用途：堆排序，[优先级队列](http://mp.weixin.qq.com/s?__biz=MzU5NTAzNjM0Mw==&mid=2247484705&idx=3&sn=a92401c2e99375866fc5a18dfa8f62b1&chksm=fe7954f9c90eddef5e6a0f011378fb890fca9f008e87e22d612d1db192881ba4a7463f314830&scene=21#wechat_redirect)。此外由于调整代价较小，也适合实时类型的排序与变更。

最后

写着写着就发现要想总结到位是一项非常庞大的工程，路漫漫其修远兮，吾将上下而求索。