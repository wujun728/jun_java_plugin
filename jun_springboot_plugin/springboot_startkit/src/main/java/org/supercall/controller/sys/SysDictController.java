package org.supercall.controller.sys;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.supercall.common.CommonUtility;
import org.supercall.dao.SysDictMapper;
import org.supercall.dao.SysDictMapperExtend;
import org.supercall.domainModel.ResultMessage;
import org.supercall.models.SysDict;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/admin/sys")
@Api(value = "系统配置")
@RestController
public class SysDictController {
    static Logger logger = Logger.getLogger(SysDictController.class);

    @Autowired
    SysDictMapper sysDictMapper;

    @Autowired
    SysDictMapperExtend sysDictMapperExtend;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    @ApiOperation(value = "系统配置列表")
    HashMap<String, Object> index(
            HttpServletRequest request,
            @ApiParam(name = "limit", value = "每页记录数")
            @RequestParam(name = "limit", required = false)
                    String limit,
            @ApiParam(name = "offset", value = "页数")
            @RequestParam(name = "offset", required = false)
                    String offset) {
        return null;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增系统配置")
    ResultMessage add(
            @ApiParam(name = "sysDict", value = "系统配置实体")
                    SysDict sysDict
    ) {
        try {
            sysDictMapper.insertSelective(sysDict);
            return new ResultMessage(true, "新增系统配置成功", "");
        } catch (Exception ex) {
            return new ResultMessage(false, "新增系统配置失败", ex.getMessage());
        }
    }


}
