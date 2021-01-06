package com.xxl.apm.client.os.impl;

import com.sun.management.OperatingSystemMXBean;
import com.xxl.apm.client.os.OsHelper;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;

/**
 * @author xuxueli 2019-01-11
 */
public class SunOsHelper extends OsHelper  {

    private OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();


    @Override
    public long getCommittedVirtualMemorySize() {
        return osmxb.getCommittedVirtualMemorySize();
    }

    @Override
    public long getTotalSwapSpaceSize() {
        return osmxb.getTotalSwapSpaceSize();
    }

    @Override
    public long getFreeSwapSpaceSize() {
        return osmxb.getFreeSwapSpaceSize();
    }

    @Override
    public long getProcessCpuTime() {
        return osmxb.getProcessCpuTime();
    }

    @Override
    public long getFreePhysicalMemorySize() {
        return osmxb.getFreePhysicalMemorySize();
    }

    @Override
    public long getTotalPhysicalMemorySize() {
        return osmxb.getTotalPhysicalMemorySize();
    }

    @Override
    public double getSystemCpuLoad() {
        return osmxb.getSystemCpuLoad();
    }

    @Override
    public double getProcessCpuLoad() {
        return osmxb.getProcessCpuLoad();
    }

    @Override
    public double getCpu_system_load_percent() {
        Method[] methods = OperatingSystemMXBean.class.getMethods();
        for(Method method : methods){
            if(method.getName().equals("getSystemCpuLoad")){
                try {
                    // "cpu.system.load.percent"
                    Double systemCpuLoad = (Double) method.invoke(osmxb, null);
                    return systemCpuLoad;
                } catch (Exception e) {
                    logger.error("SunOsHelper#getCpu_system_load_percent error:", e.getMessage());
                }
            }
        }
        return 0;
    }

    @Override
    public double getCpu_jvm_load_percent() {
        Method[] methods = OperatingSystemMXBean.class.getMethods();
        for(Method method : methods){
            if(method.getName().equals("getProcessCpuLoad")){
                try {
                    // "cpu.jvm.load.percent"
                    Double processCpuLoad = (Double) method.invoke(osmxb,null);
                    return processCpuLoad;
                } catch (Exception e) {
                    logger.error("SunOsHelper#getCpu_jvm_load_percent error:", e.getMessage());
                }
            }
        }
        return 0;
    }

}
