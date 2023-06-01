package com.lzb.onlinejava.complier.service;

import com.lzb.onlinejava.complier.vo.ResultResponse;

/**
 * author: haiyangp
 * date:  2017/9/22
 * desc: JAVA编译器service接口
 */
public interface JavaComileService {

    /**
     * 编译，并获取编译后的class类
     *
     * @param javaSource JAVA代码
     * @return 编译后的CLASS
     */
    Class complie(String javaSource, String className) throws Exception;

    /**
     * 执行MAIN方法
     *
     * @param clazz 编译后的CLASS
     * @return 执行结果
     */
    ResultResponse excuteMainMethod(Class clazz) throws Exception;

    /**
     * 执行MAIN方法
     *
     * @param clazz 编译后的CLASS
     * @param args  运行参数数组
     * @return 执行结果
     */
    ResultResponse excuteMainMethod(Class clazz, String[] args) throws Exception;

    /**
     * 执行MAIN方法
     *
     * @param timeLimit 时间限制
     */
    ResultResponse excuteMainMethod(Class clazz, Long timeLimit) throws Exception;

    /**
     * 执行MAIN方法
     *
     * @param timeLimit 时间限制
     * @param args 运行参数数组
     */
    ResultResponse excuteMainMethod(Class clazz, Long timeLimit,String[] args) throws Exception;
}
