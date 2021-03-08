package com.itstyle.web.worker;
/**
 * worker实现
 * 创建者 张志朋
 * 创建时间	2017年6月22日
 *
 */
public class PlusWorker extends Worker { //求立方和  
    @Override  
    public Object handle(Object input) {  
        int i = (Integer)input;  
        return i * i * i;  
    }  
}