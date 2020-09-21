/*   
 * Project: OSMP
 * FileName: OracleTemplate.java
 * version: V1.0
 */
package com.osmp.jdbc.support.template;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 
 * Description:OracleTemplate
 * @author: wangkaiping
 * @date: 2016年8月9日 上午10:17:45上午10:51:30
 */
public class OracleTemplate extends BaseTemplate {

    protected String buildQueryPageSql(String querySql, Map<String, Object> queryParam, int startIndex, int pageSize) {
        Assert.isTrue(StringUtils.hasText(querySql), "page query sql string is must be not null or empty");
        Assert.notNull(queryParam, "page query sql parameter map is must be not null");
        Assert.isTrue(pageSize > 0, "page query page size must be > 0");

        startIndex = Math.max(0, startIndex);
        StringBuilder sqlBuilder = new StringBuilder("select * from (select rownum as sn_rownum,tab.* from (")
                .append(querySql).append(") tab where rownum <= :_page_end_index")
                .append(") where sn_rownum > :_page_start_index");

        queryParam.put("_page_start_index", startIndex);
        queryParam.put("_page_end_index", (startIndex + pageSize));
        logger.debug("\n" + sqlBuilder.toString());
        logger.debug("\n" + queryParam.toString());
        return sqlBuilder.toString();
    }
    
    protected String buildQueryPageSql(String querySql, List<Object> queryParam, int startIndex, int pageSize) {
        Assert.isTrue(StringUtils.hasText(querySql), "page query sql string is must be not null or empty");
        Assert.notNull(queryParam, "page query sql parameter map is must be not null");
        Assert.isTrue(pageSize > 0, "page query page size must be > 0");

        startIndex = Math.max(0, startIndex);
        StringBuilder sqlBuilder = new StringBuilder("select * from (select rownum as sn_rownum,tab.* from (")
                .append(querySql).append(") tab where rownum <= ?").append(") where sn_rownum > ?");
        queryParam.add(startIndex + pageSize);
        queryParam.add(startIndex);

        logger.debug("\n" + sqlBuilder.toString());
        logger.debug("\n" + queryParam.toString());
        return sqlBuilder.toString();
    }

}
