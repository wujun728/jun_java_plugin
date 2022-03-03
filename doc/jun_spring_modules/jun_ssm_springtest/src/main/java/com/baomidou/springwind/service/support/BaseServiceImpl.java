package com.baomidou.springwind.service.support;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

}
