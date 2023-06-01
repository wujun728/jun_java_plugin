package com.cosmoplat.provider.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cosmoplat.entity.sys.SysDict;
import com.cosmoplat.entity.sys.vo.req.SysDictReqVO;
import com.cosmoplat.provider.mapper.SysDictMapper;
import com.cosmoplat.service.sys.SysDictProviderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author manager
 * @since 2020-11-11
 */
@DubboService
@Slf4j
public class SysDictProviderServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictProviderService {

    @Override
    public List<SysDict> getDictValue(String type, String code) {
        return baseMapper.selectList(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getDictType, type).eq(SysDict::getParentCode, code));
    }

    @Override
    public void updateChildParentCode(String id, String dictValue) {
        baseMapper.update(null, Wrappers.<SysDict>lambdaUpdate().eq(SysDict::getParentId, id).set(SysDict::getParentCode, dictValue));
    }

    @Override
    public List<HashMap<String, Object>> getDictValues(List<SysDictReqVO> dictReqs) {
        return dictReqs.stream().map(one -> {
            //根据code和type获取字典列表
            List<SysDict> oneList = baseMapper.selectList(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getDictType, one.getType()).eq(SysDict::getParentCode, one.getCode()).orderByAsc(SysDict::getSort));
            HashMap<String, Object> map = new HashMap<>();
            map.put("code", one.getCode());
            map.put("type", one.getType());
            //判断values是否为空
            if (CollectionUtils.isNotEmpty(oneList)) {
                map.put("values", oneList);
            } else {
                map.put("values", new ArrayList<>());
            }
            return map;
        }).collect(Collectors.toList());
    }
}
