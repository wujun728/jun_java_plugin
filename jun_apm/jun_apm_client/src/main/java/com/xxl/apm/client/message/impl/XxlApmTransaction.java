package com.xxl.apm.client.message.impl;

import com.xxl.apm.client.XxlApm;

/**
 * transaction msg, like "web/rpc avgline、95line、99line"
 *
 * @author xuxueli 2018-12-29 16:55:14
 */
public class XxlApmTransaction extends XxlApmEvent {


    private long time = System.nanoTime();      // cost time, in milliseconds

    private long time_max;
    private long time_avg;
    private long time_tp90;
    private long time_tp95;
    private long time_tp99;
    private long time_tp999;

    public XxlApmTransaction() {
    }
    public XxlApmTransaction(String type, String name) {
        super(type, name);
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime_max() {
        return time_max;
    }

    public void setTime_max(long time_max) {
        this.time_max = time_max;
    }

    public long getTime_avg() {
        return time_avg;
    }

    public void setTime_avg(long time_avg) {
        this.time_avg = time_avg;
    }

    public long getTime_tp90() {
        return time_tp90;
    }

    public void setTime_tp90(long time_tp90) {
        this.time_tp90 = time_tp90;
    }

    public long getTime_tp95() {
        return time_tp95;
    }

    public void setTime_tp95(long time_tp95) {
        this.time_tp95 = time_tp95;
    }

    public long getTime_tp99() {
        return time_tp99;
    }

    public void setTime_tp99(long time_tp99) {
        this.time_tp99 = time_tp99;
    }

    public long getTime_tp999() {
        return time_tp999;
    }

    public void setTime_tp999(long time_tp999) {
        this.time_tp999 = time_tp999;
    }


    // tool
    @Override
    public void complete() {
        super.complete();

        // etc
        int ms_nanoseconds = 1000000;
        this.time = (System.nanoTime() - time)/ms_nanoseconds;

        XxlApm.computingTP(this);
    }

}
