package com.designpatterns.chainofresponsibility;

/**
 * @author: BaoZhou
 * @date : 2019/1/4 15:11
 */
public abstract class AbstractFilter {
    //责任链中的下一个元素
    protected AbstractFilter nextLogger;

    public void setNextLogger(AbstractFilter nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void Filter(Product product) {
        doFilter(product);
        if (nextLogger != null) {
            nextLogger.Filter(product);
        }
    }

    abstract protected void doFilter(Product product);

}
