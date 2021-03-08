package com.lxx.springboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxx.springboot.meta.po.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *   Mapper 接口
 * </p>
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}
