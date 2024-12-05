/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.mapstruct.Mapper
 *  org.mapstruct.factory.Mappers
 */
package io.github.wujun728.online.convert;

import java.util.List;

import io.github.wujun728.online.entity.OnlineTableColumnEntity;
import io.github.wujun728.online.vo.OnlineTableColumnVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OnlineTableColumnConvert {
    public static   /* synthetic */ OnlineTableColumnConvert INSTANCE = (OnlineTableColumnConvert)Mappers.getMapper(OnlineTableColumnConvert.class);

    public List<OnlineTableColumnEntity> convertList2(List<OnlineTableColumnVO> var1);

    public List<OnlineTableColumnVO> convertList(List<OnlineTableColumnEntity> var1);

    public OnlineTableColumnEntity convert(OnlineTableColumnVO var1);

    public OnlineTableColumnVO convert(OnlineTableColumnEntity var1);
}

