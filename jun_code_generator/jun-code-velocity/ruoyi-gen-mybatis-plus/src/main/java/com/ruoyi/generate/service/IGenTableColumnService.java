package com.ruoyi.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.generate.pojo.GenTableColumn;

import java.util.List;

/**
 * 业务字段 服务层
 */
public interface IGenTableColumnService  extends IService<GenTableColumn> {
    /**
     * 查询业务字段列表
     *
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
     List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId);

    /**
     * 新增业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
     boolean insertGenTableColumn(GenTableColumn genTableColumn);

    /**
     * 修改业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    boolean updateGenTableColumn(GenTableColumn genTableColumn);

    /**
     * 删除业务字段信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
     boolean deleteGenTableColumnByIds(String ids);

    void removeByTableIds(List<Long> tableIds);

    List<GenTableColumn> selectDbTableColumnsByName(String tableName);
}
