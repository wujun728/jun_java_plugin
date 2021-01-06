package com.xxl.apm.client.message.impl;

import com.xxl.apm.client.message.XxlApmMsg;
import com.xxl.apm.client.os.OsHelper;

import java.io.Serializable;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * app and machine info, like "memory、gc、thread、class、system", report each minute
 *
 * @author xuxueli 2018-12-29 17:40:15
 */
public class XxlApmHeartbeat extends XxlApmMsg {

    // gc
    private GcInfo young_gc = new GcInfo();
    private GcInfo full_gc = new GcInfo();
    private GcInfo unknown_gc = new GcInfo();

    // heap, in KB units, max for used percent
    private MemoryInfo heap_all = new MemoryInfo();
    private MemoryInfo heap_eden_space = new MemoryInfo();
    private MemoryInfo heap_survivor_space = new MemoryInfo();
    private MemoryInfo heap_old_gen = new MemoryInfo();

    // non-heap
    private MemoryInfo non_heap_all = new MemoryInfo();
    private MemoryInfo non_heap_code_cache = new MemoryInfo();
    private MemoryInfo non_heap_perm_gen = new MemoryInfo();
    private MemoryInfo non_heap_metaspace = new MemoryInfo();

    // thread
    private List<ThreadInfo> thread_list = new ArrayList<ThreadInfo>();

    // class
    private ClassInfo class_info = new ClassInfo();

    // system
    private SystemInfo system_info = new SystemInfo();


    public MemoryInfo getHeap_all() {
        return heap_all;
    }

    public void setHeap_all(MemoryInfo heap_all) {
        this.heap_all = heap_all;
    }

    public MemoryInfo getHeap_eden_space() {
        return heap_eden_space;
    }

    public void setHeap_eden_space(MemoryInfo heap_eden_space) {
        this.heap_eden_space = heap_eden_space;
    }

    public MemoryInfo getHeap_survivor_space() {
        return heap_survivor_space;
    }

    public void setHeap_survivor_space(MemoryInfo heap_survivor_space) {
        this.heap_survivor_space = heap_survivor_space;
    }

    public MemoryInfo getHeap_old_gen() {
        return heap_old_gen;
    }

    public void setHeap_old_gen(MemoryInfo heap_old_gen) {
        this.heap_old_gen = heap_old_gen;
    }

    public MemoryInfo getNon_heap_all() {
        return non_heap_all;
    }

    public void setNon_heap_all(MemoryInfo non_heap_all) {
        this.non_heap_all = non_heap_all;
    }

    public MemoryInfo getNon_heap_metaspace() {
        return non_heap_metaspace;
    }

    public void setNon_heap_metaspace(MemoryInfo non_heap_metaspace) {
        this.non_heap_metaspace = non_heap_metaspace;
    }

    public MemoryInfo getNon_heap_code_cache() {
        return non_heap_code_cache;
    }

    public void setNon_heap_code_cache(MemoryInfo non_heap_code_cache) {
        this.non_heap_code_cache = non_heap_code_cache;
    }

    public MemoryInfo getNon_heap_perm_gen() {
        return non_heap_perm_gen;
    }

    public void setNon_heap_perm_gen(MemoryInfo non_heap_perm_gen) {
        this.non_heap_perm_gen = non_heap_perm_gen;
    }

    public GcInfo getYoung_gc() {
        return young_gc;
    }

    public void setYoung_gc(GcInfo young_gc) {
        this.young_gc = young_gc;
    }

    public GcInfo getFull_gc() {
        return full_gc;
    }

    public void setFull_gc(GcInfo full_gc) {
        this.full_gc = full_gc;
    }

    public GcInfo getUnknown_gc() {
        return unknown_gc;
    }

    public void setUnknown_gc(GcInfo unknown_gc) {
        this.unknown_gc = unknown_gc;
    }

    public List<ThreadInfo> getThread_list() {
        return thread_list;
    }

    public void setThread_list(List<ThreadInfo> thread_list) {
        this.thread_list = thread_list;
    }

    public ClassInfo getClass_info() {
        return class_info;
    }

    public void setClass_info(ClassInfo class_info) {
        this.class_info = class_info;
    }

    public SystemInfo getSystem_info() {
        return system_info;
    }

    public void setSystem_info(SystemInfo system_info) {
        this.system_info = system_info;
    }


