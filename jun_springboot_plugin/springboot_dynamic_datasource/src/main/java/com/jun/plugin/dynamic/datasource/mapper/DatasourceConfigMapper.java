package com.jun.plugin.dynamic.datasource.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.jun.plugin.dynamic.datasource.config.MyMapper;
import com.jun.plugin.dynamic.datasource.model.DatasourceConfig;

/**
 * <p>
 * 数据源配置 Mapper
 * </p>
 *
 * @author Wujun
 * @date Created in 2019/9/4 16:20
 */
@Mapper
public interface DatasourceConfigMapper extends MyMapper<DatasourceConfig> {
}
