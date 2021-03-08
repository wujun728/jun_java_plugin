package com.zh.springbootexception.service.impl;

import com.zh.springbootexception.dto.Result;
import com.zh.springbootexception.enums.AppResultCode;
import com.zh.springbootexception.exception.BusinessException;
import com.zh.springbootexception.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author Wujun
 * @date 2019/6/5
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public Result test(Integer index) {
        if (index == 0){
            throw new BusinessException(AppResultCode.USER_NOT_EXIST);
        }else if (index == 1){
            throw new RuntimeException("其他异常!");
        }else{
            return Result.genSuccessResult();
        }
    }
}
