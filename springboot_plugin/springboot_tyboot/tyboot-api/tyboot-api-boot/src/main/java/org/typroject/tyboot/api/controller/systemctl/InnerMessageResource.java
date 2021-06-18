package org.typroject.tyboot.api.controller.systemctl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.typroject.tyboot.api.face.systemctl.service.InnerMessageService;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.enumeration.UserType;
import org.typroject.tyboot.core.restful.doc.TycloudOperation;
import org.typroject.tyboot.core.restful.doc.TycloudResource;
import org.typroject.tyboot.core.restful.utils.ResponseHelper;
import org.typroject.tyboot.core.restful.utils.ResponseModel;

/**
 * <p>
 * 内部消息 前端控制器
 * </p>
 *
 * @author Wujun
 * @since 2018-12-12
 */

@RestController
@TycloudResource(module = "systemctl", value = "innermessage")
@RequestMapping(value = "/v1/systemctl/innermessage")
@Api(tags = "systemctl-内部消息")


public class InnerMessageResource {


    private final Logger logger = LogManager.getLogger(FeedbackResource.class);

    @Autowired
    private InnerMessageService innerMessageService;


    @TycloudOperation(ApiLevel = UserType.PUBLIC)
    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/public/page", method = RequestMethod.GET)
    public ResponseModel<Page> queryForInnerMessagePage(
            @RequestParam(value = "messageType", required = false) String messageType,
            @RequestParam(value = "current") int current,
            @RequestParam(value = "size") int size) throws Exception {
        Page page = new Page(current, size);
        return ResponseHelper.buildResponse(innerMessageService.queryForInnerMessagePage(page, RequestContext.getExeUserId(), messageType));
    }
}
