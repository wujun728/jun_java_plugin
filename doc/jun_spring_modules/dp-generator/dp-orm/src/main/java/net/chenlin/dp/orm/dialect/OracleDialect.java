package net.chenlin.dp.orm.dialect;

/**
 * oracle方言
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月8日 上午11:07:29
 */
public class OracleDialect extends Dialect {

    @Override
    public String getLimitString(String sql, int offset, int limit) {

        sql = sql.trim();
        boolean isForUpdate = false;
        if (sql.toLowerCase().endsWith(" for update")) {
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }

        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);

        pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");

        pagingSelect.append(sql);

        pagingSelect.append(" ) row_ ) where rownum_ > " + offset + " and rownum_ <= " + (offset + limit));

        if (isForUpdate) {
            pagingSelect.append(" for update");
        }

        return pagingSelect.toString();
    }

    @Override
    public String getCountString(String sql) {
        // TODO Oracle分页查询
        return null;
    }
}
