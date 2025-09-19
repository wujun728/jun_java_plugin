package io.github.wujun728.online.convert;

import java.util.List;
import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import io.github.wujun728.online.vo.OnlineTableColumnVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 在线表字段对象转换接口
 */
@Mapper
public interface OnlineTableColumnConvert {
    public static final OnlineTableColumnConvert INSTANCE = Mappers.getMapper(OnlineTableColumnConvert.class);

    List<OnlineTableColumnEntity> convertList2(List<OnlineTableColumnVO> voList);

    List<OnlineTableColumnVO> convertList(List<OnlineTableColumnEntity> entityList);

    OnlineTableColumnEntity convert(OnlineTableColumnVO vo);

    OnlineTableColumnVO convert(OnlineTableColumnEntity entity);
}

