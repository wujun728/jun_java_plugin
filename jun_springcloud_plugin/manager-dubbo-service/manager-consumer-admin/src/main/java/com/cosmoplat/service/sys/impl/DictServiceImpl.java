package com.cosmoplat.service.sys.impl;

import com.cosmoplat.common.exception.BusinessException;
import com.cosmoplat.common.utils.Constant;
import com.cosmoplat.entity.sys.SysDict;
import com.cosmoplat.entity.sys.vo.req.SysDictReqVO;
import com.cosmoplat.service.sys.CommonProviderService;
import com.cosmoplat.service.sys.DictService;
import com.cosmoplat.service.sys.HttpSessionService;
import com.cosmoplat.service.sys.SysDictProviderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 字典管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
public class DictServiceImpl implements DictService {

    @DubboReference
    SysDictProviderService sysDictProviderService;
    @DubboReference
    CommonProviderService commonProviderService;
    @Resource
    HttpSessionService httpSessionService;
    
    private String dictTypeStr = "dict_type";
    private String deletedStr = "deleted";
    private String sysDictStr = "sys_dict";

    @Override
    public void add(SysDict sysDict) {
        if (!"0".equals(sysDict.getParentId())) {
            SysDict sysDictParent = sysDictProviderService.getById(sysDict.getParentId());
            //如果不是空，查询父级，生成id
            if (sysDictParent == null) {
                throw new BusinessException("根据父级编号获取父级失败！");
            }
            sysDict.setParentCode(sysDictParent.getDictValue());
            sysDict.setDictType(sysDictParent.getDictType());
        } else {
            if (StringUtils.isBlank(sysDict.getDictType())) {
                throw new BusinessException("字典类型不能为空！");
            }
            //判断二级的type不能重复
            Map<String, Object> param = new HashMap<>();
            param.put("parent_id", "0");
            param.put(dictTypeStr, sysDict.getDictType());
            param.put(deletedStr, Constant.DATA_NOT_DELETED);
            if (commonProviderService.isExistJoin(sysDictStr, param, null)) {
                throw new BusinessException("二级类型禁止重复！");
            }
            sysDict.setParentCode("0");
        }
        //判断type跟value是否重复
        Map<String, Object> param = new HashMap<>();
        param.put("dict_value", sysDict.getDictValue());
        param.put(dictTypeStr, sysDict.getDictType());
        param.put(deletedStr, Constant.DATA_NOT_DELETED);
        if (commonProviderService.isExistJoin(sysDictStr, param, null)) {
            throw new BusinessException("相同类型下字典值禁止重复！");
        }
        //创建人
        sysDict.setCreateId(httpSessionService.getCurrentUserId());
        sysDictProviderService.save(sysDict);
    }

    @Override
    public void update(SysDict sysDict) {
        SysDict sysDictOne = sysDictProviderService.getById(sysDict.getId());
        //如果不是空，查询父级，生成id
        if (sysDictOne == null) {
            throw new BusinessException("根据id获取字典失败！");
        }
        //parent不能修改
        sysDict.setParentId(null);
        sysDict.setParentCode(null);
        //如果父级不是0， 不允许需修改type
        if (!"0".equals(sysDictOne.getParentId())) {
            sysDict.setDictType(null);
        } else {
            //判断二级的type不能重复
            Map<String, Object> param = new HashMap<>();
            param.put("parent_id", "0");
            param.put(dictTypeStr, sysDict.getDictType());
            param.put(deletedStr, Constant.DATA_NOT_DELETED);
            if (commonProviderService.isExistJoin(sysDictStr, param, sysDict.getId())) {
                throw new BusinessException("二级类型禁止重复！");
            }
        }

        //判断type跟value是否重复
        Map<String, Object> param = new HashMap<>();
        param.put("dict_value", sysDict.getDictValue());
        param.put(dictTypeStr, sysDict.getDictType());
        param.put(deletedStr, Constant.DATA_NOT_DELETED);
        if (commonProviderService.isExistJoin(sysDictStr, param, sysDict.getId())) {
            throw new BusinessException("相同类型下字典值禁止重复！");
        }
        //创建人
        sysDict.setUpdateId(httpSessionService.getCurrentUserId());

        //如果修改值， 那么child的Code也需要关联修改
        if (StringUtils.isNotBlank(sysDict.getDictValue()) && !sysDict.getDictValue().equals(sysDictOne.getDictValue())) {
            sysDictProviderService.updateChildParentCode(sysDict.getId(), sysDict.getDictValue());
        }
        sysDictProviderService.updateById(sysDict);
    }

    @Override
    public void delete(String id) {
        //删除自己及子集
        List<SysDict> list = sysDictProviderService.list();
        List<String> removeIds = new ArrayList<>();
        removeIds.add(id);
        //递归获取需要删除的ids
        getChildId(id, list, removeIds);
        sysDictProviderService.removeByIds(removeIds);

    }

    @Override
    public List<SysDict> tree() {
        // 默认加一个顶级方便新增
        SysDict sysDict = new SysDict();
        sysDict.setId("0");
        sysDict.setDictValue("0");
        sysDict.setDictName("默认顶级");
        sysDict.setDictType("default_type");
        sysDict.setChildren(getTree(sysDictProviderService.list()));
        return Collections.singletonList(sysDict);
    }

    @Override
    public List<SysDict> getDictValue(String type, String code) {
        return sysDictProviderService.getDictValue(type, code);
    }

    @Override
    public List<HashMap<String, Object>> getDictValues(List<SysDictReqVO> dictReqs) {
        return sysDictProviderService.getDictValues(dictReqs);
    }

    private List<SysDict> getTree(List<SysDict> all) {
        List<SysDict> list = new ArrayList<>();
        for (SysDict sysDict : all) {
            if ("0".equals(sysDict.getParentId())) {
                sysDict.setParentName("默认顶级");
                sysDict.setChildren(getChild(sysDict.getId(), all, sysDict.getDictName()));
                list.add(sysDict);
            }
        }
        return list;
    }

    private List<SysDict> getChild(String id, List<SysDict> all, String parentName) {
        List<SysDict> list = new ArrayList<>();
        for (SysDict sysDict : all) {
            if (sysDict.getParentId().equals(id)) {
                sysDict.setParentName(parentName);
                sysDict.setChildren(getChild(sysDict.getId(), all, sysDict.getDictName()));
                list.add(sysDict);
            }
        }
        return list;
    }

    /**
     * 获取子id
     *
     * @param id       父id
     * @param all      所有
     * @param childIds 子idList
     */
    private void getChildId(String id, List<SysDict> all, List<String> childIds) {
        for (SysDict sysDict : all) {
            if (sysDict.getParentId().equals(id)) {
                childIds.add(sysDict.getId());
                getChildId(sysDict.getId(), all, childIds);
            }
        }
    }
}
