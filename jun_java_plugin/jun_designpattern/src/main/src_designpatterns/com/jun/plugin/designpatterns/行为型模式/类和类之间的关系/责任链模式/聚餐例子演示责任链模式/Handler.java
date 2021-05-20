package com.jun.plugin.designpatterns.行为型模式.类和类之间的关系.责任链模式.聚餐例子演示责任链模式;

/**
 * 抽象处理者角色类
 * 
 * 作者: zhoubang 
 * 日期：2015年10月29日 上午9:18:45
 */
public abstract class Handler {
    /**
     * 持有下一个处理请求的对象
     */
    protected Handler successor = null;

    /**
     * 取值方法
     */
    public Handler getSuccessor() {
        return successor;
    }

    /**
     * 设置下一个处理请求的对象
     */
    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    /**
     * 处理聚餐费用的申请
     * 
     * @param user
     *            申请人
     * @param fee
     *            申请的钱数
     * @return 成功或失败的具体通知
     */
    public abstract String handleFeeRequest(String user, double fee);
}