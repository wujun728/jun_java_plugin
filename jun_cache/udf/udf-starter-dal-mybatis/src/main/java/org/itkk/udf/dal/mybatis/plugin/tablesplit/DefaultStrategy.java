package org.itkk.udf.dal.mybatis.plugin.tablesplit;

import org.apache.commons.lang3.StringUtils;
import org.itkk.udf.dal.mybatis.MyBatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述 : DefaultStrategy
 *
 * @author wangkang
 */
@Component
public class DefaultStrategy implements IStrategy {

    /**
     * myBatisProperties
     */
    @Autowired
    private MyBatisProperties myBatisProperties;

    @Override
    public String convert(String sql, Integer splitNumber) {
        String convertSql = sql.toUpperCase();
        if (StringUtils.isNotEmpty(myBatisProperties.getTableNames())) {
            String[] tableNameArray = myBatisProperties.getTableNames().split(",");
            for (String tableName : tableNameArray) {
                String tmpTableName = tableName.toUpperCase().trim();
                convertSql = convertSql.replaceAll(tmpTableName, tmpTableName + "_" + splitNumber);
            }
        }
        return convertSql;
    }

}
