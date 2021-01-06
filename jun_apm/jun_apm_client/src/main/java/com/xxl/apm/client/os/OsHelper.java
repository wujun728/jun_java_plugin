package com.xxl.apm.client.os;

import com.xxl.apm.client.os.impl.JdkOsHelper;
import com.xxl.apm.client.os.impl.SunOsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuxueli 2019-01-11
 */
public abstract class OsHelper {
    protected static transient Logger logger = LoggerFactory.getLogger(OsHelper.class);

    private static OsHelper instance = null;
    public static OsHelper getInstance() {
        return instance;
    }
    static {
        try {
            Class.forName("com.sun.management.OperatingSystemMXBean");  // remove strong dependency on oracle jdk
            instance = new SunOsHelper();
        } catch (ClassNotFoundException e) {
        }
        if (instance == null) {
            instance = new JdkOsHelper();
        }
    }

    //
    public abstract long getCommittedVirtualMemorySize();

    public abstract long getTotalSwapSpaceSize();

    public abstract long getFreeSwapSpaceSize();

    public abstract long getProcessCpuTime();

    public abstract long getFreePhysicalMemorySize();

    public abstract long getTotalPhysicalMemorySize();

    public abstract double getSystemCpuLoad();

    public abstract double getProcessCpuLoad();

    //
    public abstract double getCpu_system_load_percent();
    public abstract double getCpu_jvm_load_percent();

}