    // tool
    @Override
    public void complete() {
        super.complete();

        // memory
        int kb_bytes = 1024;
        for (final MemoryPoolMXBean memoryPool : ManagementFactory.getMemoryPoolMXBeans()) {
            if (memoryPool.getName().endsWith("Eden Space")) {
                this.heap_eden_space.setUsed_space(memoryPool.getUsage().getUsed()/kb_bytes);
                this.heap_eden_space.setMax_space(memoryPool.getUsage().getMax()/kb_bytes);
            } else if (memoryPool.getName().endsWith("Survivor Space")) {
                this.heap_survivor_space.setUsed_space(memoryPool.getUsage().getUsed()/kb_bytes);
                this.heap_survivor_space.setMax_space(memoryPool.getUsage().getMax()/kb_bytes);
            } else if (memoryPool.getName().endsWith("Old Gen")) {
                this.heap_old_gen.setUsed_space(memoryPool.getUsage().getUsed()/kb_bytes);
                this.heap_old_gen.setMax_space(memoryPool.getUsage().getMax()/kb_bytes);
            } else if (memoryPool.getName().endsWith("Code Cache")) {
                this.non_heap_code_cache.setUsed_space(memoryPool.getUsage().getUsed()/kb_bytes);
                this.non_heap_code_cache.setMax_space(memoryPool.getUsage().getMax()/kb_bytes);
            } else if (memoryPool.getName().endsWith("Perm Gen")) {
                this.non_heap_perm_gen.setUsed_space(memoryPool.getUsage().getUsed()/kb_bytes);
                this.non_heap_perm_gen.setMax_space(memoryPool.getUsage().getMax()/kb_bytes);
            } else if (memoryPool.getName().endsWith("Metaspace")) {
                this.non_heap_metaspace.setUsed_space(memoryPool.getUsage().getUsed()/kb_bytes);
                this.non_heap_metaspace.setMax_space(memoryPool.getUsage().getMax()/kb_bytes);
            }
        }

        //final MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        this.heap_all.setUsed_space( (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / kb_bytes);  // total = now-max, free = now-free
        this.heap_all.setMax_space( Runtime.getRuntime().maxMemory() / kb_bytes);       // max = jvm can use, exclude one of two Survivor(from to), may less than 'Xmx val'

        final MemoryUsage nonHeapMemoryUsage = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
        this.non_heap_all.setUsed_space(nonHeapMemoryUsage.getUsed()/kb_bytes);
        this.non_heap_all.setMax_space(nonHeapMemoryUsage.getMax()/kb_bytes);


        // gc_fullgc
        long young_gc_count = 0;
        long young_gc_time = 0;
        long old_gc_count = 0;
        long old_gc_time = 0;
        long unknown_gc_count = 0;
        long unknown_gc_time = 0;

        for (final GarbageCollectorMXBean garbageCollector : ManagementFactory.getGarbageCollectorMXBeans()) {
            if (Arrays.asList("Copy", "ParNew", "PS Scavenge", "G1 Young Generation").contains(garbageCollector.getName())) {
                // young
                young_gc_count += garbageCollector.getCollectionCount();
                young_gc_time += garbageCollector.getCollectionTime();
            } else if (Arrays.asList("MarkSweepCompact", "PS MarkSweep", "ConcurrentMarkSweep", "G1 Old Generation").contains(garbageCollector.getName())){
                // old
                old_gc_count += garbageCollector.getCollectionCount();
                old_gc_time += garbageCollector.getCollectionTime();
            } else {
                // unknown
                unknown_gc_count += garbageCollector.getCollectionCount();
                unknown_gc_time += garbageCollector.getCollectionTime();
            }
        }
        this.young_gc.setCount(young_gc_count - GcInfo.last_young_gc.getCount());
        this.young_gc.setTime(young_gc_time - GcInfo.last_young_gc.getTime());
        this.full_gc.setCount(old_gc_count - GcInfo.last_full_gc.getCount());
        this.full_gc.setTime(old_gc_time - GcInfo.last_full_gc.getTime());
        this.unknown_gc.setCount(unknown_gc_count - GcInfo.last_unknown_gc.getCount());
        this.unknown_gc.setTime(unknown_gc_time - GcInfo.last_unknown_gc.getTime());

        GcInfo.last_young_gc.setCount(young_gc_count);
        GcInfo.last_young_gc.setTime(young_gc_time);
        GcInfo.last_full_gc.setCount(old_gc_count);
        GcInfo.last_full_gc.setTime(old_gc_time);
        GcInfo.last_unknown_gc.setCount(unknown_gc_count);
        GcInfo.last_unknown_gc.setTime(unknown_gc_time);


        // thread_list
        java.lang.management.ThreadInfo[] threadInfos = ManagementFactory.getThreadMXBean().getThreadInfo(
                ManagementFactory.getThreadMXBean().getAllThreadIds(), 100);

        for (java.lang.management.ThreadInfo threadItem : threadInfos) {
            if (threadItem == null) {
                continue;
            }
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.setId(threadItem.getThreadId());
            threadInfo.setName(threadItem.getThreadName());
            threadInfo.setStatus(threadItem.getThreadState().name());
            if (threadItem.getStackTrace()!=null && threadItem.getStackTrace().length>0) {
                StringBuilder stackTrace = new StringBuilder();
                String separator = System.getProperty("line.separator");
                for (final StackTraceElement stackTraceElement : threadItem.getStackTrace()) {
                    stackTrace.append(stackTraceElement);
                    stackTrace.append(separator);
                }
                threadInfo.setStack_info(stackTrace.toString());
            }

            this.thread_list.add(threadInfo);
        }

        // class
        this.class_info.setLoaded_count(ManagementFactory.getClassLoadingMXBean().getLoadedClassCount());
        this.class_info.setUnload_count(ManagementFactory.getClassLoadingMXBean().getUnloadedClassCount());

        // system, in kb、ms
        int ms_nanoseconds = 1000000;
        this.system_info.setOs_name(System.getProperty("os.name"));
        this.system_info.setOs_arch(System.getProperty("os.arch"));
        this.system_info.setOs_version(System.getProperty("os.version"));
        this.system_info.setOs_user_name(System.getProperty("user.name"));
        this.system_info.setJava_home(System.getProperty("java.home"));
        this.system_info.setJava_version(System.getProperty("java.version"));

        this.system_info.setCommitted_virtual_memory(OsHelper.getInstance().getCommittedVirtualMemorySize()/kb_bytes);
        this.system_info.setTotal_swap_space(OsHelper.getInstance().getTotalSwapSpaceSize()/kb_bytes);
        this.system_info.setFree_swap_space(OsHelper.getInstance().getFreeSwapSpaceSize()/kb_bytes);
        this.system_info.setTotal_physical_memory(OsHelper.getInstance().getTotalPhysicalMemorySize()/kb_bytes);
        this.system_info.setFree_physical_memory(OsHelper.getInstance().getFreePhysicalMemorySize()/kb_bytes);


        long process_cpu_time = (OsHelper.getInstance().getProcessCpuTime() - SystemInfo.last_process_cpu_time)/ms_nanoseconds;
        SystemInfo.last_process_cpu_time = OsHelper.getInstance().getProcessCpuTime();

        this.system_info.setProcess_cpu_time(process_cpu_time);
        this.system_info.setSystem_cpu_load(OsHelper.getInstance().getSystemCpuLoad());
        this.system_info.setProcess_cpu_load(OsHelper.getInstance().getProcessCpuLoad());

        this.system_info.setCpu_count(ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors());
        this.system_info.setCpu_load_average_1min(ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage());

        this.system_info.setCpu_system_load_percent(OsHelper.getInstance().getCpu_system_load_percent());
        this.system_info.setCpu_jvm_load_percent(OsHelper.getInstance().getCpu_jvm_load_percent());
    }


