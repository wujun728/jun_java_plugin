package cn.springmvc.mybatis.mapper.simple;

import cn.springmvc.mybatis.entity.simple.ImportExcel;
import cn.springmvc.mybatis.mapper.BaseMapper;

/**
 * @author Wujun
 *
 */
public interface ImportExcelMapper extends BaseMapper<String, ImportExcel> {

    public void updateStartTime();

    public void updateEndTime();

    public void updateEndTimeByWeekend();

}
