package com.kvn.poi.exception;

/**
 * 错误枚举的接口类
 * 
 */
public interface IErrors {

    /**
     * 采用枚举中定义的message作为异常的信息
     * 
     * @return
     */
    public PoiElException exp();

    /**
     * 采用枚举中定义的message作为异常信息，并传递一些参数
     * 
     * @param args
     *            参数列表
     * @return
     */
    public PoiElException exp(Object... args);

    /**
     * 采用枚举中定义的message作为异常信息，并传递一些参数，支持传入底层的异常
     * 
     * @param cause
     *            原始异常
     * @param args
     *            参数列表
     * @return
     */
    public PoiElException exp(Throwable cause, Object... args);

    /**
     * 采用枚举中定义的code，使用自定义的message作为异常信息，并可能会带上一些参数
     * 
     * @param message
     *            错误信息
     * @param args
     *            具体参数列表
     * @return
     */
    public PoiElException expMsg(String message, Object... args);

    /**
     * 采用枚举中定义的code，使用自定义的message作为异常信息，并可能会带上一些参数，并自持传入底层的异常
     * 
     * @param message
     *            错误信息
     * @param cause
     *            原始异常
     * @param args
     *            具体参数列表
     * @return
     */
    public PoiElException expMsg(String message, Throwable cause, Object... args);
}
