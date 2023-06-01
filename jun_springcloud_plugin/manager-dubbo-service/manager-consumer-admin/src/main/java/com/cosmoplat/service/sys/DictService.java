package com.cosmoplat.service.sys;

import com.cosmoplat.entity.sys.SysDict;
import com.cosmoplat.entity.sys.vo.req.SysDictReqVO;

import java.util.HashMap;
import java.util.List;

/**
 * 字典管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface DictService {

    /**
     * 添加字典
     *
     * @param sysDict sysDict
     */
    void add(SysDict sysDict);

    /**
     * 修改字典
     *
     * @param sysDict sysDict
     */
    void update(SysDict sysDict);

    /**
     * 删除字典
     *
     * @param id id
     */
    void delete(String id);

    /**
     * 树形所有
     *
     * @return
     */
    List<SysDict> tree();

    /**
     * 根据类型跟编码获取字典
     *
     * @param type 类型
     * @param code 字典编码（对应父级字典值）
     * @return list
     */
    List<SysDict> getDictValue(String type, String code);


    /**
     * 根据类型跟编码获取字典
     *
     * @param dictReqs dictReqs
     * @return list list
     */
    List<HashMap<String, Object>> getDictValues(List<SysDictReqVO> dictReqs);
}
