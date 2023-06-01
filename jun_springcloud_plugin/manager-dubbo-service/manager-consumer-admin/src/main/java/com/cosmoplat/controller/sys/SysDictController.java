package com.cosmoplat.controller.sys;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cosmoplat.common.aop.annotation.LogAnnotation;
import com.cosmoplat.common.exception.BusinessException;
import com.cosmoplat.common.utils.DataResult;
import com.cosmoplat.entity.sys.SysDict;
import com.cosmoplat.entity.sys.vo.req.SysDictReqVO;
import com.cosmoplat.service.sys.DictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 字典管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年11月11日
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {

    @Resource
    DictService dictService;

    @PostMapping("/add")
    @LogAnnotation(title = "字典管理", action = "新增字典")
    public DataResult add(@RequestBody @Valid SysDict sysDict) {
        dictService.add(sysDict);
        return DataResult.success();
    }

    @PostMapping("/delete/{id}")
//    @RequiresPermissions("sys:dict:deleted")
    @LogAnnotation(title = "字典管理", action = "删除字典")
    public DataResult delete(@PathVariable("id") String id) {
        dictService.delete(id);
        return DataResult.success();
    }

    @PostMapping("/update")
    @LogAnnotation(title = "字典管理", action = "修改字典")
    public DataResult update(@RequestBody @Valid SysDict sysDict) {
        if (StringUtils.isBlank(sysDict.getId())) {
            throw new BusinessException("字典id不能为空");
        }
        dictService.update(sysDict);
        return DataResult.success();
    }

    @GetMapping("/tree")
    public DataResult tree() {
        return DataResult.success(dictService.tree());
    }

    @GetMapping("/getDictValue")
    public DataResult getType(@RequestParam(required = false) String type, @RequestParam(required = false) String code) {
        if (StringUtils.isEmpty(type)) {
            return DataResult.fail("字典类型不能为空");
        }
        if (StringUtils.isEmpty(code)) {
            return DataResult.fail("字典编码不能为空");
        }
        //根据字典类型，编码（父级编码）查询
        List<SysDict> list = dictService.getDictValue(type, code);
        return DataResult.success(list);
    }

    /**
     * 同admin端的字典获取一样
     *
     * @param dictReqs list
     * @return DataResult
     */
    @PostMapping("/getDictValues")
    public DataResult getDictValues(@Valid @RequestBody List<SysDictReqVO> dictReqs) {
        if (CollectionUtils.isEmpty(dictReqs)) {
            return DataResult.success(new ArrayList<>());
        }
        //根据字典类型，编码（父级编码）查询
        List<HashMap<String, Object>> list = dictService.getDictValues(dictReqs);
        return DataResult.success(list);
    }
}
