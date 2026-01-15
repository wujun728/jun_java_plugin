package io.github.wujun728.online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.wujun728.online.common.impl.BaseServiceImpl;
import io.github.wujun728.online.convert.OnlineTableColumnConvert;
import io.github.wujun728.online.dao.OnlineTableColumnDao;
import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import io.github.wujun728.online.service.OnlineTableColumnService;
import io.github.wujun728.online.vo.OnlineTableColumnVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 在线表列服务实现
 */
@Service
public class OnlineTableColumnServiceImpl extends BaseServiceImpl<OnlineTableColumnDao, OnlineTableColumnEntity> implements OnlineTableColumnService {

    @Override
    public void saveBatch(String tableId, List<OnlineTableColumnVO> columnList) {
        List<OnlineTableColumnEntity> entityList = OnlineTableColumnConvert.INSTANCE.convertList2(columnList);
        int sort = 0;
        
        // 设置表ID和排序
        for (OnlineTableColumnEntity entity : entityList) {
            entity.setSort(sort++);
            entity.setTableId(tableId);
        }
        
        // 批量保存
        super.saveBatch(entityList);
    }

    @Override
    public void delete(String tableId) {
        LambdaQueryWrapper<OnlineTableColumnEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OnlineTableColumnEntity::getTableId, tableId);
        remove(wrapper);
    }

    @Override
    public List<OnlineTableColumnVO> getByTableId(String tableId) {
        List<OnlineTableColumnEntity> entityList = baseMapper.getByTableId(tableId);
        return OnlineTableColumnConvert.INSTANCE.convertList(entityList);
    }
}

