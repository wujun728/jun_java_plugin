package com.jun.plugin.springboot.tools.buse.mapper;

import org.apache.ibatis.annotations.Param;

import com.jun.plugin.springboot.tools.buse.model.FxjFBiData01;
import com.jun.plugin.springboot.tools.common.mapper.BaseMapper;

import java.util.List;

public interface FxjFBiData01Mapper extends BaseMapper<FxjFBiData01> {
    List<FxjFBiData01> findList(
            @Param("query01") String query01,
            @Param("query02") String query02,
            @Param("query03") String query03,
            @Param("query03") String query04,
            @Param("query03") String query05,
            @Param("query03") String query06,
            @Param("query03") String query07,
            @Param("query03") String query08,
            @Param("query03") String query00,
            @Param("query03") String query10
    );

}