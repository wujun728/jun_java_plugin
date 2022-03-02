package com.jun.plugin.project.service;

import java.util.List;

import com.jun.plugin.project.domain.SysDataSource;

/**
 * 系统数据源 业务层
 * 
 * @author ruoyi
 */
public interface ISysDataSourceService
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
     * @param id	数据源主键
     * @return 系统数据源
     */
    public SysDataSource selectSysDataSource(Long id);
    
    /**
     * 新增数据源配置信息
     * 
     * @param dataSource 数据源配置信息
     * @return 结果
     */
    public int insertSysDataSource(SysDataSource dataSource);
    
    /**
     * 更新系统数据源信息
     * 
     * @param dataSource 系统数据源信息
     * @return 结果
     */
    public int updateSysDataSource(SysDataSource dataSource);
    
    /**
     * 批量删除数据源数据
     * 
     * @param ids 需要删除的数据
     * @return 结果
     */
    public int deleteSysDataSourceByIds(String ids);
    
    /**
     * 数据源状态修改
     * 
     * @param dataSource 数据源信息
     * @return 结果
     */
    public int changeStatus(SysDataSource dataSource);
}
