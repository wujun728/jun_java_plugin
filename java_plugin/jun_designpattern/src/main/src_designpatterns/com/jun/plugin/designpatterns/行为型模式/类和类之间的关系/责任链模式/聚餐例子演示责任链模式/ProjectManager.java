package com.jun.plugin.designpatterns.行为型模式.类和类之间的关系.责任链模式.聚餐例子演示责任链模式;

/**
 * 模拟项目经理进行费用的审核处理
 * 
 * 作者: zhoubang 
 * 日期：2015年10月29日 上午9:18:27
 */
public class ProjectManager extends Handler {

    @Override
    public String handleFeeRequest(String user, double fee) {

        String str = "";
        // 项目经理权限比较小，只能在500以内
        if (fee < 500) {
            // 为了测试，简单点，只同意张三的请求
            if ("张三".equals(user)) {
                str = "成功：项目经理同意【" + user + "】的聚餐费用，金额为" + fee + "元";
            } else {
                // 其他人一律不同意
                str = "失败：项目经理不同意【" + user + "】的聚餐费用，金额为" + fee + "元";
            }
        } else {
            // 超过500，继续传递给级别更高的人处理
            if (getSuccessor() != null) {
                return getSuccessor().handleFeeRequest(user, fee);
            }
        }
        return str;
    }

}