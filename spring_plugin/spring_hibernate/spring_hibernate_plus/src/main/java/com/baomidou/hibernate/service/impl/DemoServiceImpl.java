package com.baomidou.hibernate.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.hibernate.po.Demo;
import com.baomidou.hibernate.vo.DemoVO;
import com.baomidou.hibernate.service.DemoService;
import com.baomidou.hibernateplus.service.impl.ServiceImpl;

@Service
public class DemoServiceImpl extends ServiceImpl<Demo, DemoVO> implements DemoService {

}
