package com.huan.study.cloud.alibaba.product.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huan.fu 2020/11/22 - 14:01
 */
@Component
public class CustomBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        String errMsg = null;
        if (e instanceof AuthorityException) {
            errMsg = "授权规则不通过";
        } else if (e instanceof DegradeException) {
            errMsg = "触发了降级规则";
        } else if (e instanceof ParamFlowException) {
            errMsg = "触发了热点参数降级规则";
        } else if (e instanceof FlowException) {
            errMsg = "触发了流控规则";
        } else if (e instanceof SystemBlockException) {
            errMsg = "触发了系统降级规则";
        } else {
            errMsg = "触发失败";
        }

        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        new ObjectMapper().writeValue(response.getWriter(), errMsg);
    }
}
