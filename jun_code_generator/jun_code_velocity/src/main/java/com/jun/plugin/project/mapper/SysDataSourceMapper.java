package com.jun.plugin.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jun.plugin.project.domain.SysDataSource;

/**
 * 数据源 数据层
 * 
 * @author ruoyi
 */
@Mapper
public interface SysDataSourceMapper
{
	/**
     * 根据条件分页查询数据源数据
     * 
     * @param dataSource 数据源数据信息
     * @return 数据源数据集合信息
     */
    public List<SysDataSource> selectSysDataSourceList(SysDataSource dataSource);
    
	/**
     * 查询数据源配置信息
     * 
     * @return 数据源配置信息
     */
    public SysDataSource selectSysDataSource(Long id);
    
    /**
     * 新增数据源配置信息
     * 
     * @param sysDataSource 数据源配置信息
     * @return 结果
     */
    public int insertSysDataSource(SysDataSource sysDataSource);
    
    /**
     * 批量删除数据源数据
     * 
     * @param ids 需要删除的数据
     * @return 结果
     */
    public int deleteSysDataSourceByIds(String[] ids);
    
    /**
     * 更新数据源配置信息
     * 
     * @param sysDataSource 数据源配置信息
     * @return 结果
     */
    public int updateSysDataSource(SysDataSource sysDataSource);
}
