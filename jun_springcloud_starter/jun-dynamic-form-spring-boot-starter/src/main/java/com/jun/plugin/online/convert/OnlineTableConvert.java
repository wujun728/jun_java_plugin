/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.mapstruct.Mapper
 *  org.mapstruct.factory.Mappers
 */
package com.jun.plugin.online.convert;

import java.util.List;

import com.jun.plugin.online.entity.OnlineTableEntity;
import com.jun.plugin.online.vo.OnlineTableVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OnlineTableConvert {
    public static final /* synthetic */ OnlineTableConvert INSTANCE = (OnlineTableConvert)Mappers.getMapper(OnlineTableConvert.class);

    public List<OnlineTableVO> convertList(List<OnlineTableEntity> var1);

    public OnlineTableVO convert(OnlineTableEntity var1);

    public OnlineTableEntity convert(OnlineTableVO var1);

}

