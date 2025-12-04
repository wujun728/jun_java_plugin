package com.ruoyi.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.generate.mapper.GenTableColumnMapper;
import com.ruoyi.generate.pojo.GenTableColumn;
import com.ruoyi.generate.service.IGenTableColumnService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 业务字段 服务层实现
 */
@Service
public class GenTableColumnServiceImpl  extends ServiceImpl<GenTableColumnMapper, GenTableColumn> implements IGenTableColumnService
//        , WrapperUtils<GenTableColumn>
{

    /**
     * 查询业务字段列表
     *
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
    @Override
    public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId) {
        QueryWrapper<GenTableColumn> wrapper = new QueryWrapper<>();
        return list( wrapper.eq("table_id",tableId));
    }

    /**
     * 新增业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    @Override
    public boolean insertGenTableColumn(GenTableColumn genTableColumn) {
        return save(genTableColumn);
    }

    /**
     * 修改业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    @Override
    public boolean updateGenTableColumn(GenTableColumn genTableColumn) {
        return saveOrUpdate(genTableColumn);
    }

    /**
     * 删除业务字段对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public boolean deleteGenTableColumnByIds(String ids) {
        Long[] longs = Convert.toLongArray(ids);
        return removeByIds(Arrays.asList(longs));
    }

    @Override
    public void removeByTableIds(List<Long> tableIds) {
        QueryWrapper<GenTableColumn> wrapper = new QueryWrapper<>();
        remove(wrapper.in("table_id",tableIds));
    }

    @Override
    public List<GenTableColumn> selectDbTableColumnsByName(String tableName) {
        return baseMapper.selectDbTableColumnsByName(tableName);
    }
}