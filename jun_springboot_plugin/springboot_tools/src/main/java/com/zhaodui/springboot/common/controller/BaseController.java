package com.zhaodui.springboot.common.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 基础控制层
 *
 * @author Wujun
 */
public abstract class BaseController {
    @Autowired
    protected HttpSession session;
    @Autowired
    protected HttpServletRequest request;
}
