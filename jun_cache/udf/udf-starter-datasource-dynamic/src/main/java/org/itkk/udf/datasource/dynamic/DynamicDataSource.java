package org.itkk.udf.datasource.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itkk.udf.core.domain.datasource.DataSourceMeta;
import org.itkk.udf.core.domain.datasource.IBuildDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述 : DynamicDataSource
 *
 * @author wangkang
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 描述 : 目标数据库
     */
    private static Map<Object, Object> dyanmictargetDataSources;

    static {
        if (null == DynamicDataSource.dyanmictargetDataSources) {
            DynamicDataSource.dyanmictargetDataSources = new HashMap<>();
        }
    }

    /**
     * dynamicDataSourceConfig
     */
    @Autowired
    private DynamicDataSourceConfig dynamicDataSourceConfig;

    /**
     * 描述 : 数据源构建
     */
    @Autowired
    private IBuildDataSource buildDataSource;

    @Override
    protected Object determineCurrentLookupKey() { //NOSONAR
        //拿到code
        String dataSourceCode = DbContextHolder.getDataSourceCode();
        //判断code是否为空,如果为空,则设置为默认的数据源代码
        if (StringUtils.isBlank(dataSourceCode)) {
            dataSourceCode = dynamicDataSourceConfig.getDefaultCode();
            if (StringUtils.isBlank(dataSourceCode)) {
                throw new DynamicDataSourceException("you do not specify a data source , current dataSourceCode is null");
            }
            log.warn("you do not specify a data source , current dataSourceCode is null , now use defaultCode :" + dataSourceCode);
        }
        //拿到数据源属性,并且判断数据源属性是否存在,以及是否可用
        DataSourceMeta dataSourceProperties = dynamicDataSourceConfig.getMap().get(dataSourceCode);
        if (dataSourceProperties != null && dataSourceProperties.getEnable()) {
            Object obj = DynamicDataSource.dyanmictargetDataSources.get(dataSourceCode);
            if (obj == null) {
                log.debug(dataSourceCode + " not in the cache, ready to create a data source object");
                DataSource dataSource = buildDataSource.build(dataSourceProperties);
                if (dataSource != null) {
                    DynamicDataSource.dyanmictargetDataSources.put(dataSourceCode, dataSource);
                    super.setTargetDataSources(DynamicDataSource.dyanmictargetDataSources);
                    super.afterPropertiesSet();
                }
                log.debug(dataSourceCode + " data Source has been created and the cache");
            } else {
                log.debug(dataSourceCode + " already in the cache can be used directly");
            }
        } else {
            //如果在配置文件中,选择的数据源不存在,则也清理掉数据源缓存(可能是动态删除)
            if (DynamicDataSource.dyanmictargetDataSources.containsKey(dataSourceCode)) {
                DynamicDataSource.dyanmictargetDataSources.remove(dataSourceCode);
                super.setTargetDataSources(DynamicDataSource.dyanmictargetDataSources);
                super.afterPropertiesSet();
            }
            throw new DynamicDataSourceException("dataSourceCode : " + dataSourceCode + " not exist or not enable");
        }
        log.debug("switch to the Data Source : " + dataSourceCode);
        return dataSourceCode;
    }

}
