package com.jun.plugin.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jun.plugin.project.domain.GenTable;
import com.jun.plugin.project.domain.GenTableColumn;

/**
 * 业务 数据层
 * 
 * @author ruoyi
 */
public interface GenTableMapper
{
    /**
     * 查询业务列表
     * 
     * @param genTable 业务信息
     * @return 业务集合
     */
    public List<GenTable> selectGenTableList(GenTable genTable);

    /**
     * 查询表名称业务信息
     * 
     * @param tableName 表名称
     * @return 业务信息
     */
    public GenTable selectGenTableByName(String tableName);
    
    /**
     * 查询所有表信息
     * 
     * @param dataSourceId	数据源主键
     * @return 表信息集合
     */
    public List<GenTable> selectGenTableAll(Long dataSourceId);
    
    /**
     * 查询表ID业务信息
     * 
     * @param id 业务ID
     * @return 业务信息
     */
    public GenTable selectGenTableById(Long id);

    /**
     * 新增业务
     * 
     * @param genTable 业务信息
     * @return 结果
     */
    public int insertGenTable(GenTable genTable);

    /**
     * 修改业务
     * 
     * @param genTable 业务信息
     * @return 结果
     */
    public int updateGenTable(GenTable genTable);

    /**
     * 批量删除业务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGenTableByIds(Long[] ids);
    
    /**
     * 查询据库列表
     * 
     * @param genTable 业务信息
     * @param dbType 数据库类型
     * @return 数据库表集合
     */
    public List<GenTable> selectDbTableList(@Param("genTable")GenTable genTable, @Param("dbType")String dbType);
    
    /**
     * 查询据库列表
     * 
     * @param genTable 业务信息
     * @param dbType 数据库类型
     * @return 数据库表集合
     */
    public String selectTest();

    /**
     * 查询据库列表
     * 
     * @param tableNames 表名称组
     * @param dbType 数据库类型
     * @return 数据库表集合
     */
    public List<GenTable> selectDbTableListByNames(@Param("tableNames")String[] tableNames, @Param("dbType")String dbType);
    
    /**
     * 根据表名称查询列信息
     * 
     * @param tableName 表名称
     * @param dbType 数据库类型
     * @return 列信息
     */
    public List<GenTableColumn> selectDbTableColumnsByName(@Param("tableName")String tableName, @Param("dbType")String dbType);
}