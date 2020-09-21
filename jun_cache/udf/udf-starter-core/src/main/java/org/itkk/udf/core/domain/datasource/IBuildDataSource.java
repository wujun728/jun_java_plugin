package org.itkk.udf.core.domain.datasource;

import javax.sql.DataSource;

/**
 * 描述 : 构建数据源
 *
 * @author wangkang
 */
public interface IBuildDataSource { //NOSONAR

    /**
     * 描述 : 构建数据源
     *
     * @param properties 数据源参数
     * @return 数据源
     */
    DataSource build(DataSourceMeta properties);

}
