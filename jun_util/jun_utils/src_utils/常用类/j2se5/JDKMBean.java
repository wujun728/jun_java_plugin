package book.j2se5;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;

/**
 * 用户可以通过MXBean可以管理和监视虚拟机。
 */
public class JDKMBean {

	/**
	 * MemoryMXBean是Java 虚拟机的内存系统的管理接口 
	 * Java 虚拟机具有此接口的实现类的单一实例
	 */
	public static void printMemoryMXBean() {
		// 获得单一实例
		MemoryMXBean instance = ManagementFactory.getMemoryMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		// 返回用于对象分配的堆的当前内存使用量
		System.out.printf("%s: %s%n", "HeapMemoryUsage", instance
				.getHeapMemoryUsage());
		// 返回 Java 虚拟机使用的非堆内存的当前使用量
		System.out.printf("%s: %s%n", "getNonHeapMemoryUsage", instance
				.getNonHeapMemoryUsage());
		// 运行垃圾回收器
		instance.gc();
	}

	/**
	 * ClassLoadingMXBean是Java 虚拟机的类加载系统的管理接口 
	 * Java 虚拟机具有此接口的实现类的单个实例
	 */
	public static void printClassLoadingMXBean() {
		// 获得单一实例
		ClassLoadingMXBean instance = ManagementFactory.getClassLoadingMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		// 返回当前加载到 Java 虚拟机中的类的数量
		System.out.printf("%s: %s%n", "LoadedClassCount", instance
				.getLoadedClassCount());
		// 返回自 Java 虚拟机开始执行到目前已经加载的类的总数
		System.out.printf("%s: %s%n", "TotalLoadedClassCount", instance
				.getTotalLoadedClassCount());
		// 返回自 Java 虚拟机开始执行到目前已经卸载的类的总数
		System.out.printf("%s: %s%n", "UnloadedClassCount", instance
				.getUnloadedClassCount());
	}

	/**
	 * ThreadMXBean是Java 虚拟机线程系统的管理接口 
	 * Java 虚拟机具有此接口的实现类的单个实例
	 */
	public static void printThreadMXBean() {
		// 获得单一实例
		ThreadMXBean instance = ManagementFactory.getThreadMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		// 返回活动线程的当前数目，包括守护线程和非守护线程。
		System.out.printf("%s: %s%n", "ThreadCount", instance.getThreadCount());
		// 返回活动线程 ID
		System.out.printf("%s: %n", "Thread IDs");
		long[] ids = instance.getAllThreadIds();
		for (long id : ids) {
			System.out.printf("%s;  ", id);
		}
		System.out.printf("%n");
		// 返回活动守护线程的当前数目
		System.out.printf("%s: %s%n", "DaemonThreadCount", instance
				.getDaemonThreadCount());
		// 返回自从 Java 虚拟机启动或峰值重置以来峰值活动线程计数
		System.out.printf("%s: %s%n", "PeakThreadCount", instance
				.getPeakThreadCount());
		// 返回当前线程的总 CPU 时间
		System.out.printf("%s: %s%n", "CurrentThreadCpuTime", instance
				.getCurrentThreadCpuTime());
		// 返回当前线程在用户模式中执行的 CPU 时间
		System.out.printf("%s: %s%n", "CurrentThreadUserTime", instance
				.getCurrentThreadUserTime());
	}

