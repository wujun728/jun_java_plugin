package org.supercall.mybatis;

/**
 * 数据库 分页语句组装接口
 * 
 * <p>
 * 针对不同的数据库使用不同的分页实现
 * 
 * @author Wujun
 */
public interface IDialect {
    
    /**
     * 组装分页语句
     * 
     * @param originalSql 原始语句
     * @param offset 偏移量
     * @param limit 界限
     * @return 分页语句
     */
    String buildPaginationSql(String originalSql, int offset, int limit);
}
