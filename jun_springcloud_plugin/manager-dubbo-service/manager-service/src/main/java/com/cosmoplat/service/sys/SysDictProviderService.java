package com.cosmoplat.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cosmoplat.entity.sys.SysDict;
import com.cosmoplat.entity.sys.vo.req.SysDictReqVO;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author manager
 * @since 2020-11-11
 */
public interface SysDictProviderService extends IService<SysDict> {

    /**
     * 根据类型跟编码获取字典
     *
     * @param type 类型
     * @param code 字典编码（对应父级字典值）
     * @return list
     */
    List<SysDict> getDictValue(String type, String code);

    /**
     * 如果修改了字典值， 那么child也需要关联修改parentCode字段
     *
     * @param id        id
     * @param dictValue dictValue
     */
    void updateChildParentCode(String id, String dictValue);

    /**
     * 根据类型跟编码获取字典
     *
     * @param dictReqs dictReqs
     * @return list list
     */
    List<HashMap<String, Object>> getDictValues(List<SysDictReqVO> dictReqs);
}
