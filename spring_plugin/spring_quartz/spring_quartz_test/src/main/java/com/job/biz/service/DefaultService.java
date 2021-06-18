package com.job.biz.service;

public abstract class DefaultService {

    public abstract String getClassName();

    public abstract void addService();

    /**
     * 处理业务
     * 
     * @param message
     * @return
     */
    public abstract void execute();

}
