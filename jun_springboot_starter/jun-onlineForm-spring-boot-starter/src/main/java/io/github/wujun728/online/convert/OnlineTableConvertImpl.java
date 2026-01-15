package io.github.wujun728.online.convert;

import io.github.wujun728.online.entity.OnlineTableEntity;
import io.github.wujun728.online.vo.OnlineTableVO;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 在线表对象转换实现类
 */
public class OnlineTableConvertImpl implements OnlineTableConvert {

    public static final OnlineTableConvert INSTANCE = Mappers.getMapper(OnlineTableConvert.class);

    @Override
    public List<OnlineTableVO> convertList(List<OnlineTableEntity> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return null;
        }
        return entityList.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public OnlineTableVO convert(OnlineTableEntity entity) {
        if (entity == null) {
            return null;
        }
        OnlineTableVO vo = new OnlineTableVO();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setComments(entity.getComments());
        vo.setFormLayout(entity.getFormLayout());
        vo.setTree(entity.getTree());
        vo.setTreePid(entity.getTreePid());
        vo.setTreeLabel(entity.getTreeLabel());
        vo.setTableType(entity.getTableType());
        vo.setVersion(entity.getVersion());
        vo.setCreateTime(entity.getCreateTime());
        return vo;
    }

    @Override
    public OnlineTableEntity convert(OnlineTableVO vo) {
        if (vo == null) {
            return null;
        }
        OnlineTableEntity entity = new OnlineTableEntity();
        entity.setId(vo.getId());
        entity.setName(vo.getName());
        entity.setComments(vo.getComments());
        entity.setFormLayout(vo.getFormLayout());
        entity.setTree(vo.getTree());
        entity.setTreePid(vo.getTreePid());
        entity.setTreeLabel(vo.getTreeLabel());
        entity.setTableType(vo.getTableType());
        entity.setCreateTime(vo.getCreateTime());
        entity.setVersion(vo.getVersion());
        return entity;
    }
}

