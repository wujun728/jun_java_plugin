package net.jueb.util4j.study.jdk8.lock;

import java.util.concurrent.locks.StampedLock;

/**
 * 它是java8在java.util.concurrent.locks新增的一个API。
ReentrantReadWriteLock 在沒有任何读写锁时，才可以取得写入锁，
这可用于实现了悲观读取（Pessimistic Reading），
即如果执行中进行读取时，经常可能有另一执行要写入的需求，为了保持同步，
ReentrantReadWriteLock 的读取锁定就可派上用场。
然而，如果读取执行情况很多，写入很少的情况下，使用 ReentrantReadWriteLock 
可能会使写入线程遭遇饥饿（Starvation）问题，
也就是写入线程吃吃无法竞争到锁定而一直处于等待状态。
StampedLock控制锁有三种模式（写，读，乐观读），一个StampedLock状态是由版本和模式两个部分组成，
锁获取方法返回一个数字作为票据stamp，它用相应的锁状态表示并控制访问，数字0表示没有写锁被授权访问。在读锁上分为悲观锁和乐观锁。
所谓的乐观读模式，也就是若读的操作很多，写的操作很少的情况下，你可以乐观地认为，
写入与读取同时发生几率很少，因此不悲观地使用完全的读取锁定，程序可以查看读取资料之后，是否遭到写入执行的变更，
再采取后续的措施（重新读取变更信息，或者抛出异常） ，这一个小小改进，可大幅度提高程序的吞吐量！！
 * @author Administrator
 */
public class StampedLockDemo {
   private double x, y;
   private final StampedLock sl = new StampedLock();
   void move(double deltaX, double deltaY) { // an exclusively locked method
     long stamp = sl.writeLock();
     try {
       x += deltaX;
       y += deltaY;
     } finally {
       sl.unlockWrite(stamp);
     }
   }
  /**
   * 下面看看乐观读锁案例
   * @return
   */
  public double distanceFromOrigin() { // A read-only method
     long stamp = sl.tryOptimisticRead(); //获得一个乐观读锁
     double currentX = x, currentY = y; //将两个字段读入本地局部变量
     if (!sl.validate(stamp)) { //检查发出乐观读锁后同时是否有其他写锁发生？
        stamp = sl.readLock(); //如果没有，我们再次获得一个读悲观锁
        try {
          currentX = x; // 将两个字段读入本地局部变量
          currentY = y; // 将两个字段读入本地局部变量
        } finally {
           sl.unlockRead(stamp);
        }
     }
     return Math.sqrt(currentX * currentX + currentY * currentY);
   }
   /**
    * 下面是悲观读锁案例
    * @param newX
    * @param newY
    */
  public void moveIfAtOrigin(double newX, double newY) { // upgrade
     // Could instead start with optimistic, not read mode
     long stamp = sl.readLock();
     try {
       while (x == 0.0 && y == 0.0) { //循环，检查当前状态是否符合
         long ws = sl.tryConvertToWriteLock(stamp); //将读锁转为写锁
         if (ws != 0L) { //这是确认转为写锁是否成功
           stamp = ws; //如果成功 替换票据
           x = newX; //进行状态改变
           y = newY; //进行状态改变
           break;
         }
         else { //如果不能成功转换为写锁
           sl.unlockRead(stamp); //我们显式释放读锁
           stamp = sl.writeLock(); //显式直接进行写锁 然后再通过循环再试
         }
       }
     } finally {
       sl.unlock(stamp); //释放读锁或写锁
     }
   }
 }