    // sub class

    public static class MemoryInfo implements Serializable {
        private static final long serialVersionUID = 42L;

        private float used_space;   // in kb units
        private float max_space;  // in kb units, to generate used percent

        public MemoryInfo() {
        }
        public MemoryInfo(float used_space, float max_space) {
            this.used_space = used_space;
            this.max_space = max_space;
        }

        public float getUsed_space() {
            return used_space;
        }

        public void setUsed_space(float used_space) {
            this.used_space = used_space;
        }

        public float getMax_space() {
            return max_space;
        }

        public void setMax_space(float max_space) {
            this.max_space = max_space;
        }
    }

    public static class GcInfo implements Serializable {
        private static final long serialVersionUID = 42L;

        // last
        public static GcInfo last_young_gc = new GcInfo();
        public static GcInfo last_full_gc = new GcInfo();
        public static GcInfo last_unknown_gc = new GcInfo();

        // field
        private long count;
        private long time;     // in ms units

        public GcInfo() {
        }
        public GcInfo(long count, long time) {
            this.count = count;
            this.time = time;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }

    public static class ThreadInfo implements Serializable {
        private static final long serialVersionUID = 42L;

        private long id;
        private String name;
        private String status;      // java.lang.Thread.State
        private String stack_info;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStack_info() {
            return stack_info;
        }

        public void setStack_info(String stack_info) {
            this.stack_info = stack_info;
        }
    }

