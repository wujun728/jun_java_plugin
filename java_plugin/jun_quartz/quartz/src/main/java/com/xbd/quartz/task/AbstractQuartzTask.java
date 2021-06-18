package com.xbd.quartz.task;

public abstract class AbstractQuartzTask {

    public final static String GROUP_DEFAULT = "DEFAULT";

    public final static String TARGETMETHOD_DEFAULT = "execute";

    public abstract void execute() throws Exception;

}
