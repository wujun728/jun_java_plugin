package com.xxl.apm.client.os.impl;

import com.xxl.apm.client.os.OsHelper;

/**
 * @author xuxueli 2019-01-11
 */
public class JdkOsHelper extends OsHelper {

    @Override
    public long getCommittedVirtualMemorySize() {
        return 0;
    }

    @Override
    public long getTotalSwapSpaceSize() {
        return 0;
    }

    @Override
    public long getFreeSwapSpaceSize() {
        return 0;
    }

    @Override
    public long getProcessCpuTime() {
        return 0;
    }

    @Override
    public long getFreePhysicalMemorySize() {
        return 0;
    }

    @Override
    public long getTotalPhysicalMemorySize() {
        return 0;
    }

    @Override
    public double getSystemCpuLoad() {
        return 0;
    }

    @Override
    public double getProcessCpuLoad() {
        return 0;
    }

    @Override
    public double getCpu_system_load_percent() {
        return 0;
    }

    @Override
    public double getCpu_jvm_load_percent() {
        return 0;
    }

}
