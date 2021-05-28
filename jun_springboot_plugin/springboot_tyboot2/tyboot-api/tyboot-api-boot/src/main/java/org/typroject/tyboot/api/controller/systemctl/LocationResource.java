package org.typroject.tyboot.api.controller.systemctl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.typroject.tyboot.api.face.systemctl.model.LocationInfoModel;
import org.typroject.tyboot.api.face.systemctl.service.LocationInfoService;
import org.typroject.tyboot.core.foundation.enumeration.UserType;
import org.typroject.tyboot.core.restful.doc.TycloudOperation;
import org.typroject.tyboot.core.restful.doc.TycloudResource;
import org.typroject.tyboot.core.restful.utils.ResponseHelper;
import org.typroject.tyboot.core.restful.utils.ResponseModel;

import java.util.List;


/**
 * Created by magintursh on 2017-05-03.
 */

@TycloudResource(module = "systemctl",value = "LocationResource")
@RequestMapping(path = "/v1/systemctl/location")
@Api(tags = "systemctl-位置信息管理")
@RestController
public class LocationResource {
    private final Logger logger = LogManager.getLogger(LocationResource.class);


    @Autowired
    LocationInfoService locationInfoService;


    @TycloudOperation( ApiLevel = UserType.ANONYMOUS,needAuth = false)
    @ApiOperation(value="获取所有省份信息")
    @RequestMapping(value = "/provinces", method = RequestMethod.GET)
    public ResponseModel<List<LocationInfoModel>> getProvince() throws Exception {
        return ResponseHelper.buildResponse(locationInfoService.getByParent("0"));
    }


    @TycloudOperation(ApiLevel = UserType.ANONYMOUS)
    @ApiOperation(value="获得省辖区所有城市")
    @RequestMapping(value = "/{provinceCode}/cities", method = RequestMethod.GET)
    public ResponseModel<List<LocationInfoModel>> getCitiesByProvince(
            @PathVariable String provinceCode) throws Exception {
        return ResponseHelper.buildResponse(locationInfoService.getByParent(provinceCode));
    }

    @TycloudOperation( ApiLevel = UserType.ANONYMOUS)
    @ApiOperation(value="获得市辖区所有县区")
    @RequestMapping(value = "/{cityCode}/regions", method = RequestMethod.GET)
    public ResponseModel<List<LocationInfoModel>> getRegionsByCity(
            @PathVariable String cityCode) throws Exception {
        return ResponseHelper.buildResponse(locationInfoService.getByParent(cityCode));
    }

    @TycloudOperation( ApiLevel = UserType.ANONYMOUS,needAuth = false)
    @ApiOperation(value="根据位置编码获取位置信息")
    @RequestMapping(value = "/{locationCode}", method = RequestMethod.GET)
    public ResponseModel<LocationInfoModel> getByCode(@PathVariable String locationCode)
            throws Exception {
        return ResponseHelper.buildResponse(locationInfoService.getByCode(locationCode));
    }

    @TycloudOperation( ApiLevel = UserType.ANONYMOUS)
    @ApiOperation(value="获取所辖地区")
    @RequestMapping(value = "/{parentCode}/areas", method = RequestMethod.GET)
    public ResponseModel<List<LocationInfoModel>> getByParentCode(@PathVariable String parentCode)
            throws Exception {
        return ResponseHelper.buildResponse(locationInfoService.getByParent(parentCode));
    }

}