# 原子性
- synchironized：不可中断锁，适合竞争不激烈，可读性好，依赖JVM。
- Lock：可中断锁，多样化同步，竞争激烈时能维持常态，依赖特殊的CPU指令，代码实现，ReentrantLock。
- Atomic：竞争激烈时能维持常态，比Lock性能好；只能同步一个值。
## 普通原子类型
提供对boolean、int、long和对象的原子性操作。
- AtomicBoolean
- AtomicInteger
- AtomicLong 
- AtomicReference

## 原子类型数组
提供对数组元素的原子性操作。

- AtomicLongArray
- AtomicIntegerArray
- AtomicReferenceArray

## 原子类型字段更新器
提供对指定对象的指定字段进行原子性操作。这个字段必须被volatile修饰，不能为static,final。对于AtomicIntegerFieldUpdater和AtomicLongFieldUpdater只能修改int/long类型的字段，不能修改其包装类型（Integer/Long）。如果要修改包装类型就需要使用AtomicReferenceFieldUpdater。
- AtomicLongFieldUpdater
- AtomicIntegerFieldUpdater
- AtomicReferenceFieldUpdater

## 原子类型字段更新器
带版本号的原子引用类型，以版本戳的方式解决原子类型的ABA问题。AtomicMarkableReference跟AtomicStampedReference差不多，AtomicStampedReference是使用pair的int stamp作为计数器使用，AtomicMarkableReference的pair使用的是boolean mark。AtomicStampedReference可能关心的是动过几次，AtomicMarkableReference关心的是有没有被人动过。
- AtomicStampedReference
-  AtomicMarkableReference

## 原子累加器(JDK1.8)
AtomicLong和AtomicDouble的升级类型，专门用于数据统计，性能更高。LongAdder在AtomicLong的基础上将单点的更新压力分散到各个节点，在低并发的时候通过对base的直接更新可以很好的保障和AtomicLong的性能基本保持一致，而在高并发的时候通过分散提高了性能。缺点是LongAdder在统计的时候如果有并发更新，可能导致统计的数据有误差。

- DoubleAccumulator
- DoubleAddr
- LongAccumulator
- LongAddr
---
# 可见性
## 导致共享变量在线程间不可见的原因：
- 线程交叉执行
- 重排序结合线程交叉执行
- 共享变量更新后的值没有在工作内存与主存间及时更新

## JMM关于synchronized的两条规定：
- 线程解锁前，必须把共享变量的最新值刷新到主内存。
- 线程加锁时，将清空工作内存中共享变量的值，从而使用共享变量时需要从主内存重新读取最新的值。

##Volatile：
- 通过加入内存屏障和禁止重排序优化来实现。
- 对volatile变量写操作时，会在写操作后加入一条store屏障指令，将本地内存中的共享变量值刷新到主内存。
- 对volatile变量读操作时，会在读操作前加入一条load屏障指令，从主内存中读取共享变量。
---

# 有序性
Java内存模型中，允许编译器和处理器对指令进行重排序，但是重排序过程不会影响到单线程程序的执行，却会影响到多线程并发执行的正确性。
##happens-before原则：
-  程序次序规则：一个线程内，按照代码顺序，书写在前面的操作先行发生于书写在后面的操作；
-  锁定规则：一个unLock操作先行发生于后面对同一个锁unlock操作；
-  volatile变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作；
-  传递规则：如果操作A先行发生于操作B，而操作B又先行发生于操作C，则可以得出操作A先行发生于操作C；
-  线程启动规则：Thread对象的start()方法先行发生于此线程的每个一个动作；
-  线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生；
-  线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值手段检测到线程已经终止执行；
-  对象终结规则：一个对象的初始化完成先行发生于他的finalize()方法的开始；
