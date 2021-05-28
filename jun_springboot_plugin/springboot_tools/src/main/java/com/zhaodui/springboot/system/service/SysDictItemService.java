package com.zhaodui.springboot.system.service;

import com.zhaodui.springboot.common.service.AbstractService;
import com.zhaodui.springboot.system.mapper.SysDictItmeMapper;
import com.zhaodui.springboot.system.mapper.SysUserMapper;
import com.zhaodui.springboot.system.model.SysDictItem;
import com.zhaodui.springboot.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictItemService extends AbstractService<SysDictItem> {
    @Autowired(required = false)
    private SysDictItmeMapper sysDictItmeMapper;

    public List<SysDictItem> selectItemsByMainId(String mainId) {
        return sysDictItmeMapper.selectItemsByMainId(mainId);
    }
}
