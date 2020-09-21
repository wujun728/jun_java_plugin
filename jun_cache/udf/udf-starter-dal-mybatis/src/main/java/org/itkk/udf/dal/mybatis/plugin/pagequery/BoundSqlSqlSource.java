package org.itkk.udf.dal.mybatis.plugin.pagequery;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;

/**
 * <p>
 * ClassName: BoundSqlSqlSource
 * </p>
 * <p>
 * Description: BoundSqlSqlSource
 * </p>
 * <p>
 * Author: wangkang
 * </p>
 * <p>
 * Date: 2016年3月23日
 * </p>
 */
public class BoundSqlSqlSource implements SqlSource {

    /**
     * <p>
     * Field boundSql: sql对象
     * </p>
     */
    BoundSql boundSql;

    /**
     * <p>
     * Description: 构造函数
     * </p>
     *
     * @param boundSql sql对象
     */
    public BoundSqlSqlSource(BoundSql boundSql) {
        this.boundSql = boundSql;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return this.boundSql;
    }

}