    public static class ClassInfo implements Serializable {
        private static final long serialVersionUID = 42L;

        private int loaded_count;
        private long unload_count;

        public int getLoaded_count() {
            return loaded_count;
        }

        public void setLoaded_count(int loaded_count) {
            this.loaded_count = loaded_count;
        }

        public long getUnload_count() {
            return unload_count;
        }

        public void setUnload_count(long unload_count) {
            this.unload_count = unload_count;
        }
    }

    public static class SystemInfo implements Serializable {
        private static final long serialVersionUID = 42L;

        public static long last_process_cpu_time = OsHelper.getInstance().getProcessCpuTime();  // last already use cpu time, in ns

        // os
        private String os_name;
        private String os_arch ;
        private String os_version;
        private String os_user_name;
        private String java_home;
        private String java_version;


        // swap size
        private long total_swap_space;
        private long free_swap_space;
        // physical memory, in km
        private long total_physical_memory;
        private long free_physical_memory;
        // virtual memory
        private long committed_virtual_memory;

        // cpu
        private double system_cpu_load;
        private double process_cpu_load;

        private long process_cpu_time;      // in ms
        private int cpu_count;

        private double cpu_system_load_percent;
        private double cpu_jvm_load_percent;
        private double cpu_load_average_1min;


        public String getOs_name() {
            return os_name;
        }

        public void setOs_name(String os_name) {
            this.os_name = os_name;
        }

        public String getOs_arch() {
            return os_arch;
        }

        public void setOs_arch(String os_arch) {
            this.os_arch = os_arch;
        }

        public String getOs_version() {
            return os_version;
        }

        public void setOs_version(String os_version) {
            this.os_version = os_version;
        }

        public String getOs_user_name() {
            return os_user_name;
        }

        public void setOs_user_name(String os_user_name) {
            this.os_user_name = os_user_name;
        }

        public String getJava_home() {
            return java_home;
        }

        public void setJava_home(String java_home) {
            this.java_home = java_home;
        }

        public String getJava_version() {
            return java_version;
        }

        public void setJava_version(String java_version) {
            this.java_version = java_version;
        }

        public long getCommitted_virtual_memory() {
            return committed_virtual_memory;
        }

        public void setCommitted_virtual_memory(long committed_virtual_memory) {
            this.committed_virtual_memory = committed_virtual_memory;
        }

        public long getTotal_swap_space() {
            return total_swap_space;
        }

        public void setTotal_swap_space(long total_swap_space) {
            this.total_swap_space = total_swap_space;
        }

        public long getFree_swap_space() {
            return free_swap_space;
        }

        public void setFree_swap_space(long free_swap_space) {
            this.free_swap_space = free_swap_space;
        }

        public long getTotal_physical_memory() {
            return total_physical_memory;
        }

        public void setTotal_physical_memory(long total_physical_memory) {
            this.total_physical_memory = total_physical_memory;
        }

        public long getFree_physical_memory() {
            return free_physical_memory;
        }

        public void setFree_physical_memory(long free_physical_memory) {
            this.free_physical_memory = free_physical_memory;
        }

        public long getProcess_cpu_time() {
            return process_cpu_time;
        }

        public void setProcess_cpu_time(long process_cpu_time) {
            this.process_cpu_time = process_cpu_time;
        }

        public double getSystem_cpu_load() {
            return system_cpu_load;
        }

        public void setSystem_cpu_load(double system_cpu_load) {
            this.system_cpu_load = system_cpu_load;
        }

        public double getProcess_cpu_load() {
            return process_cpu_load;
        }

        public void setProcess_cpu_load(double process_cpu_load) {
            this.process_cpu_load = process_cpu_load;
        }

        public int getCpu_count() {
            return cpu_count;
        }

        public void setCpu_count(int cpu_count) {
            this.cpu_count = cpu_count;
        }

        public double getCpu_load_average_1min() {
            return cpu_load_average_1min;
        }

        public void setCpu_load_average_1min(double cpu_load_average_1min) {
            this.cpu_load_average_1min = cpu_load_average_1min;
        }

        public double getCpu_system_load_percent() {
            return cpu_system_load_percent;
        }

        public void setCpu_system_load_percent(double cpu_system_load_percent) {
            this.cpu_system_load_percent = cpu_system_load_percent;
        }

        public double getCpu_jvm_load_percent() {
            return cpu_jvm_load_percent;
        }

        public void setCpu_jvm_load_percent(double cpu_jvm_load_percent) {
            this.cpu_jvm_load_percent = cpu_jvm_load_percent;
        }
    }

}