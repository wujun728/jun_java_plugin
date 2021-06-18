package designpatterns.行为型模式.类和类之间的关系.责任链模式.聚餐例子演示责任链模式;

/**
 * 模拟部门经理进行费用的审核处理
 * 
 * 作者: zhoubang 
 * 日期：2015年10月29日 上午9:18:14
 */
public class DeptManager extends Handler {

    @Override
    public String handleFeeRequest(String user, double fee) {

        String str = "";
        // 部门经理的权限只能在1000以内
        if (fee < 1000) {
            // 为了测试，简单点，只同意张三的请求
            if ("张三".equals(user)) {
                str = "成功：部门经理同意【" + user + "】的聚餐费用，金额为" + fee + "元";
            } else {
                // 其他人一律不同意
                str = "失败：部门经理不同意【" + user + "】的聚餐费用，金额为" + fee + "元";
            }
        } else {
            // 超过1000，继续传递给级别更高的人处理
            if (getSuccessor() != null) {
                return getSuccessor().handleFeeRequest(user, fee);
            }
        }
        return str;
    }

}