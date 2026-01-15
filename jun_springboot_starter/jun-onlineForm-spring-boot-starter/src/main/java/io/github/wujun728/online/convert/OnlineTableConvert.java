package io.github.wujun728.online.convert;

import io.github.wujun728.online.entity.OnlineTableEntity;
import io.github.wujun728.online.vo.OnlineTableVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 在线表对象转换接口
 */
@Mapper
public interface OnlineTableConvert {
    public static final OnlineTableConvert INSTANCE = Mappers.getMapper(OnlineTableConvert.class);

    List<OnlineTableVO> convertList(List<OnlineTableEntity> entityList);

    OnlineTableVO convert(OnlineTableEntity entity);

    OnlineTableEntity convert(OnlineTableVO vo);
}

