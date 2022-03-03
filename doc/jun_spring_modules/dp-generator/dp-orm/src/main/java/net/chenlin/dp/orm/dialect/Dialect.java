package net.chenlin.dp.orm.dialect;

/**
 * 数据库方言抽象类
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月8日 上午11:09:57
 */
public abstract class Dialect {

    /**
     * 得到分页sql
     * @param sql
     * @param offset
     * @param limit
     * @return
     */
    public abstract String getLimitString(String sql, int offset, int limit);

    /**
     * 得到分页sql
     * @param sql
     * @return
     */
    public abstract String getCountString(String sql);

}
