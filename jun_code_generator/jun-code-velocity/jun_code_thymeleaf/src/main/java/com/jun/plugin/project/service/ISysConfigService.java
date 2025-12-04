package com.jun.plugin.project.service;

import com.jun.plugin.project.domain.SysConfig;

/**
 * 系统配置 业务层
 * 
 * @author ruoyi
 */
public interface ISysConfigService
{
	/**
     * 查询系统配置信息
     * 
     * @return 系统配置
     */
    public SysConfig selectSysConfig();
    
    /**
     * 更新系统配置信息
     * 
     * @param sysConfig 系统配置信息
     * @return 结果
     */
    public int updateSysConfig(SysConfig sysConfig);
}
