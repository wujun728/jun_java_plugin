package io.github.wujun728.online.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.wujun728.online.common.PageResult;
import io.github.wujun728.online.common.impl.BaseServiceImpl;
import io.github.wujun728.online.convert.OnlineTableConvert;
import io.github.wujun728.online.dao.OnlineTableDao;
import io.github.wujun728.online.ddl.MySQLTable;
import io.github.wujun728.online.entity.OnlineTableEntity;
import io.github.wujun728.online.query.OnlineTableQuery;
import io.github.wujun728.online.service.OnlineTableColumnService;
import io.github.wujun728.online.service.OnlineTableService;
import io.github.wujun728.online.vo.OnlineTableColumnVO;
import io.github.wujun728.online.vo.OnlineTableVO;
import net.maku.framework.common.exception.ServerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 在线表单服务实现
 */
@Service
public class OnlineTableServiceImpl extends BaseServiceImpl<OnlineTableDao, OnlineTableEntity> implements OnlineTableService {

    private final OnlineTableColumnService onlineTableColumnService;

    public OnlineTableServiceImpl(OnlineTableColumnService onlineTableColumnService) {
        this.onlineTableColumnService = onlineTableColumnService;
    }

    @Override
    public OnlineTableVO get(String tableId) {
        OnlineTableEntity onlineTableEntity = baseMapper.selectById(tableId);
        OnlineTableVO onlineTableVO = OnlineTableConvert.INSTANCE.convert(onlineTableEntity);
        onlineTableVO.setColumnList(onlineTableColumnService.getByTableId(tableId));
        return onlineTableVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<String> tableIds) {
        List<String> tableNames = new ArrayList<>();
        
        // 收集表名
        for (String tableId : tableIds) {
            OnlineTableEntity entity = baseMapper.selectById((Serializable) tableId);
            if (entity != null) {
                tableNames.add(entity.getName());
            }
        }
        
        // 删除数据
        removeByIds(tableIds);
        
        // 重命名实际数据库表
        MySQLTable mySQLTable = new MySQLTable();
        for (String tableName : tableNames) {
            String renameTableSQL = mySQLTable.getRenameTableSQL(tableName);
            baseMapper.exeSQL(renameTableSQL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OnlineTableVO tableVO) {
        List<OnlineTableColumnVO> newColumnList = tableVO.getColumnList();
        List<OnlineTableColumnVO> oldColumnList = onlineTableColumnService.getByTableId(tableVO.getId());
        MySQLTable mySQLTable = new MySQLTable();
        
        // 处理删除和修改的列
        for (OnlineTableColumnVO oldColumn : oldColumnList) {
            Optional<OnlineTableColumnVO> optionalNewColumn = newColumnList.stream()
                    .filter(columnVO -> ObjUtil.equals(columnVO.getId(), oldColumn.getId()))
                    .findFirst();
            
            if (!optionalNewColumn.isPresent()) {
                // 删除列
                String dropSQL = mySQLTable.getDropColumnSQL(tableVO.getName(), oldColumn.getName());
                baseMapper.exeSQL(dropSQL);
            } else {
                // 修改列
                List<String> updateColumnSQL = mySQLTable.getUpdateColumnSQL(
                        tableVO.getName(), optionalNewColumn.get(), oldColumn);
                for (String sql : updateColumnSQL) {
                    baseMapper.exeSQL(sql);
                }
            }
        }
        
        // 处理新增的列
        for (OnlineTableColumnVO newColumn : newColumnList) {
            Optional<OnlineTableColumnVO> optionalOldColumn = oldColumnList.stream()
                    .filter(columnVO -> ObjUtil.equals(columnVO.getId(), newColumn.getId()))
                    .findFirst();
            
            if (!optionalOldColumn.isPresent()) {
                // 新增列
                List<String> insertColumnSQL = mySQLTable.getInsertColumnSQL(
                        tableVO.getName(), newColumn);
                for (String sql : insertColumnSQL) {
                    baseMapper.exeSQL(sql);
                }
            }
        }
        
        // 更新表注释
        OnlineTableEntity onlineTableEntity = baseMapper.selectById((Serializable) tableVO.getId());
        if (!StrUtil.equals(tableVO.getComments(), onlineTableEntity.getComments())) {
            String updateTableSQL = mySQLTable.getUpdateTableSQL(onlineTableEntity.getName(), tableVO.getComments());
            baseMapper.exeSQL(updateTableSQL);
        }
        
        // 更新实体和列信息
        OnlineTableEntity updatedEntity = OnlineTableConvert.INSTANCE.convert(tableVO);
        updateById(updatedEntity);
        onlineTableColumnService.delete(updatedEntity.getId());
        onlineTableColumnService.saveBatch(updatedEntity.getId(), tableVO.getColumnList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(OnlineTableVO onlineTableVO) {
        // 检查表名是否已存在
        LambdaQueryWrapper<OnlineTableEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OnlineTableEntity::getName, onlineTableVO.getName());
        long count = baseMapper.selectCount(wrapper);
        
        if (count > 0) {
            throw new ServerException("表名" + onlineTableVO.getName() + "已存在");
        }
        
        // 保存实体
        OnlineTableEntity onlineTableEntity = OnlineTableConvert.INSTANCE.convert(onlineTableVO);
        baseMapper.insert(onlineTableEntity);
        
        // 保存列信息
        onlineTableColumnService.saveBatch(onlineTableEntity.getId(), onlineTableVO.getColumnList());
        
        // 创建实际数据库表
        MySQLTable mySQLTable = new MySQLTable();
        String tableSQL = mySQLTable.getTableSQL(onlineTableVO);
        baseMapper.exeSQL(tableSQL);
    }

    @Override
    public PageResult<OnlineTableVO> page(OnlineTableQuery query) {
        IPage<OnlineTableEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<>(OnlineTableConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<OnlineTableEntity> getWrapper(OnlineTableQuery query) {
        LambdaQueryWrapper<OnlineTableEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(query.getName()), OnlineTableEntity::getName, query.getName());
        wrapper.like(StrUtil.isNotBlank(query.getComments()), OnlineTableEntity::getComments, query.getComments());
        wrapper.orderByDesc(OnlineTableEntity::getCreateTime);
        return wrapper;
    }
}

