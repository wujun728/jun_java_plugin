package com.jun.plugin.project.controller;

import java.sql.Connection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.pool.DruidDataSource;
import com.jun.plugin.common.exception.BusinessException;
import com.jun.plugin.common.utils.DataSourceComposeUtils;
import com.jun.plugin.common.utils.text.Convert;
import com.jun.plugin.framework.aspectj.lang.enums.DataSourceType;
import com.jun.plugin.framework.datasource.DynamicDataSourceUtil;
import com.jun.plugin.framework.web.controller.BaseController;
import com.jun.plugin.framework.web.domain.AjaxResult;
import com.jun.plugin.framework.web.page.TableDataInfo;
import com.jun.plugin.project.domain.SysDataSource;
import com.jun.plugin.project.service.ISysDataSourceService;

/**
 * 系统数据源信息
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/dataSource")
public class SysDataSourceController extends BaseController
{
    private String prefix = "system/dataSource";

    @Autowired
    private ISysDataSourceService dataSourceService;

    /**
     * 数据源
     */
    @GetMapping()
    public String config(ModelMap mmap)
    {
        return prefix + "/list";
    }
    
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysDataSource dataSource)
    {
        startPage();
        List<SysDataSource> list = dataSourceService.selectSysDataSourceList(dataSource);
        return getDataTable(list);
    }
    
    /**
     * 选择数据源
     */
    @GetMapping("/select")
    public String selectDataSource(ModelMap mmap)
    {
        return prefix + "/select";
    }
    
    /**
     * 数据源新增
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("dataSource", new SysDataSource());
        return prefix + "/config";
    }
    
    /**
     * 数据源编辑
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
    	SysDataSource dataSource = dataSourceService.selectSysDataSource(id);
        mmap.put("dataSource", dataSource);
        return prefix + "/config";
    }
    
    /**
     * 测试连接数据源
     */
	@PostMapping("/connect")
    @ResponseBody
    public AjaxResult connect(SysDataSource dataSource) {
    	DruidDataSource druidDataSource = DataSourceComposeUtils.composeDruidDataSource(dataSource);
        Connection connection = null;
        try {
        	//去掉连接失败重试
        	druidDataSource.setBreakAfterAcquireFailure(true);
        	druidDataSource.setConnectionErrorRetryAttempts(0);
        	connection = druidDataSource.getConnection(5000);
        	if (connection != null) {
        		connection.close();
        		return success("连接成功!");
			}
        	return error("连接失败!");
		} catch (Exception e) {
			return error("连接失败!");
		}
    }
    
    /**
     * 设置系统数据源（保存）
     */
    @PostMapping("/save")
    @ResponseBody
    public AjaxResult save(SysDataSource dataSource) throws BusinessException{
    	boolean updateflag = false;//是否是更新
    	int rows= 0;
    	if (dataSource.getId() != null) {
    		rows= dataSourceService.updateSysDataSource(dataSource);
    		updateflag = true;
		} else {
			rows= dataSourceService.insertSysDataSource(dataSource);
			updateflag = false;
		}
    	if (rows > 0) {
    		DruidDataSource druidDataSource = DataSourceComposeUtils.composeDruidDataSource(dataSource);
            if (updateflag) {
            	//替换数据源
                DynamicDataSourceUtil.replaceTargetDataSource(DataSourceType.SLAVE.name() + Convert.toStr(dataSource.getId()), druidDataSource);
			} else {
				//添加数据源
	            DynamicDataSourceUtil.addTargetDataSource(DataSourceType.SLAVE.name() + Convert.toStr(dataSource.getId()), druidDataSource);
			}
            //刷新数据源
            DynamicDataSourceUtil.flushDataSource();
		}
        return toAjax(rows);
    }
    
    /**
     * 删除数据源
     */
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
    	int rows = dataSourceService.deleteSysDataSourceByIds(ids);
    	if (rows > 0) {
    		String[] array = Convert.toStrArray(ids);
    		for (String id : array) {
    			//删除数据源
    			DynamicDataSourceUtil.deleteTargetDataSource(DataSourceType.SLAVE.name() + id);
			}
    		//刷新数据源
            DynamicDataSourceUtil.flushDataSource();
		}
        return toAjax(rows);
    }
    
    /**
     * 数据源状态修改
     */
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysDataSource dataSource)
    {
        return toAjax(dataSourceService.changeStatus(dataSource));
    }
}
