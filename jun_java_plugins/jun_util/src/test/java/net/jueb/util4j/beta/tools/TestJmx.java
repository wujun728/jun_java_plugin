package net.jueb.util4j.beta.tools;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;

public class TestJmx extends Thread {

 RuntimeMXBean mxbean;

 private void setFactory() {
  mxbean = ManagementFactory.getRuntimeMXBean();
  // 以下是取得虚拟机的属性
  System.out.println("===============虚拟机信息===================");
  System.out.println("虚拟机提供商：" + mxbean.getVmVendor());
  System.out.println("虚拟机名称：" + mxbean.getVmName());
  System.out.println("当前类路径：" + mxbean.getClassPath());
  System.out.println("进程号@机器：" + mxbean.getName());
  System.out.println("运行时间(毫秒)：" + mxbean.getUptime());

  // 类管理接口
  System.out.println("===============类加载信息===================");
  ClassLoadingMXBean aClassLoadingMXBean = ManagementFactory
    .getClassLoadingMXBean();
  System.out.println("已加载类总数："
    + aClassLoadingMXBean.getLoadedClassCount());
  System.out.println("Verbose状态：" + aClassLoadingMXBean.isVerbose());

  // 内存管理接口
  System.out.println("===============内存信息=====================");
  MemoryMXBean aMemoryMXBean = ManagementFactory.getMemoryMXBean();
  System.out.println("终止被挂起数："
    + aMemoryMXBean.getObjectPendingFinalizationCount());// 返回其终止被挂起的对象的近似数目
  // 返回 Java 虚拟机使用的非堆内存的当前使用量
  MemoryUsage aMemoryUsage = aMemoryMXBean.getNonHeapMemoryUsage(); // 虚拟机启动时从系统得到的内存（以字节为单位）
  System.out.println("虚拟机启动时从系统得到的内存:" + aMemoryUsage.getInit()); // 表示当前已经使用的内存量（以字节为单位）
  System.out.println("当前已经使用的内存量:" + aMemoryUsage.getUsed()); // 表示保证可以由
  // Java
  // 虚拟机使用的内存量（以字节为单位）

  System.out.println("虚拟机使用的内存量:" + aMemoryUsage.getCommitted()); // 表示可以用于内存管理的最大内存量（以字节为单位）
  System.out.println("内存管理的初始内存量:" + aMemoryUsage.getInit());
  System.out.println("内存管理的最大内存量:" + aMemoryUsage.getMax());
  System.out.println("总体描述:" + aMemoryUsage.toString());
  
  java.util.List<GarbageCollectorMXBean> aGCMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
  GarbageCollectorMXBean aGCMXBean = aGCMXBeans.get(0);
  System.out.println("===============GC信息=====================");
  System.out.println("收集总数:" + aGCMXBean.getCollectionCount());
  System.out.println("收集时间:" + aGCMXBean.getCollectionTime());
  System.out.println("内存管理器有效:" + aGCMXBean.isValid());
  System.out.println("内存池名:" + aGCMXBean.getMemoryPoolNames()[0]);
  System.out.println("内存池名:" + aGCMXBean.getMemoryPoolNames()[1]);
  
  
  System.out.println("*****************End**********************");
 }

 public void run() {
  try {
   while (true) {
    setFactory();
    sleep(1000);
   }
  } catch (InterruptedException e) {
   e.printStackTrace();
  }

 }

 public static void main(String[] args) {
  TestJmx aTest = new TestJmx();
  aTest.start();
 }

}