package org.typroject.tyboot.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.enumeration.UserType;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.restful.doc.TycloudOperation;
import org.typroject.tyboot.core.restful.doc.TycloudResource;
import org.typroject.tyboot.core.restful.exception.instance.BadRequest;
import org.typroject.tyboot.core.restful.utils.ResponseHelper;
import org.typroject.tyboot.core.restful.utils.ResponseModel;
import org.typroject.tyboot.demo.face.model.PublicUserInfoModel;
import org.typroject.tyboot.demo.face.service.PublicUserInfoService;

import java.util.List;

@RestController
@TycloudResource(module = "demo", value = "demo")
@RequestMapping(value = "/v1/demo")
@Api(tags ="demo-示例")
public class TybootDemoResource {


    @Autowired
    private PublicUserInfoService publicUserInfoService;

    @TycloudOperation(ApiLevel = UserType.PUBLIC,needAuth = false)
    @ApiOperation(value = "创建数据")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseModel<PublicUserInfoModel> createUser(@RequestBody PublicUserInfoModel model) throws Exception {
        if(ValidationUtil.isEmpty(model) || ValidationUtil.isEmpty(model.getMobile()))
            throw new BadRequest("数据校验失败.");
        RequestContext.setExeUserId("system");//未做用户认证，拿不到当前用户信息，手动填充。
        return ResponseHelper.buildResponse(publicUserInfoService.createUser(model));
    }


    @TycloudOperation(ApiLevel = UserType.PUBLIC,needAuth = false)
    @ApiOperation(value = "更新数据")
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseModel<PublicUserInfoModel> updateUser(@PathVariable("userId") String  userId,@RequestBody PublicUserInfoModel model) throws Exception {
        RequestContext.setExeUserId("system");//未做用户认证，拿不到当前用户信息，手动填充。
        model.setUserId(userId);
        return ResponseHelper.buildResponse(publicUserInfoService.updateUser(model));
    }


    @TycloudOperation(ApiLevel = UserType.PUBLIC,needAuth = false)
    @ApiOperation(value = "查询单个数据对象")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseModel<PublicUserInfoModel> queryByUserId(@PathVariable("userId") String  userId) throws Exception {
        return ResponseHelper.buildResponse(publicUserInfoService.queryByUserId(userId));
    }


    @TycloudOperation(ApiLevel = UserType.PUBLIC,needAuth = false)
    @ApiOperation(value = "查询数据列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseModel<List<PublicUserInfoModel>> queryByAgencyCode(@RequestParam("agencyCode") String  agencyCode) throws Exception {
        return ResponseHelper.buildResponse(publicUserInfoService.queryByAgencyCode(agencyCode));
    }


    @TycloudOperation(ApiLevel = UserType.PUBLIC,needAuth = false)
    @ApiOperation(value = "分页查询数据列表")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseModel<Page<PublicUserInfoModel>> queryUserPage(
            @RequestParam(value = "agencyCode",required = false) String  agencyCode,
            @RequestParam(value = "nickName",required = false) String  nickName,
            @RequestParam(value = "current") int current,
            @RequestParam(value = "size") int size) throws Exception {
        Page<PublicUserInfoModel> page = new Page(current,size);
        return ResponseHelper.buildResponse(publicUserInfoService.queryUserPage(page,agencyCode,nickName));
    }
}
