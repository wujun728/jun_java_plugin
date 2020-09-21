/*   
 * Project: OSMP
 * FileName: MysqlTemplate.java
 * version: V1.0
 */
package com.osmp.jdbc.support.template;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 
 * Description:MysqlTemplate
 * @author: wangkaiping
 * @date: 2016年8月9日 上午10:17:37上午10:51:30
 */
public class MysqlTemplate extends BaseTemplate{

    protected String buildQueryPageSql(String querySql, Map<String, Object> queryParam, int startIndex, int pageSize) {
        Assert.isTrue(StringUtils.hasText(querySql), "page query sql string is must be not null or empty");
        Assert.notNull(queryParam, "page query sql parameter map is must be not null");
        Assert.isTrue(pageSize > 0, "page query page size must be > 0");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(querySql);
        if (pageSize > 0) {
            startIndex = Math.max(0, startIndex);
            stringBuilder.append(" limit :_page_start_index , :_page_size");
            queryParam.put("_page_start_index", startIndex);
            queryParam.put("_page_size", (pageSize));
        }
        logger.debug("\n" + stringBuilder.toString());
        logger.debug("\n" + queryParam.toString());
        return stringBuilder.toString();
    }
    
    protected String buildQueryPageSql(String querySql, List<Object> queryParam, int startIndex, int pageSize) {
        Assert.isTrue(StringUtils.hasText(querySql), "page query sql string is must be not null or empty");
        Assert.notNull(queryParam, "page query sql parameter map is must be not null");
        Assert.isTrue(pageSize > 0, "page query page size must be > 0");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(querySql);
        if (pageSize > 0) {
            startIndex = Math.max(0, startIndex);
            stringBuilder.append(" limit ? , ?");
            queryParam.add(startIndex);
            queryParam.add(pageSize);
        }
        logger.debug("\n" + stringBuilder.toString());
        logger.debug("\n" + queryParam.toString());
        return stringBuilder.toString();
    }

    
}
