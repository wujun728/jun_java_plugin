CPU cache结构和缓存一致性: https://blog.csdn.net/reliveit/article/details/50450136
# 普通原子类型
提供对boolean、int、long和对象的原子性操作。
- AtomicBoolean
- AtomicInteger
- AtomicLong
- AtomicReference

# 原子类型数组
提供对数组元素的原子性操作。

- AtomicLongArray
- AtomicIntegerArray
- AtomicReferenceArray

# 原子类型字段更新器
提供对指定对象的指定字段进行原子性操作。这个字段必须被volatile修饰，不能为static,final。对于AtomicIntegerFieldUpdater和AtomicLongFieldUpdater只能修改int/long类型的字段，不能修改其包装类型（Integer/Long）。如果要修改包装类型就需要使用AtomicReferenceFieldUpdater。
- AtomicLongFieldUpdater
- AtomicIntegerFieldUpdater
- AtomicReferenceFieldUpdater

# 原子类型字段更新器
带版本号的原子引用类型，以版本戳的方式解决原子类型的ABA问题。

-  AtomicStampedReference
-  AtomicMarkableReference

# 原子累加器(JDK1.8)
AtomicLong和AtomicDouble的升级类型，专门用于数据统计，性能更高。LongAdder在AtomicLong的基础上将单点的更新压力分散到各个节点，在低并发的时候通过对base的直接更新可以很好的保障和AtomicLong的性能基本保持一致，而在高并发的时候通过分散提高了性能。缺点是LongAdder在统计的时候如果有并发更新，可能导致统计的数据有误差。

- DoubleAccumulator
- DoubleAdder
- LongAccumulator
- LongAdder
