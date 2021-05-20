package com.lhb.lhbauth.jwt.demo.authentication.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Wujun
 * @description
 * @date 2019/1/2 0002 10:56
 */
public interface ValidateCodeGenerator {

    ValidateCode createImageCode(HttpServletRequest request, HttpServletResponse response);
}
