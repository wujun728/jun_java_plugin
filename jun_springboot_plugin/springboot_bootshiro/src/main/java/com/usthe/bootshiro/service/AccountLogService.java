package com.usthe.bootshiro.service;

import com.usthe.bootshiro.domain.bo.AuthAccountLog;

import java.util.List;

/* *
 * @author Wujun
 * @Description 
 * @Date 9:30 2018/4/22
 */
public interface AccountLogService {

    List<AuthAccountLog> getAccountLogList();
}
