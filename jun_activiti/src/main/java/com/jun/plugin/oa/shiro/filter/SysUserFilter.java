/*
 * Copyright (c) 2012-2032 Accounting Center of China Aviation(ACCA).
 * All Rights Reserved.
 */
package com.jun.plugin.oa.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.jun.plugin.oa.entity.User;
import com.jun.plugin.oa.service.IUserService;
import com.jun.plugin.oa.util.Constants;
/**
 * 每次把用户信息放入request中，暂时没用。
 * @author ZML
 *
 */

public class SysUserFilter extends PathMatchingFilter {
    
    @Autowired
	private IUserService userService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response,
                                  Object mappedValue) throws Exception {

        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = this.userService.getUserByName(username);
        request.setAttribute(Constants.CURRENT_USER, user);
        return true;
    }
}
