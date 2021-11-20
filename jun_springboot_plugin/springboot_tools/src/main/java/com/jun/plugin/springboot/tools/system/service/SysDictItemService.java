package com.jun.plugin.springboot.tools.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.springboot.tools.common.service.AbstractService;
import com.jun.plugin.springboot.tools.system.mapper.SysDictItmeMapper;
import com.jun.plugin.springboot.tools.system.mapper.SysUserMapper;
import com.jun.plugin.springboot.tools.system.model.SysDictItem;
import com.jun.plugin.springboot.tools.system.model.SysUser;

import java.util.List;

@Service
public class SysDictItemService extends AbstractService<SysDictItem> {
    @Autowired(required = false)
    private SysDictItmeMapper sysDictItmeMapper;

    public List<SysDictItem> selectItemsByMainId(String mainId) {
        return sysDictItmeMapper.selectItemsByMainId(mainId);
    }
}
