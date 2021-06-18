package org.typroject.tyboot.api.face.systemctl.service;

import org.springframework.stereotype.Component;
import org.typroject.tyboot.api.face.systemctl.model.DictionarieValueModel;
import org.typroject.tyboot.api.face.systemctl.orm.dao.DictionarieValueMapper;
import org.typroject.tyboot.api.face.systemctl.orm.entity.DictionarieValue;
import org.typroject.tyboot.component.cache.Redis;
import org.typroject.tyboot.core.rdbms.service.BaseService;

import java.util.List;

/**
 * <p>
 * 系统字典表 服务类
 * </p>
 *
 * @author Wujun
 * @since 2017-06-27
 */
@Component
public class DictionarieValueService extends BaseService<DictionarieValueModel,DictionarieValue,DictionarieValueMapper>  {



    public DictionarieValueModel queryValueByCodeAndKey(String agencyCode,String dictCode,String dictDataKey) throws Exception
    {
        return this.queryModelByParamsWithCache(agencyCode,dictCode,dictDataKey);
    }


    public List<DictionarieValueModel> queryByDictCode(String agencyCode,String dictCode) throws Exception
    {
        return this.queryForListWithCache("order_Num",true,agencyCode,dictCode);
    }





}
