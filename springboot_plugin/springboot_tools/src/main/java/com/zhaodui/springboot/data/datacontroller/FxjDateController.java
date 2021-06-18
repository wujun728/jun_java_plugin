package com.zhaodui.springboot.data.datacontroller;

import com.alibaba.druid.util.StringUtils;
import com.zhaodui.springboot.common.controller.BaseController;
import com.zhaodui.springboot.common.mdoel.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wujun
 */
@Slf4j
@RestController
@RequestMapping("/fxjDate")
@Api(tags="fxjDate")
public class FxjDateController extends BaseController {

    /**
     * 全部查询
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/list/{username}", method = RequestMethod.POST)
    @ApiOperation(value = "执行SQL", notes = "执行SQL")
    public Result<?> queryPageList(@PathVariable(name = "username") String username,
                                   @RequestParam(name = "outlenth", required = true) Integer outlenth,
                                   @RequestParam(name = "sql", required = false) String sql,
                                   @RequestParam(name = "para", required = false) String para,
                                   @RequestParam(name = "sqltype", required = true) String sqltype,
                                   HttpServletRequest req) {
        List<baseoutientity> reslit = new ArrayList<>();
        if(StringUtils.isEmpty(sql)){

            if("1".equals(sqltype)){
                sql = CTXDSQLCONSTANT.BASEINFO01;//个人资料   5
            }
            if("2".equals(sqltype)){
                sql = CTXDSQLCONSTANT.BASEINFO02;//企业资料  5
            }
            if("3".equals(sqltype)){
                sql = CTXDSQLCONSTANT.BASEINFO03;//保证人    9
            }
            if("11".equals(sqltype)){           //合同信息   11
                sql = CTXDSQLCONSTANT.BUSEINFO01;
            }
//            if("12".equals(sqltype)){
//                sql = CTXDSQLCONSTANT.BUSEINFO02;
//            }
//            if("13".equals(sqltype)){
//                sql = CTXDSQLCONSTANT.BUSEINFO03;
//            }
//            if("14".equals(sqltype)){
//                sql = CTXDSQLCONSTANT.BUSEINFO04;
//            }

            reslit = dbUtil.getbysql(sql, para, outlenth);

        }else{
              reslit = dbUtil.getbysql(sql, para, outlenth);

        }

        return Result.ok(reslit);
    }
}