	/**
	 * RuntimeMXBean是Java 虚拟机的运行时系统的管理接口 
	 * Java 虚拟机具有此接口的实现类的单个实例
	 */
	public static void printRuntimeMXBean() {
		// 获得单一实例
		RuntimeMXBean instance = ManagementFactory.getRuntimeMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		// 返回由引导类加载器用于搜索类文件的引导类路径
		System.out.printf("%s: %s%n", "BootClassPath", instance
				.getBootClassPath());
		// 返回系统类加载器用于搜索类文件的 Java 类路径
		System.out.printf("%s: %s%n", "ClassPath", instance.getClassPath());
		// 返回传递给 Java 虚拟机的输入变量，其中不包括传递给 main 方法的变量
		System.out.printf("%s%n", "InputArguments");
		List<String> args = instance.getInputArguments();
		for (String arg : args) {
			System.out.printf("%s;  ", arg);
		}
		// 返回 Java 库路径
		System.out.printf("%s: %s%n", "LibraryPath", instance.getLibraryPath());
		// 返回正在运行的 Java 虚拟机实现的管理接口的规范版本
		System.out.printf("%s: %s%n", "ManagementSpecVersion", instance
				.getManagementSpecVersion());
		// 返回表示正在运行的 Java 虚拟机的名称
		System.out.printf("%s: %s%n", "Name", instance.getName());

		// 返回 Java 虚拟机规范名称
		System.out.printf("%s: %s%n", "SpecName", instance.getSpecName());
		// 返回 Java 虚拟机规范供应商
		System.out.printf("%s: %s%n", "SpecVendor", instance.getSpecVendor());
		// 返回 Java 虚拟机规范版本
		System.out.printf("%s: %s%n", "SpecVersion", instance.getSpecVersion());

		// 返回 Java 虚拟机实现名称
		System.out.printf("%s: %s%n", "VmName", instance.getVmName());
		// 返回 Java 虚拟机实现供应商
		System.out.printf("%s: %s%n", "VmVendor", instance.getVmVendor());
		// 返回 Java 虚拟机实现版本
		System.out.printf("%s: %s%n", "VmVersion", instance.getVmVersion());

		// 返回 Java 虚拟机的启动时间
		System.out.printf("%s: %s%n", "StartTime", instance.getStartTime());
		// 返回 Java 虚拟机的正常运行时间
		System.out.printf("%s: %s%n", "Uptime", instance.getUptime());
	}

	/**
	 * OperatingSystemMXBean是操作系统的管理接口 
	 * Java 虚拟机具有此接口的实现类的单个实例
	 */
	public static void printOperatingSystemMXBean() {
		// 获得单一实例
		OperatingSystemMXBean instance = ManagementFactory
				.getOperatingSystemMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		// 返回操作系统的架构
		System.out.printf("%s: %s%n", "Arch", instance.getArch());
		// 返回 Java 虚拟机可以使用的处理器数目
		System.out.printf("%s: %s%n", "AvailableProcessors", instance
				.getAvailableProcessors());
		// 返回操作系统名称
		System.out.printf("%s: %s%n", "Name", instance.getName());
		// 返回操作系统版本
		System.out.printf("%s: %s%n", "Version", instance.getVersion());
	}

	/**
	 * CompilationMXBean是Java 虚拟机的编译系统的管理接口 
	 * Java 虚拟机具有此接口的实现类的单个实例
	 */
	public static void printCompilationMXBean() {
		// 获得单一实例
		CompilationMXBean instance = ManagementFactory.getCompilationMXBean();
		System.out.printf("%n---%s---%n", instance.getClass().getName());
		// 返回即时 (JIT) 编译器的名称
		System.out.printf("%s: %s%n", "Name", instance.getName());
		// 返回在编译上花费的累积耗费时间的近似值
		System.out.printf("%s: %s%n", "TotalCompilationTime", instance
				.getTotalCompilationTime());
	}

	/**
	 * GarbageCollectorMXBean是Java 虚拟机的垃圾回收的管理接口 
	 * Java 虚拟机可能具有此接口的实现类的一个或多个实例
	 */
	public static void printGarbageCollectorMXBean() {
		// 获得所有实例
		List<GarbageCollectorMXBean> instances = ManagementFactory
				.getGarbageCollectorMXBeans();
		System.out.printf("%n---%s---%n", GarbageCollectorMXBean.class
				.getName());
		// 遍历每个实例
		for (GarbageCollectorMXBean instance : instances) {
			// 返回垃圾收集器的名字
			System.out.printf("***%s: %s***%n", "Name", instance.getName());
			// 返回已发生的回收的总次数
			System.out.printf("%s: %s%n", "CollectionCount", instance
					.getCollectionCount());
			// 返回近似的累积回收时间
			System.out.printf("%s: %s%n", "CollectionTime", instance
					.getCollectionTime());
		}
	}

	public static void main(String[] args) {
		JDKMBean.printOperatingSystemMXBean();
		JDKMBean.printRuntimeMXBean();
		JDKMBean.printClassLoadingMXBean();
		JDKMBean.printCompilationMXBean();
		JDKMBean.printMemoryMXBean();
		JDKMBean.printThreadMXBean();
		JDKMBean.printGarbageCollectorMXBean();
	}
}
