package org.itkk.udf.dal.mybatis.plugin.tablesplit;

/**
 * 描述 : IStrategy
 *
 * @author wangkang
 */
public interface IStrategy { //NOSONAR
    /**
     * 传入一段需要分表的SQL,返回已经分表好的SQL
     *
     * @param sql         sql
     * @param splitNumber splitNumber
     * @return sql
     */
    String convert(String sql, Integer splitNumber);
}
