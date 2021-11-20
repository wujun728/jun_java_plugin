package com.jun.plugin.springboot.tools.buse.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jun.plugin.springboot.tools.buse.mapper.FxjFBiData01Mapper;
import com.jun.plugin.springboot.tools.buse.model.FxjFBiData01;
import com.jun.plugin.springboot.tools.common.mdoel.Page;
import com.jun.plugin.springboot.tools.common.service.AbstractService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FxjBiData01Service extends AbstractService<FxjFBiData01> {
    @Autowired(required = false)
    private FxjFBiData01Mapper fxjFBiData01Mapper;

    public Page<FxjFBiData01> getPage(String query01,
                                      String query02,
                                      String query03,
                                      String query04,
                                      String query05,
                                      String query06,
                                      String query07,
                                      String query08,
                                      String query09,
                                      String query10,
                                      Integer pageNum, Integer pageSize) {
        pageNum = pageNum != null ? pageNum : 0;
        pageSize = pageSize != null ? pageSize : 0;
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        // 进行查询
        List<FxjFBiData01> list =fxjFBiData01Mapper.findList(query01,
                query02,
                query03,
                query04,
                query05,
                query06,
                query07,
                query08,
                query09,
                query10
        );
        // 分页信息
        PageInfo<FxjFBiData01> pageInfo = new PageInfo<>(list);
        // 返回page对象
        return new Page<FxjFBiData01>(pageInfo.getList(), pageInfo.getTotal());
    }
}
