package org.typroject.tyboot.api.face.systemctl.service;

import org.springframework.stereotype.Service;
import org.typroject.tyboot.api.face.systemctl.model.LocationInfoModel;
import org.typroject.tyboot.api.face.systemctl.orm.dao.LocationInfoMapper;
import org.typroject.tyboot.api.face.systemctl.orm.entity.LocationInfo;
import org.typroject.tyboot.component.cache.Redis;
import org.typroject.tyboot.component.cache.enumeration.CacheType;
import org.typroject.tyboot.core.rdbms.service.BaseService;

import java.util.List;

/**
 * Created by magintursh on 2017-06-21.
 */
@Service("locationInfoService")
public class LocationInfoService extends BaseService<LocationInfoModel,LocationInfo,LocationInfoMapper> {



    public List<LocationInfoModel> getByParent(String  parentCode) throws Exception
    {
        //return this.queryForListWithCache(genCacheKeyForModelList(parentCode),null,false,parentCode);
        return this.queryForList(null,false,parentCode);
    }


    public LocationInfoModel getByCode(String  locationCode) throws Exception
    {
        LocationInfoModel model = queryModelByParamsWithCache(locationCode);
        return model;
    }




}
