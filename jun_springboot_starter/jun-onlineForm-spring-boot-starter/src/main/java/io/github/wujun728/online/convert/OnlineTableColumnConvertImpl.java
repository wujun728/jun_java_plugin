package io.github.wujun728.online.convert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import io.github.wujun728.online.vo.OnlineTableColumnVO;

/**
 * 在线表字段对象转换实现类
 */
public class OnlineTableColumnConvertImpl implements OnlineTableColumnConvert {

    @Override
    public List<OnlineTableColumnEntity> convertList2(List<OnlineTableColumnVO> voList) {
        if (voList == null) {
            return null;
        }
        List<OnlineTableColumnEntity> entityList = new ArrayList<>(voList.size());
        for (OnlineTableColumnVO vo : voList) {
            entityList.add(convert(vo));
        }
        return entityList;
    }

    @Override
    public List<OnlineTableColumnVO> convertList(List<OnlineTableColumnEntity> entityList) {
        if (entityList == null) {
            return null;
        }
        List<OnlineTableColumnVO> voList = new ArrayList<>(entityList.size());
        for (OnlineTableColumnEntity entity : entityList) {
            voList.add(convert(entity));
        }
        return voList;
    }

    @Override
    public OnlineTableColumnEntity convert(OnlineTableColumnVO vo) {
        if (vo == null) {
            return null;
        }
        OnlineTableColumnEntity entity = new OnlineTableColumnEntity();
        entity.setId(vo.getId());
        entity.setName(vo.getName());
        entity.setComments(vo.getComments());
        entity.setLength(vo.getLength());
        entity.setPointLength(vo.getPointLength());
        entity.setDefaultValue(vo.getDefaultValue());
        entity.setColumnType(vo.getColumnType());
        entity.setColumnPk(vo.isColumnPk());
        entity.setColumnNull(vo.isColumnNull());
        entity.setFormItem(vo.isFormItem());
        entity.setFormRequired(vo.isFormRequired());
        entity.setFormInput(vo.getFormInput());
        entity.setFormDefault(vo.getFormDefault());
        entity.setFormDict(vo.getFormDict());
        entity.setGridItem(vo.isGridItem());
        entity.setGridSort(vo.isGridSort());
        entity.setQueryItem(vo.isQueryItem());
        entity.setQueryType(vo.getQueryType());
        entity.setQueryInput(vo.getQueryInput());
        entity.setSort(vo.getSort());
        return entity;
    }

    @Override
    public OnlineTableColumnVO convert(OnlineTableColumnEntity entity) {
        if (entity == null) {
            return null;
        }
        OnlineTableColumnVO vo = new OnlineTableColumnVO();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setComments(entity.getComments());
        vo.setLength(entity.getLength());
        vo.setPointLength(entity.getPointLength());
        vo.setDefaultValue(entity.getDefaultValue());
        vo.setColumnType(entity.getColumnType());
        vo.setColumnPk(entity.isColumnPk());
        vo.setColumnNull(entity.isColumnNull());
        vo.setFormItem(entity.isFormItem());
        vo.setFormRequired(entity.isFormRequired());
        vo.setFormInput(entity.getFormInput());
        vo.setFormDefault(entity.getFormDefault());
        vo.setFormDict(entity.getFormDict());
        vo.setGridItem(entity.isGridItem());
        vo.setGridSort(entity.isGridSort());
        vo.setQueryItem(entity.isQueryItem());
        vo.setQueryType(entity.getQueryType());
        vo.setQueryInput(entity.getQueryInput());
        vo.setSort(entity.getSort());
        return vo;
    }
}

